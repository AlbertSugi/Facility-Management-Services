package com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.Usertype;

import com.facilitymanagement.Model.Aggregrate.BridgePattern.Schedule.ISchedule;
import com.facilitymanagement.Model.Inspection.InspectionRequest;
import com.facilitymanagement.Model.Aggregrate.Technology.iTechnology;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.IFacility;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class Inspector implements IVisitor {


   private IUser user;
    private InspectionRequest inspectionRequest ;
    private IFacility facility;

    public InspectionRequest getInspectionRequest() {
        return inspectionRequest;
    }

    @Autowired
    public void setInspectionRequest(InspectionRequest inspectionRequest) {
        this.inspectionRequest = inspectionRequest;
    }


    public IFacility getFacility() {
        return facility;
    }

    @Autowired
    public void setFacility(IFacility facility) {
        this.facility = facility;
    }

    public IUser getUser() {
        return user;
    }

    @Autowired
    public void setUser(IUser user) {
        this.user = user;
    }

    public InspectionRequest inspection(iTechnology iTechnology, ISchedule schedule, String RequestDes) {
        inspectionRequest.getRequest().setiUser(user);
        inspectionRequest.getRequest().setRequestDescription(RequestDes);
        inspectionRequest.getRequest().setTechnology(iTechnology);
        System.out.print(facility);
        schedule.setFacility(facility);
        inspectionRequest.getRequest().setSchedule(schedule);
        return inspectionRequest;
    }




    public void visitFacility(IFacility facility) {
        this.facility = facility;


    }


}

