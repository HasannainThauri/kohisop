public abstract class Menu {
    protected String kode;
    protected String nama;
    protected int harga;

    public Menu(String kode, String nama, int harga) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
    }

    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public int getHarga() { return harga; }

    public abstract double hitungPajak();
}