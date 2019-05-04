package com.facilitymanagement.DAL;


import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.FacilityDetail;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.Facility;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



@Repository
public class FacilityDAO {



	/**
	 *
	 */

	public void removeAllData() {
		try {
			Statement st = DB_Helper.getConnection().createStatement();
			String removeFacilityQuery = "delete from Facility";
			String removeFacilityDetailQuery = "delete from FacilityDetail";
			st.execute(removeFacilityQuery);
			st.execute(removeFacilityDetailQuery);

			System.out.println("FacilityDAO: *************** Query " + removeFacilityQuery + "\n");
			st.close();
		}
		catch (SQLException se) {
			System.err.println("FacilityDAO: Threw a SQLException removing the Facility object from Facility table.");
			System.err.println(se.getMessage());
			se.printStackTrace();
		}
	}

	/**
	 *   Deletes a facility from the Facility table, FacilityDetail table, and Use table.
	 *  @param name of facility to be deleted
	 * 
	 */
	public void removeFacility(String name) {
		Facility facility = getFacilityInformationByFacilityName(name);

	    try {
	    	Statement st = DB_Helper.getConnection().createStatement();
	    	String removeFacilityQuery = "delete from Facility where id = '" + facility.getFacilityID() + "'";
            st.execute(removeFacilityQuery);

	    	System.out.println("FacilityDAO: *************** Query " + removeFacilityQuery + "\n");
	    	st.close();
	    }
	    catch (SQLException se) {
	    	System.err.println("FacilityDAO: Threw a SQLException removing the Facility object from Facility table.");
	    	System.err.println(se.getMessage());
	    	se.printStackTrace();
	    }

	}


	/**
	 *
	 * @param ID
	 * @return
	 */
	public Facility getFacilityInformationByFacilityID(int ID) {
		try {

	    	Statement st = DB_Helper.getConnection().createStatement();
		    String selectDetailQuery = "SELECT FacilityID,FacilityName,NumberOfRooms,PhoneNumber FROM facilityDetail WHERE FacilityID = '" + ID + "'";
		    ResultSet detRS = st.executeQuery(selectDetailQuery);

		    return achieveFacilityDetail(detRS, selectDetailQuery);
	    }

	    catch (SQLException se) {
	    	System.err.println("FacilityDAO: Threw a SQLException retrieving the Facility object.");
	    	System.err.println(se.getMessage());
	    	se.printStackTrace();
	    }

	    return null;
	}

	public Facility getFacilityInformationByFacilityName(String facName) {
		try {

			Statement st = DB_Helper.getConnection().createStatement();
			String selectDetailQuery = "SELECT FacilityID,FacilityName,NumberOfRooms,PhoneNumber FROM facilityDetail WHERE FacilityName = '" + facName + "'";
			ResultSet detRS = st.executeQuery(selectDetailQuery);

			return achieveFacilityDetail(detRS, selectDetailQuery);
		}

		catch (SQLException se) {
			System.err.println("FacilityDAO: Threw a SQLException retrieving the Facility object.");
			System.err.println(se.getMessage());
			se.printStackTrace();
		}

		return null;
	}

	private Facility achieveFacilityDetail(ResultSet detRS, String selectDetailQuery) {
		try {
			Facility fac = new Facility();
			FacilityDetail detail = new FacilityDetail();

			System.out.println("FacilityDAO: *************** Query " + selectDetailQuery + "\n");

			while ( detRS.next() ) {
				fac.setFacilityID(detRS.getInt("facilityid"));
				detail.setName(detRS.getString("facilityname"));
				detail.setNumberOfRooms(detRS.getInt("numberofrooms"));
				if (detRS.getInt("phonenumber") != 0) {
					detail.setPhoneNumber(detRS.getInt("phonenumber"));
				}
			}

			fac.setFacilityDetail(detail);

			//close to manage resources
			detRS.close();

			return fac;
		} catch (SQLException se) {
			System.err.println("FacilityDAO: Threw a SQLException retrieving the Facility object.");
			System.err.println(se.getMessage());
			se.printStackTrace();
		}

		return null;

	}
		
	

