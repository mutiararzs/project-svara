package com.svara2.svara2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.svara2.svara2.Model.Pembayaran;

public interface PembayaranRepository
        extends JpaRepository<Pembayaran, Integer> {

}