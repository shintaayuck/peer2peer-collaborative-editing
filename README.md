# Peer-to-peer Collaborative Editing with CRDT

## Deskripsi
*Text Editor* yang mendukung *collaborative editing*, dimana beberapa pengguna dapat menghasilkan sebuah pekerjaan akhir yang sama melalui kontribusi individual.
*Text Editor* menggunakan koneksi *peer-to-peer* antar user dan menggunakan *Conflict-free Replicated Data Type*, yaitu tipe data yang dapat direplikasi pada beberapa komputer dalam sebuah jaringan.  

## Menjalankan Program
```
- <Execute Main Class> <Host Port> <Destination URI>
```
## Teknologi yang Digunakan
- Java
- Java-WebSocket
- Java Swing

## Author & Pembagian Tugas

- **13516029 Shinta Ayu Chandra Kemala** 
  - CRDT dan operasi-operasi
  - Characters
  - Version
  - Integrasi GUI dan Controller 
  - Laporan
- **13516110 Naufal Putra Pamungkas**
  - WebSocket
  - Messenger
  - Controller
  - Laporan
  
- **13516125 Aldo Azali** 
  - GUI
  - Laporan

# Laporan
## Cara Kerja dan Arsitektur Program
#### Cara Kerja :
Program bekerja di atas Java Swing dan menggunakan WebSocket TCP. Pertama-tama, program dijalankan dengan masukan *Host Port* dan *Destination URI* dari terminal. 
Kemudian program melakukan konfigurasi socket untuk dapat berjalan secara paralel. Kemudian program menampilkan aplikasi ke layar dalam bentuk Java Swing.
Ketika ada *trigger* masukan dari *keyboard* pengguna pada salah, program menentukan jenis masukan keyboard pengguna dan memberikan indeks & karakter yang di masukkan oleh pengguna ke Controller.
Setelah itu Controller mengirimkan *BroadCast* dari Messenger dan mengkonversi jenis karakter dan indeks menjadi bentuk byte untuk dikirim melalui server.
Setelah dikirim, Port Penerima akan mendapatkan masukan dari broadcast server dan kemudian mengkonversi kembali pesan yang diterima menjadi masukan indeks dan karakter.
Dari masukan yang telah diterima dari server, Controller penerima melakukan pengecekan versi pengubahan terlebih dahulu sebelum melakukan pengubahan karakter text pada program.
Setelah versi yang diperiksa telah valid dan sesuai, maka controller akan melakukan pengubahan data dan menampilkannya kembali dalam bentuk teks di GUI.

#### Arsitektur Program :


## Desain Struktur Data
### CRDT
Struktur data
<br>
Penjelasan

### Version Vector
Struktur data
<br>
Penjelasan

### Deletion Buffer 
Struktur data
<br>
Penjelasan

### Analisis dan Saran Perbaikan


### Kasus Uji
#### Kasus Uji 1
Nama Kasus Uji
<br>
Video

#### Kasus Uji 2
Nama Kasus Uji
<br>
Video

#### Kasus Uji 3
Nama Kasus Uji
<br>
Video


#### Kasus Uji 4
Nama Kasus Uji
<br>
Video
