package blt.java.emprestimo.view;

import blt.java.emprestimo.ManutencaoEmprestimos;
import blt.java.emprestimo.jdbc.EmprestimoDao;
import blt.java.emprestimo.model.Emprestimo;
import blt.java.emprestimo.model.Reserva;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;



/**
 * Dialog para editar detalhes de um empréstimo.
 *
 * @author Torres
 */
public class EmprestimoAdicionarEmprestimoControlador {
    
    @FXML
    private ChoiceBox campis;
    @FXML
    private ChoiceBox departamentos;
    @FXML
    private ChoiceBox cursos;
    @FXML
    private ChoiceBox turmas;
    @FXML
    private ChoiceBox alunos;
    @FXML
    private Label labelDepartamento;
    @FXML
    private Label labelCurso;
    @FXML
    private Label labelTurma;
    @FXML
    private Label labelAluno;
    
    private String campi = null;
    private String departamento = null;
    private String turma = null;
    private String curso = null;
    private String aluno = null;
    
    private static ManutencaoEmprestimos main;
    private Emprestimo emprestimo = new Emprestimo();
    EmprestimoDao bd = new EmprestimoDao();
    
    /**
     * Inicializa a classe controlador. Este método é chamado automaticamente
     * após o arquivo fxml ter sido carregado.
     */
    @FXML
    private void initialize() throws SQLException {
        campis.setItems(bd.pegaCampis());
        
        departamentos.setVisible(false);
        cursos.setVisible(false);
        turmas.setVisible(false);
        alunos.setVisible(false);
        labelDepartamento.setVisible(false);
        labelCurso.setVisible(false);
        labelTurma.setVisible(false);
        labelAluno.setVisible(false);
        
        campis.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                confirmaCampi(newValue.toString());
            } catch (SQLException ex) {
                Logger.getLogger(EmprestimoAdicionarEmprestimoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        departamentos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                confirmaDepartamento(newValue.toString());
            } catch (SQLException ex) {
                Logger.getLogger(EmprestimoAdicionarEmprestimoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        cursos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                confirmaCurso(newValue.toString());
            } catch (SQLException ex) {
                Logger.getLogger(EmprestimoAdicionarEmprestimoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        turmas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                confirmaTurma(newValue.toString());
            } catch (SQLException ex) {
                Logger.getLogger(EmprestimoAdicionarEmprestimoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        alunos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                mostrarCamposInserir(newValue.toString());
            } catch (SQLException ex) {
                Logger.getLogger(EmprestimoAdicionarEmprestimoControlador.class.getName()).log(Level.SEVERE, null, ex);
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
            labelDepartamento.setVisible(true);
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
            labelCurso.setVisible(true);
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
            turmas.setItems(bd.pegaTurmas(curso));
            turmas.setVisible(true);
            labelTurma.setVisible(true);
        }
    }
    
    public void confirmaTurma(String valor) throws SQLException{
        turma = valor;
        
        if(turma == null){
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Campos vazios");
            alerta.setHeaderText(null);
            alerta.setContentText("Preencha todos os campos para continuar!");
            
            alerta.showAndWait();
        }else{
            alunos.setItems(bd.pegaAlunos(turma));
            alunos.setVisible(true);
            labelAluno.setVisible(true);
        }
    }
    
    public void mostrarCamposInserir(String valor) throws SQLException{
        aluno = valor;
        
        if(aluno == null){
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Campos vazios");
            alerta.setHeaderText(null);
            alerta.setContentText("Preencha todos os campos para continuar!");
            
            alerta.showAndWait();
        }           
            
        
    }

    /**
     * Define o empréstimo a ser editado no dialog.
     *
     * @param emprestimo
     */
    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    /**
     * Chamado quando o usuário clica OK.
     */
    @FXML
    private void botaoOk() throws ParseException, SQLException {
        if (isInputValid()) {

            emprestimo.setIdAluno(bd.pegaIdAluno(aluno));
            emprestimo.setIdCampi(bd.pegaIdCampi(campi));
            Date d = new Date();
            emprestimo.setDataEmprestimo(new SimpleDateFormat("dd/MM/yyyy").format(d));
            long timeNovaData = d.getTime() + (1000*60*60*24*7);
            emprestimo.setDataPrevisaoDevolucao(new SimpleDateFormat("dd/MM/yyyy").format(new Date(timeNovaData))); 
            
            main.mostrarEmprestimoSelecionarAcervo(emprestimo);
            
        }
    }
    
    public void setMain(ManutencaoEmprestimos main) {
        EmprestimoAdicionarEmprestimoControlador.main = main;
    }
    /**
     * Chamado quando o usuário clica Cancelar.
     */
    @FXML
    private void botaoCancelar() throws IOException {
        main.mostrarEmprestimoVisaoGeral();
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
        
        if (turma == null ) {
            errorMessage += "Turma inválida!\n";
        }
        
        if (aluno == null ) {
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
