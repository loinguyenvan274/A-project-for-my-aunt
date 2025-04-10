package viewControler;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.KhachHangNo;
import service.Manager;

import java.time.LocalDate;

public class KhachHangNoControler {
    @FXML
    TableColumn<KhachHangNo,String> tenColumn,sdtColumn,kiHanColumn ;
    @FXML
    TableColumn<KhachHangNo,Long> tienDonHangColumn,traTruocColumn,conNoColumn;
    @FXML
    TableView<KhachHangNo> khachHangNoTable;

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
        khachHangNoTable.setItems(FXCollections.observableArrayList(Manager.getInstance().getKhachHangNos()));
    }

    @FXML
    private void initialize(){
        initializeTable();
        khachHangNoTable.refresh();

    }
    @FXML
    private void setInforKhachHangNo(){
        System.out.println(Manager.getInstance().getKhachHangNos().get(0).getDonHang().getTongTienDonHang());
    }





}
