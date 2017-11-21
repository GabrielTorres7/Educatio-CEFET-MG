/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blt.java.disciplina.view;

import blt.java.disciplina.jdbc.DisciplinaDao;
import blt.java.disciplina.model.Disciplina;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gabriel
 */
public class DisciplinaAlterarControlador implements Initializable {
    
    @FXML
    private TextField nome;
    @FXML
    private TextField cargaHorariaMin;
    
    
    private Stage dialogStage;
    private Disciplina Disciplina;
    private boolean okClicked = false;
    DisciplinaDao bd = new DisciplinaDao();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Define a disciplina a ser editada no dialog.
     *
     * @param disciplina
     */
    public void setDisciplina(Disciplina disciplina) {
        this.Disciplina = disciplina;
        nome.setText(disciplina.getNome());
        this.Disciplina.setIdTurma(disciplina.getIdTurma());
        cargaHorariaMin.setText(Integer.toString(disciplina.getIdTurma()));
    }

    /**
     * Retorna true se o usuário clicar OK,caso contrário false.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Chamado quando o usuário clica OK.
     */
    @FXML
    private void botaoOk() throws SQLException {
        if (isInputValid()) {
            
            
           
            Disciplina.setNome(nome.getText());
            Disciplina.setCargaHorariaMin(Integer.parseInt(cargaHorariaMin.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Chamado quando o usuário clica Cancelar.
     */
    @FXML
    private void botaoCancelar() {
        dialogStage.close();
    }
    
    private boolean isInputValid() {
        String errorMessage = "";


        

        if (nome.getText() == null || nome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }


        if (cargaHorariaMin.getText() == null || cargaHorariaMin.getText().length() == 0) {
            errorMessage += "Carga Horaria Mínima inválida!\n";
        } else {
            // tenta converter a carga horároa mínima em um int.
            try {
                Integer.parseInt(cargaHorariaMin.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Carga Horaria Mínima inválida (deve ser um inteiro)!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostra a mensagem de erro.
            Alert alert = new Alert(Alert.AlertType.ERROR);
                      alert.setTitle("Campos Inválidos");
                      alert.setHeaderText("Por favor, corrija os campos inválidos");
                      alert.setContentText(errorMessage);
                alert.showAndWait();

            return false;
        }
    }
    
}
