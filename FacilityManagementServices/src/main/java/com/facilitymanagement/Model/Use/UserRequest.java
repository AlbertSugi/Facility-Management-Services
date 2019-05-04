package com.facilitymanagement.Model.Use;

import com.facilitymanagement.Model.Aggregrate.BridgePattern.Request.IRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class UserRequest {
    private IRequest request;

    @Autowired
    public void setRequest(IRequest request) {
        this.request = request;
    }

    public IRequest getRequest() {
        return request;
    }
}