	/**
	 *   Add a new facility and their details to the Facility Table and FacilityDetail Table.
	 *  Must create a new facility object before running addNewFacility method. 
	 *  @param fac Facility object to be added to the database
	 * 
	 */
	public void addNewFacility(Facility fac) {
		Connection con = DB_Helper.getConnection();
        PreparedStatement facPst = null;
        PreparedStatement addPst = null;

        try {
        	//Insert the facility object
            String facStm = "INSERT INTO facility DEFAULT VALUES";
            facPst = con.prepareStatement(facStm, Statement. RETURN_GENERATED_KEYS);
			//facPst = con.prepareStatement(facStm);
            facPst.executeUpdate();
			ResultSet rs=facPst.getGeneratedKeys();
			if(rs.next()) { // get facility from insertion

				int id = rs.getInt(1);

				fac.setFacilityID(id);


				//Insert the facility_detail object
				String addStm = "INSERT INTO facilityDetail(FacilityName, FacilityID, NumberOfRooms, PhoneNumber) VALUES(?, ?, ?, ?)";
				addPst = con.prepareStatement(addStm);
				addPst.setString(1, fac.getFacilityDetail().getName());
				addPst.setInt(2, fac.getFacilityID());
				addPst.setInt(3, fac.getFacilityDetail().getNumberOfRooms());
				if (fac.getFacilityDetail().getPhoneNumber() != 0) {
					addPst.setInt(4, fac.getFacilityDetail().getPhoneNumber());
				} else {
					addPst.setNull(4, Types.INTEGER);
				}
				addPst.executeUpdate();
			}


        } catch (SQLException ex) {
			System.out.println(ex);

        } finally {

            try {
                if (addPst != null) {
                	addPst.close();
                	facPst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
      	      System.err.println("FacilityDAO: Threw a SQLException saving the facility object.");
    	      System.err.println(ex.getMessage());
            }

	}
        
	}

	/**
	 *
	 *
	 * @param PhoneNumber
	 */
	public void addFacilityDetail(String name, int PhoneNumber) {
		
		try { 		
	    	Connection con = DB_Helper.getConnection();
			PreparedStatement facPst = null;
	    	//Get Facility
	        
	    	String updateFacilityDetailQuery = "UPDATE facilitydetail SET phonenumber = ? WHERE facilityname = ?";
	    
            facPst = con.prepareStatement(updateFacilityDetailQuery);
            facPst.setInt(1, PhoneNumber);
            facPst.setString(2, name);
            facPst.executeUpdate();
    
	    	System.out.println("FacilityDAO: *************** Query " + updateFacilityDetailQuery + "\n");
	    	
	    	//close to manage resources
	    	facPst.close();
	    	con.close();
	    	  
	   }	    
	   catch (SQLException se) {
	      System.err.println("FacilityDAO: Threw a SQLException updating the phone number in Facility Detail table.");
	      System.err.println(se.getMessage());
	      se.printStackTrace();
	   }

	}

	/**
	 *   List the name and ID number of all the facilities 
	 *  @return list of facilities 
	 * 
	 */
	public List<Facility> listFacilities() {
		List<Facility> listOfFac = new ArrayList<Facility>();
		
		try {
			
			Statement st = DB_Helper.getConnection().createStatement();
	    	String getAllFacilitiesQuery = "SELECT * FROM facility, facilityDetail WHERE Facility.ID = FacilityDetail.FacilityID";

	    	ResultSet facRS = st.executeQuery(getAllFacilitiesQuery);      
	    	System.out.println("FacilityDAO: *************** Query " + getAllFacilitiesQuery + "\n");
			Facility fac1;
			FacilityDetail detail;
		    while ( facRS.next() ) {
				fac1 = new Facility();
		    	fac1.setFacilityID(facRS.getInt("id"));
		    	detail = new FacilityDetail();
				detail.setName(facRS.getString("facilityname"));
				detail.setNumberOfRooms(facRS.getInt("numberofrooms"));
				detail.setPhoneNumber(facRS.getInt("phonenumber"));
				fac1.setFacilityDetail(detail);
		    	listOfFac.add(fac1);
		    }
		    
		    //close to manage resources
		    facRS.close();
		    st.close();

		    return listOfFac;
	    	
		}
    	catch (SQLException se) {
  	      System.err.println("FacilityDAO: Threw a SQLException retrieving list of facilities.");
  	      System.err.println(se.getMessage());
  	      se.printStackTrace();

			return null;
  	    }

	    	
	}
}


