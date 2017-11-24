package blt.java.disciplina.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import blt.java.disciplina.model.Disciplina;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DisciplinaDao {
	 // a conexão com o banco de dados
	  private Connection conexao;
          private ObservableList campis = FXCollections.observableArrayList();
          private ObservableList departamentos = FXCollections.observableArrayList();
          private ObservableList cursos = FXCollections.observableArrayList();
          private ObservableList idTurmas = FXCollections.observableArrayList();
          private ObservableList nomes = FXCollections.observableArrayList();
	  
          public DisciplinaDao() {
	    this.conexao = new CriaConexao().getConexao();
	  }

	  public void adiciona(Disciplina disciplina){
		    String sql = "insert into disciplinas " +
                    "(idTurma,nome, cargaHorariaMin, ativo)" +
                    " values (?,?,?,?)";

		    try {
		        // prepared statement para inserção
		        PreparedStatement stmt = conexao.prepareStatement(sql);

		        // seta os valores
		        stmt.setInt(1,disciplina.getIdTurma());
		        stmt.setString(2,disciplina.getNome());
		        stmt.setInt(3,disciplina.getCargaHorariaMin());
		        stmt.setString(4, "S");

		        // executa
		        stmt.execute();
		        stmt.close();
		    } catch (SQLException e) {
		        throw new RuntimeException(e);
		    }
		}

	  public void altera(Disciplina disciplina, String nome) {
		     String sql = "update disciplinas set cargaHorariaMin=?, nome=? where nome=? AND idTurma=?";
		     try {
		         PreparedStatement stmt = conexao.prepareStatement(sql);
		         stmt.setInt(1, disciplina.getCargaHorariaMin());
		         stmt.setString(2, disciplina.getNome());
		         stmt.setString(3, nome);
		         stmt.setInt(4, disciplina.getIdTurma());
		         stmt.execute();
		         stmt.close();
		     } catch (SQLException e) {
		         throw new RuntimeException(e);
		     }
		 }

	  public List<Disciplina> getLista() {
		     try {
		         List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		         PreparedStatement stmt = this.conexao.
		                 prepareStatement("select * from disciplinas");
		         ResultSet rs = stmt.executeQuery();

		         while (rs.next()) {
		             // criando o objeto Contato
		             Disciplina disciplina = new Disciplina();
		             disciplina.setIdTurma(rs.getInt("idTurma"));
		             disciplina.setCargaHorariaMin(rs.getInt("cargaHorariaMin"));
		             disciplina.setNome(rs.getString("nome"));
		             if(rs.getString("ativo").equals( "S")){
		             // adicionando o objeto à lista somente se estiver ativo
		             disciplinas.add(disciplina);
		             }
		         }
		         rs.close();
		         stmt.close();
		         return disciplinas;
		     } catch (SQLException e) {
		         throw new RuntimeException(e);
		     }
		 }

	  public void remove(Disciplina disciplina) {
		     try {
		         PreparedStatement stmt = conexao
		                 .prepareStatement("update disciplinas set ativo=? where nome=?");
		         stmt.setString(1, "N");
		         stmt.setString(2, disciplina.getNome());
		         stmt.execute();
		         stmt.close();
		     } catch (SQLException e) {
		         throw new RuntimeException(e);
		     }
		 }
          
        public ObservableList pegaCampis() throws SQLException{
            
        int idCampi;
        
        
        String sql = "SELECT id FROM campi WHERE ativo = 'S'";
        
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        
        
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            idCampi = rs.getInt("id");
            sql = "SELECT nome FROM campi WHERE id = (?) AND ativo = 'S'";
            
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idCampi);
            
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                campis.add(result.getString("nome"));
            }
        }
        
        
        return campis;
    }
        
        public ObservableList pegaDepartamentos(String campi) throws SQLException{
                   
        
        String sql = "SELECT id FROM campi WHERE nome = (?) AND ativo = 'S'";
        
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, campi);
        
        int idDepto;
        
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            idDepto = rs.getInt("id");
            sql = "SELECT nome FROM deptos WHERE idCampi = (?) AND ativo = 'S'";
            
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idDepto);
            
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                departamentos.add(result.getString("nome"));
            }
        }
        
        
        return departamentos;
    }
        
        public ObservableList pegaCursos(String departamento) throws SQLException{
                   
        
        String sql = "SELECT id FROM deptos WHERE nome = (?) AND ativo = 'S'";
        
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, departamento);
        
        int idDepto;
        
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            idDepto = rs.getInt("id");
            sql = "SELECT nome FROM cursos WHERE idDepto = (?) AND ativo = 'S'";
            
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idDepto);
            
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                cursos.add(result.getString("nome"));
            }
        }
        
        
        return cursos;
    }
        
        public ObservableList pegaIdTurmas(String curso) throws SQLException{
                   
        
        String sql = "SELECT id FROM cursos WHERE nome = (?) AND ativo = 'S'";
        
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, curso);
        
        int idTurma;
        
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            idTurma = rs.getInt("id");
            sql = "SELECT nome FROM turmas WHERE idCurso = (?) AND ativo = 'S'";
            
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idTurma);
            
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                idTurmas.add(result.getString("nome"));
            }
        }
        
        
        return idTurmas;
    }
        
        public ObservableList pegaNomes(String turma) throws SQLException{
                   
        
        String sql = "SELECT id FROM turmas WHERE nome = (?) AND ativo = 'S'";
        
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, turma);
        
        int idTurma;
        
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            idTurma = rs.getInt("id");
            sql = "SELECT nome FROM disciplinas WHERE idTurma = (?) AND ativo = 'S'";
            
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idTurma);
            
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                nomes.add(result.getString("nome"));
            }
        }
        
        
        return nomes;
    }
       
        
        public int pegaIdTurma(String nomeTurma) throws SQLException{
           
        String sql = "SELECT id FROM turmas WHERE nome = (?) AND ativo = 'S'"; 
        
        PreparedStatement declaracao = conexao.prepareStatement(sql);
        declaracao.setString(1, nomeTurma);
        
        ResultSet rs = declaracao.executeQuery();
        rs.next();
        int idTurma = rs.getInt("id");
        
        return idTurma;
    }
        
        public int pegaCargaHorariaMinDisciplina(String nomeTurma) throws SQLException{
           
        String sql = "SELECT cargaHorariaMin FROM disciplinas WHERE nome = (?) AND ativo = 'S'"; 
        
        PreparedStatement declaracao = conexao.prepareStatement(sql);
        declaracao.setString(1, nomeTurma);
        
        ResultSet rs = declaracao.executeQuery();
        rs.next();
        int cargaHorariaMin = rs.getInt("cargaHorariaMin");
        
        return cargaHorariaMin;
    }
}
