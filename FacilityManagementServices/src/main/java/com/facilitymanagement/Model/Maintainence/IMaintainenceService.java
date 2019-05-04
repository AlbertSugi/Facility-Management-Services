package com.facilitymanagement.Model.Maintainence;

public interface IMaintainenceService {


    public void AddRequest(MaintainenceRequest maintainenceRequest) ;


    public void isInUseDuringInterval(MaintainenceRequest maintainenceRequest);


    public void RemoveRequest(int ID) ;


    public void VacateFacility(int ID);


    public void clearAllUseData();


    public MaintainenceRequest getInfobyID(int ID) ;

}
