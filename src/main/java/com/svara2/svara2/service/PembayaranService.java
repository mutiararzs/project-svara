package com.svara2.svara2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svara2.svara2.Model.Pembayaran;
import com.svara2.svara2.repository.PembayaranRepository;

@Service
public class PembayaranService {
    @Autowired
    private PembayaranRepository pembayaranRepository;
    public Pembayaran simpanPembayaran(
            Pembayaran pembayaran) {
        return pembayaranRepository.save(pembayaran);
    }
}