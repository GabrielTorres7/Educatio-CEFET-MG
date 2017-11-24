package blt.java.emprestimo.view;

import blt.java.emprestimo.ManutencaoEmprestimos;
import blt.java.emprestimo.jdbc.EmprestimoDao;
import blt.java.emprestimo.model.Emprestimo;
import blt.java.emprestimo.model.Reserva;
import blt.java.emprestimo.util.DataUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Torres
 */
public class EmprestimoExcluirEmprestimoControlador {
    
    @FXML
    private TextField idAcervoCampoTexto;
    @FXML
    private DatePicker dataDevolucaoCampoTexto;
    @FXML
    private TableView<Emprestimo> emprestimoTabela;
    @FXML
    private TableColumn<Emprestimo, String> nomeColuna;
    @FXML
    private TableColumn<Emprestimo, String> dataEmprestimoColuna;
    @FXML
    private TableColumn<Emprestimo, String> dataPrevisaoDevolucaoColuna;
    
    private static ManutencaoEmprestimos main;
    private Emprestimo emprestimo = new Emprestimo();
    private Reserva reserva = new Reserva();
    EmprestimoDao bd = new EmprestimoDao();

    @FXML
    private void initialize() throws SQLException {
        dataEmprestimoColuna.setCellValueFactory(new PropertyValueFactory<>("dataEmprestimo"));
        nomeColuna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        dataPrevisaoDevolucaoColuna.setCellValueFactory(new PropertyValueFactory<>("dataPrevisaoDevolucao"));
        
        ObservableList<Emprestimo> emprestimos = FXCollections.observableArrayList(bd.getLista());
        emprestimoTabela.setItems(emprestimos);
        FilteredList<Emprestimo> filtraDados = new FilteredList<>(emprestimos, p -> true);
        idAcervoCampoTexto.textProperty().addListener((observable, oldValue, newValue) -> 
        {
            filtraDados.setPredicate(Emprestimo -> {
            // Se o filtro estiver vazio mostra todos.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // passa o filtro pra letra minuscula
            String lowerCaseFilter = newValue.toLowerCase();
            //Compara o filtro com os nomes que estão na lista
            if (Emprestimo.getNome().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Contem o filtro.
            }
                return false; //Não contem.
            });
        });
        SortedList<Emprestimo> sorteiaDados = new SortedList<>(filtraDados);
        sorteiaDados.comparatorProperty().bind(emprestimoTabela.comparatorProperty());
        emprestimoTabela.setItems(sorteiaDados);
    }

   
    public void setMain(ManutencaoEmprestimos main) {
        EmprestimoExcluirEmprestimoControlador.main = main;
    }

    /**
     * Chamado quando o usuário clica OK.
     */
    @FXML
    private void botaoOk() throws SQLException, ParseException, IOException {
        if(isInputValid()){
            emprestimo.setIdAcervo(Integer.parseInt(bd.pegaIdAcervo(emprestimoTabela.getSelectionModel().getSelectedItem().getNome())));
             
            
            boolean existeEmprestimo = false;
            List<Emprestimo> emprestimos = bd.getLista();

		for (Emprestimo emprestimo : emprestimos) {
			if(this.emprestimo.getIdAcervo() == emprestimo.getIdAcervo() ){
				this.emprestimo = emprestimo;
                                this.emprestimo.setDataDevolucao(dataDevolucaoCampoTexto.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                                existeEmprestimo = true;
			}
		}
                
            if(existeEmprestimo){
            //Verifica se a data de devolução é maior que a de previsao
            if(DataUtil.parse(emprestimo.getDataDevolucao()).toEpochDay() < DataUtil.parse(emprestimo.getDataPrevisaoDevolucao()).toEpochDay()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Data de devolução Inválida");
                    alert.setHeaderText("A data de entrega não pode ser anterior ao prazo de devolução!");
                    alert.setContentText("Por favor, visualize os empréstimos existentes e tente novamente.");
                    alert.showAndWait();
            }else{ 
                
                //Verifica se o empréstimo existe
                
                emprestimo.setDataDevolucao(emprestimo.getDataDevolucao());
                emprestimo.setMulta(DataUtil.calculaDiferencaDias(DataUtil.parse(emprestimo.getDataDevolucao()), DataUtil.parse(emprestimo.getDataPrevisaoDevolucao())));
			
                //Remove o emprestimo e caso haja multa atualiza as reservas
                bd.remove(emprestimo);
                reserva.setIdAcervo(emprestimo.getIdAcervo());
                bd.atualizaReservas(emprestimo.getMulta(), emprestimo.getIdAcervo());
                        
                //Verifica se há reservas para o empréstimo devolvido e caso haja coloca a menos recente nos empréstimos
                if(bd.existeReservaRemover(reserva)){
                    emprestimo.setDataEmprestimo(reserva.getDataReserva());
                    emprestimo.setDataPrevisaoDevolucao(DataUtil.adicionaXDias(reserva.getDataReserva(), 7));
                    bd.emprestaReserva(reserva);
                    emprestimo.setIdAluno(reserva.getIdAluno());
                    bd.adicionaEmprestimo(emprestimo);
                }
                main.mostrarEmprestimoVisaoGeral();
            }
                         
                
            }
            
            
        }
    }

    /**
     * Chamado quando o usuário clica Cancel.
     */
    @FXML
    private void botaoCancelar() throws IOException {
        main.mostrarEmprestimoVisaoGeral();
    }
    
    private boolean isInputValid() {
        String errorMessage = "";
        
        if (dataDevolucaoCampoTexto.getValue() == null) {
            errorMessage += "Data de devolução inválida!\n";
        } 
        
        if (emprestimoTabela.getSelectionModel().getSelectedItem() == null){
            errorMessage += "Selecione um campo na tabela para continuar!\n";
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
