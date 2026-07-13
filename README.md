# SVARA — Sistem Pemesanan Tiket Konser

> Proyek akademik untuk mata kuliah **Pemrograman Berorientasi Objek (PBO)** — aplikasi web pemesanan tiket konser dengan arsitektur **client–server** dan **REST API**.

---

## 1. Deskripsi Project

**SVARA** adalah aplikasi web pemesanan tiket konser yang memungkinkan pengguna untuk melihat daftar konser, melakukan registrasi/login, memilih kategori tiket, melakukan simulasi pembayaran, serta melihat riwayat transaksi. Aplikasi juga menyediakan **dashboard Admin** untuk mengelola data pengguna (CRUD).

Backend dibangun dengan **Spring Boot** mengikuti arsitektur **Controller → Service → Repository**, sedangkan frontend berupa halaman statis (HTML + Tailwind CSS) yang disajikan langsung oleh Spring Boot dari folder `static`, sehingga backend dan frontend berjalan dalam satu aplikasi yang sama (tidak terpisah host/port).

Tujuan akademik proyek ini adalah mendemonstrasikan:
- Perancangan **RESTful API** dengan Spring Boot.
- Konektivitas backend ke basis data **PostgreSQL** yang di-hosting di **Supabase**.
- **Deployment** aplikasi ke platform *Platform-as-a-Service* (**Railway**).
- Mekanisme **route guard** sederhana di sisi klien menggunakan `sessionStorage`.
- Komunikasi asynchronous frontend–backend menggunakan `fetch API` (format JSON).

---

## 2. Preview

Proyek belum menyertakan berkas tangkapan layar di dalam repository. Berikut halaman-halaman yang tersedia beserta fungsinya masing-masing:

| Halaman | Fungsi |
|---|---|
| `login.html` | Halaman registrasi akun User baru |
| `LoginUser.html` | Halaman login untuk User |
| `LoginAdmin.html` | Halaman login untuk Admin |
| `HomePage.html` | Menampilkan daftar konser yang tersedia |
| `Pembayaran.html` | Detail konser, pemilihan kategori tiket, dan simulasi pembayaran |
| `Verifikasi.html` | Halaman konfirmasi/struk setelah pembayaran berhasil |
| `Riwayat.html` | Riwayat pembelian tiket milik User yang sedang login |
| `Admin.html` | Dashboard Admin — melihat, mengubah, dan menghapus data User (CRUD) |

