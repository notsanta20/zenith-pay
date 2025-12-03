package com.santa.user_service.service;

import com.santa.user_service.exception.UserNotFoundException;
import com.santa.user_service.model.User;
import com.santa.user_service.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class UserService {

    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public User getUser(Integer id){
        return userRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));
    }

    public User updateUser(User user){
        User currentUser = getUser(user.getUser_id());

        currentUser.setFull_name(user.getFull_name());
        currentUser.setDob(user.getDob());
        currentUser.setPhone(user.getPhone());
        currentUser.setCreated_at(LocalDate.now());
        currentUser.setUpdated_at(new Date());

        return currentUser;
    }
}
