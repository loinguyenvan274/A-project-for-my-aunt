package model;

import jakarta.persistence.*;
import myException.DonHangExc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "don_hang")
public class DonHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_don_hang")
    private long idDonHang;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang", referencedColumnName = "id_khach_hang", nullable = false)
    private KhachHang khachHang;

    @OneToOne(mappedBy = "donHang",cascade = CascadeType.ALL)
    private KhachHangNo khachHangNo;

    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DonHangSanPham> danhSachSanPham = new ArrayList<>();

    @Column(name = "thoi_gian_mua")
    private LocalDateTime thoiGianMua;

    public DonHang(KhachHang khachHang, LocalDateTime thoiGianMua) {
        this.khachHang = khachHang;
        this.thoiGianMua = thoiGianMua;
    }
    public DonHang(){}

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public List<DonHangSanPham> getDanhSachSanPham() {
        return Collections.unmodifiableList( danhSachSanPham);
    }

    public void addSanPhamMua(DonHangSanPham donHangSanPham)throws DonHangExc{
        if(danhSachSanPham.contains(donHangSanPham)){
            throw new DonHangExc("Sản phẩm đã tồn tại trong đơn hàng");
        }
        danhSachSanPham.add(donHangSanPham);
    }
    public void removeSanPhamMua(DonHangSanPham donHangSanPham){
        danhSachSanPham.remove(donHangSanPham);
    }

//    public void setDanhSachSanPham(List<DonHangSanPham> danhSachSanPham) {
//        this.danhSachSanPham = danhSachSanPham;
//    }

    public KhachHangNo getKhachHangNo() {
        return khachHangNo;
    }

    public void setKhachHangNo(KhachHangNo khachHangNo) {
        this.khachHangNo = khachHangNo;
    }

    public LocalDateTime getThoiGianMua() {
        return thoiGianMua;
    }

    public long getIdDonHang() {
        return idDonHang;
    }

    public long getTongTienDonHang(){

       return danhSachSanPham.stream().mapToLong(element -> element.getSoLuong()*element.getGia()).sum();
    }

    public void setThoiGianMua(LocalDateTime thoiGianMua) {
        this.thoiGianMua = thoiGianMua;
    }
}
