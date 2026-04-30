// 1 EUR = 14 IDR  →  1 IDR = 1/14 EUR
public class EUR implements MataUang {

    @Override
    public String getKodeMataUang() { 
        return "EUR"; 
    }

    @Override
    public String getNamaMataUang() { 
        return "Euro"; 
    }

    @Override
    public double konversiDariIDR(double nilaiIDR) {
        return nilaiIDR / 14.0;
    }

    @Override
    public String getRateInfo() { 
        return "1 EUR = 14 IDR"; 
    }
}
