package com.facilitymanagement.Model.Aggregrate.BridgePattern.Schedule;

import com.facilitymanagement.Model.Aggregrate.ObserverPattern.Subscriber.IObservable;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.IFacility;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface ISchedule {
     int getFacilityID();
     IFacility getFacility();
     int getScheduleID();
     void setScheduleID(int ScheduleID);
     void setRoomNumber(int roomNumber);
     int getRoomNumber();
     void setStartDate(LocalDate startDate);
     LocalDate getStartDate();
     LocalDate getEndDate();
     void setEndDate(LocalDate endDate);
     String RequestType();
     void setRequestType(String requestType);
     void setFacility(IFacility fac);

     public IObservable getObservable();

     public void setObservable(IObservable observable);
}

