package com.facilitymanagement.Model.Use;

import com.facilitymanagement.Model.Aggregrate.BridgePattern.Request.IRequest;

public interface IUseService {
    public void AddRequest(UserRequest userRequest);

    public void isInUseDuringInterval(UserRequest userRequest);


    public void RemoveRequest(int ID);

    public void VacateFacility(int ID);


    public void clearAllUseData();

    public UserRequest getInfobyID(int ID);

}
