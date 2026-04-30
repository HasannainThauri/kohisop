// 1 USD = 15 IDR  →  1 IDR = 1/15 USD
public class USD implements MataUang {

    @Override
    public String getKodeMataUang() { 
        return "USD"; 
    }

    @Override
    public String getNamaMataUang() { 
        return "US Dollar"; 
    }

    @Override
    public double konversiDariIDR(double nilaiIDR) {
        return nilaiIDR / 15.0;
    }

    @Override
    public String getRateInfo() { 
        return "1 USD = 15 IDR"; 
    }
}
