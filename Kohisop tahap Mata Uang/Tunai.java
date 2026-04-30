class Tunai implements ChannelPembayaran {
    @Override
    public double hitungTotalAkhir(double subtotal) {
        return subtotal;
    }
    @Override
    public boolean cekSaldo(double saldo, double totalAkhir) {
        return true; 
    }
}
