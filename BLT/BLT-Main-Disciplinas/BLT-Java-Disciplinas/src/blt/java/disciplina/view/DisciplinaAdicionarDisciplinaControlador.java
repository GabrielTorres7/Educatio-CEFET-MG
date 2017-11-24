package blt.java.disciplina.view;

import blt.java.disciplina.ManutencaoDisciplinas;
import blt.java.disciplina.jdbc.DisciplinaDao;
import blt.java.disciplina.model.Disciplina;
import java.io.IOException;
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
public class DisciplinaAdicionarDisciplinaControlador {
    
    @FXML
    private ChoiceBox campis;
    @FXML
    private ChoiceBox departamentos;
    @FXML
    private ChoiceBox cursos;
    @FXML
    private ChoiceBox idTurmaSelect;
    @FXML
    private TextField nomeCampoTexto;
    @FXML
    private TextField cargaHorariaMinCampoTexto;
    @FXML
    private Label nomeDepartamento;
    @FXML
    private Label nomeCurso;
    @FXML
    private Label idTurma;
    @FXML
    private Label nome;
    @FXML
    private Label cargaHorariaMin;
    
    private String campi = new String();
    private String departamento = new String();
    private String turma = new String();
    private String curso = new String();
    
    private static ManutencaoDisciplinas main;
    private Disciplina Disciplina = new Disciplina();
    DisciplinaDao bd = new DisciplinaDao();
    
    @FXML
    private void initialize() throws SQLException {
        campis.setItems(bd.pegaCampis());
        
        departamentos.setVisible(false);
        cursos.setVisible(false);
        idTurmaSelect.setVisible(false);
        nomeCampoTexto.setVisible(false);
        cargaHorariaMinCampoTexto.setVisible(false);
        nomeDepartamento.setVisible(false);
        nomeCurso.setVisible(false);
        idTurma.setVisible(false);
        nome.setVisible(false);
        cargaHorariaMin.setVisible(false);
        
        campis.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                confirmaCampi(newValue.toString());
            } catch (SQLException ex) {
                Logger.getLogger(DisciplinaAdicionarDisciplinaControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        departamentos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                confirmaDepartamento(newValue.toString());
            } catch (SQLException ex) {
                Logger.getLogger(DisciplinaAdicionarDisciplinaControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        cursos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                confirmaCurso(newValue.toString());
            } catch (SQLException ex) {
                Logger.getLogger(DisciplinaAdicionarDisciplinaControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        idTurmaSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                mostrarCamposInserir(newValue.toString());
            } catch (SQLException ex) {
                Logger.getLogger(DisciplinaAdicionarDisciplinaControlador.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void mostrarCamposInserir(String valor) throws SQLException{
        turma = valor;
        
        if(turma == null){
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Campos vazios");
            alerta.setHeaderText(null);
            alerta.setContentText("Preencha todos os campos para continuar!");
            
            alerta.showAndWait();
        }else{
            nome.setVisible(true);
            nomeCampoTexto.setVisible(true);
            cargaHorariaMinCampoTexto.setVisible(true);
            cargaHorariaMin.setVisible(true);
            
        }
    }

    /**
     * Define a disciplina a ser editada no dialog.
     *
     * @param disciplina
     */
    
    public void setMain(ManutencaoDisciplinas main) {
        DisciplinaAdicionarDisciplinaControlador.main = main;
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
            
            
            Disciplina.setIdTurma(bd.pegaIdTurma(turma));
            Disciplina.setCargaHorariaMin(Integer.parseInt(cargaHorariaMinCampoTexto.getText()));
            Disciplina.setNome(nomeCampoTexto.getText());


            bd.adiciona(Disciplina);
            Alert alert = new Alert(AlertType.INFORMATION);
                      alert.setTitle("Disciplina criada!");
                      alert.setHeaderText("Disciplina criada!");
                      alert.setContentText("A nova disciplina foi adicionada ao banco de dados.!");
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

    /**
     * Valida a entrada do usuário nos campos de texto.
     *
     * @return true se a entrada é válida
     */
    private boolean isInputValid() {
        String errorMessage = "";


        

        if (nomeCampoTexto.getText() == null || nomeCampoTexto.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }


        if (cargaHorariaMinCampoTexto.getText() == null || cargaHorariaMinCampoTexto.getText().length() == 0) {
            errorMessage += "Carga Horaria Mínima inválida!\n";
        } else {
            // tenta converter a carga horároa mínima em um int.
            try {
                Integer.parseInt(cargaHorariaMinCampoTexto.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Carga Horaria Mínima inválida (deve ser um inteiro)!\n";
            }
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