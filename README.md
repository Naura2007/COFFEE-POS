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

1. `public AdminGUI()`   
    Metode ini pertama kali dieksekusi saat objek AdminGUI dibuat. Ini mengatur properti dasar jendela (JFrame) seperti judul, ukuran, dan layout (BorderLayout). Poin pentingnya adalah memuat data awal: menuItems dan toppingItems diisi dengan data yang diambil dari file JSON melalui MenuService.loadAll() dan ToppingService.loadAll(). Akhirnya, ia memanggil buildUI() untuk merakit tampilan.  
3. `private void buildUI()`  
    Metode ini membuat JTabbedPane untuk menampung panel Menu dan Topping. Untuk setiap panel, ia membuat DefaultTableModel (untuk mengatur kolom tabel), JTable (untuk menampilkan data), tombol-tombol CRUD, dan memasang event listener pada tombol-tombol tersebut (misalnya, btnAddMenu.addActionListener(e -> addMenu())). Metode ini diakhiri dengan mengisi data awal ke tabel melalui refreshMenuTable() dan refreshToppingTable().
4. `private void addMenu()`  
    Menangani penambahan item menu baru. Menggunakan JOptionPane.showInputDialog untuk meminta input Nama dan Harga dari pengguna. Melakukan validasi try-catch pada Harga. Kemudian, ia menghasilkan ID baru secara otomatis (didasarkan pada ID item terakhir), membuat objek MenuItemModel baru, menambahkannya ke menuItems, menyimpan seluruh daftar ke file JSON (JsonDB.save), dan memanggil refreshMenuTable() untuk memperbarui tampilan.
5. `private void editMenu(int index)`  
    Menangani pengubahan data item menu yang sudah ada. Item yang akan diedit diidentifikasi oleh index baris yang dipilih. Ia menampilkan JOptionPane dengan Nama dan Harga lama sebagai nilai default untuk diubah. Setelah validasi input baru, objek lama di posisi index diganti dengan objek baru yang telah diperbarui. Perubahan kemudian disimpan ke file JSON dan tampilan disegarkan.
6. `private void refreshMenuTable()`  
    Metode ini memulai dengan menghapus semua baris yang ada di menuTableModel (setRowCount(0)). Selanjutnya, ia melakukan iterasi (loop) melalui setiap objek MenuItemModel di menuItems dan menambahkannya sebagai baris baru ke tabel.
7. `private void addTopping()`  
    Metode ini meminta input untuk Topping, membuat objek Topping, memanipulasi daftar toppingItems, dan menyimpan data ke file data/toppings.json.
8. `private void editTopping(int index)`  
    Metode ini mengedit objek Topping pada toppingItems dan menyimpan perubahan ke file data/toppings.json.
9. `private void refreshToppingTable()`  
    Metode ini membersihkan dan mengisi ulang toppingTableModel menggunakan data dari toppingItems.

## Dokumentasi
1. Judul
2. deskripsi
3. Rancangan Kelas
4. Gambar aplikasi, Penjelasan
5. Link source code
6. Link Video Demo
