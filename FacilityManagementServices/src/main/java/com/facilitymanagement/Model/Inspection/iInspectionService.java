package com.facilitymanagement.Model.Inspection;

public interface iInspectionService {

    public void AddRequest(InspectionRequest inspectionRequest) ;


    public void isInUseDuringInterval(InspectionRequest inspectionRequest) ;


    public void RemoveRequest(int ID) ;


    public void VacateFacility(int ID) ;

    public void clearAllUseData();


    public InspectionRequest visitgetInfobyID(int ID);






}
