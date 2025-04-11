package service;

import dao.AppDAO;
import model.KhachHangNo;

import java.util.List;

public class KhachHangNoService {
    final AppDAO appDAO = Manager.getInstance().appDAO;
    private static KhachHangNoService manager;
    private KhachHangNoService(){}
    
    public static KhachHangNoService getInstance(){
        if(manager == null){
            manager = new KhachHangNoService();
        }
        return manager;
    }
    public List<KhachHangNo> getKhachHangNos(){return  appDAO.getItems(KhachHangNo.class);}
}
