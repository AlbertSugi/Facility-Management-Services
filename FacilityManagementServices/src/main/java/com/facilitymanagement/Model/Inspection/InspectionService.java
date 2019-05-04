package com.facilitymanagement.Model.Inspection;

import com.facilitymanagement.DAL.InspectionDAO;
import com.facilitymanagement.DAL.ScheduleDAO;
import com.facilitymanagement.DAL.UserDAO;
import com.facilitymanagement.Model.Aggregrate.BridgePattern.Schedule.ISchedule;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class InspectionService implements iInspectionService {

    private  InspectionDAO inspectionDAO;
    private UserDAO userDAO;
    private InspectionRequest inspection;
    private  IUser user;
    private  ScheduleDAO scheduleDAO;

    private Scanner scanner =  new Scanner(System.in);

    @Autowired
    public InspectionService(InspectionDAO inspectionDAO, UserDAO userDAO, InspectionRequest inspection, IUser user, ScheduleDAO scheduleDAO) {
        this.inspectionDAO = inspectionDAO;
        this.userDAO = userDAO;
        this.inspection = inspection;
        this.user = user;
        this.scheduleDAO = scheduleDAO;
    }



    public void AddRequest(InspectionRequest inspectionRequest) {
        user = userDAO.getInformationByuserID(inspectionRequest.getRequest().getiUser().getUserID());
        if (!(inspectionDAO.isInUseDuringInterval(inspectionRequest))) {
            inspectionRequest.getRequest().getSchedule().setRequestType("Inspection");
            inspectionDAO.AddInsRequest(inspectionRequest);
            System.out.println("Successfully added your request");
        }
        else {
            Subscribe();
        }

    }


    public void isInUseDuringInterval(InspectionRequest inspectionRequest) {
        inspectionDAO.isInUseDuringInterval(inspectionRequest);
    }


    public void RemoveRequest(int ID) {

        inspection =inspectionDAO.getInfobyID(ID,inspection);
        inspection.getRequest().getSchedule().getObservable().notifyObserver();
        inspectionDAO.removeInspectionRequest(inspection);
    }


    public void VacateFacility(int ID) {
        inspection=inspectionDAO.getInfobyID(ID,inspection);
        inspectionDAO.VacateInsFacility(inspection);
    }


    public void clearAllUseData() {
        inspectionDAO.removeAllData();
    }


    public InspectionRequest visitgetInfobyID(int ID) {
        inspection =inspectionDAO.getInfobyID(ID,inspection);
        return inspection;
    }


    //Observer
    private void Subscribe(){
        System.out.println("Sorry this room is not available in this time, " +
                "would you like to get notified when its available in this time? Y/N ");
        String answer = scanner.nextLine();
        if(answer.equals("Y")){
            ISchedule schedule = scheduleDAO.getInfobyID(inspectionDAO.getID());
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
