package com.facilitymanagement.DAL;



import com.facilitymanagement.Model.Aggregrate.BridgePattern.Schedule.ISchedule;
import com.facilitymanagement.Model.Aggregrate.BridgePattern.Schedule.Schedule;
import com.facilitymanagement.Model.Aggregrate.ObserverPattern.Subscriber.IObservable;
import com.facilitymanagement.Model.Aggregrate.ObserverPattern.Subscriber.Observable;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ScheduleDAO {
    private int ID;

    public int getID() {
        return ID;
    }

    public boolean isInUseDuringInterval(ISchedule facUse) {
        boolean result = false;

        try {
            //Insert the FacilityID, RoomNumber, and start/end Dates into Use table
            Statement st = DB_Helper.getConnection().createStatement();
            String selectUseAssignments = "SELECT * FROM schedule WHERE FacilityID = " + facUse.getFacilityID() +
                    " AND RoomNumber IN (0, " + facUse.getRoomNumber() + ")";

            ResultSet useRS = st.executeQuery(selectUseAssignments);
            System.out.println("ScheduleDAO: *************** Query " + selectUseAssignments + "\n");

            //check if dates in database overlap with input interval
            while (useRS.next()) {
                LocalDate assignStart = useRS.getDate("StartDate").toLocalDate();
                LocalDate assignEnd = useRS.getDate("EndDate").toLocalDate();
                if (facUse.getStartDate().isBefore(assignEnd) && (assignStart.isBefore(facUse.getEndDate()) ||
                        assignStart.equals(facUse.getEndDate()))) {
                    result = true;
                    ID = useRS.getInt("scheduleID");
                    break;
                }
            }

            //close to manage resources
            useRS.close();
            st.close();

        } catch (SQLException se) {
            System.err.println("ScheduleDAO: Threw a SQLException checking if "
                    + "Facility is in use during an interval.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        return result;
    }

    public void AssignFacilityToUse(ISchedule facUse) {
        Connection con = DB_Helper.getConnection();
        PreparedStatement usePst = null;
        //Random rand = new Random();

        try {
            // set Facility available to FALSE


            //int d = rand.nextInt(1000);
            //Insert the facility ID, room number, and start/end dates into use table
            String useStm = "INSERT INTO schedule(FacilityID, RoomNumber, StartDate, "
                    + "EndDate,RequestType,UserIDList) VALUES (?, ?, ?,?,?,?)";
            usePst = con.prepareStatement(useStm, Statement.RETURN_GENERATED_KEYS);
            usePst.setInt(1, facUse.getFacilityID());
            usePst.setInt(2, facUse.getRoomNumber());
            usePst.setDate(3, Date.valueOf(facUse.getStartDate()));
            usePst.setDate(4, Date.valueOf(facUse.getEndDate()));
            usePst.setString(5,facUse.RequestType());
            if (!(facUse.getObservable().listofobserverid() == null)){
                usePst.setString(6, facUse.getObservable().listofobserverid());

            }
            usePst.setString(6, "none");

            usePst.executeUpdate();

            ResultSet rs = usePst.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                facUse.setScheduleID(id);
            }
            System.out.println("ScheduleDAO: *************** Query " + usePst + "\n");

            //close to manage resources
            usePst.close();
            con.close();
        } catch (SQLException se) {
            System.err.println("ScheduleDAO: Threw a SQLException assigning a facility "
                    + "to Schedule in the Schedule table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }


    }

    public void VacateFacility(ISchedule use) {
        try {

            
            Statement st = DB_Helper.getConnection().createStatement();
            String vacateQuery = "";

            if (LocalDate.now().isAfter(use.getStartDate()) && (LocalDate.now().equals(use.getEndDate()) ||
                    LocalDate.now().isBefore(use.getEndDate()))) {
                vacateQuery = "UPDATE schedule SET EndDate = '" + Date.valueOf(LocalDate.now().minusDays(1)) +
                        "' WHERE scheduleid = '"+use.getScheduleID()+"'";
            }

            st.execute(vacateQuery);
            System.out.println("ScheduleDAO: *************** Query " + vacateQuery + "\n");

        } catch (SQLException se) {
            System.err.println("ScheduleDAO: Threw a SQLException vacating the facility.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }


    }

    public void addSubscriber(ISchedule schedule, IUser user) {

        try {

            Connection con = DB_Helper.getConnection();
            PreparedStatement facPst = null;

            schedule.getObservable().addObserver(user);
            String updateQuery = "UPDATE schedule SET UserIDList  = ? WHERE scheduleID = ?";

            facPst = con.prepareStatement(updateQuery);
            facPst.setString(1,schedule.getObservable().listofobserverid() );
            facPst.setInt(2, schedule.getScheduleID());
            facPst.executeUpdate();

            System.out.println("ScheduleDAO: *************** Query " + updateQuery + "\n");

            //close to manage resources
            facPst.close();
            con.close();

        }
        catch (SQLException se) {
            System.err.println("ScheduleDAO: Threw a SQLException updating the userIDList in Schedule table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

    }

    public ISchedule getInfobyID(int ID) {
        try {

            Statement st = DB_Helper.getConnection().createStatement();
            String selectDetailQuery = "SELECT scheduleid, FacilityID, RoomNumber, StartDate, " +
                    "EndDate,RequestType,UserIDList  FROM Schedule WHERE Schedule.ScheduleID= '" + ID + "'";
            ResultSet detRS = st.executeQuery(selectDetailQuery);

            return achieveschedule(detRS, selectDetailQuery);
        }

        catch (SQLException se) {
            System.err.println("FacilityDAO: Threw a SQLException retrieving the Facility object.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        return null;
    }

    private ISchedule achieveschedule(ResultSet detRS, String selectDetailQuery) {
        try {

            FacilityDAO facilityDAO = new FacilityDAO();
            ISchedule schedule = new Schedule();
            IObservable observable = new Observable();
            UserDAO userDAO  = new UserDAO();




            System.out.println("ScheduleDAO: *************** Query " + selectDetailQuery + "\n");

            while ( detRS.next() ) {
                schedule.setScheduleID(detRS.getInt("scheduleid"));
                schedule.setFacility(facilityDAO.getFacilityInformationByFacilityID(detRS.getInt("facilityid")));
                schedule.setRequestType("requesttype");
                schedule.setRoomNumber(detRS.getInt("roomnumber"));
                schedule.setStartDate(detRS.getDate("startdate").toLocalDate());
                schedule.setEndDate(detRS.getDate("enddate").toLocalDate());
                if (!(detRS.getString("UserIDList").equals( "none"))) {
                    String subscriber = detRS.getString("useridlist");
                    List<String> listcode = new ArrayList<String>(Arrays.asList(subscriber.split(",")));
                    ArrayList<Integer> codereal = new ArrayList<Integer>();
                    for(String s : listcode) codereal.add(Integer.valueOf(s));
                    for (int m : codereal) observable.addObserver(userDAO.getInformationByuserID(m));

                }


                }

            schedule.setObservable(observable);

            //close to manage resources
            detRS.close();

            return schedule;
        } catch (SQLException se) {
            System.err.println("ScheduleDAO: Threw a SQLException retrieving the schedule object.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        return null;

    }
}
