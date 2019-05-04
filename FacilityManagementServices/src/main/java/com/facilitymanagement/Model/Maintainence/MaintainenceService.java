package com.facilitymanagement.Model.Maintainence;

import com.facilitymanagement.DAL.MaintainenceDAO;
import com.facilitymanagement.DAL.ScheduleDAO;
import com.facilitymanagement.DAL.UserDAO;
import com.facilitymanagement.Model.Aggregrate.BridgePattern.Schedule.ISchedule;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class MaintainenceService implements IMaintainenceService{

    private MaintainenceRequest maintainenceRequest ;
    private MaintainenceDAO maintainenceDAO ;
    private UserDAO userDAO ;
    private IUser user ;
    private ScheduleDAO scheduleDAO;
    private Scanner scanner =  new Scanner(System.in);

    @Autowired
    public MaintainenceService(MaintainenceRequest maintainenceRequest, MaintainenceDAO maintainenceDAO, UserDAO userDAO, IUser user, ScheduleDAO scheduleDAO) {
        this.maintainenceRequest = maintainenceRequest;
        this.maintainenceDAO = maintainenceDAO;
        this.userDAO = userDAO;
        this.user = user;
        this.scheduleDAO = scheduleDAO;
    }

    public void AddRequest(MaintainenceRequest maintainenceRequest) {

        user = userDAO.getInformationByuserID(maintainenceRequest.getRequest().getiUser().getUserID());
        if (!(maintainenceDAO.isInUseDuringInterval(maintainenceRequest))) {
            maintainenceRequest.getRequest().getSchedule().setRequestType("Maintainence");
            maintainenceDAO.AddmaintRequest(maintainenceRequest);
            System.out.println("Successfully added your request");
        }
        else {
            Subscribe();
        }
    }


    public void isInUseDuringInterval(MaintainenceRequest maintainenceRequest) {
        maintainenceDAO.isInUseDuringInterval(maintainenceRequest);
    }


    public void RemoveRequest(int ID) {
        maintainenceRequest=maintainenceDAO.getInfobyID(ID,maintainenceRequest);
        maintainenceDAO.removeMaintainenceRequest(maintainenceRequest);
    }


    public void VacateFacility(int ID) {
        maintainenceRequest=maintainenceDAO.getInfobyID(ID,maintainenceRequest);
        maintainenceDAO.VacateInsFacility(maintainenceRequest);
    }


    public void clearAllUseData() {
        maintainenceDAO.removeAllData();
    }


    public MaintainenceRequest getInfobyID(int ID) {
        maintainenceRequest =maintainenceDAO.getInfobyID(ID,maintainenceRequest);
    return maintainenceRequest;
    }





    private void Subscribe(){
        System.out.println("Sorry this room is not available in this time, " +
                "would you like to get notified when its available in this time? Y/N ");
        String answer = scanner.nextLine();
        if(answer.equals("Y")){
            ISchedule schedule = scheduleDAO.getInfobyID(maintainenceDAO.getID());
            scheduleDAO.addSubscriber(schedule,user);


            System.out.println("You will be notified when the room is available in the selected time via your choice of service");
        }
        else if(answer.equals("N")){
            System.out.println("Please choose another room");

        }
        else {
            System.out.println("Wrong input, please try again");
            Subscribe();
        }


    }
}
