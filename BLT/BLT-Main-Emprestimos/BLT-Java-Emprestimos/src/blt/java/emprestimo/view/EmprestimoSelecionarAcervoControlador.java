/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blt.java.emprestimo.view;

import blt.java.emprestimo.ManutencaoEmprestimos;
import blt.java.emprestimo.jdbc.EmprestimoDao;
import blt.java.emprestimo.model.Acervo;
import blt.java.emprestimo.model.Emprestimo;
import blt.java.emprestimo.model.Reserva;
import blt.java.emprestimo.util.DataUtil;
import java.io.IOException;
import java.text.ParseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Gabriel
 */
public class EmprestimoSelecionarAcervoControlador  {
    @FXML
    private TextField filtra;
    @FXML
    private TableView<Acervo> acervoTabela;
    @FXML
    private TableColumn<Acervo, Integer> idColuna;
    @FXML
    private TableColumn<Acervo, String> nomeColuna;
    @FXML
    private TableColumn<Acervo, String> tipoColuna;
    @FXML
    private TableColumn<Acervo, String> localColuna;
    @FXML
    private TableColumn<Acervo, String> anoColuna;
    @FXML
    private TableColumn<Acervo, String> editoraColuna;
    @FXML
    private TableColumn<Acervo, Integer> paginasColuna;
    
    private static ManutencaoEmprestimos main;
    EmprestimoDao bd = new EmprestimoDao();
    Emprestimo emprestimo = new Emprestimo();
    Reserva reserva = new Reserva();
    Acervo acervo;
    /**
     * Initializes the controller class.
     */
    public void initialize() {
        idColuna.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeColuna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tipoColuna.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        localColuna.setCellValueFactory(new PropertyValueFactory<>("local"));
        anoColuna.setCellValueFactory(new PropertyValueFactory<>("ano"));
        editoraColuna.setCellValueFactory(new PropertyValueFactory<>("editora"));
        paginasColuna.setCellValueFactory(new PropertyValueFactory<>("paginas"));
       
    }    
    
    public void setTabela(){
        ObservableList<Acervo> acervos = FXCollections.observableArrayList(bd.getListaAcervos(emprestimo.getIdCampi()));
        acervoTabela.setItems(acervos);
        FilteredList<Acervo> filtraDados = new FilteredList<>(acervos, p -> true);
        filtra.textProperty().addListener((observable, oldValue, newValue) -> 
        {
            filtraDados.setPredicate(Acervo -> {
            // Se o filtro estiver vazio mostra todos.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // passa o filtro pra letra minuscula
            String lowerCaseFilter = newValue.toLowerCase();
            //Compara o filtro com os nomes que estão na lista
            if (Acervo.getNome().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Contem o filtro.
            }
                return false; //Não contem.
            });
        });
        SortedList<Acervo> sorteiaDados = new SortedList<>(filtraDados);
        sorteiaDados.comparatorProperty().bind(acervoTabela.comparatorProperty());
        acervoTabela.setItems(sorteiaDados);
    }
    /**
     * Define o empréstimo a ser editado no dialog.
     *
     * @param emprestimo
     */
    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
        
    }
    
    @FXML
    private void botaoOk() throws ParseException, IOException {   
        if(acervoTabela.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
                      alert.setTitle("Campos Inválidos");
                      alert.setHeaderText("Por favor, corrija os campos inválidos");
                      alert.setContentText("Selecione um campo na tabela para continuar!");
                alert.showAndWait();
        }else{
            emprestimo.setIdAcervo(acervoTabela.getSelectionModel().getSelectedItem().getId());
            
            if(bd.existeEmprestimo(emprestimo)){
                   reserva.setIdAluno(emprestimo.getIdAluno());
                   reserva.setIdAcervo(emprestimo.getIdAcervo());
                if(bd.existeReservaAdicionar(reserva)){
                   
                   reserva.setDataReserva(DataUtil.adicionaXDias(reserva.getDataReserva(), 8));
                   reserva.setTempoEspera(7L);
                   bd.adicionaReserva(reserva);
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                      alert.setTitle("Empréstimo não criado");
                      alert.setHeaderText("Reserva criado!");
                      alert.setContentText("Já existe um empréstimo para esse acervo! Foi criado uma reserva!");
                      alert.showAndWait();
                      
                }else{
                    
                    reserva.setDataReserva(DataUtil.adicionaXDias(emprestimo.getDataPrevisaoDevolucao(), 1));            
                    reserva.setTempoEspera(7L);
                    bd.adicionaReserva(reserva);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                      alert.setTitle("Empréstimo não criado");
                      alert.setHeaderText("Reserva criado!");
                      alert.setContentText("Já existe um empréstimo para esse acervo! Foi criado uma reserva!");
                      alert.showAndWait();
                
            }
            } else {
                bd.adicionaEmprestimo(emprestimo);
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                      alert.setTitle("Empréstimo Criado");
                      alert.setHeaderText("Empréstimo criado!");
                      alert.setContentText("O novo empréstimo foi adicionado ao banco de dados!");
                      alert.showAndWait();
            }
            main.mostrarEmprestimoVisaoGeral();
        } 
    }
    
    public void setMain(ManutencaoEmprestimos main) {
        EmprestimoSelecionarAcervoControlador.main = main;
    }
    
    @FXML
    private void botaoCancelar() throws IOException{
        main.mostrarEmprestimoVisaoGeral();
    }
    
   
            

            
        
    
}
