public class ItemPesanan {
    private Menu menu;
    private int qty;

    public ItemPesanan(Menu menu, int qty) {
        this.menu = menu;
        this.qty = qty;
    }

    public Menu getMenu() { return menu; }
    public int getQty() { return qty; }

    public double hitungPajak() {
        double persen = menu.hitungPajak();
        return menu.getHarga() * persen * qty;
    }
}