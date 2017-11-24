package blt.java.disciplina;

import java.io.IOException;

import blt.java.disciplina.model.Disciplina;
import blt.java.disciplina.view.DisciplinaAlterarControlador;
import blt.java.disciplina.view.DisciplinaAdicionarDisciplinaControlador;
import blt.java.disciplina.view.DisciplinaExcluirControlador;
import blt.java.disciplina.view.DisciplinaPesquisarControlador;
import blt.java.disciplina.view.DisciplinaVisaoGeralControlador;
import blt.java.disciplina.view.DisciplinaVisualizarControlador;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManutencaoDisciplinas extends Application {

    private Stage stage;
    private BorderPane borda;
    
    public ManutencaoDisciplinas() {

    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        try {
            initRootLayout();
            mostrarDisciplinaVisaoGeral();
        } catch (IOException ex) {
            Logger.getLogger(ManutencaoDisciplinas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

	/**
     * Inicializa o root layout (layout base).
     */
    public void initRootLayout() throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ManutencaoDisciplinas.class.getResource("view/RootLayout.fxml"));
            borda = (BorderPane) loader.load();
            Scene scene = new Scene(borda);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarDisciplinaVisaoGeral() throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ManutencaoDisciplinas.class.getResource("view/DisciplinaVisaoGeral.fxml"));
            AnchorPane disciplinaVisaoGeral = (AnchorPane) loader.load();
            borda.setCenter(disciplinaVisaoGeral);
            DisciplinaVisaoGeralControlador controller = loader.getController();
            controller.setMainApp(this);            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void mostrarDisciplinaPesquisar() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ManutencaoDisciplinas.class.getResource("view/DisciplinaPesquisar.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            borda.setCenter(page);
            DisciplinaPesquisarControlador controller = loader.getController();
            controller.setMain(this);           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void mostrarDisciplinaExcluir() {
        try {
            // Carrega o arquivo fxml e cria um novo stage para a janela popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ManutencaoDisciplinas.class.getResource("view/DisciplinaExcluir.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Cria o palco dialogStage.
            borda.setCenter(page);

            // Define a classe no controle.
            DisciplinaExcluirControlador controller = loader.getController();
            controller.setMain(this);
                     
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void mostrarDisciplinaAlterar(Disciplina disciplina) {
        try {
            // Carrega o arquivo fxml e cria um novo stage para a janela popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ManutencaoDisciplinas.class.getResource("view/DisciplinaAlterar.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Cria o palco dialogStage.
            borda.setCenter(page);
            // Define a classe no controle.
            DisciplinaAlterarControlador controller = (DisciplinaAlterarControlador) loader.getController();
            controller.setDisciplina(disciplina);
            controller.setMain(this);
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Abre uma janela para editar detalhes para a disciplina especificada. Se o usuário clicar
     * OK, as mudanças são salvas no objeto disciplina fornecido e retorna true.
     */
    public void mostrarAdicionarDisciplina() {
        try {
            // Carrega o arquivo fxml e cria um novo stage para a janela popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ManutencaoDisciplinas.class.getResource("view/DisciplinaAdicionarDisciplina.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Cria o palco dialogStage.
            borda.setCenter(page);

            DisciplinaAdicionarDisciplinaControlador controller = loader.getController();
            controller.setMain(this);

            
        } catch (IOException e) {
            e.printStackTrace();
            
        }
    }

    //Mostra a tabela de resultados
    public void mostrarDisciplinasVisualizar() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ManutencaoDisciplinas.class.getResource("view/DisciplinaVisualizar.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            borda.setCenter(page);

            DisciplinaVisualizarControlador controller = loader.getController();
            controller.setMain(this);
            
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    public static void main(String[] args) {
        launch(args);
    }

}