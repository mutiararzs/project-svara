package com.svara2.svara2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svara2.svara2.Model.Admin;
import com.svara2.svara2.repository.AdminRepository;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    // SIMPAN ADMIN
    public Admin simpanAdmin(
            Admin admin) {
        return adminRepository
                .save(admin);
    }

    // LOGIN ADMIN
    public Admin loginAdmin(
            String email,
            String password) {
        return adminRepository
                .findByEmailAndPassword(
                        email,
                        password
                );
        }
}