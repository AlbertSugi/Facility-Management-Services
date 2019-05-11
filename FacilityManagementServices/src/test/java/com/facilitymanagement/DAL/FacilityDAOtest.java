package com.facilitymanagement.DAL;

import com.facilitymanagement.DAL.FacilityDAO;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.Facility;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.FacilityDetail;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.IFacility;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FacilityDAO.class)
public class FacilityDAOtest {

    @Autowired
    private FacilityDAO facilityDAO ;

    //How to Implement Mock Here?
    private IFacility facility = new Facility()  ;
    
    private FacilityDetail detail= new FacilityDetail();

    private List<Facility> facilitylist;

    @Before
    public void init(){
        facilityDAO.removeAllData();
        detail.setName("Libary");
        detail.setNumberOfRooms(10);
        detail.setPhoneNumber(5553);
        facility.setFacilityDetail(detail);
        facilityDAO.addNewFacility(facility);

        detail.setName("Law Center");
        detail.setNumberOfRooms(10);
        detail.setPhoneNumber(123456);
        facility.setFacilityDetail(detail);
        facilityDAO.addNewFacility(facility);

        detail.setName("Auditorium");
        detail.setNumberOfRooms(20);
        detail.setPhoneNumber(123456);
        facility.setFacilityDetail(detail);
        facilityDAO.addNewFacility(facility);


    }
    @Test
    public void listFacilities() {
        facilitylist = facilityDAO.listFacilities();
        Assert.assertEquals(3,facilitylist.size());
    }

    @Test
    public void addNewFacility() {
        detail.setName("Stadium");
        detail.setNumberOfRooms(1);
        facility.setFacilityDetail(detail);
        facilityDAO.addNewFacility(facility);
        System.out.println(facility.getFacilityDetail().getName());
        facilitylist = facilityDAO.listFacilities();
        Assert.assertEquals(4,facilitylist.size());

    }
    @Test
    public void addFacilityDetail() {
        facilityDAO.addFacilityDetail("'Auditorium'",345321);
        facility = facilityDAO.getFacilityInformationByFacilityName("Auditorium");
        Assert.assertEquals(345321,facility.getFacilityDetail().getPhoneNumber());
    }
    @Test
    public void removeFacility() {
        facilityDAO.removeFacility("Stadium");
        facilitylist = facilityDAO.listFacilities();
        Assert.assertEquals(3,facilitylist.size());
    }



    @Test
    public void getFacilityInformationByFacilityID() {

        IFacility facility1 =facilityDAO.getFacilityInformationByFacilityID(facility.getFacilityID());
        //System.out.println(facility1.getFacilityID());
        Assert.assertEquals(facility.getFacilityDetail().getName(),facility1.getFacilityDetail().getName());

    }

    @Test
    public void getFacilityInformationByFacilityName() {

        IFacility facility1 = facilityDAO.getFacilityInformationByFacilityName("Law Center");
        Assert.assertEquals("Law Center",facility1.getFacilityDetail().getName());
    }





    @Test
    public void removeAllData() {
        facilityDAO.removeAllData();
        facilitylist = facilityDAO.listFacilities();
        Assert.assertEquals(0,facilitylist.size());
    }

    @After
    public void exit(){
        facilityDAO.removeAllData();
    }


}