Aplikasi yang sudah live dapat dicoba langsung melalui: **[project-svara-production.up.railway.app/login.html](https://project-svara-production.up.railway.app/login.html)**

---

## 3. Cara Kerja

Alur penggunaan aplikasi secara garis besar:

1. **Registrasi/Login User** — User mendaftar lewat `login.html` (`POST /register`) atau login lewat `LoginUser.html` (`POST /user/login`). Bila berhasil, nama User disimpan di `sessionStorage`.
2. **Melihat daftar konser** — `HomePage.html` menampilkan daftar konser. Data konser (nama, tanggal, lokasi, kategori tiket, harga) saat ini **hardcoded langsung di JavaScript** halaman, belum diambil dari database.
3. **Pemesanan tiket** — User memilih konser dan diarahkan ke `Pembayaran.html?konserId=<id>`, memilih kategori tiket dan jumlah, lalu mengirim data transaksi ke backend (`POST /pembayaran`), yang menyimpannya ke tabel `pembayaran` beserta stempel waktu pembelian.
4. **Verifikasi/struk** — Setelah pembayaran tersimpan, `Verifikasi.html` menampilkan ringkasan transaksi yang diambil dari `localStorage`.
5. **Riwayat pembelian** — `Riwayat.html` memanggil `GET /pembayaran/riwayat?namaUser=<nama>` untuk menampilkan seluruh transaksi milik User tersebut.
6. **Login & dashboard Admin** — Admin login lewat `LoginAdmin.html` (`POST /admin/login`). Setelah berhasil, `sessionStorage` menyimpan flag `isAdminLoggedIn`, lalu diarahkan ke `Admin.html`, yang memanggil `GET /users` dan menyediakan aksi **update** (`PUT /users/{id}`) dan **delete** (`DELETE /users/{id}`).
7. **Proteksi halaman Admin** — Setiap kali `Admin.html` dimuat, skrip memeriksa `sessionStorage.isAdminLoggedIn`; jika tidak bernilai `"true"`, akses ditolak dan pengguna diarahkan kembali ke `LoginAdmin.html`.

Seluruh komunikasi frontend–backend menggunakan `fetch()` dalam format JSON, tanpa framework frontend (React/Vue) — murni HTML + JavaScript.

---

## 4. Tech Stack

| Lapisan | Teknologi |
|---|---|
| **Bahasa** | Java 17 |
| **Backend Framework** | Spring Boot 3.4.5 (`spring-boot-starter-web`, `spring-boot-starter-data-jpa`) |
| **Database** | PostgreSQL (di-hosting di Supabase, via connection pooler) — sebelumnya H2 (file-based) untuk pengembangan lokal |
| **ORM** | Spring Data JPA / Hibernate |
| **Build tool** | Maven (Maven Wrapper `mvnw`/`mvnw.cmd`) |
| **Frontend** | HTML5, Tailwind CSS (via CDN), Google Material Symbols, JavaScript (vanilla, `fetch API`) |
| **Deployment** | Railway (Platform-as-a-Service) |

---

## 5. Struktur Project

```
svara2/
├── src/
│   ├── main/
│   │   ├── java/com/svara2/svara2/
│   │   │   ├── Svara2Application.java        # Entry point Spring Boot
│   │   │   ├── controller/
│   │   │   │   └── Svara2Controller.java     # Seluruh REST endpoint
│   │   │   ├── service/
│   │   │   │   ├── AdminService.java
│   │   │   │   ├── UserService.java
│   │   │   │   └── PembayaranService.java
│   │   │   ├── repository/
│   │   │   │   ├── AdminRepository.java      # Spring Data JPA
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── PembayaranRepository.java
│   │   │   └── Model/
│   │   │       ├── Admin.java                # Entity
│   │   │       ├── User.java                 # Entity
│   │   │       └── Pembayaran.java           # Entity
│   │   └── resources/
│   │       ├── application.properties        # Konfigurasi datasource & JPA
│   │       └── static/                       # Frontend (disajikan langsung oleh Spring Boot)
│   │           ├── login.html                # Registrasi User
│   │           ├── LoginUser.html
│   │           ├── LoginAdmin.html
│   │           ├── HomePage.html
│   │           ├── Pembayaran.html
│   │           ├── Verifikasi.html
│   │           ├── Riwayat.html
│   │           ├── Admin.html
│   │           └── js/svara.js                # Konfigurasi Tailwind bersama
│   └── test/                                  # Direktori test (saat ini belum ada test case)
├── pom.xml                                    # Dependensi & konfigurasi Maven
├── mvnw / mvnw.cmd                            # Maven Wrapper
└── data/                                      # Berkas database H2 lokal (diabaikan git)
```

Catatan penamaan: package model menggunakan huruf kapital `Model` (bukan konvensi umum `model` huruf kecil) — perlu diperhatikan bila menambah kelas baru di package yang sama.

---

## 6. Prerequisites

Sebelum menjalankan project ini secara lokal, pastikan sudah tersedia:

- **JDK 17** atau lebih baru
- **Maven** (opsional — project sudah menyertakan Maven Wrapper `./mvnw`, jadi instalasi Maven manual tidak wajib)
- **Git**
- Akun **Supabase** (atau instance PostgreSQL lain) jika ingin memakai database cloud, **atau** cukup mengaktifkan H2 (file-based) untuk pengembangan lokal tanpa setup tambahan
- IDE yang mendukung Spring Boot (disarankan: VS Code dengan Extension Pack for Java, atau IntelliJ IDEA)

---

## 7. Menjalankan Secara Lokal

```bash
# 1. Clone repository
git clone https://github.com/mutiararzs/project-svara.git
cd svara2

# 2. (Opsional) atur environment variable agar tidak memakai kredensial default
export SPRING_DATASOURCE_URL=jdbc:postgresql://<host-supabase>:5432/postgres
export SPRING_DATASOURCE_USERNAME=<username>
export SPRING_DATASOURCE_PASSWORD=<password>

# 3. Jalankan aplikasi via Maven Wrapper
./mvnw spring-boot:run
```

Setelah aplikasi berjalan, buka browser ke:

```
http://localhost:9999/login.html
```

> Di Windows, gunakan `mvnw.cmd spring-boot:run` sebagai gantinya.

### Menjalankan dengan database H2 (tanpa koneksi internet)

Project ini awalnya menggunakan **H2** (database file lokal). Untuk kembali memakainya saat pengembangan offline:
1. Aktifkan kembali dependency `h2` yang saat ini di-*comment* di `pom.xml`.
2. Di `application.properties`, comment baris konfigurasi PostgreSQL/Supabase, lalu uncomment baris konfigurasi H2 (`spring.datasource.url=jdbc:h2:file:./data/svara2db`, dst).

---

## 8. Konfigurasi

Konfigurasi utama berada di `src/main/resources/application.properties`:

| Properti | Keterangan |
|---|---|
| `server.port` | Port aplikasi (default: `9999`) |
| `spring.datasource.url` | URL koneksi database PostgreSQL/Supabase |
| `spring.datasource.username` / `password` | Kredensial database |
| `spring.jpa.hibernate.ddl-auto` | `update` — tabel dibuat/diperbarui otomatis dari Entity Java. Ubah ke `validate` atau `none` di production yang sudah stabil |
| `spring.jpa.show-sql` | Menampilkan query SQL di console (berguna untuk debugging) |

**⚠️ Catatan keamanan penting:** saat ini `application.properties` menyimpan URL, username, dan **password Supabase secara eksplisit sebagai nilai default** (`${SPRING_DATASOURCE_URL:...}`, dst). Ini membuat kredensial database ikut ter-commit ke Git dan berisiko jika repository bersifat publik. Disarankan:
1. Hapus nilai default kredensial dari file tersebut,
2. Simpan kredensial hanya melalui **environment variable** (lokal: `export ...`; Railway: tab **Variables**),
3. Pertimbangkan rotasi password Supabase yang sudah pernah ter-expose.

Contoh environment variable yang dibutuhkan:

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/postgres
SPRING_DATASOURCE_USERNAME=<username>
SPRING_DATASOURCE_PASSWORD=<password>
```

`BASE_URL` di sisi frontend juga perlu diperhatikan: beberapa halaman (`Admin.html`, `LoginAdmin.html`, `LoginUser.html`, `login.html`) meng-hardcode URL Railway production (`https://project-svara-production.up.railway.app`) sebagai `BASE_URL`, sedangkan halaman lain (`Pembayaran.html`, `Riwayat.html`) memakai `window.location.origin`. Saat menjalankan secara lokal, halaman-halaman dengan URL hardcoded tersebut tetap akan memanggil API production, bukan `localhost` — perlu diubah manual bila ingin menguji penuh secara lokal.

---

## 9. Deploy ke Production

Aplikasi saat ini sudah di-deploy dan aktif di **Railway**:

**🔗 [https://project-svara-production.up.railway.app](https://project-svara-production.up.railway.app)**

Langkah umum deploy ke Railway:

1. Buat project baru di [Railway](https://railway.app) dan hubungkan ke repository GitHub (`mutiararzs/project-svara`).
2. Railway akan otomatis mendeteksi project Maven/Spring Boot (via Nixpacks) dan menjalankan build (`./mvnw package`) serta start command secara otomatis.
3. Pada tab **Variables**, tambahkan environment variable berikut (menggantikan nilai default di `application.properties`):
   - `SPRING_DATASOURCE_URL`
   - `SPRING_DATASOURCE_USERNAME`
   - `SPRING_DATASOURCE_PASSWORD`
4. Pastikan Railway meneruskan variable `PORT` yang sesuai dengan `server.port` aplikasi (`9999`), atau sesuaikan `application.properties` menjadi `server.port=${PORT:9999}` agar otomatis mengikuti port yang diberikan Railway.
5. Setelah deploy sukses, Railway akan menyediakan domain publik (`*.up.railway.app`) yang langsung menyajikan backend REST API sekaligus frontend statis dari folder `static`.

---

## 10. Menjalankan Test

Direktori `src/test/java` saat ini **belum berisi test case otomatis** (unit test maupun integration test). Pengujian yang tersedia bersifat **pengujian manual/fungsional** melalui browser, dengan skenario berikut:

**a) Registrasi & Login**
1. Buka `login.html`, daftarkan akun User baru → cek response `POST /register`.
2. Login lewat `LoginUser.html` → pastikan diarahkan ke `HomePage.html`.

**b) Pemesanan tiket**
1. Dari `HomePage.html`, pilih salah satu konser.
2. Pilih kategori tiket & jumlah di `Pembayaran.html`, lalu selesaikan pembayaran.
3. Verifikasi transaksi muncul di `Verifikasi.html` dan `Riwayat.html`.

**c) Proteksi akses Admin**
1. Tanpa login, akses langsung `Admin.html` lewat address bar.
2. **Hasil yang diharapkan:** muncul alert penolakan akses dan redirect otomatis ke `LoginAdmin.html`.

