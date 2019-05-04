package com.facilitymanagement.Model.User;

import com.facilitymanagement.DAL.UserDAO;
import com.facilitymanagement.Model.Aggregrate.VisitorPattern.User.User.IUser;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private UserDAO userDAO = new UserDAO();
    @Override
    public void clearAllUserData() {
        userDAO.removeAllData();
    }

    @Override
    public void addNewUser(IUser user) {
        userDAO.addNewUser(user);
    }

    @Override
    public IUser getUserInformation(int ID) {
        return userDAO.getInformationByuserID(ID);
    }

    @Override
    public void removeUser(int id) {
        userDAO.removeuser(id);
    }
}
