package blt.java.disciplina.view;

import javafx.fxml.FXML;

import java.sql.SQLException;
import blt.java.disciplina.ManutencaoDisciplinas;
import blt.java.disciplina.jdbc.DisciplinaDao;
import java.io.IOException;


/**
*
* @author Torres
*/

public class DisciplinaVisaoGeralControlador {
    
    private ManutencaoDisciplinas mainApp; // Referencia à aplicação principal.
    DisciplinaDao bd = new DisciplinaDao(); //Abre conexão com BD.
    
    
    //Construtor
    public DisciplinaVisaoGeralControlador() {
    }

    /**
     * Chamado quando o usuário clica no botão adicionar abre uma janela para editar
     * detalhes da nova pessoa.
     * @throws SQLException
     */
    @FXML
    private void botaoNovaDisciplina()  {

        mainApp.mostrarAdicionarDisciplina();
        
        
    }
    
    /**
     * Chamado quando o usuário clica no botão editar abre a janela para pesquisar
     * uma pessoa a ser editada.
     */
    @FXML
   private void botaoEditarDisciplina() throws IOException{
 
        mainApp.mostrarDisciplinaPesquisar();
        
    }

    @FXML
    private void botaoDeletarDisciplina() throws IOException{
        
        mainApp.mostrarDisciplinaExcluir();

    }

    @FXML
    private void botaoVisualizarDisciplina() throws IOException{

        mainApp.mostrarDisciplinasVisualizar();

    }

    /**
     * É chamado pela aplicação principal para dar uma referência de volta a si mesmo.
     *
     * @param mainApp
     */
    public void setMainApp(ManutencaoDisciplinas mainApp) {
        this.mainApp = mainApp;

    }
}

