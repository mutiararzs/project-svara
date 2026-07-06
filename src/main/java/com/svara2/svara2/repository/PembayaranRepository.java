package com.svara2.svara2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.svara2.svara2.Model.Pembayaran;

public interface PembayaranRepository
        extends JpaRepository<Pembayaran, Integer> {

    // Ambil semua riwayat pembelian milik satu user, terbaru duluan
    List<Pembayaran> findByNamaUserOrderByIdDesc(String namaUser);
}