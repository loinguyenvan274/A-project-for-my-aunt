package service;

import dao.AppDAO;
import model.SanPham;
import myException.SanPhamExc;
import utils.DinhDangString;

import java.util.List;

public class SanPhamService {
    private static SanPhamService instance;
    private final AppDAO appDAO = Manager.getInstance().appDAO;
    private SanPhamService(){}
    public static SanPhamService getInstance(){
        if(instance == null){
            instance = new SanPhamService();
        }
        return instance;
    }

    private void checkValidSanPham(SanPham sanPham)throws SanPhamExc {
        if(sanPham.getTenSanPham() == null || sanPham.getTenSanPham().isEmpty()){
            throw new SanPhamExc("Sản phẩm Chưa Có Tên", SanPhamExc.MaLoi.KHONG_CO_TEN);
        }
    }public void addSanPham(SanPham sanPham) throws SanPhamExc{
        checkValidSanPham(sanPham);
        appDAO.saveItem(sanPham);
    }
    public void updateInfoSanPham(SanPham sanPham)throws SanPhamExc{
        checkValidSanPham(sanPham);
        appDAO.updateItem(sanPham);
    }
    public List<SanPham> getSanPhams(){
        return appDAO.getItems(SanPham.class);
    }
    public void deleteSanPham(String idSanPham){
        appDAO.deleteItem(SanPham.class,idSanPham);
    }
    public List<SanPham> findSanPhamTheoTen(String tenSanPham){
        return appDAO.<SanPham>getItemsLike(SanPham.class, DinhDangString.vanBanDep(tenSanPham),"tenSanPham");
    }
}
