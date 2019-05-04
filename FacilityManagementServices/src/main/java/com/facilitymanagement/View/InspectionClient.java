package com.facilitymanagement.View;

import com.facilitymanagement.Model.Inspection.InspectionRequest;
import com.facilitymanagement.Model.Aggregrate.BridgePattern.Schedule.ISchedule;
import com.facilitymanagement.Model.Inspection.iInspectionService;
import com.facilitymanagement.Model.Aggregrate.ObserverPattern.ObserverNotify.IObserver;
import com.facilitymanagement.Model.Aggregrate.ObserverPattern.Subscriber.IObservable;
import com.facilitymanagement.Model.Aggregrate.Technology.iTechnology;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.IFacility;
import com.facilitymanagement.Model.Facility.IFacilityService;
import com.facilitymanagement.Model.User.IUserService;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.Usertype.Inspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class InspectionClient {

    private IFacilityService facilityService ;
    private iInspectionService InspectionService ;
    private IUserService userService ;

    private ISchedule schedule;
    private IUser user ;
    private Inspector inspector ;
    private iTechnology technology ;
    private InspectionRequest inspectionRequest ;


    private IObserver observer ;
    private IObservable observable ;

    @Autowired
    public InspectionClient(IFacilityService facilityService, com.facilitymanagement.Model.Inspection.InspectionService inspectionService, IUserService userService, ISchedule schedule, IUser user, Inspector inspector, iTechnology technology, InspectionRequest inspectionRequest, IObserver observer, IObservable observable) {
        this.facilityService = facilityService;
        InspectionService = inspectionService;
        this.userService = userService;
        this.schedule = schedule;
        this.user = user;
        this.inspector = inspector;
        this.technology = technology;
        this.inspectionRequest = inspectionRequest;
        this.observer = observer;
        this.observable = observable;
    }

    /* //spring
    private IFacilityService facilityService ;
    private InspectionService InspectionService ;
    private ISchedule schedule;
    private Inspector inspector ;
    private iTechnology technology;
    private clearallUseData clearallUseData;
    private AddRequest addRequest;
    private removeRequest removeRequest;
    private vacateFacility vacateFacility;
    private isInUseDuringInterval isInUseDuringInterval;

    public InspectionClient(IFacilityService facilityService, InspectionService inspectionService, ISchedule schedule, Inspector inspector, iTechnology technology, clearallUseData clearallUseData, AddRequest addRequest, removeRequest removeRequest, vacateFacility vacateFacility, isInUseDuringInterval isInUseDuringInterval) {
        this.facilityService = facilityService;
        InspectionService = inspectionService;
        this.schedule = schedule;
        this.inspector = inspector;
        this.technology = technology;
        this.clearallUseData = clearallUseData;
        this.addRequest = addRequest;
        this.removeRequest = removeRequest;
        this.vacateFacility = vacateFacility;
        this.isInUseDuringInterval = isInUseDuringInterval;
    }
*/

    public void start(){
        System.out.println("\nInspectionClient: *************** Clearing All Data *************************");
        InspectionService.clearAllUseData();
        //---------------------------------------------------------------

        System.out.println("\nInspectionClient: *************** Instantiating InspectionRequest *************************");
        IFacility fac = facilityService.getFacilityInformation("Law Center");
        user = userService.getUserInformation(2);
        inspector.setUser(user);
        fac.accept(inspector);
        schedule.setRoomNumber(8);
        schedule.setStartDate(LocalDate.of(2020,05,01) );
        schedule.setEndDate(LocalDate.of(2020,05,04));
        schedule.setObservable(observable);

        technology.addtechnology(20116,"PC Computer");
        technology.addtechnology(20134,"Projector");

        inspectionRequest= inspector.inspection(technology,schedule,"Daily Checkup");


        InspectionService.AddRequest(inspectionRequest);

        System.out.println("\nInspectionClient: *************** Vacate a Facility in Inspection in Schedule (Shorten Time)  *************************");
        InspectionService.VacateFacility(5);


        System.out.println("\nInspectionClient: *************** Remove a Facility  from InspectionRequest and Schedule *************************");
        InspectionService.RemoveRequest(5);
    }
}
