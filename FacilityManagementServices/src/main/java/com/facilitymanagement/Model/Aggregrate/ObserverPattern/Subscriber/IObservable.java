package com.facilitymanagement.Model.Aggregrate.ObserverPattern.Subscriber;

import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import org.springframework.stereotype.Component;


public interface IObservable {
    void addObserver(IUser user);
    void removeObserver(IUser user);
    void notifyObserver();
    public String listofobserverid();
   /* public boolean isAvailable();
    public void setAvailable(boolean available);*/
}
