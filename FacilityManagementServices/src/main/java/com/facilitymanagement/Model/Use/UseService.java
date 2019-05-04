package com.facilitymanagement.Model.Use;

import com.facilitymanagement.DAL.ScheduleDAO;
import com.facilitymanagement.DAL.UseRequestDAO;
import com.facilitymanagement.DAL.UserDAO;
import com.facilitymanagement.Model.Aggregrate.BridgePattern.Request.IRequest;
import com.facilitymanagement.Model.Aggregrate.BridgePattern.Schedule.ISchedule;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class UseService implements IUseService {


    private UseRequestDAO useRequestDAO ;
    private UserDAO userDAO;
    private IUser user ;
    private Scanner scanner =  new Scanner(System.in);
    private ScheduleDAO scheduleDAO;
    private UserRequest userRequest;

    @Autowired
    public UseService(UseRequestDAO useRequestDAO, UserDAO userDAO, IUser user, ScheduleDAO scheduleDAO,UserRequest userRequest) {
        this.useRequestDAO = useRequestDAO;
        this.userDAO = userDAO;
        this.user = user;
        this.scheduleDAO = scheduleDAO;
        this.userRequest = userRequest;
    }

    public void AddRequest(UserRequest userRequest) {
        user = userDAO.getInformationByuserID(userRequest.getRequest().getiUser().getUserID());
        if (!(useRequestDAO.isInUseDuringInterval(userRequest))) {
            userRequest.getRequest().getSchedule().setRequestType("Use");
            useRequestDAO.AddUseRequest(userRequest);
            System.out.println("Successfully added your request");
        }
        else {
            Subscribe();
        }
    }


    public void isInUseDuringInterval(UserRequest userRequest) {
        useRequestDAO.isInUseDuringInterval(userRequest);
    }


    public void RemoveRequest(int ID) {
        userRequest = useRequestDAO.getInfobyID(ID,userRequest);
        useRequestDAO.removeUseRequest(userRequest);
    }


    public void VacateFacility(int ID) {
        userRequest = useRequestDAO.getInfobyID(ID,userRequest);
        useRequestDAO.VacateInsFacility(userRequest);
    }


    public void clearAllUseData() {
        useRequestDAO.removeAllData();
    }


    public UserRequest getInfobyID(int ID) {
        return useRequestDAO.getInfobyID(ID,userRequest);
    }


    private void Subscribe() {
        System.out.println("Sorry this room is not available in this time, " +
                "would you like to get notified when its available in this time? Y/N ");
        String answer = scanner.nextLine();
        if (answer.equals("Y")) {
            ISchedule schedule = scheduleDAO.getInfobyID(useRequestDAO.getID());
            scheduleDAO.addSubscriber(schedule, user);


            System.out.println("You will be notified when the room is available in the selected time via your choice of service");
        } else if (answer.equals("N")) {
            System.out.println("Please choose another room");

        } else {
            System.out.println("Wrong input, please try again");
            Subscribe();
        }
    }
}
