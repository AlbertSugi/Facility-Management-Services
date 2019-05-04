package com.facilitymanagement.View;

import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.*;
import com.facilitymanagement.Model.Facility.IFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FacilityClient {


	private IFacilityService facilityService;

	private IFacility fac ;
	private FacilityDetail detail;

	@Autowired
	public void setFacilityService(IFacilityService facilityService) {
		this.facilityService = facilityService;
	}

	@Autowired
	public void setFac(IFacility fac) {
		this.fac = fac;
	}



	@Autowired
	public void setDetail(FacilityDetail detail) {
		this.detail = detail;
	}

	public void start()  {

		//facilityService.clearAllFacilityData();

		//---------------------------------------------------------------
		System.out.println("\nFacilityClient: *************** Instantiating a facility and its details *************************");



	/*	detail.setName("Libary");
		detail.setNumberOfRooms(10);
		detail.setPhoneNumber(5553);
		fac.setFacilityDetail(detail);
		facilityService.addNewFacility(fac);*/


		/*detail.setName("Law Center");
		detail.setNumberOfRooms(10);
		detail.setPhoneNumber(123456);
		fac.setFacilityDetail(detail);
		facilityService.addNewFacility(fac);
*/

	/*	detail.setName("Labatory");
		detail.setNumberOfRooms(100);
		detail.setPhoneNumber(111111);
		fac.setFacilityDetail(detail);
		facilityService.addNewFacility(fac);
*/


		/*System.out.println("FacilityClient: *************** Facility is inserted in Facility Database *************************");
		fac = facilityService.getFacilityInformation("Labatory");
		System.out.println(fac.getFacilityDetail().getName());*/
		//---------------------------------------------------------------


		facilityService.addFacilityDetail("Library", 3120136);
		Facility updatedFacility = facilityService.getFacilityInformation(fac.getFacilityDetail().getName());
		FacilityDetail facilityNewDet = updatedFacility.getFacilityDetail();

		System.out.println("\nFacilityClient: *************** Here is the updated facility information *************************");
		System.out.println("\n\tFacility ID:   \t\t" + updatedFacility.getFacilityID());
		System.out.println("\tInfo About Facility:  \t" + facilityNewDet.getName() +
				"\n\t\t\t\t Number of Rooms: " + facilityNewDet.getNumberOfRooms());
		if (facilityNewDet.getPhoneNumber() != 0) {
			System.out.print("\t\t\t\t Phone Number: " + facilityNewDet.getPhoneNumber() +
					"\n\t\t\t\t" + "\n");
		} else {
			System.out.print("\t\t\t\t Phone Number: unlisted" +
					"\n\t\t\t\t" + "\n");
		}


		//---------------------------------------------------------------
		/*System.out.println("\nFacilityClient: *************** Remove a facility from the database *************************");
		facilityService.removeFacility(fac.getFacilityDetail().getName());
		System.out.println("************ Facility Removed ************");*/

		/*System.out.println("\nFacilityClient: *************** An updated list of all the facilities *************************");
		List<Facility> listOfFacilities = facilityService.listFacilities();
		for (Facility fact : listOfFacilities) {
			FacilityDetail facDet = fact.getFacilityDetail();
			System.out.println("\n\t" + facDet.getName() + " ID: " + fac.getFacilityID());
		}
*/
		//---------------------------------------------------------------
		/*System.out.println("\nFacilityClient: *************** Request available capacity of a facility *************************");
		//uses sample data
		//int roomsAvail = facService.requestAvailableCapacity(fac2);
		//System.out.println("There are " + roomsAvail + " rooms currently available at Facility #" + fac2.getFacilityID() + ".");
*/
		//---------------------------------------------------------------
		/*System.out.println("\nFacilityClient: *************** Remove Facility *************************");
		facilityService.removeFacility("Student Center");
	*/}
}
