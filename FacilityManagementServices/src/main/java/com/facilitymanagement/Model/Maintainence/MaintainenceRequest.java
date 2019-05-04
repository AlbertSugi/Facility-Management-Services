package com.facilitymanagement.Model.Maintainence;

import com.facilitymanagement.Model.Aggregrate.BridgePattern.Request.IRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MaintainenceRequest {
    private IRequest request;
    private int cost;

    @Autowired
    public MaintainenceRequest(IRequest request) {
        this.request = request;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public IRequest getRequest() {
        return request;
    }
}
