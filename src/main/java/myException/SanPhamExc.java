package myException;

public class SanPhamExc extends Exception {
       public enum MaLoi{
        KHONG_CO_TEN,
    };

    private MaLoi maLoi;
    public SanPhamExc(String message,MaLoi maLoi) {
        super(message);
        this.maLoi = maLoi;
    }
    public boolean isMaLoi(MaLoi maLoi){
        return maLoi.equals(this.maLoi);
    }
}
