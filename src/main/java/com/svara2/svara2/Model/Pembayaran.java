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
    private String namaKonser; // contoh: "Coldplay: Music of the Spheres", "Sheila on 7"
    private String kategoriTiket; // contoh: "Tribune Gold", "Festival A", "Tribune Silver"
    private String namaUser; // nama user yang login & membeli tiket, contoh: "Keisya"
    private String tanggalBeli; // otomatis diisi backend saat transaksi disimpan
    private int jumlahTiket;
    private String metodePembayaran;
    private double totalBayar;
    public Pembayaran() {
    }
    public Pembayaran(String namaKonser,
                      String kategoriTiket,
                      String namaUser,
                      int jumlahTiket,
                      String metodePembayaran,
                      double totalBayar) {
        this.namaKonser = namaKonser;
        this.kategoriTiket = kategoriTiket;
        this.namaUser = namaUser;
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
    public String getKategoriTiket() {
        return kategoriTiket;
    }
    public void setKategoriTiket(String kategoriTiket) {
        this.kategoriTiket = kategoriTiket;
    }
    public String getNamaUser() {
        return namaUser;
    }
    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }
    public String getTanggalBeli() {
        return tanggalBeli;
    }
    public void setTanggalBeli(String tanggalBeli) {
        this.tanggalBeli = tanggalBeli;
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
}