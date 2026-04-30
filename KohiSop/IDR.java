public class IDR implements MataUang {

    @Override
    public String getKodeMataUang() { 
        return "IDR"; 
    }

    @Override
    public String getNamaMataUang() { 
        return "Indonesian Rupiah"; 
    }

    @Override
    public double konversiDariIDR(double nilaiIDR) {
        return nilaiIDR; 
    }

    @Override
    public String getRateInfo() { 
        return "1 IDR = 1 IDR"; 
    }
}
