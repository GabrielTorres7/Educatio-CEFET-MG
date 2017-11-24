package blt.java.disciplina.view;

import blt.java.disciplina.ManutencaoDisciplinas;
import blt.java.disciplina.jdbc.DisciplinaDao;
import blt.java.disciplina.model.Disciplina;
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
import javafx.stage.Stage;

/**
 * 
 * @author Torres
 */

public class DisciplinaVisualizarControlador {
    @FXML
    private TableView<Disciplina> disciplinaTabela;
    @FXML
    private TableColumn<Disciplina, String> nomeColuna;
    @FXML
    private TableColumn<Disciplina, Integer> idTurmaColuna;
    @FXML
    private TableColumn<Disciplina, Integer> cargaHorariaMinColuna;
    @FXML
    private TextField filtro;

    ManutencaoDisciplinas mainApp;
    private static ManutencaoDisciplinas main;
    DisciplinaDao bd = new DisciplinaDao();
   
    public DisciplinaVisualizarControlador() {
    }

    public void setMain(ManutencaoDisciplinas main) {
        DisciplinaVisualizarControlador.main = main;
    }


    /**
     * Inicializa a classe controller. Este método é chamado automaticamente
     *  após o arquivo fxml ter sido carregado.
     */
    @FXML
    private void initialize() {
        // Inicializa a tablea de disciplina com três colunas.
    	nomeColuna.setCellValueFactory(new PropertyValueFactory<Disciplina, String>("nome"));
        idTurmaColuna.setCellValueFactory(new PropertyValueFactory<Disciplina, Integer>("idTurma"));
        cargaHorariaMinColuna.setCellValueFactory(new PropertyValueFactory<Disciplina, Integer>("cargaHorariaMin"));

        ObservableList<Disciplina> disciplinas = FXCollections.observableArrayList(bd.getLista());
        disciplinaTabela.setItems(disciplinas);
        
        FilteredList<Disciplina> filtraDados = new FilteredList<>(disciplinas, p -> true);
        filtro.textProperty().addListener((observable, oldValue, newValue) -> 
        {
            filtraDados.setPredicate(Disciplina -> {
            // Se o filtro estiver vazio mostra todos.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // passa o filtro pra letra minuscula
            String lowerCaseFilter = newValue.toLowerCase();
            //Compara o filtro com os nomes que estão na lista
            String id = Disciplina.getNome();
            if (id.toLowerCase().contains(lowerCaseFilter)) {
                return true; // Contem o filtro.
            }
                return false; //Não contem.
            });
        });
        SortedList<Disciplina> sorteiaDados = new SortedList<>(filtraDados);
        sorteiaDados.comparatorProperty().bind(disciplinaTabela.comparatorProperty());
        disciplinaTabela.setItems(sorteiaDados);
    }

    @FXML
    private void botaoOk() throws IOException {
        main.mostrarDisciplinaVisaoGeral();
    }

}
