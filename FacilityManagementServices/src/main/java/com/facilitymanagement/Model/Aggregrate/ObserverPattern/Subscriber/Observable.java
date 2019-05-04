package com.facilitymanagement.Model.Aggregrate.ObserverPattern.Subscriber;

import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Observable implements  IObservable {

    private List<IUser> subscribers = new ArrayList<>();
   /* private boolean Available = true;

    public boolean isAvailable() {
        return Available;
    }

    public void setAvailable(boolean available) {
        Available = available;
    }*/

    @Override
    public void addObserver(IUser user) {
        subscribers.add(user);
    }

    @Override
    public void removeObserver(IUser user) {
        subscribers.remove(user);
    }

    @Override
    public void notifyObserver() {
        for(IUser user : subscribers ){
            user.getObserver().update(user.getPhonenumber());
        };
    }

    public String listofobserverid(){
        String observer="";
        for(int i=0;i<this.subscribers.size();i++){
            observer += String.valueOf(this.subscribers.get(i).getUserID())+",";
        }
        return observer;
    }



}
