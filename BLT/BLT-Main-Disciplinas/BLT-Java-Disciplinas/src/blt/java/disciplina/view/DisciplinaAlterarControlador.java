/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blt.java.disciplina.view;

import blt.java.disciplina.ManutencaoDisciplinas;
import blt.java.disciplina.jdbc.DisciplinaDao;
import blt.java.disciplina.model.Disciplina;
import java.io.IOException;
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
public class DisciplinaAlterarControlador {
    
    @FXML
    private TextField nome;
    @FXML
    private TextField cargaHorariaMin;
    
    private Disciplina disciplina;
    private Disciplina disciplina2 = new Disciplina();
    private static ManutencaoDisciplinas main;
    DisciplinaDao bd = new DisciplinaDao();
    
    /**
     * Initializes the controller class.
     */
    
    public void initialize() {
        // TODO
    }    
    
     public void setMain(ManutencaoDisciplinas main) {
        DisciplinaAlterarControlador.main = main;
    }

    /**
     * Define a disciplina a ser editada no dialog.
     *
     * @param disciplina
     */
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        nome.setText(disciplina.getNome());
        this.disciplina.setIdTurma(disciplina.getIdTurma());
        this.disciplina.setCargaHorariaMin(disciplina.getCargaHorariaMin());
        cargaHorariaMin.setText(Integer.toString(disciplina.getCargaHorariaMin()));
    }

    /**
     * Retorna true se o usuário clicar OK,caso contrário false.
     *
     * @return
     */
   

    /**
     * Chamado quando o usuário clica OK.
     */
    @FXML
    private void botaoOk() throws SQLException, IOException {
        if (isInputValid()) {
            
            
           
            disciplina2.setNome(nome.getText());
            disciplina2.setCargaHorariaMin(Integer.parseInt(cargaHorariaMin.getText()));
            disciplina2.setIdTurma(disciplina.getIdTurma());
            
            bd.altera(disciplina2, disciplina.getNome());
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Disciplina alterada!");
                                alert.setHeaderText("Disciplina encontrada!");
                                alert.setContentText("A disciplina foi alterada com sucesso!");
                                alert.showAndWait();
            main.mostrarDisciplinaVisaoGeral();
        }
    }

    /**
     * Chamado quando o usuário clica Cancelar.
     */
    @FXML
    private void botaoCancelar() throws IOException {
        main.mostrarDisciplinaVisaoGeral();
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
