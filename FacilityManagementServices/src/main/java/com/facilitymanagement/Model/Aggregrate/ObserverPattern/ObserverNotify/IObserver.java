package com.facilitymanagement.Model.Aggregrate.ObserverPattern.ObserverNotify;


import org.springframework.stereotype.Component;


public interface IObserver {
     void update(String information);
    String getType();

}
