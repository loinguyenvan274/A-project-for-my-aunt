package viewControler;

import javafx.event.EventDispatcher;
import model.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import myException.DonHangExc;
import service.DonHangService;
import utils.FormatType;
import utils.MoneyFormatter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class
DonHangControler {
  private DonHang donHangMoiOrUpdate;
  @FXML
  private Button btChonSanPham, btAddOrUpdateDonHang;
  @FXML
  private BorderPane addForm;
  @FXML
  private HBox cuaSoChonKhachHang,formChonSanPham;
  @FXML
  private Label tenKhachHangMoi,sdtKhachHangMoi,CTDHTen,CTDHSdt,CTDHGmail,CTDHDiaChi,CTDHThoiGianMua,CTDHTongTien;
  @FXML
  private TextField textFGiaBan,textFSoTienKhachTraTruoc;
  @FXML
  private Spinner spinnerSoLuong;
  @FXML
  private TableView sanPhamMuaTable, donHangTable,CTDHCacSanPhamTable;
  @FXML
  private DatePicker datePickerThoiGianMua,datePickerKiHan;
  @FXML
  private CheckBox checkBoxIsKhachNo;


  // ở phần tạo đơn hàng mới
  @FXML private TableColumn<DonHangSanPham,String> tenSanPham,CTDHTenSanPhamColumn,donViTinh,cTDHDonViTinh;
  @FXML private TableColumn<DonHangSanPham,Long> giaBan,tongTien,CTDHGiaColumn,CTDHTongTienColumn;
  @FXML private TableColumn<DonHangSanPham,Integer> soLuong,CTDHSoLuongColumn;

  @FXML private TableColumn<DonHang,String> donHangTenKhachHangColumn,donHangTGMuaColumn,donHangTongTienColumn;


static int count  = 0;
    @FXML private void initialize(){
        Button btChonKhachHang = (Button) cuaSoChonKhachHang.lookup("#btChonKhachHang");
        Button btDongForm = (Button) cuaSoChonKhachHang.lookup("#btDongForm");
        btChonKhachHang.setOnAction(e->{
            KhachHang khachHangChon = FormChonKhachHangControler.getKhachHangDangChon();
            if(khachHangChon ==null )
                return;
            if(addForm.isVisible()){
                donHangMoiOrUpdate.setKhachHang(khachHangChon);
                updateInfoOfTaoDonHangForm();
            }else {
                //TODO:
                donHangTable.setItems(FXCollections.observableArrayList(DonHangService.getInstance().findDonHang(khachHangChon)));
            }
            closeCuaSoChonKhachHang();
        });
        btDongForm.setOnAction(e->{closeCuaSoChonKhachHang();});


        TableView tableSanPhamView = (TableView) formChonSanPham.lookup("#tableSanPhamView");
        EventDispatcher xyLyNutChonSanPham = (e, eventDispatchChain)->{
            btChonSanPham.setDisable(true);
            if(tableSanPhamView != null){
                try{
                    Integer.parseInt( spinnerSoLuong.getEditor().getText());
                    Long.parseLong(textFGiaBan.getText());

                    SanPham sanPham = (SanPham) tableSanPhamView.getSelectionModel().getSelectedItem();
                    if (sanPham != null) {
                        btChonSanPham.setDisable(false);
                    }
                }catch (NumberFormatException exp){
                }
            }
            return  eventDispatchChain.dispatchEvent(e);
        };
        formChonSanPham.setEventDispatcher(xyLyNutChonSanPham);

        checkBoxIsKhachNo.setOnAction((e->{
            KhachHangNo khachHangNo = null;
            if(checkBoxIsKhachNo.isSelected()){
                khachHangNo = new KhachHangNo();
            }
            setKhachHangNoInputBox(khachHangNo);
        }));

        cuaSoChonKhachHang.setVisible(false);
        addForm.setVisible(false);
        formChonSanPham.setVisible(false);

        initDonHangSanPhamColumns(tenSanPham,soLuong,giaBan,tongTien,donViTinh);
        initDonHangSanPhamColumns(CTDHTenSanPhamColumn,CTDHSoLuongColumn,CTDHGiaColumn,CTDHTongTienColumn,cTDHDonViTinh);

        donHangTenKhachHangColumn.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getKhachHang().getTen()));
        donHangTGMuaColumn.setCellValueFactory(celldata -> {
            LocalDate thoiGianMua = celldata.getValue().getThoiGianMua();
            String thoigianMuaString = "";
            if(thoiGianMua !=null){
                thoigianMuaString = thoiGianMua.toString();
            }
           return new SimpleStringProperty(thoigianMuaString);
        });
        donHangTongTienColumn.setCellValueFactory(celldata->
        {
            long tongTien = 0;
            List<DonHangSanPham> danhSachSanPham = celldata.getValue().getDanhSachSanPham();
            for(DonHangSanPham items : danhSachSanPham){
                tongTien += items.getGia()*items.getSoLuong();
            }
           return new SimpleStringProperty(Long.toString(tongTien));
        });

        refreshDonHangTable();
    }
    private void initDonHangSanPhamColumns(TableColumn<DonHangSanPham, String> column1,TableColumn<DonHangSanPham, Integer> column2,TableColumn<DonHangSanPham, Long> column3,TableColumn<DonHangSanPham, Long> column4,TableColumn<DonHangSanPham, String> column5){
        column1.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getSanPham().getTenSanPham()));
        column2.setCellValueFactory(celldata -> new SimpleIntegerProperty(celldata.getValue().getSoLuong()).asObject());
        column3.setCellValueFactory(celldata -> new SimpleLongProperty(celldata.getValue().getGia()).asObject());
        column4.setCellValueFactory(celldata -> new SimpleLongProperty(celldata.getValue().getGia()*celldata.getValue().getSoLuong()).asObject());
        column5.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getSanPham().getDonViTinh()));

    }


    private DonHang  getDonHangFromTable(){
        return (DonHang) donHangTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void setInforChiTietDonHang(){
        DonHang donHang = getDonHangFromTable();
        if(donHang == null)
            return;
        String ten = "",sdt = "",gmail = "",diaChi = "",thoiGianMua,tongTienDonHang;

        thoiGianMua = (donHang.getThoiGianMua() != null ? donHang.getThoiGianMua().toString() : "");

        KhachHang khachHang = donHang.getKhachHang();
        if(khachHang != null){
            ten = khachHang.getTen();
            sdt = khachHang.getSdt();
            gmail = khachHang.getGmail();
            diaChi = khachHang.getDiaChi();
        }
        tongTienDonHang = Long.toString( donHang.getTongTienDonHang());


        CTDHTen.setText(ten);
        CTDHSdt.setText(sdt);
        CTDHGmail.setText(gmail);
        CTDHDiaChi.setText(diaChi);
        CTDHThoiGianMua.setText(thoiGianMua);
        CTDHTongTien.setText(tongTienDonHang);

        CTDHCacSanPhamTable.setItems(FXCollections.observableArrayList(donHang.getDanhSachSanPham()));
    }


    @FXML
    private void refreshDonHangTable(){
        donHangTable.setItems(FXCollections.observableArrayList(DonHangService.getInstance().getDonHangs()));
    }
   private void updateInfoOfTaoDonHangForm(){
       if(donHangMoiOrUpdate == null){
           donHangMoiOrUpdate = new DonHang();
       }
       KhachHang khachHang = donHangMoiOrUpdate.getKhachHang();
       KhachHangNo khachHangNo = donHangMoiOrUpdate.getKhachHangNo();
       datePickerThoiGianMua.setValue(donHangMoiOrUpdate.getThoiGianMua());
       setKhachHangNoInputBox(khachHangNo);
       if(khachHang !=null){
           tenKhachHangMoi.setText(khachHang.getTen());
           sdtKhachHangMoi.setText(khachHang.getSdt());
       }else{
           tenKhachHangMoi.setText("");
           sdtKhachHangMoi.setText("");
       }
       ObservableList<DonHangSanPham> data= FXCollections.observableArrayList(donHangMoiOrUpdate.getDanhSachSanPham());
       sanPhamMuaTable.setItems(data);


   }
   private void setKhachHangNoInputBox(KhachHangNo khachHangNo){
       boolean disableFlag = true;
       String soTienKhachTraTruoc = "";
       LocalDate kiHanTra = null;
       if(khachHangNo!=null){
           soTienKhachTraTruoc = Long.toString( khachHangNo.getSoTienKhachTra());
           kiHanTra = khachHangNo.getKiHan();
           disableFlag = false;
       }
       checkBoxIsKhachNo.setSelected(!disableFlag);
       textFSoTienKhachTraTruoc.setDisable(disableFlag);
       textFSoTienKhachTraTruoc.setText(soTienKhachTraTruoc);
       datePickerKiHan.setDisable(disableFlag);
       datePickerKiHan.setValue(kiHanTra);
   }
   @FXML
   private void openAddForm() throws IOException {
        initAddForm();
        btAddOrUpdateDonHang.setText("Thêm đơn hàng");
   }
   private void initAddForm(){
       updateInfoOfTaoDonHangForm();
       addForm.setVisible(true);
   }


   @FXML
   private void closeAddForm(){
      addForm.setVisible(false);
      donHangMoiOrUpdate = null;
      refreshDonHangTable();
   }

   @FXML
    private void openCuaSoChonKhachHang(){
        cuaSoChonKhachHang.setVisible(true);
   }
   @FXML
   private void openCuaSoChonSanPham(){formChonSanPham.setVisible(true);}
   @FXML
    private void closeCuaSoChonSanPham() {formChonSanPham.setVisible(false);}

    private void closeCuaSoChonKhachHang(){
        cuaSoChonKhachHang.setVisible(false);
   }
   @FXML
   private void chooseSanPham(){
            int soLuong = Integer.parseInt( spinnerSoLuong.getEditor().getText());
            long soTien = Long.parseLong(textFGiaBan.getText());
            TableView tableSanPhamView = (TableView) formChonSanPham.lookup("#tableSanPhamView");
            SanPham sanPham = (SanPham) tableSanPhamView.getSelectionModel().getSelectedItem();
            DonHangSanPham donHangSanPham = new DonHangSanPham(donHangMoiOrUpdate,sanPham,soLuong,soTien);

        /*
        TODO: làm cái thông báo khi người dùng chọn sản phẩm mà đã tồn tại rồi.
        FIXME: thêm cái thông báo khi người dùng nhấn thêm.
         */
       try{
           donHangMoiOrUpdate.addSanPhamMua(donHangSanPham);
       }catch (DonHangExc e){
           System.out.println("error: " + e.getMessage());
       }
            updateInfoOfTaoDonHangForm();
            closeCuaSoChonSanPham();
   }

   private KhachHangNo getKhachHangNoFromUI(){
        if(!checkBoxIsKhachNo.isSelected()){
            return null;
        }
        KhachHangNo khachHangNo = new KhachHangNo(donHangMoiOrUpdate, MoneyFormatter.convertToMoneyFormat(FormatType.KHONG_XU_LY_NGOAI_LE,textFSoTienKhachTraTruoc.getText()),datePickerKiHan.getValue());
        return khachHangNo;
   }

   @FXML
    private void addDonHang() throws DonHangExc {
       LocalDate thoiGianMua = datePickerThoiGianMua.getValue();
       donHangMoiOrUpdate.setThoiGianMua(thoiGianMua != null ? thoiGianMua : null);
       donHangMoiOrUpdate.setKhachHangNo(getKhachHangNoFromUI());
       // TODO:

       DonHangService.getInstance().inUpDonHang(donHangMoiOrUpdate);
       closeAddForm();
   }

   @FXML
    private void xoaSanPhamMua(){
        DonHangSanPham donHangSanPham = (DonHangSanPham) sanPhamMuaTable.getSelectionModel().getSelectedItem();
        if(donHangSanPham == null)
            return;
        donHangMoiOrUpdate.removeSanPhamMua(donHangSanPham);
        sanPhamMuaTable.getItems().remove(donHangSanPham);
//        sanPhamMuaTable.refresh();
   }
   @FXML
    private void xoaDonHang(){
       DonHang donHang = getDonHangFromTable();
       if (donHang !=null){
           DonHangService.getInstance().deleteDonHang(Long.toString(donHang.getIdDonHang()));
       }
       refreshDonHangTable();
   }
   @FXML
    private void openUpdateDonHangForm() throws IOException {
       donHangMoiOrUpdate = getDonHangFromTable();
       if(donHangMoiOrUpdate != null){
            initAddForm();
            btAddOrUpdateDonHang.setText("Hoàn tất");
        }
   }
   @FXML
    private void timKiemKhachHang(){
        openCuaSoChonKhachHang();
   }
}
