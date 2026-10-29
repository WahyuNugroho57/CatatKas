# CatatKas

Aplikasi web pencatatan pemasukan/pengeluaran dengan Java 17, Spring Boot, Maven, Thymeleaf, dan MySQL.

## Fitur
- Menambah transaksi: tanggal, jenis, kategori, keterangan, jumlah.
- Melihat dan menghapus transaksi.
- Ringkasan pemasukan, pengeluaran, dan saldo.
- Tampilan responsif sederhana.

## Menjalankan di NetBeans 17
1. Ekstrak ZIP.
2. Di NetBeans pilih File > Open Project, lalu pilih folder `CatatKas` yang berisi `pom.xml`.
3. Tunggu Maven mengunduh dependensi.
4. Jalankan MySQL di XAMPP. Pengaturan default memakai host `localhost`, port `3306`, user `root`, password kosong.
5. Jika database tidak dibuat otomatis, buat database `catatkas_db` melalui phpMyAdmin.
6. Klik kanan project > Clean and Build, lalu Run.
7. Buka http://localhost:8080.

Jika nama pengguna, password, atau port MySQL berbeda, ubah `src/main/resources/application.properties`.
Nama file dan class utama sudah konsisten: `CatatKasApplication.java` dan `CatatKasApplication`.
