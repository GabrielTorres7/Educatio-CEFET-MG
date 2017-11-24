package blt.java.emprestimo.view;

import javafx.fxml.FXML;
import java.sql.SQLException;
import java.util.List;
import blt.java.emprestimo.ManutencaoEmprestimos;
import blt.java.emprestimo.jdbc.EmprestimoDao;
import blt.java.emprestimo.model.Emprestimo;
import blt.java.emprestimo.model.Reserva;
import blt.java.emprestimo.util.DataUtil;
import java.text.ParseException;
import javafx.scene.control.Alert;


public class EmprestimoVisaoGeralControlador {

    // Referencia à aplicação principal.
    private ManutencaoEmprestimos mainApp;
    EmprestimoDao bd = new EmprestimoDao(); //Conexão com o bd.
    
  
    public EmprestimoVisaoGeralControlador() {
    }

    /**
     * Chamado quando o usuário clica no botão novo. Abre uma janela para editar
     * detalhes de um novo empréstimo.
     * @throws SQLException
     */
    @FXML
    private void botaoNovoEmprestimo() throws ParseException  {
        
        mainApp.mostrarEmprestimoAdicionar(); 
    }

    /**
     * Chamado quando o usuário clica no botão remover. Abre a janela para pesquisar o id
     * do aluno que realizou empréstimo.
     */
    @FXML
    private void botaoDeletarEmprestimo() throws ParseException {
    	
        
        mainApp.mostrarEmprestimoExcluir();
        

    }

    @FXML
    private void botaoVisualizarEmprestimo()  {
        
        mainApp.mostrarEmprestimoVisualizar();

    }

    /**
     * É chamado pela aplicação principal para dar uma referência de volta a si mesmo.
     *
     * @param mainApp
     */
    public void setMainApp(ManutencaoEmprestimos mainApp) {
        this.mainApp = mainApp;

    }
}