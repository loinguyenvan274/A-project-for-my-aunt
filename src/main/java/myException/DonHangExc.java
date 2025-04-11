package myException;

public class DonHangExc extends Exception {
   public enum DonHangExcType{
       KHONG_CO_KHACH_HANG("Chưa chọn khách hàng."),
       KHONG_CO_SAN_PHAM("Chưa thêm sản phẩm."),
       SAN_PHAM_DA_TON("Sản phẩm đã tồn tại trong đơn hàng."),
       TIEN_TRA_LON_HON_TIEN_HANG("Tiền nợ trả trước lớn hơn tổng tiền đơn hàng."),
       KI_HAN_TRA_TRUOC_NGAY_MUA("Ngày trả tiền phải trước ngày mua.");
      DonHangExcType(String message){
            this.message = message;
        };
      private String message;
      public String getMessage(){return message;}
   }
   private DonHangExcType donHangExcType;
    public DonHangExc(DonHangExcType donHangExcType) {
       super(donHangExcType.getMessage());
       this.donHangExcType = donHangExcType;
    }
    public DonHangExcType getDonHangExcType() {
        return donHangExcType;
    }
}
