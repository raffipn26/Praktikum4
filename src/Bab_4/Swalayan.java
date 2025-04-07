package Bab_4;

import java.util.Scanner;

public class Swalayan {

    static class TinySupermarket {
        private Customer[] customers;
        private int loginAttempts = 0;

        public TinySupermarket(Customer[] customers) {
            this.customers = customers;
        }

        private Customer findCustomerByPin(String pin) {
            for (Customer c : customers) {
                if (c.authenticateByPin(pin)) {
                    return c;
                }
            }
            return null;
        }

        public void processPurchase(Scanner scanner) {
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

            loginAttempts = 0; // reset jika berhasil login

            System.out.print("Masukkan nominal pembelian: ");
            double amount = scanner.nextDouble(); scanner.nextLine();

            if (acc.purchase(amount)) {
                System.out.println("Pembelian berhasil.");
                System.out.printf("Sisa saldo: Rp%.2f%n", acc.getBalance());
            } else {
                System.out.println("Pembelian gagal. Saldo tidak mencukupi.");
            }
        }

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

            loginAttempts = 0; // reset jika berhasil login

            System.out.print("Masukkan nominal top-up: ");
            double amount = scanner.nextDouble(); scanner.nextLine();

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

        Customer[] customers = {
            new Customer("74ABC123", "Andi", 1500000, "1234"),
            new Customer("38DEF456", "Budi", 800000, "5678"),
            new Customer("56GHI789", "Citra", 1200000, "0000")
        };

        TinySupermarket system = new TinySupermarket(customers);

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
