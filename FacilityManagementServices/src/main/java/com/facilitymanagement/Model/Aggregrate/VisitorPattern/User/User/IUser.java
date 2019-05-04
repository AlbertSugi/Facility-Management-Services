package com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User;

import com.facilitymanagement.Model.Aggregrate.ObserverPattern.ObserverNotify.IObserver;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


public interface IUser {

    public int getUserID();

    public void setUserID(int id);

    public String getUsername();

    public void setUsername(String username);

    public String getDepartment();

    public void setDepartment(String department);

    public String getUsertype();

    public IObserver getObserver();

    public void setObserver(IObserver observer);

    public String getPhonenumber();

    public void setPhonenumber(String phonenumber);

    public void setUsertype(String usertype);


}
