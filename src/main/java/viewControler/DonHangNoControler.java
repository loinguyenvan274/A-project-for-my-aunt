package viewControler;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import model.KhachHang;
import model.KhachHangNo;
import service.DonHangNoService;
import utils.FormatType;
import utils.MoneyFormatter;

import java.time.LocalDate;

public class DonHangNoControler {
    @FXML
    TableColumn<KhachHangNo,String> tenColumn,sdtColumn,kiHanColumn ;
    @FXML
    TableColumn<KhachHangNo,Long> tienDonHangColumn,traTruocColumn,conNoColumn;
    @FXML
    TableView<KhachHangNo> khachHangNoTable;
    @FXML
    Node editForm,formTimKiem;
    @FXML
    Label tenKhachhang,sdt,ngayMua,tienDonHang;
    @FXML
    TextField textFTienKhachTra,textFConNo;
    @FXML
    DatePicker datePKiHan;
    KhachHangNo khachHangNoEditing;

    private void initializeTable(){
        tenColumn.setCellValueFactory(celldata-> new SimpleStringProperty( celldata.getValue().getDonHang().getKhachHang().getTen()));
        sdtColumn.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getDonHang().getKhachHang().getSdt()));
        kiHanColumn.setCellValueFactory(celldata -> {
            LocalDate ngayTra = celldata.getValue().getKiHan();
            if(ngayTra == null)
                return null;
            return new SimpleStringProperty(ngayTra.toString());
        } );
        tienDonHangColumn.setCellValueFactory(celldata -> new SimpleLongProperty(celldata.getValue().getDonHang().getTongTienDonHang()).asObject());
        traTruocColumn.setCellValueFactory(celldata -> new SimpleLongProperty(celldata.getValue().getSoTienKhachTra()).asObject());
        conNoColumn.setCellValueFactory(celldata-> {
            SimpleLongProperty value;
            try {
               value = new SimpleLongProperty(celldata.getValue().getSoTienConNo());
            }catch (Exception e){
                System.out.println("update late");
                return null;
            }
            return value.asObject();
        });
        khachHangNoTable.setItems(FXCollections.observableArrayList(DonHangNoService.getInstance().getKhachHangNos()));

        //setup form tim kiem
        Button btChonKhachHang = (Button) formTimKiem.lookup("#btChonKhachHang");
        Button btDongForm = (Button) formTimKiem.lookup("#btDongForm");
        btChonKhachHang.setOnAction(e->{
            KhachHang khachHangChon = FormChonKhachHangControler.getKhachHangDangChon();
            if(khachHangChon ==null ) {
                return;
            }
            else {
                //TODO:
                khachHangNoTable.setItems(FXCollections.observableArrayList(DonHangNoService.getInstance().findKhachHangNo(khachHangChon)));
            }
            closeFormTimKiem();
        });
        btDongForm.setOnAction(e->{closeFormTimKiem();});
    }

    @FXML
    private void initialize(){
        initializeTable();
        khachHangNoTable.refresh();
        editForm.setVisible(false);
        formTimKiem.setVisible(false);
    }
    //khach hang no dang chon o table
    private KhachHangNo getKhachHangDangChon(){
        return khachHangNoTable.getSelectionModel().getSelectedItem();
    }
    @FXML
    private void setInforKhachHangNo(){
        khachHangNoEditing = getKhachHangDangChon();
        if(khachHangNoEditing == null)
            return;
        openFormEdit(khachHangNoEditing);

    }
    private void openFormEdit(KhachHangNo khachHangNoChonEdit){
        tenKhachhang.setText(khachHangNoChonEdit.getDonHang().getKhachHang().getTen());
        sdt.setText(khachHangNoChonEdit.getDonHang().getKhachHang().getSdt());
        ngayMua.setText(khachHangNoChonEdit.getDonHang().getThoiGianMua().toString());
        tienDonHang.setText(Long.toString(khachHangNoChonEdit.getDonHang().getTongTienDonHang()));
        textFTienKhachTra.setText(Long.toString(khachHangNoChonEdit.getSoTienKhachTra()));
        textFConNo.setText(Long.toString(khachHangNoChonEdit.getSoTienConNo()));
        datePKiHan.setValue(khachHangNoChonEdit.getKiHan());
        editForm.setVisible(true);
    }

    @FXML
    private void deleteKhachHangNo(){
        KhachHangNo khachHangChonXoa = getKhachHangDangChon();
        if(khachHangChonXoa == null)
            return;
        //todo
        DonHangNoService.getInstance().xoaKhachHangNo(khachHangChonXoa);
        khachHangNoTable.getItems().remove(khachHangChonXoa);
    }

    @FXML
    private void closeEditForm(){
        editForm.setVisible(false);
    }
    @FXML
    private void setTienKhachTra(){
        khachHangNoEditing.setSoTienKhachTra(MoneyFormatter.convertToMoneyFormat(FormatType.KHONG_XU_LY_NGOAI_LE,textFTienKhachTra.getText()));
        textFConNo.setText(Long.toString(khachHangNoEditing.getSoTienConNo()));
    }
    @FXML
    private void setKiHan(){
        khachHangNoEditing.setKiHan(datePKiHan.getValue());
    }

    @FXML
    private void updateKhachHangNo(){
        DonHangNoService.getInstance().updateInfoKhachHangNo(khachHangNoEditing);
        khachHangNoTable.refresh();
        closeEditForm();
    }
    @FXML
    private void openFromTimKiem(){
        formTimKiem.setVisible(true);
    }
    private void closeFormTimKiem(){
        formTimKiem.setVisible(false);
    }








}
