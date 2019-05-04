package com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.Usertype;

import com.facilitymanagement.Model.Aggregrate.BridgePattern.Schedule.ISchedule;
import com.facilitymanagement.Model.Aggregrate.Technology.iTechnology;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.IFacility;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import com.facilitymanagement.Model.Use.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class Faculty implements IVisitor {


    private IUser user;
    private UserRequest userRequest;
    private IFacility facility;

    @Autowired
    public Faculty(IUser user, UserRequest userRequest, IFacility facility) {
        this.user = user;
        this.userRequest = userRequest;
        this.facility = facility;
    }

    public IUser getUser() {
        return user;
    }

    public void setUser(IUser user) {
        this.user = user;
    }

    public UserRequest use(iTechnology iTechnology, ISchedule schedule, String RequestDes) {
        userRequest.getRequest().setiUser(user);
        userRequest.getRequest().setRequestDescription(RequestDes);
        userRequest.getRequest().setTechnology(iTechnology);
        schedule.setFacility(facility);
        userRequest.getRequest().setSchedule(schedule);
        return userRequest;

    }

    public void visitFacility(IFacility facility) {
        this.facility = facility;
    }


}
