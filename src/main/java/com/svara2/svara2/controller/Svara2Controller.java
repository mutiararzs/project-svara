package com.svara2.svara2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.svara2.svara2.Model.Admin;
import com.svara2.svara2.Model.Pembayaran;
import com.svara2.svara2.Model.User;
import com.svara2.svara2.service.AdminService;
import com.svara2.svara2.service.PembayaranService;
import com.svara2.svara2.service.UserService;

@RestController
@CrossOrigin("*")
public class Svara2Controller {
    @Autowired
    private AdminService adminService;
    @Autowired
    private PembayaranService pembayaranService;
    @Autowired
    private UserService userService;
    
    // Admin
    @PostMapping("/admin/login")
    public ResponseEntity<?> loginAdmin(
        @RequestBody Admin admin) {
    Admin cekAdmin =
            adminService.loginAdmin(
                    admin.getEmail(),
                    admin.getPassword()
            );
    // LOGIN BERHASIL
    if (cekAdmin != null) {
        return ResponseEntity
                .ok(cekAdmin);
    }
    // LOGIN GAGAL
    return ResponseEntity
            .status(401)
            .body("Email atau password salah");
}

    // REGISTER USER
    @PostMapping("/register")
    public User registerUser(
            @RequestBody User user) {
        return userService
                .simpanUser(user);
    }

    // LOGIN USER
    @PostMapping("/user/login")
    public ResponseEntity<?> loginUser(
        @RequestBody User user) {
    User cekUser =
            userService.loginUser(
                    user.getEmail(),
                    user.getPassword()
            );
    // LOGIN BERHASIL
    if (cekUser != null) {
        return ResponseEntity
                .ok(cekUser);
    }
    // LOGIN GAGAL
    return ResponseEntity
            .status(401)
            .body("Email atau password salah");
}

    // GET ALL USERS
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService
                .getAllUsers();
    }
    // GET USER BY ID
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(
            @PathVariable int id) {
        User user =
                userService
                        .getUserById(id);
        if (user != null) {
            return ResponseEntity
                    .ok(user);
        }
        return ResponseEntity
                .status(404)
                .body("User tidak ditemukan");
    }
    // UPDATE USER
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable int id,
            @RequestBody User user) {
        User updateUser =
                userService
                        .updateUser(id, user);
        if (updateUser != null) {
            return ResponseEntity
                    .ok(updateUser);
        }
        return ResponseEntity
                .status(404)
                .body("User tidak ditemukan");
    }
    // DELETE USER
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable int id) {
        boolean deleted =
                userService
                        .deleteUser(id);
        if (deleted) {
            return ResponseEntity
                    .ok("User berhasil dihapus");
        }
        return ResponseEntity
                .status(404)
                .body("User tidak ditemukan");
    }

    // PEMBAYARAN
    @PostMapping("/pembayaran")
    public Pembayaran simpanPembayaran(
            @RequestBody Pembayaran pembayaran) {
        return pembayaranService
                .simpanPembayaran(
                        pembayaran
                );
    }

    // RIWAYAT PEMBELIAN PER USER
    @GetMapping("/pembayaran/riwayat")
    public List<Pembayaran> getRiwayatPembayaran(
            @RequestParam String namaUser) {
        return pembayaranService
                .getRiwayatByUser(namaUser);
    }
}