package com.facilitymanagement.Model.User;

import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import org.springframework.stereotype.Component;

@Component
public interface IUserService {
    void clearAllUserData();
    void addNewUser(IUser user);
    IUser getUserInformation(int ID);
    void removeUser(int id);
}
