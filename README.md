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

2. `private void buildUI()`  
    Metode ini membuat JTabbedPane untuk menampung panel Menu dan Topping. Untuk setiap panel, ia membuat DefaultTableModel (untuk mengatur kolom tabel), JTable (untuk menampilkan data), tombol-tombol CRUD, dan memasang event listener pada tombol-tombol tersebut (misalnya, btnAddMenu.addActionListener(e -> addMenu())). Metode ini diakhiri dengan mengisi data awal ke tabel melalui refreshMenuTable() dan refreshToppingTable().

3. `private void addMenu()`  
    Menangani penambahan item menu baru. Menggunakan JOptionPane.showInputDialog untuk meminta input Nama dan Harga dari pengguna. Melakukan validasi try-catch pada Harga. Kemudian, ia menghasilkan ID baru secara otomatis (didasarkan pada ID item terakhir), membuat objek MenuItemModel baru, menambahkannya ke menuItems, menyimpan seluruh daftar ke file JSON (JsonDB.save), dan memanggil refreshMenuTable() untuk memperbarui tampilan.

4. `private void editMenu(int index)`  
    Menangani pengubahan data item menu yang sudah ada. Item yang akan diedit diidentifikasi oleh index baris yang dipilih. Ia menampilkan JOptionPane dengan Nama dan Harga lama sebagai nilai default untuk diubah. Setelah validasi input baru, objek lama di posisi index diganti dengan objek baru yang telah diperbarui. Perubahan kemudian disimpan ke file JSON dan tampilan disegarkan.

5. `private void refreshMenuTable()`  
    Metode ini memulai dengan menghapus semua baris yang ada di menuTableModel (setRowCount(0)). Selanjutnya, ia melakukan iterasi (loop) melalui setiap objek MenuItemModel di menuItems dan menambahkannya sebagai baris baru ke tabel.

6. `private void addTopping()`  
    Metode ini meminta input untuk Topping, membuat objek Topping, memanipulasi daftar toppingItems, dan menyimpan data ke file data/toppings.json.

7. `private void editTopping(int index)`  
    Metode ini mengedit objek Topping pada toppingItems dan menyimpan perubahan ke file data/toppings.json.

8. `private void refreshToppingTable()`  
    Metode ini membersihkan dan mengisi ulang toppingTableModel menggunakan data dari toppingItems.
## LoginGUI
Kelas ini mengumpulkan kredensial (Username dan Password) dari pengguna, memvalidasinya melalui lapisan layanan (UserService), dan mengarahkan alur aplikasi ke interface yang sesuai (AdminGUI untuk administrator atau POSGUI untuk kasir) berdasarkan hak akses (Role) yang berhasil diotentikasi.

1. public LoginGUI()

    Metode ini adalah titik awal inisialisasi tampilan login. Pengaturan Jendela: Menetapkan properti dasar seperti judul, operasi penutupan default (EXIT_ON_CLOSE), ukuran (360x220), dan memposisikan jendela di tengah layar (setLocationRelativeTo(null)). Layout: Membuat JPanel menggunakan GridLayout(3, 2, 10, 10) untuk menata label dan bidang input dalam grid 3 baris dan 2 kolom. Input: Menambahkan JLabel untuk "Username:" dan "Password:", diikuti oleh komponen tfUser dan pfPass. Tombol: Membuat JButton "Login". Listener Aksi: Menambahkan ActionListener pada tombol "Login" yang berisi seluruh logika otentikasi dan navigasi

## POSGUI
Kelas ini memungkinkan kasir untuk membuat dan memproses pesanan pelanggan secara real-time. Fungsionalitas utamanya meliputi:
    Memuat daftar Menu dan Topping yang telah dikelola oleh AdminGUI,
    Menerima input menu, topping, dan kuantitas dari kasir,
    Menghitung subtotal dan grand total pesanan secara dinamis,
    Menyelesaikan transaksi dan menghasilkan struk pembayaran.

