package com.facilitymanagement.Model.Aggregrate.BridgePattern.Request;

import com.facilitymanagement.Model.Aggregrate.BridgePattern.Schedule.ISchedule;
import com.facilitymanagement.Model.Aggregrate.Technology.iTechnology;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Request implements IRequest {

    private int RequestID;
    private String RequestDescription;
    private ISchedule schedule;
    private  iTechnology technology;
    private IUser iUser;

    @Override
    public int getRequestID() {
        return RequestID;
    }

    @Override
    public void setRequestID(int requestID) {
        RequestID = requestID;
    }

    @Override
    public String getRequestDescription() {
        return RequestDescription;
    }

    @Override
    public void setRequestDescription(String requestDescription) {
        RequestDescription = requestDescription;
    }

    @Override
    public ISchedule getSchedule() {
        return schedule;
    }

    @Override
    @Autowired
    public void setSchedule(ISchedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public iTechnology getTechnology() {
        return technology;
    }

    @Override
    @Autowired
    public void setTechnology(iTechnology technology) {
        this.technology = technology;
    }

    @Override
    public IUser getiUser() {
        return iUser;
    }

    @Override
    @Autowired
    public void setiUser(IUser iUser) {
        this.iUser = iUser;
    }
}
