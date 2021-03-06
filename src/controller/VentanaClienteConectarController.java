/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.pcClienteDao;
import Utilidades.ControladorGeneral;
import animatefx.animation.ZoomIn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import model.PropertiesServer;
import model.PcCliente;
import view.Principal;

/**
 * FXML Controller class
 *
 * @author Enso
 */
public class VentanaClienteConectarController implements Initializable {

    PcCliente pcClienteModel;
    pcClienteDao pcClienteDao = new pcClienteDao();
    @FXML
    private JFXTextField textFieldNamePc;
    @FXML
    private JFXButton buttonCancel;
    @FXML
    private JFXButton buttonConfirm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void eventOnCancel(ActionEvent event) {
        if (event.getSource().equals(buttonCancel)) {
            System.exit(0);
        }
    }

    @FXML
    private void eventOnAction(ActionEvent event) {
        if (event.getSource().equals(buttonConfirm)) {
            String namePc = textFieldNamePc.getText();
            if (!namePc.isEmpty()) {
                if (ControladorGeneral.toInt(namePc)) {
                    saveNamePcConfigPropertiesAndBD(namePc);
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor debes ingresar un número", "Error de autentificación", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void saveNamePcConfigPropertiesAndBD(String name) {
        PropertiesServer propertieServer = new PropertiesServer();
        pcClienteModel = new PcCliente();
        String id = String.valueOf(pcClienteDao.getLastCodeFromTable(pcClienteModel));
        propertieServer.setPropertiesValues(id,name);     
        //aqui te quedaste genera el codigo de forma automatica
        pcClienteModel.setId_pc(pcClienteDao.getLastCodeFromTable(pcClienteModel));
        pcClienteModel.setNombre_pc(name);
        boolean isSavePcCliente = pcClienteDao.savePcCliente(pcClienteModel);
        boolean isSavePropertiesName = propertieServer.getPropertiesValueNamePc().isEmpty();
        if (isSavePcCliente && !isSavePropertiesName) {
            callToViewInstall();
        }

    }

    private void callToViewInstall() {
        try {
            Principal.stageExtends.close();
            Parent root = FXMLLoader.load(getClass().getResource("/view/VentanaCargandoInstalacion.fxml"));
            Scene scene = new Scene(root);
            Principal.stageExtends.setScene(scene);
            Principal.stageExtends.setResizable(false);
            Principal.stageExtends.show(); 
        } catch (IOException ex) {
            Logger.getLogger(VentanaClienteConectarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
