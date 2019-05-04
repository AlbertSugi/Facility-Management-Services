package com.facilitymanagement.Model.Facility;

import com.facilitymanagement.DAL.FacilityDAO;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.Facility;
import com.facilitymanagement.Model.Facility.IFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityService implements IFacilityService {


	private FacilityDAO facDAO;

	@Autowired
	public void setFacDAO(FacilityDAO facDAO) {
		this.facDAO = facDAO;
	}

	@Override
	public void clearAllFacilityData() {
		facDAO.removeAllData();
	}

	// Adding a new facility to the database
	@Override
	public void addNewFacility(Facility facility) {
		facDAO.addNewFacility(facility);
	}

	@Override
	public Facility getFacilityInformation(String facName) {
		return facDAO.getFacilityInformationByFacilityName(facName);
	}

	@Override
	public void removeFacility(String id) {
		facDAO.removeFacility(id);
	}

	@Override
	public void addFacilityDetail(String facilityname, int PhoneNumber) {
		facDAO.addFacilityDetail(facilityname, PhoneNumber);
	}

	@Override
	public List<Facility> listFacilities() {
		return facDAO.listFacilities();
	}
}

