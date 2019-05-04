package com.facilitymanagement.DAL;

import com.facilitymanagement.Model.Inspection.InspectionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Repository
public class InspectionDAO {

    private ScheduleDAO scheduleDAO ;
    private FacilityDAO facilityDAO;
    private UserDAO userDAO;

    @Autowired
    public InspectionDAO(ScheduleDAO scheduleDAO, FacilityDAO facilityDAO, UserDAO userDAO) {
        this.scheduleDAO = scheduleDAO;
        this.facilityDAO = facilityDAO;
        this.userDAO = userDAO;
    }


    private int ID;

    public int getID() {
        return ID;
    }


    public void removeAllData() {
        try {
            Statement st = DB_Helper.getConnection().createStatement();
            String removeInsRequestQuery = "delete from InspectionRequest";
            String removeInsSchedule = "delete from schedule where requesttype = " + "'" + "Inspection" + "'";
            st.execute(removeInsRequestQuery);
            st.execute(removeInsSchedule);

            System.out.println("InspectionRequestDAO: *************** Query " + removeInsRequestQuery + "\n");
            st.close();
        } catch (SQLException se) {
            System.err.println("InspectionRequestDAO: Threw a SQLException removing the InspectionRequest object from InspectionRequest table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
    }

    public void removeInspectionRequest(InspectionRequest InsRequest) {
        try {
            Statement st = DB_Helper.getConnection().createStatement();
            String removeInsRequestQuery = "delete from InspectionRequest where scheduleID = '" + InsRequest.getRequest().getSchedule().getScheduleID() + "'";
            st.execute(removeInsRequestQuery);
            String removeScheduleQuery = "delete from schedule where scheduleID = '" + InsRequest.getRequest().getSchedule().getScheduleID() + "'";
            st.execute(removeScheduleQuery);
            System.out.println("InspectionRequest DAO: *************** Query " + removeInsRequestQuery + "\n");
            System.out.println("Schedule DAO: *************** Query " + removeScheduleQuery + "\n");
            st.close();
        } catch (SQLException se) {
            System.err.println("InspectionRequestDAO: Threw a SQLException removing the Inspection object from InspectionRequest table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

    }

    public void AddInsRequest(InspectionRequest InsRequest) {

            Connection con = DB_Helper.getConnection();
            PreparedStatement addIns = null;
            Random rand = new Random();

            try {
                //InsetMaintainenceService Object
                scheduleDAO.AssignFacilityToUse(InsRequest.getRequest().getSchedule());

                //Insert the inspection object
                String addReq = "INSERT INTO InspectionRequest(InspectionRequestID, FacilityID, ScheduleID, RoomNumber,InspectionDescription ,Equipmenttype  ,Equipmentcode ,userID ) VALUES(?, ?, ?, ?,?,?,?,?)";
                addIns = con.prepareStatement(addReq);
                addIns.setInt(1, InsRequest.getRequest().getRequestID());
                addIns.setInt(2, InsRequest.getRequest().getSchedule().getFacilityID());
                addIns.setInt(3, InsRequest.getRequest().getSchedule().getScheduleID());
                addIns.setInt(4, InsRequest.getRequest().getSchedule().getRoomNumber());
                addIns.setString(5, InsRequest.getRequest().getRequestDescription());
                addIns.setString(6,InsRequest.getRequest().getTechnology().ListoftechType());
                addIns.setString(7,InsRequest.getRequest().getTechnology().ListoftechCode());
                addIns.setInt(8, InsRequest.getRequest().getiUser().getUserID());





                addIns.executeUpdate();


            } catch (SQLException ex) {
                System.out.println(ex);

            } finally {

                try {
                    if (addIns != null) {
                        addIns.close();
                    }
                    if (con != null) {
                        con.close();
                    }

                } catch (SQLException ex) {
                    System.err.println("FacilityDAO: Threw a SQLException saving the InsRequest object.");
                    System.err.println(ex.getMessage());
                }

            }


        }


    public boolean isInUseDuringInterval(InspectionRequest ins) {
        if (!scheduleDAO.isInUseDuringInterval(ins.getRequest().getSchedule())){
            return false;
        }
        ID = scheduleDAO.getID();
        return true;

    }



    public void VacateInsFacility(InspectionRequest ins) {
        scheduleDAO.VacateFacility(ins.getRequest().getSchedule());
    }

    public InspectionRequest getInfobyID(int ID, InspectionRequest inspectionRequest) {
        try {

            Statement st = DB_Helper.getConnection().createStatement();
            String selectDetailQuery = "SELECT *  FROM inspectionrequest,Schedule WHERE Schedule.ScheduleID= '" + ID + "' AND inspectionrequest.ScheduleID = Schedule.ScheduleID ";
            ResultSet detRS = st.executeQuery(selectDetailQuery);

            return achieveinspection(detRS, selectDetailQuery,inspectionRequest);
        }

        catch (SQLException se) {
            System.err.println("FacilityDAO: Threw a SQLException retrieving the Facility object.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        return null;
    }

    private InspectionRequest achieveinspection(ResultSet detRS, String selectDetailQuery, InspectionRequest inspectionRequest) {
        try {






            System.out.println("inspectionDAO: *************** Query " + selectDetailQuery + "\n");

            while ( detRS.next() ) {
                inspectionRequest.getRequest().setRequestID(detRS.getInt("inspectionrequestid"));
                inspectionRequest.getRequest().setRequestDescription(detRS.getString("inspectiondescription"));

                String code = detRS.getString("equipmentcode");
                String type = detRS.getString("equipmenttype");
                inspectionRequest.getRequest().getTechnology().setTechnologytype( new ArrayList<String>(Arrays.asList(type.split(","))));
                List<String> listcode = new ArrayList<String>(Arrays.asList(code.split(" ,")));
                ArrayList<Integer> codereal = new ArrayList<Integer>();
                for(String s : listcode) codereal.add(Integer.valueOf(s));
                inspectionRequest.getRequest().getTechnology().setTechnologycode(codereal);

                inspectionRequest.getRequest().setiUser(userDAO.getInformationByuserID(detRS.getInt("UserID")));

                inspectionRequest.getRequest().getSchedule().setRequestType("requesttype");
                inspectionRequest.getRequest().getSchedule().setFacility(facilityDAO.getFacilityInformationByFacilityID(detRS.getInt("facilityid")));
                inspectionRequest.getRequest().getSchedule().setScheduleID(detRS.getInt("scheduleid"));
                inspectionRequest.getRequest().getSchedule().setRoomNumber(detRS.getInt("roomnumber"));
                inspectionRequest.getRequest().getSchedule().setStartDate(detRS.getDate("startdate").toLocalDate());
                inspectionRequest.getRequest().getSchedule().setEndDate(detRS.getDate("enddate").toLocalDate());
                if (!(detRS.getString("UserIDList").equals( "none"))) {
                    String subscriber = detRS.getString("useridlist");
                    List<String> userid = new ArrayList<String>(Arrays.asList(subscriber.split(",")));
                    ArrayList<Integer> useridint = new ArrayList<Integer>();
                    for(String s : userid) useridint.add(Integer.valueOf(s));
                    for (int m : useridint) inspectionRequest.getRequest().getSchedule().getObservable().addObserver(userDAO.getInformationByuserID(m));

                }

            }



            //close to manage resources
            detRS.close();

            return inspectionRequest;
        } catch (SQLException se) {
            System.err.println("inspectionDAO: Threw a SQLException retrieving the request object.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        return null;

    }

}
