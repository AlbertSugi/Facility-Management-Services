package com.facilitymanagement.View;


import com.facilitymanagement.Model.Aggregrate.BridgePattern.Schedule.ISchedule;
import com.facilitymanagement.Model.Use.IUseService;
import com.facilitymanagement.Model.Use.UseService;
import com.facilitymanagement.Model.Aggregrate.ObserverPattern.Subscriber.IObservable;
import com.facilitymanagement.Model.Aggregrate.Technology.iTechnology;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.Facility;
import com.facilitymanagement.Model.Facility.IFacilityService;
import com.facilitymanagement.Model.Use.UserRequest;
import com.facilitymanagement.Model.User.IUserService;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.Usertype.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UseRequestClient {

    private IFacilityService facilityService ;
    private IUserService userService ;

    private ISchedule schedule;
    private IUser user;
    private Faculty faculty ;
    private iTechnology technology;
    private IObservable observable ;
    private UserRequest userRequest ;
    private IUseService useService;

    @Autowired
    public UseRequestClient(IFacilityService facilityService, IUserService userService, ISchedule schedule, IUser user, Faculty faculty, iTechnology technology, IObservable observable, UserRequest userRequest, UseService useService) {
        this.facilityService = facilityService;
        this.userService = userService;
        this.schedule = schedule;
        this.user = user;
        this.faculty = faculty;
        this.technology = technology;
        this.observable = observable;
        this.userRequest = userRequest;
        this.useService = useService;
    }

    public void start(){
       /* System.out.println("\nUseClient: *************** Clearing All Data *************************");
        useService.clearAllUseData();

*/

        //---------------------------------------------------------------

        System.out.println("\nUseClient: *************** Instantiating UseRequest *************************");
        Facility fac = facilityService.getFacilityInformation("Law Center");
        user = userService.getUserInformation(3);
        fac.accept(faculty);
        schedule.setRoomNumber(20);
        schedule.setStartDate(LocalDate.of(2020,05,01) );
        schedule.setEndDate(LocalDate.of(2020,05,04));
        schedule.setObservable(observable);

        technology.addtechnology(20116,"PC Computer");
        technology.addtechnology(20134,"Projector");

        userRequest = faculty.use(technology,schedule,"Class");

        useService.AddRequest(userRequest);




      /*  System.out.println("\nUseClient: *************** Vacate a Facility in Use in Schedule (Shorten Time)  *************************");
        useService.VacateFacility(4);

        System.out.println("\nUseRequestClient: *************** Remove a Facility  from UseRequest and Schedule *************************");
        useService.RemoveRequest(4);
        */

    }
}