package blt.java.emprestimo;

import java.io.IOException;

import blt.java.emprestimo.model.Emprestimo;
import blt.java.emprestimo.view.EmprestimoAdicionarEmprestimoControlador;
import blt.java.emprestimo.view.EmprestimoExcluirEmprestimoControlador;
import blt.java.emprestimo.view.EmprestimoSelecionarAcervoControlador;
import blt.java.emprestimo.view.EmprestimoVisaoGeralControlador;
import blt.java.emprestimo.view.EmprestimoVisualizarControlador;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ManutencaoEmprestimos extends Application {

    private Stage stage;
    private BorderPane borda;


    public ManutencaoEmprestimos() {

    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        try {
            initRootLayout();
            mostrarEmprestimoVisaoGeral();
        } catch (IOException ex) {
            Logger.getLogger(ManutencaoEmprestimos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

	/**
     * Inicializa o root layout (layout base).
     */
    public void initRootLayout() throws IOException {
        try {
            // Carrega o root layout do arquivo fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ManutencaoEmprestimos.class.getResource("view/RootLayout.fxml"));
            borda = (BorderPane) loader.load();

            // Mostra a scene (cena) contendo o root layout.
            Scene scene = new Scene(borda);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mostra o emprestimo visao geral dentro do root layout.
     */
    public void mostrarEmprestimoVisaoGeral() throws IOException{
        try {
            // Carrega o empréstimo visão geral.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ManutencaoEmprestimos.class.getResource("view/EmprestimoVisaoGeral.fxml"));
            AnchorPane emprestimoVisaoGeral = (AnchorPane) loader.load();

            // Define a emprestimo visão geral no centro do root layout.
            borda.setCenter(emprestimoVisaoGeral);

            // Dá ao controlador acesso à aplicação principal.
            EmprestimoVisaoGeralControlador controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void mostrarEmprestimoExcluir() {
        try {
            // Carrega o arquivo fxml e cria um novo stage para a janela popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ManutencaoEmprestimos.class.getResource("view/EmprestimoExcluirEmprestimo.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            borda.setCenter(page);

            // Define a classe no controle.
            EmprestimoExcluirEmprestimoControlador controller = loader.getController();
            controller.setMain(this);
            

        } catch (IOException e) {
            e.printStackTrace();
            
        }
    }

    public void mostrarEmprestimoAdicionar() {
        try {
            // Carrega o arquivo fxml e cria um novo stage para a janela popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ManutencaoEmprestimos.class.getResource("view/EmprestimoAdicionarEmprestimo.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Cria o palco dialogStage.
            borda.setCenter(page);

            // Define a classe no controlador.
            EmprestimoAdicionarEmprestimoControlador controller = loader.getController();
            controller.setMain(this);
            

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void mostrarEmprestimoSelecionarAcervo(Emprestimo emprestimo) {
        try {
            // Carrega o arquivo fxml e cria um novo stage para a janela popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ManutencaoEmprestimos.class.getResource("view/EmprestimoSelecionarAcervo.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Cria o palco dialogStage.
            borda.setCenter(page);
            
            // Define a classe no controlador.
            EmprestimoSelecionarAcervoControlador controller = loader.getController();
            controller.setMain(this);
            controller.setEmprestimo(emprestimo);
            controller.setTabela();
            

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Mostra a tabela de resultados
    public void mostrarEmprestimoVisualizar() {
        try {
            // Carrega o fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ManutencaoEmprestimos.class.getResource("view/EmprestimoVisualizar.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            borda.setCenter(page);
            
            // Dá ao controlador acesso à aplicação principal.
            EmprestimoVisualizarControlador controller = loader.getController();
            controller.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}