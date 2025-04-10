package service;

import dao.AppDAO;
import model.DonHang;

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

    public void addDonHang(DonHang donHang) {
        if(appDAO.isAttributeExists(DonHang.class,"idDonHang",Long.toString(donHang.getIdDonHang()))){
            appDAO.updateItem(donHang);
        }else {
            appDAO.saveItem(donHang);
        }
    }

    public List<DonHang> getDonHangs(){return  appDAO.getItems(DonHang.class);}


    public void deleteDonHang(String idDonHang){appDAO.deleteItem(DonHang.class,idDonHang);}
}