1. public POSGUI()

    Pengaturan Jendela: Menentukan judul, ukuran, dan layout (BorderLayout). Pemuatan Data: Memuat semua Menu dan Topping dari persistence layer melalui MenuService.loadAll() dan ToppingService.loadAll(). Membangun GUI: Memanggil buildUI() untuk merakit semua komponen.

2. private void buildUI()
   
   Metode ini merakit semua komponen antarmuka kasir. Header (NORTH): Membuat JPanel berisi cbMenu (ComboBox untuk Menu dengan custom renderer untuk menampilkan harga), wrapper untuk dropdown Topping, JSpinner (spQty) untuk kuantitas, dan tombol "Tambah ke Order" (btnAdd). Area Tengah (CENTER): Membuat JTable menggunakan orderTableModel untuk menampilkan detail pesanan yang sedang berlangsung (Nama, Topping, Qty, Harga, Subtotal). Footer (SOUTH): Membuat JPanel yang berisi lblTotal (menampilkan total harga pesanan) dan tombol "Bayar" (btnPay). Listener: Memasang listener pada btnAdd (addOrderItem()) dan btnPay (payOrder()).

## ReceiptWindow
Kelas ReceiptWindow adalah class Graphical User Interface (GUI) yang sangat sederhana, berfungsi sebagai jendela pop-up khusus untuk menampilkan struk pembayaran (receipt) setelah transaksi selesai di POSGUI.

1. public ReceiptWindow(String receipt)

   Metode ini adalah titik awal inisialisasi dan pembangunan jendela struk. Input: Menerima satu parameter string, yaitu receipt, yang merupakan seluruh konten teks struk yang telah diformat (misalnya, yang dibuat oleh POSGUI.payOrder()). Pengaturan Jendela: Menetapkan judul jendela menjadi "Receipt", ukuran (400x500), dan memposisikannya di tengah layar. Area Teks: Membuat JTextArea baru (ta) dan mengisinya dengan string receipt. Formatting: Mengatur ta.setEditable(false) agar konten tidak dapat diubah, dan menetapkan font menjadi Monospaced untuk memastikan alignment teks dan tabel dalam struk tetap terjaga. Tampilan: Menambahkan JTextArea ke dalam JScrollPane (untuk memungkinkan scrolling jika struk panjang) sebelum menambahkannya ke JFrame.

## Main.java

Kelas Main adalah entry point atau titik awal eksekusi dari seluruh aplikasi E-commerce untuk Coffee Shop. Karena merupakan kelas utama, ia hanya berisi metode main yang bertanggung jawab untuk inisialisasi lingkungan aplikasi dan menampilkan interface pengguna pertama.

## JSONDB.java
Kelas ini mengabstraksi detail teknis penyimpanan file, memungkinkan Service Layer (seperti MenuService atau UserService) untuk berinteraksi dengan data seolah-olah data tersebut adalah daftar objek (List<T>) tanpa perlu khawatir tentang parsing atau format file.

1. public static <T> List<T> load(String path, Class<T> clazz)

   Method ini berfungsi untuk membaca data dari file JSON dan mengembalikannya dalam bentuk List objek Java.

2. public static void save(String path, List<?> data)

    Method ini berfungsi untuk menyimpan List objek Java ke dalam file JSON.

## Discount.java

public Discount(String code, int percent)

Metode ini digunakan untuk menginisialisasi objek Discount baru. Ia menerima dua parameter, yaitu kode diskon (code) dan nilai persentase potongan (percent), dan menyimpannya ke dalam atribut instance yang sesuai.

public String getCode()

Metode ini berfungsi untuk mengembalikan nilai kode diskon (code) yang disimpan dalam objek ini. Kode ini biasanya digunakan oleh Service Layer untuk memvalidasi dan mengaktifkan diskon

public int getPercent()

Metode ini berfungsi untuk mengembalikan nilai persentase potongan (percent) dalam bentuk bilangan bulat.

## Membership.java

Kelas ini mendefinisikan struktur data yang diperlukan untuk melacak kepemilikan dan status poin dari setiap anggota.

public Membership(String username, int points)

