import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);
    static List<Minuman> menuMinuman = new ArrayList<>();
    static List<Makanan> menuMakanan = new ArrayList<>();
    static List<ItemPesanan> daftarPesanan = new ArrayList<>();

    public static void main(String[] args) {
        initMenu();
        System.out.println("=== SISTEM PEMESANAN KOHISOP ===");
        
        ambilPesanan("Minuman", 5, 3);
        ambilPesanan("Makanan", 5, 2);

        if (!daftarPesanan.isEmpty()) {
            tampilkanDetailAwal();
        }

        double subtotal = 0;
        double totalPajak = 0;

        for (ItemPesanan ip : daftarPesanan) {
            subtotal += ip.getMenu().getHarga() * ip.getQty();
            totalPajak += ip.hitungPajak();
        }

        double totalSetelahPajak = subtotal + totalPajak;

        System.out.println("\n--- PEMBAYARAN ---");
        System.out.println("Subtotal: Rp " + subtotal);
        System.out.println("Total Pajak: Rp " + totalPajak);
        System.out.println("Total Tagihan Anda: Rp " + totalSetelahPajak);

        System.out.println("\nPilih Mata Uang:");
        System.out.println("1. IDR");
        System.out.println("2. USD");
        System.out.println("3. JPY");
        System.out.println("4. MYR");
        System.out.println("5. EUR");
        System.out.print("Masukkan pilihan Anda: ");
        int pilihMU = input.nextInt();

        MataUang mu;

        if (pilihMU == 2) mu = new USD();
        else if (pilihMU == 3) mu = new JPY();
        else if (pilihMU == 4) mu = new MYR();
        else if (pilihMU == 5) mu = new EUR();
        else mu = new IDR();

        double totalDalamMU = mu.konversiDariIDR(totalSetelahPajak);

        System.out.println("\n--- KONVERSI MATA UANG ---");
        System.out.println("Mata Uang: " + mu.getNamaMataUang());
        System.out.println(mu.getRateInfo());
        System.out.printf("Total dalam %s: %.2f\n", mu.getKodeMataUang(), totalDalamMU);

        System.out.println("\nPilih Channel Pembayaran:");
        System.out.println("1. Tunai");
        System.out.println("2. QRIS (Diskon 5%)");
        System.out.println("3. eMoney (Diskon 7% + Admin Rp 20)");
        System.out.print("Masukkan pilihan Anda (1/2/3): ");
        int pilihan = input.nextInt();
        ChannelPembayaran channel = null;

        if (pilihan == 1) {
            channel = new Tunai();
        } else if (pilihan == 2) {
            channel = new QRIS();
        } else if (pilihan == 3) {
            channel = new EMoney();
        }

        if (channel != null) {
            double tagihanAkhir = channel.hitungTotalAkhir(totalDalamMU);
            System.out.printf("\nTotal akhir yang harus dibayar dalam %s: %.2f\n", mu.getKodeMataUang(), tagihanAkhir);

            if (pilihan == 2 || pilihan == 3) {
                boolean lunas = false;
                while (!lunas) {
                    System.out.print("Masukkan nominal saldo Anda (" + mu.getKodeMataUang() + "): ");
                    double saldo = input.nextDouble();
                    
                    if (!channel.cekSaldo(saldo, tagihanAkhir)) {
                        System.out.println("Maaf, saldo anda kurang!");
                    } else {
                        System.out.println("Pembayaran berhasil!");
                        lunas = true;
                    }
                }
            } else {
                System.out.println("Pembayaran Tunai berhasil!");
            }

            cetakKuitansi(mu, pilihan, totalDalamMU, tagihanAkhir);

        } else {
            System.out.println("Pilihan channel pembayaran tidak valid.");
        }
    }

    static void cetakKuitansi(MataUang mu, int pilihanChannel, double totalSetelahPajakMU, double totalAkhirMU) {
        System.out.println("\n=======================================================");
        System.out.println("                  KUITANSI PEMBAYARAN                  ");
        System.out.println("=======================================================");

        double subtotalKeseluruhan = 0;

        List<ItemPesanan> listMakanan = new ArrayList<>();
        List<ItemPesanan> listMinuman = new ArrayList<>();

        for (ItemPesanan ip : daftarPesanan) {
            if (ip.getMenu() instanceof Makanan) listMakanan.add(ip);
            if (ip.getMenu() instanceof Minuman) listMinuman.add(ip);
        }

        System.out.println("\n>>> KATEGORI MAKANAN:");
        for (ItemPesanan ip : listMakanan) {
            double sub = ip.getMenu().getHarga() * ip.getQty();
            subtotalKeseluruhan += sub;
            System.out.printf("- [%s] %-20s (Rp %d)\n", ip.getMenu().getKode(), ip.getMenu().getNama(), ip.getMenu().getHarga());
            System.out.printf("  Qty: %d | Total: Rp %.2f | Pajak: Rp %.2f\n", ip.getQty(), sub, ip.hitungPajak());
        }

        double subtotalMinuman = 0;
        double pajakMinuman = 0;

        System.out.println("\n>>> KATEGORI MINUMAN:");
        for (ItemPesanan ip : listMinuman) {
            double sub = ip.getMenu().getHarga() * ip.getQty();
            double pjk = ip.hitungPajak();
            subtotalMinuman += sub;
            pajakMinuman += pjk;
            subtotalKeseluruhan += sub;
            
            System.out.printf("- [%s] %-20s (Rp %d)\n", ip.getMenu().getKode(), ip.getMenu().getNama(), ip.getMenu().getHarga());
            System.out.printf("  Qty: %d | Total: Rp %.2f | Pajak: Rp %.2f\n", ip.getQty(), sub, pjk);
        }

        System.out.println("-------------------------------------------------------");
        System.out.printf("Total Minuman (Tanpa Pajak)           : Rp %.2f\n", subtotalMinuman);
        System.out.printf("Total Minuman (Dengan Pajak)          : Rp %.2f\n", (subtotalMinuman + pajakMinuman));
        System.out.println("-------------------------------------------------------");


        double diskonMU = 0;
        double adminMU = 0;

        if (pilihanChannel == 2) { 
            diskonMU = totalSetelahPajakMU * 0.05;
        } else if (pilihanChannel == 3) { 
            diskonMU = totalSetelahPajakMU * 0.07;
            adminMU = 20; 
        }

 
        double totalSebelumPajakDiskonAdminMU = mu.konversiDariIDR(subtotalKeseluruhan);
        System.out.printf("Total Tagihan Awal (%s)             : %.2f (Sebelum Pajak & Diskon)\n", mu.getKodeMataUang(), totalSebelumPajakDiskonAdminMU);
        
        System.out.printf("Diskon Pembayaran (%s)              : %.2f\n", mu.getKodeMataUang(), diskonMU);
        if (adminMU > 0) {
            System.out.printf("Biaya Admin (%s)                    : %.2f\n", mu.getKodeMataUang(), adminMU);
        }

        System.out.println("-------------------------------------------------------");
        System.out.printf("TOTAL TAGIHAN AKHIR (%s)            : %.2f\n", mu.getKodeMataUang(), totalAkhirMU);
        System.out.println("=======================================================");
        System.out.println("        Terima kasih dan silakan datang kembali        ");
        System.out.println("=======================================================");
    }


    static void tampilkanDetailAwal() {
        System.out.println("\n--- DETAIL PESANAN ---");
        System.out.printf("%-25s %-5s %-10s\n", "Nama", "Qty", "Harga");
        
        int tempTotal = 0;
        for (ItemPesanan ip : daftarPesanan) {
            int subtotal = ip.getMenu().getHarga() * ip.getQty();
            System.out.printf("%-25s %-5d %-10d\n", 
                ip.getMenu().getNama(), ip.getQty(), subtotal);
            tempTotal += subtotal;
        }
        System.out.printf("Total Sementara (IDR): Rp %d.0\n", tempTotal);
    }

    static void ambilPesanan(String tipe, int limitJenis, int limitQty) {
        System.out.println("\n=== MENU " + tipe.toUpperCase() + " ===");
        if (tipe.equals("Minuman")) {
            for (Minuman m : menuMinuman) System.out.println(m.getKode() + " - " + m.getNama() + " (" + m.getHarga() + ")");
        } else {
            for (Makanan m : menuMakanan) System.out.println(m.getKode() + " - " + m.getNama() + " (" + m.getHarga() + ")");
        }

        int count = 0;
        while (count < limitJenis) {
            System.out.print("\nMasukkan Kode (0 lanjut / CC batal): ");
            String k = input.nextLine().trim().toUpperCase();
            
            if (k.equals("CC")) { 
                System.out.println("Batal."); 
                System.exit(0); 
            }
            if (k.equals("0")) break;

            Menu dipilih = null;
            if (tipe.equals("Minuman")) {
                for (Minuman m : menuMinuman) if (m.getKode().equals(k)) dipilih = m;
            } else {
                for (Makanan m : menuMakanan) if (m.getKode().equals(k)) dipilih = m;
            }

            if (dipilih != null) {
                int q = inputQty(limitQty);
                if (q > 0) {
                    daftarPesanan.add(new ItemPesanan(dipilih, q));
                    count++;
                    tampilkanDetailAwal();
                }
            } else {
                System.out.println("Kode salah!");
            }
        }
    }

    static int inputQty(int max) {
        while (true) {
            try {
                System.out.print("Jumlah (max " + max + ", S=skip, enter=1): ");
                String in = input.nextLine().trim().toUpperCase();
                
                if (in.equals("CC")) { 
                    System.out.println("Batal."); 
                    System.exit(0); 
                }
                if (in.equals("S") || in.equals("0")) return 0;
                if (in.isEmpty()) return 1;

                int q = Integer.parseInt(in);
                if (q >= 1 && q <= max) return q;
                
                System.out.println("Maksimal " + max + "!");
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka!");
            }
        }
    }

    static void initMenu() {
        menuMinuman.add(new Minuman("A1", "Caffe Latte", 46));
        menuMinuman.add(new Minuman("A2", "Cappuccino", 46));
        menuMinuman.add(new Minuman("E1", "Caffe Americano", 37));
        menuMinuman.add(new Minuman("E2", "Caffe Mocha", 55));
        menuMinuman.add(new Minuman("E3", "Caramel Macchiato", 59));
        menuMinuman.add(new Minuman("E4", "Asian Dolce Latte", 55));
        menuMinuman.add(new Minuman("E5", "Double Shots Iced Shaken Espresso", 50));
        menuMinuman.add(new Minuman("B1", "Freshly Brewed Coffee", 23));
        menuMinuman.add(new Minuman("B2", "Vanilla Sweet Cream Cold Brew", 50));
        menuMinuman.add(new Minuman("B3", "Cold Brew", 44));
        
        menuMakanan.add(new Makanan("M1", "Petemania Pizza", 112));
        menuMakanan.add(new Makanan("M2", "Mie Rebus Super Mario", 35));
        menuMakanan.add(new Makanan("M3", "Ayam Bakar Goreng Rebus Spesial", 72));
        menuMakanan.add(new Makanan("M4", "Soto Kambing Iga Guling", 124));
        menuMakanan.add(new Makanan("S1", "Singkong Bakar A La Carte", 37));
        menuMakanan.add(new Makanan("S2", "Ubi Cilembu Bakar Arang", 58));
        menuMakanan.add(new Makanan("S3", "Tempe Mendoan", 18));
        menuMakanan.add(new Makanan("S4", "Tahu Bakso Extra Telur", 28));
    }
}