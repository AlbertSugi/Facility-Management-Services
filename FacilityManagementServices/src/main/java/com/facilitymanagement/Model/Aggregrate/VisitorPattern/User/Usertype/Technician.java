package com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.Usertype;

import com.facilitymanagement.Model.Maintainence.MaintainenceRequest;
import com.facilitymanagement.Model.Aggregrate.BridgePattern.Schedule.ISchedule;
import com.facilitymanagement.Model.Aggregrate.Technology.iTechnology;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.Facility;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.IFacility;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Technician implements IVisitor {

    private IUser user;
    private MaintainenceRequest maintainenceRequest;
    private IFacility facility ;

    @Autowired
    public Technician(IUser user, MaintainenceRequest maintainenceRequest, IFacility facility) {
        this.user = user;
        this.maintainenceRequest = maintainenceRequest;
        this.facility = facility;
    }

    public IUser getUser() {
        return user;
    }

    public void setUser(IUser user) {
        this.user = user;
    }

    public MaintainenceRequest maintain(iTechnology iTechnology, ISchedule schedule, String RequestDes, int cost) {
        maintainenceRequest.getRequest().setiUser(user);
        maintainenceRequest.getRequest().setRequestDescription(RequestDes);
        maintainenceRequest.getRequest().setTechnology(iTechnology);
        schedule.setFacility(facility);
        maintainenceRequest.getRequest().setSchedule(schedule);
        maintainenceRequest.setCost(cost);
        return maintainenceRequest;
    }

    public void visitFacility(IFacility facility) {
        this.facility = facility;
    }



}
