package viewControler;

import model.SanPham;
import service.Manager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import myException.SanPhamExc;
import service.SanPhamService;

public class SanPhamControler {
    @FXML private Label tenSanPhamCanhBao;
    @FXML private HBox hBoxThemSanPham;
    @FXML private VBox vBoxEditForm;
    @FXML private Button btUpdateDB,btSubmitDB;
    @FXML private TextField textFTenSanPham, textFGiaBan, textFDonViTinh,textFMoTa,thanhTimKiem;
    @FXML private TableView tableSanPhamView;
    @FXML private TableColumn<SanPham,String> tenColumn,donViTinhColumn,moTaColumn;
    @FXML private TableColumn<SanPham,Double> giaBanColumn;

    private static SanPham selectingSanPham;
    @FXML private void initialize(){
        tenColumn.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
        giaBanColumn.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
        donViTinhColumn.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
        moTaColumn.setCellValueFactory(new PropertyValueFactory<>("moTa"));

        refectTable();

        vBoxEditForm.setManaged(false);
        vBoxEditForm.setVisible(false);
    }

    @FXML private void refectTable(){
        ObservableList<SanPham> data = FXCollections.observableArrayList(SanPhamService.getInstance().getSanPhams());
        tableSanPhamView.setItems(data);
    }


    @FXML private  void closeForm(){
        hBoxThemSanPham.setVisible(true);
        hBoxThemSanPham.setManaged(true);
        vBoxEditForm.setManaged(false);
        vBoxEditForm.setVisible(false);
    }
    @FXML private void updateDB(){
        clodeEditFormWarring();
        long giaBan = 0;
        try{
            giaBan = Long.parseLong(textFGiaBan.getText());
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }

        selectingSanPham.setTenSanPham(textFTenSanPham.getText());
        selectingSanPham.setGiaBan(giaBan);
        selectingSanPham.setDonViTinh(textFDonViTinh.getText());
        selectingSanPham.setMoTa(textFMoTa.getText());

        try {
            SanPhamService.getInstance().updateInfoSanPham(selectingSanPham);
            tableSanPhamView.refresh();
            closeForm();
        }catch (SanPhamExc e){
            if(e.isMaLoi(SanPhamExc.MaLoi.KHONG_CO_TEN)){
                tenSanPhamCanhBao.setText("Vui lòng điền tên sản phẩm");
                tenSanPhamCanhBao.setVisible(true);
            }
        }

    }
    @FXML private void openFormToEdit(){
        selectingSanPham =(SanPham) tableSanPhamView.getSelectionModel().getSelectedItem();
        if(selectingSanPham != null){
            openFormToAdd();
            btUpdateDB.setVisible(true);
            btUpdateDB.setManaged(true);
            btSubmitDB.setManaged(false);
            btSubmitDB.setVisible(false);
            textFTenSanPham.setText(selectingSanPham.getTenSanPham());
            textFGiaBan.setText(Double.toString(selectingSanPham.getGiaBan()));
            textFDonViTinh.setText(selectingSanPham.getDonViTinh());
            textFMoTa.setText(selectingSanPham.getMoTa());
        }
    }
    @FXML private void submitDB(){
        clodeEditFormWarring();
        long giaBan = 0;
        try{
            giaBan = Long.parseLong(textFGiaBan.getText());
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
        SanPham sanPham = new SanPham(textFTenSanPham.getText(),giaBan, textFDonViTinh.getText(),textFMoTa.getText());
        try {
            SanPhamService.getInstance().addSanPham(sanPham);
            tableSanPhamView.getItems().add(sanPham);
            closeForm();
        }catch (SanPhamExc e){
            if(e.isMaLoi(SanPhamExc.MaLoi.KHONG_CO_TEN)){
                tenSanPhamCanhBao.setText("Vui lòng điền tên sản phẩm");
                tenSanPhamCanhBao.setVisible(true);
            }
        }
    }

    private void clodeEditFormWarring(){
        tenSanPhamCanhBao.setVisible(false);
    }

    @FXML private void deleteSanPham() {
        SanPham selectedSanPham = (SanPham) tableSanPhamView.getSelectionModel().getSelectedItem();
        if (selectedSanPham != null) {
            SanPhamService.getInstance().deleteSanPham(Long.toString(selectedSanPham.getIdSanPham()));
            tableSanPhamView.getItems().remove(selectedSanPham);
        }
    }
    @FXML
    private void timKiemTenSanPham(){
        ObservableList<SanPham> data = FXCollections.observableArrayList(SanPhamService.getInstance().findSanPhamTheoTen(thanhTimKiem.getText()));
        tableSanPhamView.setItems(data);
    }
    @FXML private void openFormToAdd(){
        textFTenSanPham.clear();
        textFGiaBan.clear();
        textFDonViTinh.clear();
        textFMoTa.clear();
        clodeEditFormWarring();
        vBoxEditForm.setVisible(true);
        vBoxEditForm.setManaged(true);
        hBoxThemSanPham.setVisible(false);
        hBoxThemSanPham.setVisible(false);
        btUpdateDB.setVisible(false);
        btUpdateDB.setManaged(false);
        btSubmitDB.setManaged(true);
        btSubmitDB.setVisible(true);
    }

}
