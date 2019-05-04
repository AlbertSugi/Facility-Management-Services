package com.facilitymanagement.View;

import com.facilitymanagement.Model.Maintainence.MaintainenceRequest;
import com.facilitymanagement.Model.Aggregrate.BridgePattern.Schedule.ISchedule;
import com.facilitymanagement.Model.Maintainence.IMaintainenceService;
import com.facilitymanagement.Model.Maintainence.MaintainenceService;
import com.facilitymanagement.Model.Aggregrate.ObserverPattern.Subscriber.IObservable;
import com.facilitymanagement.Model.Aggregrate.Technology.iTechnology;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.IFacility;
import com.facilitymanagement.Model.Facility.IFacilityService;
import com.facilitymanagement.Model.User.IUserService;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.Usertype.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Component
public class MaintainenceClient {

    private IFacilityService facilityService;
    private IMaintainenceService maintainenceService ;
    private MaintainenceRequest maintainenceRequest ;
    private IUserService userService ;

    private ISchedule schedule ;
    private IUser user ;
    private iTechnology technology ;
    private Technician technician ;
    private IObservable observable ;

    @Autowired
    public MaintainenceClient(IFacilityService facilityService, MaintainenceService maintainenceService, MaintainenceRequest maintainenceRequest, IUserService userService, ISchedule schedule, IUser user, iTechnology technology, Technician technician, IObservable observable) {
        this.facilityService = facilityService;
        this.maintainenceService = maintainenceService;
        this.maintainenceRequest = maintainenceRequest;
        this.userService = userService;
        this.schedule = schedule;
        this.user = user;
        this.technology = technology;
        this.technician = technician;
        this.observable = observable;
    }

    public void start(){
        System.out.println("\nMaintenanceClient: *************** Clearing All Data *************************");
        maintainenceService.clearAllUseData();

       // ---------------------------------------------------------------

   System.out.println("\nMaintenanceClient: *************** Instantiating MaintenanceRequest *************************");
        IFacility facility = facilityService.getFacilityInformation("Law Center");
        user = userService.getUserInformation(4);
        technician.setUser(user);
        facility.accept(technician);
        schedule.setRoomNumber(20);
        schedule.setStartDate(LocalDate.of(2020,05,01) );
        schedule.setEndDate(LocalDate.of(2020,05,04));
        schedule.setObservable(observable);


        technology.addtechnology(2222,"Air conditioner");
        maintainenceRequest = technician.maintain(technology,schedule,"Repair",300);
        maintainenceService.AddRequest(maintainenceRequest);


        System.out.println("\nMaintenanceClient: *************** Vacate a Facility in Maintenance in Schedule (Shorten Time)  *************************");
         maintainenceService.VacateFacility(3); //scheduleid


 System.out.println("\nMaintenanceClient: *************** Remove a Facility  from MaintenanceRequest and Schedule *************************");
        maintainenceService.RemoveRequest(3); //scheduleid



    }
}
