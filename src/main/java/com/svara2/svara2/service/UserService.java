package com.svara2.svara2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svara2.svara2.Model.User;
import com.svara2.svara2.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    // REGISTER USER
    public User simpanUser(
            User user) {
        return userRepository
                .save(user);
    }

    // LOGIN USER
    public User loginUser(
            String email,
            String password) {
        return userRepository
                .findByEmailAndPassword(
                        email,
                        password
                );
    }
    
    // GET ALL USERS
    public List<User> getAllUsers() {
        return userRepository
                .findAll();
    }
 
    // GET USER BY ID
    public User getUserById(
            int id) {
        return userRepository
                .findById(id)
                .orElse(null);
    }

    // UPDATE USER  
    public User updateUser(
            int id,
            User user) {
        User oldUser =
                userRepository
                        .findById(id)
                        .orElse(null);
        if (oldUser == null) {
            return null;
        }
        oldUser.setNama(
                user.getNama());
        oldUser.setEmail(
                user.getEmail());
        oldUser.setNomorTelepon(
                user.getNomorTelepon());
        oldUser.setTanggalLahir(
                user.getTanggalLahir());
        return userRepository
                .save(oldUser);
    }

    // DELETE USER
    public boolean deleteUser(
            int id) {
        User user =
                userRepository
                        .findById(id)
                        .orElse(null);
        if (user == null) {
            return false;
        }
        userRepository
                .deleteById(id);
        return true;
    }
}