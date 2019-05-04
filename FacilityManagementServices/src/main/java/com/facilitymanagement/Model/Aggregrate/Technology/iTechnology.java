package com.facilitymanagement.Model.Aggregrate.Technology;

import org.springframework.stereotype.Component;

import java.util.ArrayList;



public interface iTechnology {

    ArrayList<Integer> technologycode = new ArrayList<Integer>();
    ArrayList<String> technologytype = new ArrayList<String>();

    public void addtechnology(int technologycode, String technologytype);

    public void deleteTechnology(int technologycode);

    public String ListoftechCode();
    public String ListoftechType();

    public void setTechnologycode(ArrayList<Integer> m);
    public void setTechnologytype(ArrayList<String> m);


}
