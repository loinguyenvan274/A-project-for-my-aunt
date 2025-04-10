package model;

import jakarta.persistence.*;
import myException.KhachHangExc;
import utils.DinhDangString;

@Entity
@Table(name = "khach_hang")
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_khach_hang")
    private long idKhachHang;

    @Column(name = "ten",nullable = false)
    private String ten;

    @Column(name = "sdt",nullable = false, unique = true)
    private String sdt;

    @Column(name = "gmail",unique = true)
    private String gmail;

    @Column(name = "dia_chi")
    private String diaChi;

    public KhachHang( String ten, String sdt, String gmail, String diaChi){
        setTen(ten);
        setSdt(sdt);
        setGmail(gmail);
        setDiaChi(diaChi);
    }
    public KhachHang(){}

    public long getIdKhachHang() {
        return idKhachHang;
    }

    public String getTen() {
        return ten;
    }

    public KhachHang setTen(String ten) {
        if(ten != null){
            this.ten = DinhDangString.dinhDangTen(ten);
        }
        return  this;
    }

    public String getSdt() {
        return sdt;
    }

    public KhachHang setSdt(String sdt){
        if(sdt != null ){
            this.sdt = DinhDangString.boKhoangTrang(sdt);
        }
        return this;
    }

    public String getGmail() {
        return gmail;
    }

    public KhachHang setGmail(String gmail) {
        if(gmail == null || gmail.isEmpty() ) {
            this.gmail = null;
        }else{
            this.gmail = DinhDangString.boKhoangTrang(gmail);
        }
        return  this;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public KhachHang setDiaChi(String diaChi) {
        if(diaChi != null){
            this.diaChi = DinhDangString.vanBanDep(diaChi);
        }
        return this;
    }
}
