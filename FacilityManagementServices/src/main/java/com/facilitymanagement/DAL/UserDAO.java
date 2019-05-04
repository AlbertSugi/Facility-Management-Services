package com.facilitymanagement.DAL;

import com.facilitymanagement.Model.Aggregrate.ObserverPattern.ObserverNotify.IObserver;
import com.facilitymanagement.Model.Aggregrate.ObserverPattern.ObserverNotify.viaSMS;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserDAO {
    public void removeAllData() {
        try {
            Statement st = DB_Helper.getConnection().createStatement();
            String removeFacilityQuery = "delete from users";
            st.execute(removeFacilityQuery);

            System.out.println("userDAO: *************** Query " + removeFacilityQuery + "\n");
            st.close();
        }
        catch (SQLException se) {
            System.err.println("userDAO: Threw a SQLException removing the Facility object from Facility table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
    }

    /**
     *   Deletes a facility from the Facility table, FacilityDetail table, and Use table.
     *  @param ID of facility to be deleted
     *
     */
    public void removeuser(int ID) {
        try {
            Statement st = DB_Helper.getConnection().createStatement();
            String removeQuery = "delete from users where userID = '" + ID + "'";
            st.execute(removeQuery);

            System.out.println("userDAO: *************** Query " + removeQuery + "\n");
            st.close();
        }
        catch (SQLException se) {
            System.err.println("userDAO: Threw a SQLException removing the user object from user table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

    }


    /**
     *
     * @param ID
     * @return
     */
    public IUser getInformationByuserID(int ID) {
        try {

            Statement st = DB_Helper.getConnection().createStatement();
            String selectDetailQuery = "SELECT * FROM users WHERE userID = '" + ID + "'";
            ResultSet detRS = st.executeQuery(selectDetailQuery);






            return achieveUser(detRS, selectDetailQuery);
        }

        catch (SQLException se) {
            System.err.println("UserDAO: Threw a SQLException retrieving the User object.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        return null;
    }



    private IUser achieveUser(ResultSet detRS, String selectDetailQuery) {
        try {
            IUser user = new User();
            IObserver observer = null;
            System.out.println("UserDAO: *************** Query " + selectDetailQuery + "\n");

            while ( detRS.next() ) {
               /* if (detRS.getString("Usertype").equals("Inspector")){
                    user = new Inspector();
                }
                else if (detRS.getString("Usertype").equals("Faculty")){
                    user = new Faculty();
                }
                else if (detRS.getString("Usertype").equals("Technician")){
                    user = new Technician();
                }
                else{
                    System.out.println("usertype not recognizable");*/



                if (detRS.getString("contactpreference").equals("SMS")){
                    observer = new viaSMS();
                }
                user.setUserID(detRS.getInt("UserID"));
               user.setDepartment(detRS.getString("Department"));
               user.setUsername(detRS.getString("Username"));
               user.setPhonenumber(detRS.getString("Phonenumber"));
               user.setObserver(observer);


            }



            //close to manage resources
            detRS.close();

            return user;
        } catch (SQLException se) {
            System.err.println("FacilityDAO: Threw a SQLException retrieving the user object.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        return null;

    }





    public void addNewUser(IUser user) {
        Connection con = DB_Helper.getConnection();
        PreparedStatement usePst = null;
        //Random rand = new Random();

        try {


            //int d = rand.nextInt(1000);
            //Insert the facility ID, room number, and start/end dates into use table
            String useStm = "INSERT INTO USERS( username, department, "
                    + "phonenumber,usertype, contactpreference) VALUES (?, ?, ?,?,?)";
            usePst = con.prepareStatement(useStm, Statement.RETURN_GENERATED_KEYS);
            usePst.setString(1, user.getUsername());
            usePst.setString(2, user.getDepartment());
            usePst.setString(3, user.getPhonenumber());
            usePst.setString(4, user.getUsertype());
            usePst.setString(5,user.getObserver().getType());
            usePst.executeUpdate();

            ResultSet rs = usePst.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                user.setUserID(id);
            }
            System.out.println("UserDAO: *************** Query " + usePst + "\n");

            //close to manage resources
            usePst.close();
            con.close();
        } catch (SQLException se) {
            System.err.println("UserDAO: Threw a SQLException assigning a User "
                    + " in the User table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }


    }

}
