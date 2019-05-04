package com.facilitymanagement.Model.Aggregrate.Technology;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class Technology implements iTechnology{

    private ArrayList<Integer> technologycode = new ArrayList<Integer>();
    private ArrayList<String> technologytype = new ArrayList<String>();

    public void addtechnology(int code, String type){
        technologycode.add(code);
        technologytype.add(type);
    }

    public void deleteTechnology(int code){
        boolean exist = false;
        for(int i=0;i<this.technologycode.size();i++){
            if(this.technologycode.get(i) == code){

                exist=true;
                technologycode.remove(code);
                technologytype.remove(technologytype.get(i));
                break;
            }
        }
        if (exist){
            System.out.println("The equipment "+ code +"has been removed from the list");
        }
        else{
            System.out.println(code+"does not exist in the list");
        }
    }

    public String ListoftechCode(){
        String Appliance="";
        for(int i=0;i<this.technologycode.size();i++){
            Appliance += String.valueOf(this.technologycode.get(i))+" ,";
        }
        return Appliance;
    }
    public String ListoftechType(){
        String Appliance="";
        for(int i=0;i<this.technologytype.size();i++){
            Appliance += this.technologytype.get(i)+" ,";
        }
        return Appliance;
    }

    public void setTechnologycode(ArrayList<Integer> m){
        technologycode = m;
    }
    public void setTechnologytype(ArrayList<String> m){
        technologytype = m;
    }
}
