package com.svara2.svara2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.svara2.svara2.Model.Admin;

public interface AdminRepository
        extends JpaRepository<Admin, Integer> {

    Admin findByEmailAndPassword(
            String email,
            String password);

}