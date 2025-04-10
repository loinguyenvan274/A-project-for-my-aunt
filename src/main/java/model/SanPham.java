package model;

import jakarta.persistence.*;

@Entity
@Table(name = "san_pham")
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_san_pham")
    private long idSanPham;

    public long getIdSanPham() {
        return idSanPham;
    }

    @Column(name = "ten_san_pham" , nullable = false)
    private String tenSanPham;

    @Column(name = "gia_ban",precision = 15)
    private double giaBan;

    @Column(name = "don_vi_tinh")
    private String donViTinh;

    @Column(name = "mo_ta")
    private String moTa;

    public SanPham(String tenSanPham, double giaBan, String donViTinh, String moTa) {
        this.tenSanPham = tenSanPham;
        this.giaBan = giaBan;
        this.donViTinh = donViTinh;
        this.moTa = moTa;
    }
    public  SanPham(){}

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
