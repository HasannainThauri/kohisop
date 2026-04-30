public interface MataUang {
    String getKodeMataUang();
    String getNamaMataUang();
    
    double konversiDariIDR(double nilaiIDR);
    
    String getRateInfo();
}
