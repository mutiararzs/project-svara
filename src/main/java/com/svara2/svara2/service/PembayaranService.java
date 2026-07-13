package com.svara2.svara2.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        SimpleDateFormat formatTanggal =
                new SimpleDateFormat("d MMMM yyyy, HH:mm", new Locale("id", "ID"));
        pembayaran.setTanggalBeli(formatTanggal.format(new Date()));

        return pembayaranRepository.save(pembayaran);
    }

    public List<Pembayaran> getRiwayatByUser(String namaUser) {
        return pembayaranRepository.findByNamaUserOrderByIdDesc(namaUser);
    }
}