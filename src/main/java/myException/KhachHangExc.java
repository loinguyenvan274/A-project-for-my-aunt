package myException;

public class KhachHangExc extends Exception {
    public enum MaLoi{
        KHONG_CO_TEN,
        KHONG_CO_SDT,
        TRUNG_SO_DIEN_THOAI,
        TRUNG_GMAIL;
    };

    private MaLoi maLoi;
    public KhachHangExc(String message, MaLoi maLoi) {
        super(message);
        this.maLoi = maLoi;
    }
    public MaLoi getMaLoi(){return  maLoi;}
    public boolean isMaLoi(MaLoi maLoi){
        return maLoi.equals(this.maLoi);
    }
}
