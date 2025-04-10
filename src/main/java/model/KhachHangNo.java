package model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "khach_hang_no")
public class KhachHangNo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_don_hang", referencedColumnName = "id_don_hang", nullable = false)
    private DonHang donHang;

    @Column(name = "so_tien_khach_tra")
    private long soTienKhachTra;

    @Column(name = "ki_han")
    private LocalDate kiHan;

    public KhachHangNo( DonHang donHang, long soTienKhachTra, LocalDate kiHan) {
        this.donHang = donHang;
        this.soTienKhachTra = soTienKhachTra;
        this.kiHan = kiHan;
    }
    public KhachHangNo(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DonHang getDonHang() {
        return donHang;
    }
    public long getSoTienConNo() {return donHang.getTongTienDonHang() - getSoTienKhachTra();}

    public void setDonHang(DonHang donHang) {
        this.donHang = donHang;
    }

    public long getSoTienKhachTra() {
        return soTienKhachTra;
    }

    public void setSoTienKhachTra(long soTienKhachTra) {
        this.soTienKhachTra = soTienKhachTra;
    }

    public LocalDate getKiHan() {
        return kiHan;
    }

    public void setKiHan(LocalDate kiHan) {
        this.kiHan = kiHan;
    }
}
