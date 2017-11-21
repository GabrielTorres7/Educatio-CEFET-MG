package blt.java.disciplina.view;

import blt.java.disciplina.jdbc.DisciplinaDao;
import blt.java.disciplina.model.Disciplina;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;


/**
 * Dialog para editar detalhes de uma disciplina.
 *
 * @author Torres
 */
public class DisciplinaPesquisarControlador {
    
    @FXML
    private ChoiceBox campis;
    @FXML
    private ChoiceBox departamentos;
    @FXML
    private ChoiceBox cursos;
    @FXML
    private ChoiceBox idTurmaSelect;
    @FXML
    private ChoiceBox nomeCampoTexto;
    @FXML
    private Label nomeDepartamento;
    @FXML
    private Label nomeCurso;
    @FXML
    private Label idTurma;
    @FXML
    private Label nome;
    
    private String campi = null;
    private String departamento = null;
    private String turma = null;
    private String curso = null;
    private String idTurmaString = null;
    private String nomeString = null;
    
    private Stage dialogStage;
    private Disciplina Disciplina;
    private boolean okClicked = false;
    DisciplinaDao bd = new DisciplinaDao();
    
    @FXML
    private void initialize() throws SQLException {
        campis.setItems(bd.pegaCampis());
        
        departamentos.setVisible(false);
        cursos.setVisible(false);
        idTurmaSelect.setVisible(false);
        nomeCampoTexto.setVisible(false);
        nomeDepartamento.setVisible(false);
        nomeCurso.setVisible(false);
        idTurma.setVisible(false);
        nome.setVisible(false);
        
        
        campis.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
            try {
                confirmaCampi(newValue.toString());
            } catch (SQLException ex) {
                Logger.getLogger(DisciplinaCaixaEditarControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        departamentos.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
            try {
                confirmaDepartamento(newValue.toString());
            } catch (SQLException ex) {
                Logger.getLogger(DisciplinaCaixaEditarControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        cursos.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
            try {
                confirmaCurso(newValue.toString());
            } catch (SQLException ex) {
                Logger.getLogger(DisciplinaCaixaEditarControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        idTurmaSelect.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
            try {
                confirmaIdTurma(newValue.toString());
            } catch (SQLException ex) {
                Logger.getLogger(DisciplinaCaixaEditarControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
         nomeCampoTexto.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
            try {
                mostrarCamposInserir(newValue.toString());
            } catch (SQLException ex) {
                Logger.getLogger(DisciplinaCaixaEditarControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    public void confirmaCampi(String valor) throws SQLException{
        campi = valor;
        
        if(campi == null){
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Campos vazios");
            alerta.setHeaderText(null);
            alerta.setContentText("Preencha todos os campos para continuar!");
            
            alerta.showAndWait();
        }else{
            departamentos.setItems(bd.pegaDepartamentos(campi));
            departamentos.setVisible(true);
            nomeDepartamento.setVisible(true);
        }
    }
    
    public void confirmaDepartamento(String valor) throws SQLException{
        departamento = valor;
        
        if(departamento == null){
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Campos vazios");
            alerta.setHeaderText(null);
            alerta.setContentText("Preencha todos os campos para continuar!");
            
            alerta.showAndWait();
        }else{
            cursos.setItems(bd.pegaCursos(departamento));
            cursos.setVisible(true);
            nomeCurso.setVisible(true);
        }
    }
    
    public void confirmaCurso(String valor) throws SQLException{
        curso = valor;
        
        if(curso == null){
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Campos vazios");
            alerta.setHeaderText(null);
            alerta.setContentText("Preencha todos os campos para continuar!");
            
            alerta.showAndWait();
        }else{
            idTurmaSelect.setItems(bd.pegaIdTurmas(curso));
            idTurmaSelect.setVisible(true);
            idTurma.setVisible(true);
        }
    }
    
    public void confirmaIdTurma(String valor) throws SQLException{
        idTurmaString = valor;
        
        if(idTurma == null){
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Campos vazios");
            alerta.setHeaderText(null);
            alerta.setContentText("Preencha todos os campos para continuar!");
            
            alerta.showAndWait();
        }else{
            nomeCampoTexto.setItems(bd.pegaNomes(idTurmaString));
            nomeCampoTexto.setVisible(true);
            nome.setVisible(true);
        }
    }
    
    public void mostrarCamposInserir(String valor) throws SQLException{
        nomeString = valor;
        
        if(nomeString == null){
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Campos vazios");
            alerta.setHeaderText(null);
            alerta.setContentText("Preencha todos os campos para continuar!");
            
            alerta.showAndWait();
        }
          
           
    }
    
    /**
     * Define o palco deste dialog.
     *
     * @param dialogStage
     */
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
            
            
            Disciplina.setIdTurma(bd.pegaIdTurma(idTurmaString));
            Disciplina.setNome(nomeString);
            Disciplina.setCargaHorariaMin(bd.pegaCargaHorariaMinDisciplina(nomeString));

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

    /**
     * Valida a entrada do usuário nos campos de texto.
     *
     * @return true se a entrada é válida
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (campi == null ) {
            errorMessage += "Campi inválido!\n";
        }
        
        if (departamento == null ) {
            errorMessage += "Departamento inválido!\n";
        }
        
        if (curso == null ) {
            errorMessage += "Curso inválido!\n";
        }
        
        if (idTurmaString == null ) {
            errorMessage += "Turma inválida!\n";
        }
        
        if (nomeString == null ) {
            errorMessage += "Nome inválido!\n";
        }
 
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostra a mensagem de erro.
            Alert alert = new Alert(AlertType.ERROR);
                      alert.setTitle("Campos Inválidos");
                      alert.setHeaderText("Por favor, corrija os campos inválidos");
                      alert.setContentText(errorMessage);
                alert.showAndWait();

            return false;
        }
    }
}