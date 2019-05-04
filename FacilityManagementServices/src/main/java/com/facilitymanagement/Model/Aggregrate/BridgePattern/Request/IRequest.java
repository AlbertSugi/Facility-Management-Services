package com.facilitymanagement.Model.Aggregrate.BridgePattern.Request;

import com.facilitymanagement.Model.Aggregrate.BridgePattern.Schedule.ISchedule;
import com.facilitymanagement.Model.Aggregrate.Technology.iTechnology;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;

public interface IRequest {



    int getRequestID() ;
    void setRequestID(int requestID);
    String getRequestDescription();
    void setRequestDescription(String requestDescription) ;
    ISchedule getSchedule();
    void setSchedule(ISchedule schedule);
    iTechnology getTechnology();
    void setTechnology(iTechnology technology);
    IUser getiUser();
    void setiUser(IUser iUser) ;





}
