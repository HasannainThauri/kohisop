class QRIS implements ChannelPembayaran {
    @Override
    public double hitungTotalAkhir(double subtotal) {
        return subtotal*0.95;
    }
    @Override
    public boolean cekSaldo(double saldo, double totalAkhir) {
        return saldo >= totalAkhir;
    }
}