package model;

import jakarta.persistence.*;
import myException.DonHangExc;

import java.time.LocalDate;
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

    @OneToOne(mappedBy = "donHang",cascade = CascadeType.ALL,orphanRemoval = true)
    private KhachHangNo khachHangNo;

    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DonHangSanPham> danhSachSanPham = new ArrayList<>();

    @Column(name = "thoi_gian_mua")
    private LocalDate thoiGianMua;

    public DonHang(KhachHang khachHang, LocalDate thoiGianMua) {
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
            throw new DonHangExc(DonHangExc.DonHangExcType.SAN_PHAM_DA_TON);
        }
        danhSachSanPham.add(donHangSanPham);
    }
    public void removeSanPhamMua(DonHangSanPham donHangSanPham){
        danhSachSanPham.remove(donHangSanPham);
    }

    public KhachHangNo getKhachHangNo() {
        return khachHangNo;
    }

    public void setKhachHangNo(KhachHangNo khachHangNo) {
        this.khachHangNo = khachHangNo;
    }

    public LocalDate getThoiGianMua() {
        return thoiGianMua;
    }

    public long getIdDonHang() {
        return idDonHang;
    }

    public long getTongTienDonHang(){

       return danhSachSanPham.stream().mapToLong(element -> element.getSoLuong()*element.getGia()).sum();
    }

    public void setThoiGianMua(LocalDate thoiGianMua) {
        this.thoiGianMua = thoiGianMua;
    }
}
