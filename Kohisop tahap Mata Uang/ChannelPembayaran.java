public interface ChannelPembayaran {
    double hitungTotalAkhir(double subtotal);
    boolean cekSaldo(double saldo, double totalAkhir);
}