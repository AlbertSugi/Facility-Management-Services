package com.facilitymanagement.DAL;


import com.facilitymanagement.Model.Maintainence.MaintainenceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Repository
public class MaintainenceDAO {


        private ScheduleDAO scheduleDAO; //Spring
        private UserDAO userDAO;
        private FacilityDAO facilityDAO;
        private int ID;

    @Autowired
    public void setScheduleDAO(ScheduleDAO scheduleDAO) {
        this.scheduleDAO = scheduleDAO;
    }
    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    @Autowired
    public void setFacilityDAO(FacilityDAO facilityDAO) {
        this.facilityDAO = facilityDAO;
    }

    public int getID() {
            return ID;
        }




        public void removeAllData() {
            try {
                Statement st = DB_Helper.getConnection().createStatement();
                String removeUseRequestQuery = "delete from MaintainenceRequest";
                String removeUseSchedule = "delete from schedule where requesttype = " + "'" + "Maintainence" + "'";
                st.execute(removeUseRequestQuery);
                st.execute(removeUseSchedule);

                System.out.println("MaintainenceRequest DAO: *************** Query " + removeUseRequestQuery + "\n");
                st.close();
            } catch (SQLException se) {
                System.err.println("MaintainenceRequestDAO: Threw a SQLException removing the MaintainenceRequest object from MaintainanceRequest table.");
                System.err.println(se.getMessage());
                se.printStackTrace();
            }
        }

        public void removeMaintainenceRequest(MaintainenceRequest MainRequest) {
            try {
                Statement st = DB_Helper.getConnection().createStatement();
                String removeMaintRequestQuery = "delete from MaintainenceRequest where scheduleID = '" + MainRequest.getRequest().getSchedule().getScheduleID() + "'";
                st.execute(removeMaintRequestQuery);
                String removeScheduleQuery = "delete from schedule where scheduleID = '" + MainRequest.getRequest().getSchedule().getScheduleID() + "'";
                st.execute(removeScheduleQuery);
                System.out.println("MaintainenceRequest DAO: *************** Query " + removeMaintRequestQuery + "\n");
                System.out.println("Schedule DAO: *************** Query " + removeScheduleQuery + "\n");
                st.close();
            } catch (SQLException se) {
                System.err.println("MaintainenceRequestDAO: Threw a SQLException removing the Maintainence object from MaintainenceRequest table.");
                System.err.println(se.getMessage());
                se.printStackTrace();
            }

        }

        public void AddmaintRequest(MaintainenceRequest MainRequest) {


                Connection con = DB_Helper.getConnection();
                PreparedStatement addMain = null;
                Random rand = new Random(); //Spring ?

                try {
                    //InsetMaintainenceService Object
                    scheduleDAO.AssignFacilityToUse(MainRequest.getRequest().getSchedule());
                    //System.out.println(MainRequest.getMaintainence().getScheduleID());

                    //Insert the Mainaintence object
                    String addReq = "INSERT INTO MaintainenceRequest(MaintainenceRequestID, FacilityID, ScheduleID, RoomNumber,MaintainenceDescription ,Cost ,TechType  ,TechCodes  ,userid ) VALUES(?, ?, ?, ?,?,?,?,?,?)";
                    addMain = con.prepareStatement(addReq);
                    addMain.setInt(1, MainRequest.getRequest().getRequestID());
                    addMain.setInt(2, MainRequest.getRequest().getSchedule().getFacilityID());
                    addMain.setInt(3, MainRequest.getRequest().getSchedule().getScheduleID());
                    addMain.setInt(4, MainRequest.getRequest().getSchedule().getRoomNumber());
                    addMain.setString(5, MainRequest.getRequest().getRequestDescription());
                    addMain.setInt(6,MainRequest.getCost());
                    addMain.setString(7,MainRequest.getRequest().getTechnology().ListoftechType());
                    addMain.setString(8, MainRequest.getRequest().getTechnology().ListoftechCode());
                    addMain.setInt(9,MainRequest.getRequest().getiUser().getUserID());


                    addMain.executeUpdate();


                } catch (SQLException ex) {
                    System.out.println(ex);

                } finally {

                    try {
                        if (addMain != null) {
                            addMain.close();
                        }
                        if (con != null) {
                            con.close();
                        }

                    } catch (SQLException ex) {
                        System.err.println("MaintainenceRequestDAO: Threw a SQLException saving the MainRequest object.");
                        System.err.println(ex.getMessage());
                    }

                }


            }



