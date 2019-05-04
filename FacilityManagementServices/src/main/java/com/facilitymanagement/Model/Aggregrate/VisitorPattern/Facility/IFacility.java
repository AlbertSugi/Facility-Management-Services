package com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility;

import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.Usertype.IVisitor;
import org.springframework.stereotype.Component;

@Component
public interface IFacility {
    public void accept(IVisitor visitor);

    public FacilityDetail getFacilityDetail();
    public void setFacilityDetail(FacilityDetail details);

    public void setFacilityID(int facilityID);

    public int getFacilityID();


}
