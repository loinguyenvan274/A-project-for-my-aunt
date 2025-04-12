package viewControler;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import model.KhachHang;

public class FormChonKhachHangControler {
    @FXML
    Node formChonKhachHang;
    @FXML
    Button btChonKhachHang;
    private static KhachHang khachHangDangChon;
    public static KhachHang getKhachHangDangChon(){
        return khachHangDangChon;
    }
    @FXML
    private void initialize(){
        TableView tableKhachHangView = (TableView) formChonKhachHang.lookup("#khachHangTable");
        if(tableKhachHangView!=null){
            formChonKhachHang.setEventDispatcher((e, evenDispatchChain)->{
                if(e instanceof MouseEvent mouseEvent){
                    KhachHang khachHang = (KhachHang) tableKhachHangView.getSelectionModel().getSelectedItem();
                    if(khachHang !=null){
                        btChonKhachHang.setDisable(false);
                        khachHangDangChon = khachHang;
                    }else{
                        khachHangDangChon = null;
                        btChonKhachHang.setDisable(true);
                    }
                }
                return evenDispatchChain.dispatchEvent(e);

            });
        }
    }



}
