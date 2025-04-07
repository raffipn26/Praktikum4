package Bab_4;

public class Customer {

    // ENKAPSULASI DATA
    // Semua atribut dibuat private agar tidak bisa diakses atau diubah langsung dari luar class

    private String customerNumber;   // Nomor unik pelanggan
    private String name;             // Nama pelanggan
    private double balance;          // Saldo akun pelanggan
    private String pin;              // PIN rahasia untuk autentikasi
    private boolean blocked = false; // Status blokir akun
    private int failedAttempts = 0;  // Jumlah percobaan PIN yang gagal

    // Konstruktor: digunakan untuk membuat objek customer dengan data awal
    public Customer(String customerNumber, String name, double balance, String pin) {
        this.customerNumber = customerNumber;
        this.name = name;
        this.balance = balance;
        this.pin = pin;
    }

    // GETTER METHOD
    // ENKAPSULASI: memberikan akses terbatas ke atribut private

    public String getCustomerNumber() {
        return customerNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isBlocked() {
        return blocked;
    }

    // METODE AUTENTIKASI
    // ENKAPSULASI: mengecek PIN tanpa membuka atau mengekspos nilai pin sebenarnya
    public boolean authenticateByPin(String inputPin) {
        if (blocked) return false; // Tidak bisa login jika sudah diblokir

        if (this.pin.equals(inputPin)) {
            failedAttempts = 0; // Reset percobaan jika berhasil
            return true;
        } else {
            failedAttempts++;
            if (failedAttempts >= 3) blocked = true; // Blokir jika gagal 3 kali
            return false;
        }
    }

    // METODE INTERNAL UNTUK MENGHITUNG CASHBACK
    // ENKAPSULASI: logika khusus disembunyikan dari pengguna luar kelas

    // Cashback untuk transaksi besar (> 1 juta)
    private double getCashback(double amount) {
        if (amount <= 1000000) return 0;

        // Mengambil 2 digit pertama dari customerNumber untuk menentukan cashback
        String prefix = customerNumber.substring(0, 2);
        switch (prefix) {
            case "38": return amount * 0.05;
            case "56": return amount * 0.07;
            case "74": return amount * 0.10;
            default: return 0;
        }
    }

    // Cashback untuk transaksi kecil (≤ 1 juta)
    private double getSmallCashback(double amount) {
        if (amount > 1000000) return 0;

        String prefix = customerNumber.substring(0, 2);
        switch (prefix) {
            case "56": return amount * 0.02;
            case "74": return amount * 0.05;
            default: return 0;
        }
    }

    // TRANSAKSI PEMBELIAN
    // ENKAPSULASI: transaksi dilakukan via method ini, tidak bisa langsung ubah saldo

    public boolean purchase(double amount) {
        // Hitung total cashback
        double cashback = getCashback(amount) + getSmallCashback(amount);

        // Hitung total yang benar-benar dikurangi dari saldo
        double finalAmount = amount - cashback;

        // Pastikan saldo tidak kurang dari 10 ribu
        if (balance - finalAmount < 10000) return false;

        // Update saldo
        balance -= finalAmount;
        return true;
    }

    // TRANSAKSI TOP-UP
    // ENKAPSULASI: penambahan saldo juga hanya bisa melalui method ini

    public boolean topUp(double amount) {
        if (amount <= 0) return false;
        balance += amount;
        return true;
    }
}
