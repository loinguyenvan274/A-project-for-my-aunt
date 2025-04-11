package service;

import dao.AppDAO;
import model.DonHang;
import model.KhachHang;
import model.KhachHangNo;
import model.SanPham;
import myException.KhachHangExc;
import myException.KhachHangExc.MaLoi;
import myException.SanPhamExc;
import utils.DinhDangString;

import java.util.List;

public class Manager  {
    final AppDAO appDAO = new AppDAO();
    private static Manager manager;
    private Manager(){}


   public static Manager getInstance(){
        if(manager == null){
            manager = new Manager();
        }
        return manager;
    }
}
