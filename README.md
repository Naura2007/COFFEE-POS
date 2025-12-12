# Aplikasi E-commerce untuk Coffee Shop

LINK:
| Nomor | Nama | NRP |
| --- | --- | --- |
| 1. | . | . |
| 2. | . | . |
| 3. | . | . |

Aplikasi E-commerce untuk Coffee Shop adalah aplikasi yang mengimplementasikan Java sebagai bahasa pemrograman utamanya, Graphical User Interface (GUI) untuk operasional yang mudah, serta menggunakan format JSON sebagai database yang ringan.

## Rancangan Kode
### AdminGUI
Kelas AdminGUI adalah komponen GUI utama dalam aplikasi E-commerce untuk Coffee Shop. Kelas ini berfungsi sebagai Panel Administrasi yang memungkinkan manajer atau administrator melakukan manajemen data produk. Mengimplementasikan prinsip CRUD (Create, Read, Update, Delete) terhadap dua data utama: Menu (MenuItemModel) dan Topping (Topping).

  1. public AdminGUI()
    Metode ini pertama kali dieksekusi saat objek AdminGUI dibuat. Ini mengatur properti dasar jendela (JFrame) seperti judul, ukuran, dan layout (BorderLayout). Poin pentingnya adalah memuat data awal: menuItems dan toppingItems diisi dengan data yang diambil dari file JSON melalui MenuService.loadAll() dan ToppingService.loadAll(). Akhirnya, ia memanggil buildUI() untuk merakit tampilan.     

## Dokumentasi
1. Judul
2. deskripsi
3. Rancangan Kelas
4. Gambar aplikasi, Penjelasan
5. Link source code
6. Link Video Demo
