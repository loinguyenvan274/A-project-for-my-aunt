package model;

import jakarta.persistence.*;

@Entity
@Table(name = "don_hang_san_pham")
public class DonHangSanPham {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_don_hang", referencedColumnName = "id_don_hang")
    private DonHang donHang;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_san_pham", referencedColumnName = "id_san_pham")
    private SanPham sanPham;

    @Column(name = "so_luong")
    private int soLuong;

    @Column(name = "gia")
    private long gia;

    public DonHangSanPham(DonHang donHang, SanPham sanPham, int soLuong, long gia) {
        this.donHang = donHang;
        this.sanPham = sanPham;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return  true;
        } else if (!obj.getClass().equals(this.getClass())) {
            return false;
        }
        return this.donHang.equals(((DonHangSanPham)obj).donHang) && this.sanPham.equals(((DonHangSanPham)obj).sanPham);
    }

    public DonHangSanPham(){}

    public DonHang getDonHang() {
        return donHang;
    }

    public void setDonHang(DonHang donHang) {
        this.donHang = donHang;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }
}
