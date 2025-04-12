package service;

import dao.AppDAO;
import model.DonHang;
import model.KhachHang;
import model.KhachHangNo;

import java.util.ArrayList;
import java.util.List;

public class DonHangNoService {
    final AppDAO appDAO = Manager.getInstance().appDAO;
    private static DonHangNoService manager;
    private DonHangNoService(){}
    
    public static DonHangNoService getInstance(){
        if(manager == null){
            manager = new DonHangNoService();
        }
        return manager;
    }

    public void xoaKhachHangNo(KhachHangNo khachHangNoXoa){
        DonHang donHang = khachHangNoXoa.getDonHang();
        donHang.setKhachHangNo(null);
        try {
             DonHangService.getInstance().inUpDonHang(donHang);
            }catch (Exception e){
            System.out.println("lỗi xóa khách hàng nợ: " + e.getMessage());
        }
    }

    public List<KhachHangNo> findKhachHangNo(KhachHang khachHang){
        List<DonHang> donHangs = DonHangService.getInstance().findDonHang(khachHang);
        List<KhachHangNo> khachHangNos = new ArrayList<>();
        for(DonHang item : donHangs){
            if(item.getKhachHangNo() !=null) {
                khachHangNos.add(item.getKhachHangNo());
            }
        }
        return khachHangNos;
    }

    public void updateInfoKhachHangNo(KhachHangNo khachHangNo){ appDAO.updateItem(khachHangNo);}

    public List<KhachHangNo> getKhachHangNos(){return  appDAO.getItems(KhachHangNo.class);}
}
