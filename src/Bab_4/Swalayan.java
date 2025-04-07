package Bab_4;

import java.util.Scanner;

public class Swalayan {

    // Kelas TinySupermarket bertindak sebagai sistem pengelola transaksi
    // Berperan sebagai "pengguna" objek Customer, sesuai prinsip encapsulation
    static class TinySupermarket {

        // ENKAPSULASI: Variabel 'customers' disembunyikan dengan modifier 'private'
        // Tujuannya agar hanya bisa diakses melalui method dalam kelas ini
        private Customer[] customers;

        // ENKAPSULASI: 'loginAttempts' juga disembunyikan agar tidak bisa diubah dari luar
        private int loginAttempts = 0;

        // Konstruktor: menerima array customer yang akan dikelola
        public TinySupermarket(Customer[] customers) {
            this.customers = customers;
        }

        // ENKAPSULASI: Metode pencarian customer berdasarkan PIN
        // Tidak mengizinkan akses langsung ke objek Customer dari luar
        private Customer findCustomerByPin(String pin) {
            for (Customer c : customers) {
                // ENKAPSULASI: autentikasi dilakukan melalui method milik Customer
                // Detail PIN tidak diekspos secara langsung
                if (c.authenticateByPin(pin)) {
                    return c;
                }
            }
            return null;
        }

        // Method untuk memproses transaksi pembelian
        public void processPurchase(Scanner scanner) {
            System.out.print("Masukkan PIN: ");
            String inputPin = scanner.nextLine();

            Customer acc = findCustomerByPin(inputPin);

            if (acc == null) {
                loginAttempts++;
                if (loginAttempts >= 3) {
                    System.out.println("Gagal login 3 kali. Akun diblokir.");
                    System.exit(0); // Program berhenti total
                }
                System.out.println("Password salah atau akun diblokir.");
                return;
            }

            loginAttempts = 0; // Reset jika berhasil login

            System.out.print("Masukkan nominal pembelian: ");
            double amount = scanner.nextDouble(); scanner.nextLine();

            // ENKAPSULASI: saldo hanya dapat diubah lewat method purchase()
            if (acc.purchase(amount)) {
                System.out.println("Pembelian berhasil.");

                // ENKAPSULASI: saldo tidak diakses langsung, tetapi lewat method getBalance()
                System.out.printf("Sisa saldo: Rp%.2f%n", acc.getBalance());
            } else {
                System.out.println("Pembelian gagal. Saldo tidak mencukupi.");
            }
        }

        // Method untuk proses penambahan saldo (top-up)
        public void processTopUp(Scanner scanner) {
            System.out.print("Masukkan PIN: ");
            String inputPin = scanner.nextLine();

            Customer acc = findCustomerByPin(inputPin);

            if (acc == null) {
                loginAttempts++;
                if (loginAttempts >= 3) {
                    System.out.println("Gagal login 3 kali. Akun diblokir.");
                    System.exit(0);
                }
                System.out.println("Password salah atau akun diblokir.");
                return;
            }

            loginAttempts = 0;

            System.out.print("Masukkan nominal top-up: ");
            double amount = scanner.nextDouble(); scanner.nextLine();

            // ENKAPSULASI: top-up dilakukan dengan method yang mengatur logika internal saldo
            if (acc.topUp(amount)) {
                System.out.println("Top-up berhasil.");
                System.out.printf("Saldo saat ini: Rp%.2f%n", acc.getBalance());
            } else {
                System.out.println("Top-up gagal.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Membuat data customer sebagai array objek
        // Setiap Customer menyimpan data pribadi dan saldo secara aman
        Customer[] customers = {
            new Customer("74235123", "Andi", 6000000, "1234"),
            new Customer("38362456", "Budi", 8000000, "5678"),
            new Customer("56236789", "Citra", 9000000, "0000")
        };

        // Objek swalayan bertindak sebagai pengelola akun dan transaksi
        TinySupermarket system = new TinySupermarket(customers);

        // Menu utama untuk memilih layanan
        while (true) {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Pembelian");
            System.out.println("2. Top-up");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = scanner.nextInt(); scanner.nextLine();

            switch (pilihan) {
                case 1:
                    system.processPurchase(scanner);
                    break;
                case 2:
                    system.processTopUp(scanner);
                    break;
                case 3:
                    System.out.println("Terima kasih!");
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
}
