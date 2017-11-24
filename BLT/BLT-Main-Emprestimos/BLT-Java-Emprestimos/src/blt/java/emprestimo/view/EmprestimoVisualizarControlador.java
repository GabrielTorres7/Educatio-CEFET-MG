package blt.java.emprestimo.view;

import blt.java.emprestimo.ManutencaoEmprestimos;
import blt.java.emprestimo.jdbc.EmprestimoDao;
import blt.java.emprestimo.model.Emprestimo;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class EmprestimoVisualizarControlador {
    @FXML
    private TableView<Emprestimo> emprestimoTabela;
    @FXML
    private TableColumn<Emprestimo, String> idAlunoColuna;
    @FXML
    private TableColumn<Emprestimo, Integer> idAcervoColuna;
    @FXML
    private TableColumn<Emprestimo, String> dataEmprestimoColuna;
    @FXML
    private TableColumn<Emprestimo, String> dataPrevisaoDevolucaoColuna;
    @FXML
    private TextField filtro;

    private static ManutencaoEmprestimos main;
    EmprestimoDao bd = new EmprestimoDao();
    
    
    public EmprestimoVisualizarControlador() {
    }

    public void setMain(ManutencaoEmprestimos main) {
        EmprestimoVisualizarControlador.main = main;
    }

    /**
     * Inicializa a classe controlador. Este método é chamado automaticamente
     *  após o arquivo fxml ter sido carregado.
     */
    @FXML
    private void initialize() {
        // Inicializa a tablea de pessoa com seis colunas.
    	idAlunoColuna.setCellValueFactory(new PropertyValueFactory<>("idAluno"));
        idAcervoColuna.setCellValueFactory(new PropertyValueFactory<>("idAcervo"));
        dataEmprestimoColuna.setCellValueFactory(new PropertyValueFactory<>("dataEmprestimo"));
        dataPrevisaoDevolucaoColuna.setCellValueFactory(new PropertyValueFactory<>("dataPrevisaoDevolucao"));
        
        ObservableList<Emprestimo> emprestimos = FXCollections.observableArrayList(bd.getLista());
        emprestimoTabela.setItems(emprestimos);
        
        FilteredList<Emprestimo> filtraDados = new FilteredList<>(emprestimos, p -> true);
        filtro.textProperty().addListener((observable, oldValue, newValue) -> 
        {
            filtraDados.setPredicate(Emprestimo -> {
            // Se o filtro estiver vazio mostra todos.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // passa o filtro pra letra minuscula
            String lowerCaseFilter = newValue.toLowerCase();
            //Compara o filtro com os nomes que estão na lista
            String id = Integer.toString(Emprestimo.getIdAcervo());
            if (id.toLowerCase().contains(lowerCaseFilter)) {
                return true; // Contem o filtro.
            }
                return false; //Não contem.
            });
        });
        SortedList<Emprestimo> sorteiaDados = new SortedList<>(filtraDados);
        sorteiaDados.comparatorProperty().bind(emprestimoTabela.comparatorProperty());
        emprestimoTabela.setItems(sorteiaDados);
    }

    @FXML
    private void botaoOk() throws IOException {
        main.mostrarEmprestimoVisaoGeral();
    }

}
