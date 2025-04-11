package service;

import dao.AppDAO;
import model.DonHang;
import model.KhachHang;
import myException.DonHangExc;

import java.util.List;

public class DonHangService {
    private static DonHangService instance;
    private final AppDAO appDAO = Manager.getInstance().appDAO;
    private DonHangService(){}
    public static DonHangService getInstance(){
        if(instance == null){
            instance = new DonHangService();
        }
        return instance;
    }
    public void checkValidDonHang(DonHang donHang) throws DonHangExc{
        if(donHang.getKhachHang() == null){
            throw new DonHangExc(DonHangExc.DonHangExcType.KHONG_CO_KHACH_HANG);
        }else if (donHang.getDanhSachSanPham().isEmpty()){
            throw new DonHangExc(DonHangExc.DonHangExcType.KHONG_CO_SAN_PHAM);
        }else if(donHang.getKhachHangNo() != null){
            if(donHang.getKhachHangNo().getSoTienKhachTra() >= donHang.getTongTienDonHang()) {
                throw new DonHangExc(DonHangExc.DonHangExcType.TIEN_TRA_LON_HON_TIEN_HANG);
            } else if (donHang.getThoiGianMua()!=null && donHang.getKhachHangNo().getKiHan() != null && !donHang.getThoiGianMua().isBefore(donHang.getKhachHangNo().getKiHan())) {
                throw new DonHangExc(DonHangExc.DonHangExcType.KI_HAN_TRA_TRUOC_NGAY_MUA);
            }
        }
    }

    public void inUpDonHang(DonHang donHang) throws DonHangExc{
        checkValidDonHang(donHang);
        if(appDAO.isAttributeExists(DonHang.class,"idDonHang",Long.toString(donHang.getIdDonHang()))){
            appDAO.updateItem(donHang);
        }else {
            appDAO.saveItem(donHang);
        }
    }
        public List<DonHang> findDonHang(KhachHang khachHang){
           return appDAO.getItemsEqual(DonHang.class,"khachHang",khachHang);
        }



    public List<DonHang> getDonHangs(){return  appDAO.getItems(DonHang.class);}


    public void deleteDonHang(String idDonHang){appDAO.deleteItem(DonHang.class,idDonHang);}
}
