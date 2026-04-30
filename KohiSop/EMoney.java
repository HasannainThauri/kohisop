class EMoney implements ChannelPembayaran {
    @Override
    public double hitungTotalAkhir(double subtotal) {
        return subtotal*0.93 + 20;
    }
    @Override
    public boolean cekSaldo(double saldo, double totalAkhir) {
        return saldo >= totalAkhir;
    }
}