**d) CRUD Admin**
1. Login sebagai Admin lewat `LoginAdmin.html`.
2. Di `Admin.html`, uji **Read** (daftar user tampil otomatis via `GET /users`), **Update** (tombol Edit → `PUT /users/{id}`), dan **Delete** (tombol Delete + konfirmasi → `DELETE /users/{id}`).

> **Tips:** karena frontend memakai Tailwind CDN dan `svara.js` statis, lakukan **hard refresh** (`Ctrl+Shift+R` / `Cmd+Shift+R`) bila tampilan atau perubahan kode terbaru tidak langsung terlihat.

Untuk menambahkan test otomatis ke depannya, disarankan memakai `spring-boot-starter-test` (JUnit 5 + Mockito) untuk unit test pada `service/`, serta `@SpringBootTest` atau `MockMvc` untuk integration test pada `Svara2Controller`.

---

## 11. Batasan Desain

Beberapa batasan yang perlu diketahui dari implementasi saat ini:

- **Password disimpan plain-text** — kolom `password` pada `User` dan `Admin` tidak di-hash (misalnya dengan BCrypt); login membandingkan string secara langsung (`findByEmailAndPassword`). Tidak aman untuk penggunaan produksi sungguhan.
- **Tidak ada autentikasi berbasis token** — tidak ada JWT/session server-side; status login hanya disimpan di `sessionStorage` sisi klien, sehingga mudah dimanipulasi lewat DevTools browser.
- **Data konser bersifat statis** — daftar konser, harga, dan kategori tiket di-hardcode langsung di JavaScript (`Pembayaran.html`, `HomePage.html`), belum ada Entity/tabel `Konser` di database maupun endpoint CRUD untuk mengelolanya.
- **Tidak ada validasi input di backend** — Controller langsung menerima `@RequestBody` tanpa validasi (mis. `@Valid`, format email, panjang password), sehingga rawan data tidak konsisten.
- **`@CrossOrigin("*")`** mengizinkan request dari origin mana pun — longgar untuk keperluan production yang sesungguhnya.
- **Pembayaran bersifat simulasi** — tidak ada integrasi payment gateway sungguhan (mis. Midtrans/Xendit); transaksi langsung dianggap berhasil dan disimpan ke tabel `pembayaran`.
- **Belum ada test otomatis** (lihat bagian [Menjalankan Test](#10-menjalankan-test)).
- **Kredensial database ter-hardcode sebagai default** di `application.properties` (lihat bagian [Konfigurasi](#8-konfigurasi)).

---

## 12. Lisensi

Proyek ini dibuat sebagai **tugas akademik** untuk mata kuliah **Pemrograman Berorientasi Objek (PBO)**, dan tidak dirilis di bawah lisensi open-source formal. Penggunaan ulang kode untuk keperluan di luar akademik disarankan untuk menghubungi pemilik repository terlebih dahulu.

---

## Anggota Tim

- Mutiara Rizky Salsabila — 41524010092
- Deswita Nindya Putri — 41524010082
- Keisya Rizkia Kamila — 41524010093

