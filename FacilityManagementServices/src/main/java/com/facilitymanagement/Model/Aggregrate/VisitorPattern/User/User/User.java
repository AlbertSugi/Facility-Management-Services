package com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User;

import com.facilitymanagement.Model.Aggregrate.ObserverPattern.ObserverNotify.IObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class User implements IUser {

    private int UserID;
    private String Username;
    private String Department;
    private String Usertype;
    private IObserver observer ;
    private String Phonenumber;

    @Override
    public int getUserID() {
        return UserID;
    }

    @Override
    public void setUserID(int userID) {
        UserID = userID;
    }

    @Override
    public String getUsername() {
        return Username;
    }

    @Override
    public void setUsername(String username) {
        Username = username;
    }

    @Override
    public String getDepartment() {
        return Department;
    }

    @Override
    public void setDepartment(String department) {
        Department = department;
    }

    @Override
    public String getUsertype() {
        return Usertype;
    }

    public void setUsertype(String usertype) {
        Usertype = usertype;
    }

    @Override
    public IObserver getObserver() {
        return observer;
    }

    @Override
    @Autowired
    public void setObserver(IObserver observer) {
        this.observer = observer;
    }

    @Override
    public String getPhonenumber() {
        return Phonenumber;
    }

    @Override
    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }
}
