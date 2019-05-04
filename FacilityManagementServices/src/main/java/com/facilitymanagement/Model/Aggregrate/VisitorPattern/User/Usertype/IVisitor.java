package com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.Usertype;

import com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility.IFacility;
import org.springframework.stereotype.Component;

@Component
public interface IVisitor {
    public void visitFacility(IFacility facility);
}
