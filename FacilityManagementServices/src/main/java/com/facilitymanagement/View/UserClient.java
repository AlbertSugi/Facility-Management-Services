package com.facilitymanagement.View;

import com.facilitymanagement.Model.Aggregrate.ObserverPattern.ObserverNotify.IObserver;
import com.facilitymanagement.Model.User.IUserService;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserClient {

    private IUserService userService ;
    private IObserver observer ;
    private IUser user;

    @Autowired
    public UserClient(IUserService userService, IObserver observer, IUser user) {
        this.userService = userService;
        this.observer = observer;
        this.user = user;
    }

    public void start() {
        System.out.println("\nUserClient: *************** Instantiating UserRequest *************************");

        user.setUsername("Albert");
        user.setPhonenumber("8722032998");
        user.setDepartment("ITRS");
        user.setUsertype("Inspector");
        user.setObserver(observer);
        userService.addNewUser(user);


        user.setUsername("Baboon");
        user.setPhonenumber("7738074658");
        user.setDepartment("Armpit Research");
        user.setUsertype("Technician");
        user.setObserver(observer);
        userService.addNewUser(user);


        user.setUsername("Marian");
        user.setPhonenumber("2173208198");
        user.setDepartment("Engineering");
        user.setUsertype("Inspector");
        user.setObserver(observer);
        userService.addNewUser(user);

        //user = userService.getUserInformation(2);
        //System.out.println(user);

      /*  System.out.println("\nUserClient: *************** Remove a user from the database *************************");
        userService.removeUser(1);
        System.out.println("************ User Removed ************");*/
    }

    }
