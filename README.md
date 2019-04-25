# Peer-to-peer Collaborative Editing with CRDT

## Deskripsi
*Text Editor* yang mendukung *collaborative editing*, dimana beberapa pengguna dapat menghasilkan sebuah pekerjaan akhir yang sama melalui kontribusi individual.
*Text Editor* menggunakan koneksi *peer-to-peer* antar user dan menggunakan *Conflict-free Replicated Data Type*, yaitu tipe data yang dapat direplikasi pada beberapa komputer dalam sebuah jaringan.  

## Menjalankan Program
First node :
```
java Main <Server port>  
```

Other node(s) :
```
java Main <Server port> <Destination URI>  
```

Disarankan menggunakan IntelliJ untuk memudahkan proses menjalankan program 
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
  - Integrasi GUI, Controller, dan Messenger
  - Laporan
  
- **13516125 Aldo Azali** 
  - GUI
  - Laporan

# Laporan
## Cara Kerja dan Arsitektur Program
#### Cara Kerja :
Program bekerja di atas Java Swing dan menggunakan WebSocket TCP. Pertama-tama, program dijalankan dengan masukan *Host Port* dan *Destination URI* dari terminal. 
Kemudian program melakukan konfigurasi socket untuk dapat berjalan secara paralel. Kemudian program menampilkan aplikasi text editor sederhana ke layar menggunakan Java Swing.
Ketika ada *trigger* masukan dari *keyboard* pengguna pada salah, program menentukan jenis masukan keyboard pengguna dan memberikan indeks & karakter yang di masukkan oleh pengguna ke Controller.
Setelah itu Controller mengubah data CRDT secara lokal dan mengirimkan *BroadCast* dari Messenger dan mengkonversi jenis karakter dan indeks menjadi bentuk byte untuk dikirim melalui server.
Setelah dikirim, Port Penerima akan mendapatkan masukan dari broadcast server dan kemudian mengkonversi kembali pesan yang diterima menjadi masukan indeks dan karakter.
Dari masukan yang telah diterima dari server, Controller penerima melakukan pengecekan versi pengubahan terlebih dahulu sebelum melakukan pengubahan karakter text pada program.
Setelah versi yang diperiksa telah valid dan sesuai, maka controller akan melakukan pengubahan data CRDT secara remote dan menampilkannya kembali dalam bentuk teks di GUI.

#### Arsitektur Program :
![Sister_Arsitektur](/uploads/8f018d6a52eb3c0c27c423bcbccac7e0/Sister_Arsitektur.png)

## Desain Struktur Data
### CRDT
CRDT berfungsi untuk mengelola Character yang disimpan pada node, termasuk menambahkan dan menghapus Character baik yang berasal dari user pada node terkait maupun dari node lain yang terhubung.
CRDT juga bertanggung jawab mengatur izin operasi agar sesuai dengan aturan dan menjaga konkurensi dan idempoten dari operasi yang dilakukan.

<br>
Struktur data :

- SortedMap : **document** : TreeMap terurut yang menyimpan pasangan <Posisi Relatif, Character> untuk mendata karakter yang ada pada CRDT
- String : **idNode** : site ID node tempat CRDT berada
- HashMap : **versions** : Menyimpan versi terakhir dari setiap node yang terhubung pada sistem
- HashMap : **deletionBuffers** : Tempat penyimpanan sementara untuk karakter yang belum saatnya dihapus 
- ArrayList : **Position** : List untuk mengkonversi posisi relatif menjadi index pada GUI
- Constructor CRDT
- Function **getPosition** : mengembalikan nilai posisi relatif karakter pada CRDT berdasarkan index karakter pada GUI.
- Function **localInsert** : melakukan insertion yang dilakukan pada node sendiri ke dalam CRDT
- Function **setCharPosition** : mengkonversi nilai posisi pointer tempat insert menjadi nilai posisi relatif karakter pada CRDT
- Function **localDelete** : melakukan deletion yang dilakukan pada node sendiri dari dalam CRDT
- Procedure **broadcastChar** : prosedur untuk print hasil setiap aksi dari tiap fungsi ke dalam layar terminal
- Function **remoteInsert** : melakukan insertion yang dilakukan pada node peer dan memasukannya ke dalam CRDT pada node sendiri
- Function **remoteDelete** : melakukan deletion yang dilakukan pada node peer dan menghapusnya dari dalam CRDT pada node sendiri

<br>

### Version Vector
Version vector berfungsi untuk menyimpan pasangan idNode dan counter versi node tersebut, untuk menandakan operasi ke berapa yang dilakukan oleh node mana. 
Version vector digunakan di dua tempat, CRDT dan Character. Pada CRDT, Version Vector menyimpan versi terbaru dari tiap node. 
Sedangkan pada Character, version vector digunakan untuk menandai versi setiap operasi, sehingga saat melakukan deletion bisa dipastikan versi Character saat diinsert sudah diterima.  

Struktur data : 
- String : **idNode** : site ID dari suatu node 
- Integer : **counter** : versi terakhir dari perubahan yang dilakukan oleh suatu node
- Constructor Version 
- Function **getIdNode** : mengembalikan nilai idNode kelas
- procedure **setIdNode** : men-set nilai idNode kelas dari nilai parameter
- Function **getCounter** : mengembalikan nilai counter kelas.
- procedure **setCounter** : men-set nilai counter kelas dari nilai parameter


### Character
Character berfungsi untuk menyimpan data yang diperlukan untuk setiap karakter yang dioperasikan, termasuk posisi relatif, jenis operasi dan versi.

Struktur data :
- char : **value** : nilai pada Character
- Float : **position** : posisi relatif Character pada CRDT
- Float : **nextIdx** : posisi relatif dari Character setelahnya
- Boolean : **isInsert** : true jika operasi insert, false jika operasi delete
- Version : **insertVersion** : version saat operasi insert Character terkait dilakukan
- Version : **deleteVersion** : version saat operasi delete Character terkait dilakukan

### Analisis dan Saran Perbaikan
Sistem yang kami buat saat ini sudah mencakup fitur-fitur dan persyaratan yang esensial yang diperlukan oleh sebuah collaborative editing tools. 
Namun secara performa dan penggunaan memory masih bisa ditingkatkan. Implementasi peer-to-peer dan websocket yang menggunakan protokol TCP secara umum
mungkin kalah dibanding penggunaan UDP yang lebih cepat, meskipun TCP relatif lebih aman. Sehingga bila yang dikejar adalah kecepatan, protokol yang digunakan bisa diubah menjadi UDP.

Untuk penyimpanan data, SortedMap untuk penyimpanan Character di CRDT tergolong cepat, efisien dan sesuai dengan kebutuhannya yang butuh dimapping berdasarkan posisi relatif. 
Namun *trade-off*nya adalah dibutuhkannya satu List tambahan untuk menyimpan konversi antara posisi relatif dan indeks untuk menampilkan tulisan. Untuk perbaikan, perlu dicari struktur data yang lebih sesuai. 

### Kasus Uji
#### Kasus Uji 1
Mengetikan kalimat pada salah satu node
<br>
![Test Case 1](img/TC%201.PNG)

#### Kasus Uji 2
Mengetikan kalimat pada dua node bersamaan
<br>
![Test Case 2](img/TC%202.PNG)

#### Kasus Uji 3
Menghapus kata pada salah satu node
<br>
![Test Case 3](img/TC%203.PNG)



#### Kasus Uji 4
Menghapus kata pada satu node bersamaan dengan mengetikan kata di node lain
<br>
![Test Case 4](img/TC%204.PNG)

