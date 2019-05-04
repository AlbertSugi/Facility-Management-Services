package com.facilitymanagement.Model.Aggregrate.VisitorPattern.Facility;


import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.Usertype.IVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class Facility implements IFacility{



	private int facilityID;


	private FacilityDetail facilityDetail ;



	@Override
	public void accept(IVisitor visitor) {
		visitor.visitFacility(this);
	}



	public FacilityDetail getFacilityDetail() {
		return this.facilityDetail;
	}

	@Autowired
	public void setFacilityDetail(FacilityDetail details) {
		this.facilityDetail = details;
	}

	public void setFacilityID(int facilityID) {
		this.facilityID = facilityID;
	}

	public int getFacilityID() {
		return this.facilityID;
	}

}
