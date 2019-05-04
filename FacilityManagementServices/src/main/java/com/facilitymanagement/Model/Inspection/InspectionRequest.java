package com.facilitymanagement.Model.Inspection;

import com.facilitymanagement.Model.Aggregrate.BridgePattern.Request.IRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class InspectionRequest{

   private IRequest request;

   @Autowired
    public void setRequest(IRequest request) {
        this.request = request;
    }

    public IRequest getRequest() {
        return request;
    }
}
