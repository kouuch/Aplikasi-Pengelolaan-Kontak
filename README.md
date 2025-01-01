# Aplikasi Pengelolaan Kontak

Aplikasi Pengelolaan Kontak adalah aplikasi berbasis Java yang menggunakan Swing untuk antarmuka grafis dan MySQL sebagai database. Aplikasi ini memungkinkan pengguna untuk mengelola daftar kontak dengan fitur seperti **CRUD (Create, Read, Update, Delete)**, pencarian, serta ekspor dan impor data ke/dari file CSV.

---

## Fitur Utama

1. **Tambah Kontak**
   - Menyimpan informasi kontak seperti nama, nomor telepon, alamat, dan kategori ke dalam database.

2. **Lihat Semua Kontak**
   - Menampilkan daftar semua kontak yang tersimpan di database dalam bentuk tabel.

3. **Edit Kontak**
   - Memperbarui informasi kontak berdasarkan data yang dipilih.

4. **Hapus Kontak**
   - Menghapus kontak berdasarkan data yang dipilih.

5. **Pencarian Kontak**
   - Mencari kontak berdasarkan nama atau nomor telepon dan menampilkan hasilnya di tabel.

6. **Ekspor ke CSV**
   - Mengekspor daftar kontak ke file CSV.

7. **Impor dari CSV**
   - Mengimpor data kontak dari file CSV ke database.

8. **Validasi Nomor Telepon**
   - Memastikan nomor telepon hanya berisi angka dan memiliki panjang antara 10-13 digit.

---

## Teknologi yang Digunakan

- **Bahasa Pemrograman:** Java
- **Database:** MySQL (dengan JDBC)
- **Antarmuka Grafis:** Java Swing
- **Format File Ekspor/Impor:** CSV

---

## Instalasi

### Prasyarat

1. **Java Development Kit (JDK):** Pastikan Anda telah menginstal JDK di komputer Anda.
2. **MySQL Server:** Pastikan MySQL sudah terinstal dan berjalan.
3. **Library JDBC:** Pastikan `mysql-connector-java` sudah dihubungkan ke proyek Anda.
4. **IDE:** NetBeans atau IntelliJ IDEA untuk pengembangan.

### Langkah-Langkah

1. Clone repository ini atau salin kode ke proyek Anda.
2. Buat database MySQL dengan nama `contact_manager` dan jalankan skrip berikut:
   ```sql
   CREATE TABLE contacts (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       phone VARCHAR(15) NOT NULL,
       address VARCHAR(255) NOT NULL,
       category VARCHAR(50) NOT NULL
   );
