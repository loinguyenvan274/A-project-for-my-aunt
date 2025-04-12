package viewControler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class BarControler {
    private static final Stage primaryStage;
    static {
       primaryStage =  MainControler.getMainStage();
    }

    private void setScene(String pathFileFxml, String pathFileCss) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pathFileFxml));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
        scene.getStylesheets().add(this.getClass().getResource(pathFileCss).toExternalForm());
        primaryStage.setScene(scene);
    }

    @FXML
    private void setKhachHangScene() throws IOException {
        setScene("/view/khachHangView.fxml", "/css/generalCss.css");
    }
    @FXML
    private void setSanPhamScene() throws IOException {
        setScene("/view/sanPhamView.fxml", "/css/generalCss.css");
    }
    @FXML
    private void setDonHangScene() throws IOException {
        setScene("/view/donHangView.fxml", "/css/generalCss.css");
    }
    @FXML
    private void setKhachHangNoScene() throws IOException {
        setScene("/view/donHangNoView.fxml", "/css/generalCss.css");
    }
}
