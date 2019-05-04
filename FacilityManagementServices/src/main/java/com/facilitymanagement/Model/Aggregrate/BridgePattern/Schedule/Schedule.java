package com.facilitymanagement.Model.Aggregrate.BridgePattern.Schedule;

import com.facilitymanagement.Model.Aggregrate.ObserverPattern.Subscriber.IObservable;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.IFacility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
@Qualifier("schedule")
public class Schedule implements ISchedule{
    private int ScheduleID;
    private IFacility facility;
    private int roomNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private String requestType;
    private IObservable observable;



    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public IFacility getFacility() {
        return facility;
    }

    @Autowired
    public void setFacility(IFacility facility) {
        this.facility = facility;
    }

    public int getScheduleID() {
        return ScheduleID;
    }

    public void setScheduleID(int scheduleID) {

        ScheduleID = scheduleID;
    }

    public int getFacilityID(){
        return facility.getFacilityID();
    };
    public void setRoomNumber(int roomNumber){
        this.roomNumber = roomNumber;
    };
    public int getRoomNumber(){
        return roomNumber;
    };
    public void setStartDate(LocalDate startDate){
        this.startDate = startDate;
    };
    public LocalDate getStartDate(){
        return startDate;
    };
    public void setEndDate(LocalDate endDate){
        this.endDate = endDate;
    };
    public LocalDate getEndDate(){
        return endDate;
    };

    public String RequestType(){
        return requestType;
    };

    public IObservable getObservable() {
        return observable;
    }

    @Autowired
    public void setObservable(IObservable observable) {
        this.observable = observable;
    }
}



