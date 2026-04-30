// 1 MYR = 4 IDR  →  1 IDR = 1/4 MYR
public class MYR implements MataUang {

    @Override
    public String getKodeMataUang() { 
        return "MYR"; 
    }

    @Override
    public String getNamaMataUang() { 
        return "Malaysian Ringgit"; 
    }

    @Override
    public double konversiDariIDR(double nilaiIDR) {
        return nilaiIDR / 4.0;
    }

    @Override
    public String getRateInfo() { 
        return "1 MYR = 4 IDR"; 
    }
}
