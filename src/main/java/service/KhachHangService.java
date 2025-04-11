package service;

import dao.AppDAO;
import model.KhachHang;
import myException.KhachHangExc;
import utils.DinhDangString;

import java.util.List;


public class KhachHangService {
    public enum FindType{
        TIM_THEO_TEN ("ten"),
        TIM_THEO_SDT ("sdt"),
        TIM_THEO_GMAIL("gmail"),
        TIM_THEO_DIA_CHI("diaChi");
        private final String code;
        FindType(String code){
            this.code = code;
        }
        public String getCode(){
            return code;
        }
        public String getStrIntoFormatType(String str){
            String formatedStr = "";
            switch (this) {
                case TIM_THEO_TEN:
                    formatedStr = DinhDangString.dinhDangTen(str);
                    break;
                case TIM_THEO_GMAIL:
                case TIM_THEO_SDT:
                    formatedStr = DinhDangString.boKhoangTrang(str);
                    break;
                case TIM_THEO_DIA_CHI:
                    formatedStr = DinhDangString.vanBanDep(str);
                    break;

            }
            return formatedStr;

        }
    }
    private static KhachHangService instance;
    private final AppDAO appDAO = Manager.getInstance().appDAO;
    private KhachHangService(){}

    private void checkKhachHangValidWhenAdd(KhachHang khachHang) throws KhachHangExc {
        if(khachHang.getTen().isEmpty()){
            throw new KhachHangExc("Khach hang chua co ten", KhachHangExc.MaLoi.KHONG_CO_TEN);
        }else if(khachHang.getSdt().isEmpty()){
            throw new KhachHangExc("Khach hang chua co So dien thoai", KhachHangExc.MaLoi.KHONG_CO_SDT);
        }else if(appDAO.isAttributeExists(KhachHang.class,"sdt",khachHang.getSdt())){
            System.out.println("Lỗi: Số điện thoại đã tồn tại.");
            throw new KhachHangExc("Số điện thoại đã tồn tại", KhachHangExc.MaLoi.TRUNG_SO_DIEN_THOAI);
        } else if (appDAO.isAttributeExists(KhachHang.class,"gmail", khachHang.getGmail())) {
            System.out.println("Lỗi: gmail đã tồn tại.");
            throw new KhachHangExc("gmail đã tồn tại", KhachHangExc.MaLoi.TRUNG_GMAIL);
        }
    }
    private void checkKhachHangValidWhenUpdate(KhachHang khachHang) throws KhachHangExc{
        KhachHang khachhangInDB = appDAO.getKhachHang(Long.toString(khachHang.getIdKhachHang()));
        if(khachHang.getTen().isEmpty()){
            throw new KhachHangExc("Khach hang chua co ten", KhachHangExc.MaLoi.KHONG_CO_TEN);
        }else if(khachHang.getSdt().isEmpty()){
            throw new KhachHangExc("Khach hang chua co So dien thoai", KhachHangExc.MaLoi.KHONG_CO_SDT);
        }else if(appDAO.isAttributeExists(KhachHang.class,"sdt",khachHang.getSdt() ) && (khachhangInDB == null || !khachhangInDB.getSdt().equals(khachHang.getSdt()))){
            System.out.println("Lỗi: Số điện thoại đã tồn tại.");
            throw new KhachHangExc("Số điện thoại đã tồn tại", KhachHangExc.MaLoi.TRUNG_SO_DIEN_THOAI);
        } else if (appDAO.isAttributeExists(KhachHang.class,"gmail", khachHang.getGmail()) && (khachhangInDB == null || !khachhangInDB.getGmail().equals(khachHang.getGmail()) )) {
            System.out.println("Lỗi: gmail đã tồn tại.");
            throw new KhachHangExc("gmail đã tồn tại", KhachHangExc.MaLoi.TRUNG_GMAIL);
        }
    }
    public void addKhachHang(KhachHang khachHang) throws KhachHangExc {
        checkKhachHangValidWhenAdd(khachHang);
        appDAO.saveItem(khachHang);
    }
    public void updateInfoKhachHang(KhachHang khachHang) throws KhachHangExc{
        checkKhachHangValidWhenUpdate(khachHang);
        appDAO.updateItem(khachHang);
    }


    public List<KhachHang> findKhachHang(String key, FindType findType){
        return appDAO.getItemsLike(KhachHang.class, findType.getStrIntoFormatType(key),findType.getCode());

    }
    public List<KhachHang> getKhachHangs(){
        return appDAO.getItems(KhachHang.class);
    }

    public void deleteKhachHang(String idKhachHang){
        appDAO.deleteItem(KhachHang.class,idKhachHang);
    }

    public static KhachHangService getInstance(){
        if(instance == null){
            instance = new KhachHangService();
        }
        return instance;
    }

}