Metode ini digunakan untuk menginisialisasi objek Membership baru. Ia menerima Username (identifikasi anggota) dan jumlah Poin Awal, dan menyimpannya ke dalam atribut instance yang sesuai.

public String getUsername()

Metode ini berfungsi untuk mengembalikan Username anggota yang terkait dengan akun ini. Username ini biasanya digunakan sebagai kunci unik untuk pencarian data anggota

public int getPoints()

Metode ini berfungsi untuk mengembalikan jumlah Poin Loyalitas saat ini yang dimiliki oleh anggota. Nilai ini akan digunakan dalam logika penukaran atau tampilan saldo.

public void setPoints(int points)

Metode ini berfungsi untuk memperbarui jumlah Poin Loyalitas anggota. Metode ini dipanggil setelah transaksi selesai, baik untuk menambahkan poin baru atau mengurangi poin karena penukaran.

## MenuitemModel.java

Kelas ini mendefinisikan struktur data fundamental untuk setiap produk utama yang terdaftar dalam katalog toko. Data ini dimuat dari file JSON dan digunakan di seluruh aplikasi, terutama oleh AdminGUI untuk manajemen katalog dan POSGUI untuk transaksi.

public MenuItemModel(String id, String name, int price)

Metode ini digunakan untuk menginisialisasi objek MenuItemModel baru. Ia menerima ID unik, Nama produk, dan Harga dasar produk, dan menetapkan nilainya ke atribut instance.

public String getId()

Metode ini mengembalikan ID unik item menu. ID ini digunakan oleh sistem internal untuk identifikasi data.

public String getName()

Metode ini mengembalikan Nama item menu. Nama ini digunakan untuk tampilan di tabel (AdminGUI) dan daftar pilihan (POSGUI).

public int getPrice()

Metode ini mengembalikan Harga dasar item menu. Harga ini merupakan komponen utama dalam perhitungan subtotal pesanan.

public String toString()

Metode ini di-override untuk mengembalikan Nama item menu (name). Ini penting karena ketika objek ini digunakan dalam komponen GUI seperti JComboBox (seperti di POSGUI), JComboBox akan memanggil metode ini untuk menentukan teks apa yang akan ditampilkan pada daftar pilihan.

## OrderItem.java

Kelas ini adalah model komposit yang menggabungkan informasi dari MenuItemModel dan Topping, menyimpan data dan menyediakan logika perhitungan harga untuk item tersebut.

public OrderItem(...)

Metode ini menginisialisasi objek pesanan. Menerima ID menu, nama, harga dasar (basePrice), kuantitas (qty), dan daftar topping yang dipilih (List<Topping>).

public int getMenuId()	

Mengembalikan ID menu dari item yang dipesan.

public String getName() 

Mengembalikan nama menu dari item yang dipesan.

public int getQty()	

Mengembalikan kuantitas item yang dipesan.

public List<Topping> getToppings()	

Mengembalikan daftar objek Topping yang ditambahkan pada pesanan ini.

public int getBasePrice()	

Mengembalikan harga dasar menu sebelum ditambahkan topping.

public void setQty(int qty)

Memungkinkan perubahan kuantitas item pesanan setelah objek dibuat (misalnya, jika kasir ingin menambah/mengurangi jumlah).

public int getPrice()

Menghitung Harga Satuan: Metode ini menghitung harga total untuk satu unit item pesanan. Hasilnya adalah penjumlahan dari basePrice dan total harga semua topping yang terkait.

public int subtotal()

Menghitung Subtotal Item: Metode ini menghitung total biaya untuk baris pesanan ini. Hasilnya adalah getPrice() (Harga Satuan) dikalikan dengan kuantitas (qty).

public String toppingNames()	

Metode utilitas untuk memformat daftar Topping menjadi satu string yang dipisahkan koma (e.g., "Extra Shot, Syrup Vanilla"). Digunakan untuk menampilkan detail topping pada tabel pesanan atau struk.

## Dokumentasi
1. Judul
2. deskripsi
3. Rancangan Kelas
4. Gambar aplikasi, Penjelasan
5. Link source code
6. Link Video Demo
