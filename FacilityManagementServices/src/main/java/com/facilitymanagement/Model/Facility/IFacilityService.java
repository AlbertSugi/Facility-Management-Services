package com.facilitymanagement.Model.Facility;

import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.Facility;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IFacilityService {
     void clearAllFacilityData();
     void addNewFacility(Facility facility);
     Facility getFacilityInformation(String facName);
     void removeFacility(String id);
          void addFacilityDetail(String Facilityname, int PhoneNumber);
     List<Facility> listFacilities();


}
