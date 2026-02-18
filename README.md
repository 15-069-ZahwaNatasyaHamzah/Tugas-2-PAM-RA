# Tugas 2 |  Pemrograman Aplikasi Mobile

---

## Deskripsi

Aplikasi ini merupakan simulasi news feed yang menampilkan berita baru setiap 2 detik menggunakan Kotlin Flow. Aplikasi juga menerapkan filter kategori, transformasi data, StateFlow untuk state management, serta Coroutines untuk proses asynchronous.

---

- **Nama:** Zahwa Natasya Hamzah
- **NIM:** 123140069

## SCREENSHOT APLIKASI
<img width="503" height="904" alt="image" src="https://github.com/user-attachments/assets/dac0240a-9a0f-40ac-9ad1-52f885009e3b" />
<img width="507" height="899" alt="image" src="https://github.com/user-attachments/assets/93c3fdc4-94e6-48d9-998e-fc6cebbeeba5" />

## Implementasi 

### 1. Flow
- Menggunakan `flow {}` builder
- Menggunakan `emit()` untuk mengirim data setiap 2 detik
- Data dikumpulkan dengan `collect`

### 2. Operator Flow
- `filter` untuk kategori berita
- `map` untuk transformasi tampilan
- `onEach` untuk update jumlah berita dibaca
- `catch` untuk error handling

### 3. StateFlow
- Menggunakan `MutableStateFlow`
- Mengekspos sebagai `StateFlow`
- Menyimpan jumlah berita yang sudah dibaca

### 4. Coroutines
- Menggunakan `viewModelScope`
- Menggunakan `async` dan `await`
- Menggunakan `Dispatchers.IO`
- Tidak memblokir UI thread

---

## Cara Menjalankan

1. Buka project di Android Studio  
2. Sync Gradle  
3. Jalankan di emulator atau device  

---

