// 10 JPY = 1 IDR  →  1 IDR = 10 JPY
public class JPY implements MataUang {

    @Override
    public String getKodeMataUang() { 
        return "JPY"; 
    }

    @Override
    public String getNamaMataUang() { 
        return "Japanese Yen"; 
    }

    @Override
    public double konversiDariIDR(double nilaiIDR) {
        return nilaiIDR * 10.0;
    }

    @Override
    public String getRateInfo() { 
        return "10 JPY = 1 IDR"; 
    }
}
