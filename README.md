# SVARA — Sistem Verifikasi & Administrasi Data Pengguna

![Status](https://img.shields.io/badge/status-deployed-success)
![Backend](https://img.shields.io/badge/backend-Spring%20Boot%203.4.5-6DB33F?logo=springboot&logoColor=white)
![Database](https://img.shields.io/badge/database-Supabase%20(PostgreSQL)-3ECF8E?logo=supabase&logoColor=white)
![Frontend](https://img.shields.io/badge/frontend-Tailwind%20CSS-38BDF8?logo=tailwindcss&logoColor=white)
![License](https://img.shields.io/badge/license-Academic%20Project-lightgrey)

> Proyek ini disusun sebagai bagian dari tugas mata kuliah (proyek akademik) untuk mendemonstrasikan penerapan arsitektur **client–server**, **REST API**, dan **manajemen basis data cloud** dalam sebuah studi kasus Manajemen Pengguna (*User Management System*).

---

## 1. Deskripsi Proyek

**SVARA** adalah aplikasi web untuk manajemen pengguna (*User Management*) yang memisahkan secara tegas antara **lapisan antarmuka (frontend)**, **lapisan logika bisnis & API (backend)**, dan **lapisan penyimpanan data (database)**. Aplikasi ini mengimplementasikan alur kerja administratif standar berupa autentikasi admin, serta operasi **CRUD** (*Create, Read, Update, Delete*) terhadap data pengguna, lengkap dengan mekanisme proteksi halaman berbasis sesi di sisi klien.

Tujuan akademik dari proyek ini adalah untuk menunjukkan pemahaman terhadap:

- Perancangan **RESTful API** menggunakan Spring Boot (arsitektur *Controller–Service–Repository*).
- Konektivitas backend ke basis data **PostgreSQL** yang di-hosting secara *cloud* melalui **Supabase**.
- Proses **deployment** aplikasi backend ke platform *Platform-as-a-Service* (**Railway**).
- Penerapan mekanisme **proteksi rute (route guard)** sederhana pada sisi klien menggunakan `sessionStorage`.
- Interaksi asynchronous antara frontend dan backend menggunakan `fetch API` (format JSON).

### 1.1 Arsitektur Teknologi

```
┌───────────────────────┐        HTTPS / JSON        ┌──────────────────────────┐        JDBC (PostgreSQL)      ┌───────────────────────┐
│        FRONTEND        │ ──────────────────────────▶ │         BACKEND           │ ─────────────────────────────▶ │       DATABASE         │
│  HTML5 + Tailwind CSS   │ ◀────────────────────────── │  Spring Boot (REST API)   │ ◀───────────────────────────── │  Supabase (PostgreSQL) │
│  Material Symbols       │        Response JSON        │  Hosted on Railway         │                                │  Terkelola (Managed)    │
└───────────────────────┘                              └──────────────────────────┘                                └───────────────────────┘
```

| Lapisan | Teknologi | Keterangan |
|---|---|---|
| **Frontend** | HTML5, Tailwind CSS (via CDN), Google Material Symbols | Halaman statis (`login.html`, `LoginAdmin.html`, `LoginUser.html`, `Admin.html`, `HomePage.html`, `Verifikasi.html`, `Pembayaran.html`) yang dikonsumsi langsung dari `src/main/resources/static` sebagai bagian dari aplikasi Spring Boot. |
| **Backend / API** | Java 17, Spring Boot 3.4.5 (`spring-boot-starter-web`, `spring-boot-starter-data-jpa`) | Menyediakan REST endpoint untuk autentikasi Admin/User dan operasi CRUD data pengguna melalui `Svara2Controller`. |
| **Database** | Supabase (PostgreSQL, terhubung via connection pooler) | Diakses backend melalui Spring Data JPA dengan dialek `PostgreSQLDialect`; skema tabel dikelola otomatis (`ddl-auto: update`). |
| **Deployment** | Railway | Backend berjalan sebagai layanan *live* yang dapat diakses publik melalui URL produksi. |

### 1.2 Struktur Modul Backend

```
com.svara2.svara2
├── controller/   → Svara2Controller.java      (REST endpoint)
├── service/      → AdminService, UserService, PembayaranService
├── repository/   → AdminRepository, UserRepository, PembayaranRepository (Spring Data JPA)
└── Model/        → Admin, User, Pembayaran     (Entity JPA)
```

---

## 2. Live Deployment

Backend & API SVARA telah di-deploy dan aktif secara publik pada platform **Railway**:

**🔗 Base URL API:**
[`https://project-svara-production.up.railway.app`](https://project-svara-production.up.railway.app)

Seluruh halaman frontend (`.html`) juga diseajikan langsung oleh backend Spring Boot melalui folder `static`, sehingga URL di atas dapat diakses langsung dari browser tanpa perlu menjalankan *server* tambahan.

Beberapa endpoint API utama yang tersedia:

| Method | Endpoint | Deskripsi |
|---|---|---|
| `POST` | `/admin/login` | Autentikasi login Admin |
| `POST` | `/user/login` | Autentikasi login User |
| `POST` | `/register` | Registrasi User baru |
| `GET` | `/users` | Mengambil seluruh data User |
| `GET` | `/users/{id}` | Mengambil data User berdasarkan ID |
| `PUT` | `/users/{id}` | Memperbarui data User |
| `DELETE` | `/users/{id}` | Menghapus data User |
| `POST` | `/pembayaran` | Menyimpan data transaksi/pembayaran |

---

## 3. Panduan Pengujian

Panduan berikut disusun sebagai skenario pengujian bertahap agar penguji dapat memverifikasi setiap fitur utama aplikasi secara sistematis. Disarankan untuk membuka aplikasi melalui:

```
https://project-svara-production.up.railway.app/login.html
```

> ⚠️ **Sebelum memulai pengujian**, mohon lakukan **Hard Refresh** terlebih dahulu. Lihat bagian [Troubleshooting](#4-troubleshooting) untuk penjelasan lengkap.

### Skenario A — Keamanan (Proteksi Bypass URL)

Skenario ini menguji mekanisme *client-side route guard* yang mencegah akses langsung ke halaman Admin tanpa melalui proses login.

1. Buka browser dalam kondisi **belum login** (atau buka *Incognito/Private Window* baru).
2. Ketikkan langsung URL halaman dashboard admin pada address bar:
   ```
   https://project-svara-production.up.railway.app/Admin.html
   ```
3. **Hasil yang diharapkan:** Muncul peringatan (`alert`) *"Akses ditolak! Anda harus login sebagai Admin terlebih dahulu."*, kemudian sistem secara otomatis mengarahkan (redirect) kembali ke halaman `LoginAdmin.html`.
4. **Penjelasan teknis:** Setiap kali halaman `Admin.html` dimuat, skrip pada halaman tersebut memeriksa nilai kunci `isAdminLoggedIn` pada `sessionStorage` milik browser. Apabila nilainya bukan `"true"` (misalnya karena diakses langsung tanpa login, atau sesi telah berakhir), pengguna akan ditolak dan dialihkan. Mekanisme ini membuktikan bahwa akses ke halaman administratif **tidak dapat di-bypass** hanya dengan mengetikkan URL secara langsung.

### Skenario B — Login Admin

1. Pada halaman `LoginAdmin.html`, masukkan kredensial akun Admin yang valid (email & password) sesuai data yang tersimpan pada tabel `Admin` di basis data Supabase.
2. Klik tombol **Login**.
3. **Hasil yang diharapkan:**
   - Jika kredensial **valid** → sistem menyimpan status sesi (`sessionStorage.setItem("isAdminLoggedIn", "true")`) dan mengarahkan pengguna ke halaman `Admin.html` (dashboard).
   - Jika kredensial **tidak valid** → API mengembalikan respons HTTP `401 Unauthorized` dengan pesan *"Email atau password salah"*, dan pengguna tetap berada di halaman login.

> 💡 *Catatan:* Kredensial akun Admin bersifat rahasia dan tidak dicantumkan dalam dokumen ini demi menjaga keamanan data. 

### Skenario C — Fitur CRUD Data User

Skenario ini dilakukan **setelah** berhasil login sebagai Admin (Skenario B) dan berada pada halaman `Admin.html`.

**a) Read (Menampilkan Data)**
1. Setelah dashboard admin berhasil dimuat, sistem secara otomatis memanggil endpoint `GET /users` dan menampilkan seluruh daftar pengguna dalam bentuk tabel.
2. **Hasil yang diharapkan:** Data pengguna (nama, email, nomor telepon, tanggal lahir, dsb.) tampil sesuai dengan data yang tersimpan di basis data Supabase secara *real-time*.

**b) Update (Memperbarui Data via Prompt)**
1. Klik tombol **Edit** pada salah satu baris data pengguna.
2. Browser akan menampilkan serangkaian kotak dialog `prompt()` secara berurutan untuk memasukkan: **Nama baru**, **Email baru**, **Nomor telepon baru**, dan **Tanggal lahir baru**.
3. Isi setiap kolom sesuai kebutuhan pengujian, lalu tekan **OK**.
4. **Hasil yang diharapkan:** Sistem mengirimkan permintaan `PUT /users/{id}` ke backend, tabel data pada dashboard diperbarui secara otomatis (`loadUsers()` dipanggil ulang), dan perubahan tersimpan permanen di database Supabase.

**c) Delete (Menghapus Data dengan Konfirmasi)**
1. Klik tombol **Delete** pada salah satu baris data pengguna.
2. **Hasil yang diharapkan:** Sistem meminta konfirmasi (`confirm()`) sebelum melanjutkan proses penghapusan.
3. Jika penguji memilih **OK/Yes**, sistem mengirimkan permintaan `DELETE /users/{id}`, data pengguna terkait dihapus secara permanen dari basis data, dan tabel diperbarui otomatis.
4. Jika penguji memilih **Cancel**, tidak ada perubahan yang terjadi pada data.

---

## 4. Troubleshooting

### ⚠️ Wajib Lakukan *Hard Refresh* Sebelum Pengujian

Karena proyek ini menggunakan **Tailwind CSS via CDN** dan berkas JavaScript statis (`svara.js`) yang disajikan langsung oleh server, browser cenderung **menyimpan versi lama (cache)** dari berkas-berkas tersebut, terutama jika halaman pernah dibuka sebelumnya. Hal ini dapat menyebabkan:

- Tampilan halaman terlihat tidak rapi atau gaya (*styling*) tidak termuat dengan benar.
- Perubahan kode terbaru yang telah di-deploy tidak langsung terlihat oleh penguji.
- Fungsi JavaScript (seperti validasi sesi atau pemanggilan API) berjalan menggunakan versi skrip yang usang.

**Solusi:** Sebelum memulai pengujian — atau kapan pun tampilan terasa tidak sesuai — lakukan **Hard Refresh** pada browser untuk memaksa pengambilan ulang seluruh berkas dari server (bukan dari cache):

| Sistem Operasi | Kombinasi Tombol |
|---|---|
| Windows / Linux | `Ctrl` + `F5` atau `Ctrl` + `Shift` + `R` |
| macOS | `Cmd` + `Shift` + `R` |

Apabila masalah tampilan masih berlanjut setelah Hard Refresh, disarankan untuk membuka aplikasi melalui jendela **Incognito/Private Browsing** guna memastikan tidak ada data cache maupun `sessionStorage` lama yang memengaruhi hasil pengujian.

### Isu Umum Lainnya

| Gejala | Kemungkinan Penyebab | Solusi |
|---|---|---|
| Data tidak muncul di dashboard Admin | Backend Railway sedang dalam kondisi *sleep/cold start* atau koneksi ke Supabase belum aktif | Tunggu beberapa detik lalu refresh halaman; periksa status layanan pada dashboard Railway |
| Redirect terus terjadi walau sudah login | Sesi `sessionStorage` tersimpan di tab/browser yang berbeda | `sessionStorage` bersifat per-tab; pastikan login dan pengujian dilakukan pada tab yang sama |
| Tombol Edit/Delete tidak merespons | Skrip lama masih ter-cache | Lakukan Hard Refresh sesuai instruksi di atas |

---

## 5. Cara Menjalankan Proyek Secara Lokal (Opsional)

```bash
# 1. Clone repository
git clone <URL_REPOSITORY_ANDA>
cd svara2

# 2. Konfigurasi koneksi database (Supabase) melalui environment variable
#    agar kredensial tidak tersimpan di dalam kode sumber
export SPRING_DATASOURCE_URL=jdbc:postgresql://<host-supabase>:5432/postgres
export SPRING_DATASOURCE_USERNAME=<username>
export SPRING_DATASOURCE_PASSWORD=<password>

# 3. Jalankan aplikasi menggunakan Maven Wrapper
./mvnw spring-boot:run

```

> **Catatan Keamanan (Akademik):** Untuk lingkungan produksi, kredensial basis data sebaiknya **tidak** disimpan sebagai nilai *default* di dalam `application.properties`, melainkan disuntikkan sepenuhnya melalui *environment variable* pada platform deployment (Railway → tab *Variables*). Hal ini merupakan praktik standar dalam pengelolaan *secrets* pada aplikasi berbasis cloud.

---

## 6. Anggota Tim 

| Peran | Nama | NIM/Keterangan |
|Frontend|Mutiara Rizky Salsabila|41524010092|
|Backend Core|Deswita Nindya Putri|41524010082|
|Backend logic|Keisya Rizkia Kamila|41524010093|

| Mata Kuliah | PEMROGRAMAN BERORIENTASI OBJEK | 
| Dosen Pengampu | Prastika Indriyanti, S.Kom, MCS |
| Institusi | Universitas Mercu Buana | — |

---

<p align="center"><i>Dokumen ini disusun sebagai bagian dari deliverable akademik proyek SVARA — Manajemen Pengguna Berbasis Web.</i></p>
