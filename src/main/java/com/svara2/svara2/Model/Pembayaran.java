package com.svara2.svara2.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pembayaran")
public class Pembayaran {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String namaKonser;
    private int jumlahTiket;
    private String metodePembayaran;
    private double totalBayar;
    private String namaUser;
    public Pembayaran() {
    }
    public Pembayaran(String namaKonser,
                      int jumlahTiket,
                      String metodePembayaran,
                      double totalBayar) {
        this.namaKonser = namaKonser;
        this.jumlahTiket = jumlahTiket;
        this.metodePembayaran = metodePembayaran;
        this.totalBayar = totalBayar;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNamaKonser() {
        return namaKonser;
    }
    public void setNamaKonser(String namaKonser) {
        this.namaKonser = namaKonser;
    }
    public int getJumlahTiket() {
        return jumlahTiket;
    }
    public void setJumlahTiket(int jumlahTiket) {
        this.jumlahTiket = jumlahTiket;
    }
    public String getMetodePembayaran() {
        return metodePembayaran;
    }
    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }
    public double getTotalBayar() {
        return totalBayar;
    }
    public void setTotalBayar(double totalBayar) {
        this.totalBayar = totalBayar;
    }
    public String getNamaUser() {
        return namaUser;
    }
    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }
}