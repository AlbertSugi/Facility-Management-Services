package com.facilitymanagement.DAL;

import com.facilitymanagement.Model.Aggregrate.BridgePattern.Request.IRequest;
import com.facilitymanagement.Model.Aggregrate.BridgePattern.Request.Request;
import com.facilitymanagement.Model.Aggregrate.BridgePattern.Schedule.ISchedule;
import com.facilitymanagement.Model.Aggregrate.BridgePattern.Schedule.Schedule;
import com.facilitymanagement.Model.Aggregrate.Technology.Technology;
import com.facilitymanagement.Model.Aggregrate.Technology.iTechnology;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.Facility;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.FacilityDetail;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.User;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.Usertype.Inspector;
import com.facilitymanagement.Model.Inspection.InspectionRequest;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InspectionDAO.class)
class InspectionDAOTest {

    @Autowired
    private InspectionDAO inspectionDAO;
    private InspectionRequest inspectionRequest = new InspectionRequest();
    private IRequest request = new Request();
    private ISchedule schedule = new Schedule();
    private IUser user = new User();
    private Inspector inspector = new Inspector();
    private iTechnology technology = new Technology();
    private FacilityDAO facilityDAO = new FacilityDAO();

    @Before
    public void init(){
        FacilityDetail detail = new FacilityDetail();
        Facility facility = new Facility();
        detail.setName("InspectionTest");
        detail.setNumberOfRooms(10);
        detail.setPhoneNumber(5553);
        facility.setFacilityDetail(detail);
        facilityDAO.addNewFacility(facility);


    }
    @Test
    void getID() {

    }

    @Test
    void removeAllData() {
    }

    @Test
    void removeInspectionRequest() {
    }

    @Test
    void addInsRequest() {
    }

    @Test
    void isInUseDuringInterval() {
    }

    @Test
    void vacateInsFacility() {
    }

    @Test
    void getInfobyID() {
    }
}