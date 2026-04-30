class Minuman extends Menu {
    public Minuman(String kode, String nama, int harga) {
        super(kode, nama, harga);
    }

    @Override
    public double hitungPajak() {
        if (harga < 50) {
            return 0;
        } else if (harga <= 55) {
            return 0.08;
        } else {
            return 0.11;
        }
    }
}