    public boolean isInUseDuringInterval(MaintainenceRequest ins) {
        if (!scheduleDAO.isInUseDuringInterval(ins.getRequest().getSchedule())){
            return false;
        }
        ID = scheduleDAO.getID();
        return true;

    }
    public void VacateInsFacility(MaintainenceRequest ins) {
        scheduleDAO.VacateFacility(ins.getRequest().getSchedule());
    }

    public MaintainenceRequest getInfobyID(int ID, MaintainenceRequest maintainenceRequest) {
        try {

            Statement st = DB_Helper.getConnection().createStatement();
            String selectDetailQuery = "SELECT *  FROM MaintainenceRequest,Schedule WHERE Schedule.ScheduleID= '" + ID + "' AND MaintainenceRequest.ScheduleID = Schedule.ScheduleID ";
            ResultSet detRS = st.executeQuery(selectDetailQuery);

            return achievemaintanience(detRS, selectDetailQuery,maintainenceRequest);
        }

        catch (SQLException se) {
            System.err.println("FacilityDAO: Threw a SQLException retrieving the Facility object.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        return null;
    }

    private MaintainenceRequest achievemaintanience(ResultSet detRS, String selectDetailQuery,MaintainenceRequest maintainenceRequest) {
        try {





            System.out.println("MaintenanceDAO: *************** Query " + selectDetailQuery + "\n");

            while ( detRS.next() ) {
                maintainenceRequest.getRequest().setRequestID(detRS.getInt("MaintainenceRequestID"));
                maintainenceRequest.setCost(detRS.getInt("cost"));
                maintainenceRequest.getRequest().setRequestDescription(detRS.getString("maintainencedescription"));

                String code = detRS.getString("TechCodes");
                String type = detRS.getString("TechType");
                maintainenceRequest.getRequest().getTechnology().setTechnologytype(new ArrayList<String>(Arrays.asList(type.split(","))));
                List<String> listcode = new ArrayList<String>(Arrays.asList(code.split(" ,")));
                ArrayList<Integer> codereal = new ArrayList<Integer>();
                for (String s : listcode) codereal.add(Integer.valueOf(s));
                maintainenceRequest.getRequest().getTechnology().setTechnologycode(codereal);

                maintainenceRequest.getRequest().setiUser(userDAO.getInformationByuserID(detRS.getInt("userID")));
                maintainenceRequest.getRequest().getSchedule().setRequestType("requesttype");
                maintainenceRequest.getRequest().getSchedule().setFacility(facilityDAO.getFacilityInformationByFacilityID(detRS.getInt("facilityid")));
                maintainenceRequest.getRequest().getSchedule().setScheduleID(detRS.getInt("scheduleid"));
                maintainenceRequest.getRequest().getSchedule().setRoomNumber(detRS.getInt("roomnumber"));
                maintainenceRequest.getRequest().getSchedule().setStartDate(detRS.getDate("startdate").toLocalDate());
                maintainenceRequest.getRequest().getSchedule().setEndDate(detRS.getDate("enddate").toLocalDate());
                if (!(detRS.getString("UserIDList").equals("none"))) {
                    String subscriber = detRS.getString("useridlist");
                    List<String> userid = new ArrayList<String>(Arrays.asList(subscriber.split(",")));
                    ArrayList<Integer> useridint = new ArrayList<Integer>();
                    for (String s : userid) useridint.add(Integer.valueOf(s));
                    for (int m : useridint)
                        maintainenceRequest.getRequest().getSchedule().getObservable().addObserver(userDAO.getInformationByuserID(m));
                }
            }



            //close to manage resources
            detRS.close();

            return maintainenceRequest;
        } catch (SQLException se) {
            System.err.println("maintenanceDAO: Threw a SQLException retrieving the request object.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        return null;

    }


}


