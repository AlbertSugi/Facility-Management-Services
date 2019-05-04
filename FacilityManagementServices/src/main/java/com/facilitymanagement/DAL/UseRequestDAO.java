package com.facilitymanagement.DAL;



import com.facilitymanagement.Model.Use.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Repository
public class UseRequestDAO {

    private ScheduleDAO scheduleDAO; //Spring
    private UserDAO userDAO;
    private FacilityDAO facilityDAO;
    private int ID;

    @Autowired
    public UseRequestDAO(ScheduleDAO scheduleDAO, UserDAO userDAO, FacilityDAO facilityDAO) {
        this.scheduleDAO = scheduleDAO;
        this.userDAO = userDAO;
        this.facilityDAO = facilityDAO;
    }

    public int getID() {
        return ID;
    }


    public void removeAllData() {
        try {
            Statement st = DB_Helper.getConnection().createStatement();
            String removeUseRequestQuery = "delete from userequest";
            String removeUseSchedule = "delete from schedule where requesttype = " + "'" + "Use" + "'";
            st.execute(removeUseRequestQuery);
            st.execute(removeUseSchedule);

            System.out.println("UseRequest DAO: *************** Query " + removeUseRequestQuery + "\n");
            st.close();
        } catch (SQLException se) {
            System.err.println("UseRequestDAO: Threw a SQLException removing the UseRequest object from UseRequest table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
    }

    public void removeUseRequest(UserRequest useRequest) {
        try {
            Statement st = DB_Helper.getConnection().createStatement();
            String removeUseRequestQuery = "delete from userequest where scheduleID = '" + useRequest.getRequest().getSchedule().getScheduleID() + "'";
            st.execute(removeUseRequestQuery);
            String removeScheduleQuery = "delete from schedule where scheduleID = '" + useRequest.getRequest().getSchedule().getScheduleID() + "'";
            st.execute(removeScheduleQuery);
            System.out.println("UserRequest DAO: *************** Query " + removeUseRequestQuery + "\n");
            System.out.println("Schedule DAO: *************** Query " + removeScheduleQuery + "\n");
            st.close();
        } catch (SQLException se) {
            System.err.println("UseRequestDAO: Threw a SQLException removing the UseRequest object from UseRequest table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

    }

    public void AddUseRequest(UserRequest useRequest) {

            Connection con = DB_Helper.getConnection();
            PreparedStatement addUse = null;
            Random rand = new Random(); //Spring ?

            try {
                //Insert the UseService object

                //int id = rand.nextInt(100);


                //useRequest.setUseRequestID(id2);
                scheduleDAO.AssignFacilityToUse(useRequest.getRequest().getSchedule());
                System.out.println(useRequest.getRequest().getSchedule().getScheduleID());

                //Insert the facility_detail object
                String addReq = "INSERT INTO UseRequest(UseRequestID, FacilityID, ScheduleID, RoomNumber,useDescription,EquipmentType ,EquipmentCode, UserID ) VALUES(?, ?, ?, ?,?,?,?,?)";
                addUse = con.prepareStatement(addReq);
                addUse.setInt(1, useRequest.getRequest().getRequestID());
                addUse.setInt(2, useRequest.getRequest().getSchedule().getFacilityID());
                addUse.setInt(3, useRequest.getRequest().getSchedule().getScheduleID());
                addUse.setInt(4, useRequest.getRequest().getSchedule().getRoomNumber());
                addUse.setString(5, useRequest.getRequest().getRequestDescription());
                addUse.setString(6, useRequest.getRequest().getTechnology().ListoftechType());
                addUse.setString(7, useRequest.getRequest().getTechnology().ListoftechCode());
                addUse.setInt(8, useRequest.getRequest().getiUser().getUserID());



                addUse.executeUpdate();

            } catch (SQLException ex) {
                System.out.println(ex);

            } finally {

                try {
                    if (addUse != null) {
                        addUse.close();
                    }
                    if (con != null) {
                        con.close();
                    }

                } catch (SQLException ex) {
                    System.err.println("UseRequestDAO: Threw a SQLException saving the UseRequest object.");
                    System.err.println(ex.getMessage());
                }

            }


        }


    public boolean isInUseDuringInterval(UserRequest ins) {
        if (!scheduleDAO.isInUseDuringInterval(ins.getRequest().getSchedule())){
            return false;
        }
        ID = scheduleDAO.getID();
        return true;

    }
    public void VacateInsFacility(UserRequest ins) {
        scheduleDAO.VacateFacility(ins.getRequest().getSchedule());
    }


    public UserRequest getInfobyID(int ID,UserRequest userRequest) {
        try {

            Statement st = DB_Helper.getConnection().createStatement();
            String selectDetailQuery = "SELECT *  FROM userequest,Schedule WHERE Schedule.ScheduleID= '" + ID + "' AND userequest.ScheduleID = Schedule.ScheduleID ";
            ResultSet detRS = st.executeQuery(selectDetailQuery);

            return achieveuser(detRS, selectDetailQuery,userRequest);
        }

        catch (SQLException se) {
            System.err.println("FacilityDAO: Threw a SQLException retrieving the Facility object.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        return null;
    }

    private UserRequest achieveuser(ResultSet detRS, String selectDetailQuery,UserRequest userRequest) {
        try {





            System.out.println("UserequestDAO: *************** Query " + selectDetailQuery + "\n");

            while ( detRS.next() ) {
                userRequest.getRequest().setRequestID(detRS.getInt("userequestid"));
                userRequest.getRequest().setRequestDescription(detRS.getString("usedescription"));

                String code = detRS.getString("equipmentcode");
                String type = detRS.getString("equipmenttype");
                userRequest.getRequest().getTechnology().setTechnologytype( new ArrayList<String>(Arrays.asList(type.split(","))));
                List<String> listcode = new ArrayList<String>(Arrays.asList(code.split(" ,")));
                ArrayList<Integer> codereal = new ArrayList<Integer>();
                for(String s : listcode) codereal.add(Integer.valueOf(s));
                userRequest.getRequest().getTechnology().setTechnologycode(codereal);

              userRequest.getRequest().setiUser(userDAO.getInformationByuserID(detRS.getInt("UserID")));

                userRequest.getRequest().getSchedule().setRequestType("requesttype");
                userRequest.getRequest().getSchedule().setFacility(facilityDAO.getFacilityInformationByFacilityID(detRS.getInt("facilityid")));
                userRequest.getRequest().getSchedule().setScheduleID(detRS.getInt("scheduleid"));
                userRequest.getRequest().getSchedule().setRoomNumber(detRS.getInt("roomnumber"));
                userRequest.getRequest().getSchedule().setStartDate(detRS.getDate("startdate").toLocalDate());
                userRequest.getRequest().getSchedule().setEndDate(detRS.getDate("enddate").toLocalDate());

                if (!(detRS.getString("UserIDList").equals("none"))) {
                    String subscriber = detRS.getString("useridlist");
                    List<String> userid = new ArrayList<String>(Arrays.asList(subscriber.split(",")));
                    ArrayList<Integer> useridint = new ArrayList<Integer>();
                    for (String s : userid) useridint.add(Integer.valueOf(s));
                    for (int m : useridint)
                        userRequest.getRequest().getSchedule().getObservable().addObserver(userDAO.getInformationByuserID(m));
                }
            }



            //close to manage resources
            detRS.close();

            return userRequest;
        } catch (SQLException se) {
            System.err.println("userrequestDAO: Threw a SQLException retrieving the request object.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        return null;

    }

}

        //}


