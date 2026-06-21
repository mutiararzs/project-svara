package com.svara2.svara2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.svara2.svara2.Model.User;

public interface UserRepository
        extends JpaRepository<User, Integer> {

}