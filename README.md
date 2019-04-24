# Peer-to-peer Collaborative Editing with CRDT

## Deskripsi
*Text Editor* yang mendukung *collaborative editing*, dimana beberapa pengguna dapat menghasilkan sebuah pekerjaan akhir yang sama melalui kontribusi individual.
*Text Editor* menggunakan koneksi *peer-to-peer* antar user dan menggunakan *Conflict-free Replicated Data Type*, yaitu tipe data yang dapat direplikasi pada beberapa komputer dalam sebuah jaringan.  

## Menjalankan Program
```
<Execute Main Class> <Host Port> <Destination URI>
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
Setelah itu Controller mengubah data CRDT secara lokal dan mengirimkan *BroadCast* dari Messenger dan mengkonversi jenis karakter dan indeks menjadi bentuk byte untuk dikirim melalui server.
Setelah dikirim, Port Penerima akan mendapatkan masukan dari broadcast server dan kemudian mengkonversi kembali pesan yang diterima menjadi masukan indeks dan karakter.
Dari masukan yang telah diterima dari server, Controller penerima melakukan pengecekan versi pengubahan terlebih dahulu sebelum melakukan pengubahan karakter text pada program.
Setelah versi yang diperiksa telah valid dan sesuai, maka controller akan melakukan pengubahan data CRDT secara remote dan menampilkannya kembali dalam bentuk teks di GUI.

#### Arsitektur Program :
![Sister_Arsitektur](/uploads/8f018d6a52eb3c0c27c423bcbccac7e0/Sister_Arsitektur.png)

## Desain Struktur Data
### CRDT
Struktur data :
- SortedMap : document
- String : idNode
- HashMap : version
- HashMap : deletionBuffers
- ArrayList : Position
- Constructor CRDT
- Function getPosition : mengembalikan nilai posisi CRDT suatu index karakter.
- Function localInsert : memasukkan nilai karakter yang diterima dari GUI ke dalam SortedMap document dan mengembalikan nilai indeks pointer selanjutnya
- Function setCharPosition : memasukkan nilai posisi karakter ke dalam ArrayList position dan mengembalikan nilai indeks pointer selanjutnya
- Function localDelete : menghapus nilai karakter dari SortedMap document dan nilai posisi karakter tersebut dari GUI serta mengembalikan nilai dari karakter tersebut
- Procedure broadcastChar : prosedur untuk print perintah broadcast tiap fungsi ke dalam layar terminal
- Function remoteInsert : memasukkan nilai karakter yang diterima dari TCP ke dalam SortedMap document dan mengembalikan nilai indeks pointer selanjutnya
- Function remoteDelete : menghapus nilai karakter dari SortedMap document dan nilai posisi karakter tersebut dari TCP serta mengembalikan nilai dari karakter tersebut

<br>

### Version Vector
Struktur data : 
- String : idNode
- Integer : counter
- Constructor Version 
- Function getIdNode : mengembalikan nilai idNode kelas
- procedure setIdNode : men-set nilai idNode kelas dari nilai parameter
- Function getCounter : mengembalikan nilai counter kelas.
- procedure setCounter : men-set nilai counter kelas dari nilai parameter


### Deletion Buffer 
Struktur data :
TBD

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
