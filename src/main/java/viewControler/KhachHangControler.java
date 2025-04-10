package viewControler;

import model.KhachHang;
import service.KhachHangService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import myException.KhachHangExc;


public class KhachHangControler {
    @FXML
    private HBox hBoxChuabtAddKH;
    @FXML
    private VBox addKHFrom;
    @FXML
    private TextField textFHoTen,textFSDT,textFGmail,textFDiaChi,thanhTimKiem;
    @FXML
    private Label xuLyTenThBao,xuLySdtThBao,xuLyGmailThBao;
    @FXML
    private TableView<KhachHang> khachHangTable;
    @FXML
    private TableColumn<KhachHang,String> tenColumn,sdtColumn,gmailColumn,diaChiColumn;
    @FXML
    private Button btSumitDataBase,btUpdateDataBase;

    private static KhachHang editingKhachHang;

    @FXML
    public void initialize() {
        closeEditKHForm();
        tenColumn.setCellValueFactory(new PropertyValueFactory<>("ten"));
        sdtColumn.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        gmailColumn.setCellValueFactory(new PropertyValueFactory<>("gmail"));
        diaChiColumn.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        refectTable();
    }

    private void initOpenForm(){
        hBoxChuabtAddKH.setManaged(false);
        hBoxChuabtAddKH.setVisible(false);
        addKHFrom.setManaged(true);
        addKHFrom.setVisible(true);
    }

    @FXML
    private void openAddKhachHangForm(){
        initOpenForm();
        btSumitDataBase.setVisible(true);
        btSumitDataBase.setManaged(true);
        btUpdateDataBase.setManaged(false);
        btUpdateDataBase.setVisible(false);
    }

    private void xuLyThongBaoNhap(KhachHangExc e){
        if(e.isMaLoi(KhachHangExc.MaLoi.KHONG_CO_TEN)){
            xuLyTenThBao.setText("Vui lòng điền tên");
            xuLyTenThBao.setVisible(true);
        } else if (e.isMaLoi(KhachHangExc.MaLoi.KHONG_CO_SDT)) {
            xuLySdtThBao.setText("Vui lòng điền số điện thoại");
            xuLySdtThBao.setVisible(true);
        }
        else if(e.isMaLoi(KhachHangExc.MaLoi.TRUNG_SO_DIEN_THOAI)){
            xuLySdtThBao.setText("Số điện thoại đã tồn tại");
            xuLySdtThBao.setVisible(true);
        } else if (e.isMaLoi(KhachHangExc.MaLoi.TRUNG_GMAIL)) {
            xuLyGmailThBao.setText("Gmail đã tồn tại");
            xuLyGmailThBao.setVisible(true);
        }
    }

    @FXML
    private void addKhachHang(){
        closeCanhBaoNhap();
        try{
            KhachHangService.getInstance().addKhachHang(new KhachHang(textFHoTen.getText(),textFSDT.getText(),textFGmail.getText(),textFDiaChi.getText()));
            refectTable();
            closeEditKHForm();
        }catch(KhachHangExc e){
           xuLyThongBaoNhap(e);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    @FXML
    private void refectTable(){
        ObservableList<KhachHang> data = FXCollections.observableArrayList(KhachHangService.getInstance().getKhachHangs());
        khachHangTable.setItems(data);
    }

    @FXML
    private void removeRow(){
        KhachHang selectedKhachHang = khachHangTable.getSelectionModel().getSelectedItem();
        if(selectedKhachHang!=null){
            KhachHangService.getInstance().deleteKhachHang(Long.toString(selectedKhachHang.getIdKhachHang()));
            refectTable();
        }
    }
    private void closeCanhBaoNhap(){
        xuLyTenThBao.setVisible(false);
        xuLySdtThBao.setVisible(false);
        xuLyGmailThBao.setVisible(false);
    }
    @FXML
    private void openEditKHForm(){
        KhachHang selectedKhachHang = khachHangTable.getSelectionModel().getSelectedItem();
        if(selectedKhachHang ==null){
            return;
        }
        editingKhachHang = selectedKhachHang;
        initOpenForm();
        btSumitDataBase.setVisible(false);
        btSumitDataBase.setManaged(false);
        btUpdateDataBase.setManaged(true);
        btUpdateDataBase.setVisible(true);

        textFHoTen.setText(selectedKhachHang.getTen());
        textFSDT.setText(selectedKhachHang.getSdt());
        textFGmail.setText(selectedKhachHang.getGmail());
        textFDiaChi.setText(selectedKhachHang.getDiaChi());
    }

    @FXML
    private void updateKhachHang(){
        closeCanhBaoNhap();
        try{
            editingKhachHang.setTen(textFHoTen.getText())
                            .setSdt(textFSDT.getText())
                            .setGmail(textFGmail.getText())
                            .setDiaChi(textFDiaChi.getText());
            KhachHangService.getInstance().updateInfoKhachHang(editingKhachHang);
            refectTable();
            closeEditKHForm();
        }catch(KhachHangExc e){
            xuLyThongBaoNhap(e);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    @FXML
    private void closeEditKHForm(){
        addKHFrom.setVisible(false);
        addKHFrom.setManaged(false);
        hBoxChuabtAddKH.setVisible(true);
        hBoxChuabtAddKH.setManaged(true);
        closeCanhBaoNhap();
        textFHoTen.clear();
        textFSDT.clear();
        textFGmail.clear();
        textFDiaChi.clear();
    }
    private void setToFindItems(KhachHangService.FindType findType){
        ObservableList<KhachHang> data = FXCollections.observableArrayList(KhachHangService.getInstance().findKhachHang(thanhTimKiem.getText(), findType));
        khachHangTable.setItems(data);
    }

    @FXML
    private void timTheoTen(){
       setToFindItems(KhachHangService.FindType.TIM_THEO_TEN);
    }
    @FXML
    private void timTheoSdt(){
        setToFindItems(KhachHangService.FindType.TIM_THEO_SDT);
    }
    @FXML
    private void timTheoGmail(){
        setToFindItems(KhachHangService.FindType.TIM_THEO_GMAIL);
    }
    @FXML
    private void timTheoDiaChi(){
        setToFindItems(KhachHangService.FindType.TIM_THEO_DIA_CHI);
    }
}
