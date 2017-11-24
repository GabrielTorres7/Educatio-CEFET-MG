package blt.java.emprestimo.jdbc;

import blt.java.emprestimo.model.Acervo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import blt.java.emprestimo.model.Emprestimo;
import blt.java.emprestimo.model.Reserva;
import blt.java.emprestimo.util.DataUtil;
import java.text.ParseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmprestimoDao {
	 // a conexão com o banco de dados
	  private final Connection conexao;
          private ObservableList campis = FXCollections.observableArrayList();
          private ObservableList departamentos = FXCollections.observableArrayList();
          private ObservableList cursos = FXCollections.observableArrayList();
          private ObservableList turmas = FXCollections.observableArrayList();
          private ObservableList alunos = FXCollections.observableArrayList();

	  public EmprestimoDao() {
	    this.conexao = new CriaConexao().getConexao();
	  }

	  public void adicionaEmprestimo(Emprestimo emprestimo){
		    String sql = "insert into emprestimos " +
                    "(idAluno, idAcervo, dataEmprestimo, dataPrevisaoDevolucao, dataDevolucao, multa, ativo)" +
                    " values (?,?,?,?,?,?,?)";

		    try {
		        // prepared statement para inserção
		        PreparedStatement stmt = conexao.prepareStatement(sql);

		        // seta os valores
		        stmt.setString(1, emprestimo.getIdAluno());
		        stmt.setInt(2, emprestimo.getIdAcervo());
		        stmt.setString(3, emprestimo.getDataEmprestimo());
                        stmt.setString(4, emprestimo.getDataPrevisaoDevolucao());
                        stmt.setString(5, "");
                        stmt.setString(6, "");
		        stmt.setString(7, "S");

		        // executa
		        stmt.execute();
		        stmt.close();
		    } catch (SQLException e) {
		        throw new RuntimeException(e);
		    }
		}
          
          public void adicionaReserva(Reserva reserva){
		    String sql = "insert into reservas " +
                    "(idAluno, idAcervo, dataReserva, tempoEspera, emprestou, ativo)" +
                    " values (?,?,?,?,?,?)";

		    try {
		        // prepared statement para inserção
		        PreparedStatement stmt = conexao.prepareStatement(sql);

		        // seta os valores
		        stmt.setString(1, reserva.getIdAluno());
		        stmt.setInt(2, reserva.getIdAcervo());
		        stmt.setString(3, reserva.getDataReserva());
                        stmt.setLong(4, reserva.getTempoEspera());
                        stmt.setString(5, "N");
		        stmt.setString(6, "S");

		        // executa
		        stmt.execute();
		        stmt.close();
		    } catch (SQLException e) {
		        throw new RuntimeException(e);
		    }
		}
          
          public boolean existeEmprestimo(Emprestimo emprestimo){
            try {
		         PreparedStatement stmt = this.conexao.
		                 prepareStatement("select * from emprestimos");
		         ResultSet rs = stmt.executeQuery();
                         boolean existe = false;
                         
		         while (rs.next()) {
                             if(emprestimo.getIdAcervo() == rs.getInt("idAcervo") && rs.getString("ativo").equals("S")){
                   
                                 emprestimo.setDataPrevisaoDevolucao(rs.getString("dataPrevisaoDevolucao"));
                                 existe = true;
                             }      
		         }
		         rs.close();
		         stmt.close();
		         return existe;
		     } catch (SQLException e) {
		         throw new RuntimeException(e);
		     }
           
          }
          
          public void atualizaReservas(long diasAtrasados, int idAcervo) throws ParseException{
              try {      
                                              
                    PreparedStatement stmt = conexao
	                 .prepareStatement("select * from reservas");
                    ResultSet rs = stmt.executeQuery();
                    String data, idAluno;
                 
                    while (rs.next()) {
                        if(idAcervo == rs.getInt("idAcervo") && rs.getString("ativo").equals("S")){

                            data = rs.getString("dataReserva");
                            idAluno = rs.getString("idAluno");
                            

                            atualizaReservasDados(DataUtil.adicionaXDias(data, (int) diasAtrasados), idAcervo, idAluno);
                        }      
                    }
                    stmt.close();
                    rs.close();
		     } catch (SQLException e) {
		         throw new RuntimeException(e);
		     }
          }
          
          private void atualizaReservasDados(String dataNova, int idAcervo, String idAluno){
               try {
		         PreparedStatement stmt = conexao
		                 .prepareStatement("update reservas set dataReserva=? where idAcervo=? AND idAluno=?");
		         stmt.setString(1, dataNova);
                         stmt.setInt(2, idAcervo);
                         stmt.setString(3, idAluno);
		         stmt.execute();
		         stmt.close();
		     } catch (SQLException e) {
		         throw new RuntimeException(e);
		     }
          }
          
          public void emprestaReserva(Reserva reserva){
              try {
		         PreparedStatement stmt = conexao
		                 .prepareStatement("update reservas set ativo=?, emprestou=? where idAcervo=? AND dataReserva=?");
		         stmt.setString(1, "N");
                         stmt.setString(2, "S");
                         stmt.setInt(3, reserva.getIdAcervo());
                         stmt.setString(4, reserva.getDataReserva());
		         stmt.execute();
		         stmt.close();
		     } catch (SQLException e) {
		         throw new RuntimeException(e);
		     }
          }
          
	  public boolean existeReservaAdicionar(Reserva reserva){
            try {
                PreparedStatement stmt = this.conexao.
                        prepareStatement("select * from reservas");
                ResultSet rs = stmt.executeQuery();
                boolean existe = false;

                while (rs.next()) {
                    if(reserva.getIdAcervo() == rs.getInt("idAcervo") && rs.getString("ativo").equals("S")){

                        reserva.setDataReserva(rs.getString("dataReserva"));
                        existe = true;
                    }      
                }
                rs.close();
                stmt.close();
                return existe;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

          }
          
          public boolean existeReservaRemover(Reserva reserva){
            try {
                PreparedStatement stmt = this.conexao.
                        prepareStatement("select * from reservas");
                ResultSet rs = stmt.executeQuery();
                boolean existe = false;

                while (rs.next()) {
                    if(reserva.getIdAcervo() == rs.getInt("idAcervo") && rs.getString("ativo").equals("S")){

                        reserva.setDataReserva(rs.getString("dataReserva"));
                        reserva.setIdAluno(rs.getString("idAluno"));
                        existe = true;
                        break;
                    }      
                }
                rs.close();
                stmt.close();
                return existe;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

          }
          
          public List<Emprestimo> getLista() {
		     try {
		         List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
		         PreparedStatement stmt = this.conexao.
		                 prepareStatement("select * from emprestimos");
		         ResultSet rs = stmt.executeQuery();

		         while (rs.next()) {
		             // criando o objeto Emprestimo
		             Emprestimo emprestimo = new Emprestimo();
                             
                            
                             
		             emprestimo.setIdAluno(rs.getString("idAluno"));
		             emprestimo.setIdAcervo(rs.getInt("idAcervo"));
                             emprestimo.setNome(pegaNomeAcervo(rs.getInt("idAcervo")));
		             emprestimo.setDataEmprestimo(rs.getString("dataEmprestimo"));
                             emprestimo.setDataPrevisaoDevolucao(rs.getString("dataPrevisaoDevolucao"));
                             emprestimo.setDataDevolucao(rs.getString("dataDevolucao"));
                             emprestimo.setMulta(rs.getLong("multa"));
		             if(rs.getString("ativo").equals( "S")){
		             // adicionando o objeto à lista somente se estiver ativo
		             emprestimos.add(emprestimo);
		             }
		         }
		         rs.close();
		         stmt.close();
		         return emprestimos;
		     } catch (SQLException e) {
		         throw new RuntimeException(e);
		     }
		 }
          
          public List<Acervo> getListaAcervos(int idCampi) {
		     try {
		         List<Acervo> acervos = new ArrayList<Acervo>();
		         PreparedStatement stmt = this.conexao.
		                 prepareStatement("select * from acervo WHERE idCampi=?");
		         stmt.setInt(1, idCampi);
                         ResultSet rs = stmt.executeQuery();
                         
                         
		         while (rs.next()) {
		             // criando o objeto Emprestimo
		             Acervo acervo = new Acervo();
                             
                             acervo.setId(rs.getInt("id"));
		             acervo.setNome(rs.getString("nome"));
		             acervo.setTipo(rs.getString("tipo"));
		             acervo.setLocal(rs.getString("local"));
                             acervo.setAno(rs.getString("ano"));
                             acervo.setEditora(rs.getString("editora"));
                             acervo.setPaginas(rs.getInt("paginas"));
		             if(rs.getString("ativo").equals( "S")){
		             // adicionando o objeto à lista somente se estiver ativo
		             acervos.add(acervo);
		             }
		         }
		         rs.close();
		         stmt.close();
		         return acervos;
		     } catch (SQLException e) {
		         throw new RuntimeException(e);
		     }
		 }
          
	  public void remove(Emprestimo emprestimo) {
		     try {
		         PreparedStatement stmt = conexao
		                 .prepareStatement("update emprestimos set ativo=?, dataDevolucao=?, multa=? where idAcervo=? AND idAluno=?");
		         stmt.setString(1, "N");
                         stmt.setString(2, emprestimo.getDataDevolucao());
                         stmt.setLong(3, emprestimo.getMulta());
		         stmt.setInt(4, emprestimo.getIdAcervo());
                         stmt.setString(5, emprestimo.getIdAluno());
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
        
        public ObservableList pegaTurmas(String curso) throws SQLException{
                   
        
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
                turmas.add(result.getString("nome"));
            }
        }
        
        
        return turmas;
    }
        
        public ObservableList pegaAlunos(String turma) throws SQLException{
                   
        
        String sql = "SELECT id FROM turmas WHERE nome = (?) AND ativo = 'S'";
        
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, turma);
        
        int idTurma;
        
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            idTurma = rs.getInt("id");
            sql = "SELECT nome FROM alunos WHERE idTurma = (?) AND ativo = 'S'";
            
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idTurma);
            
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                alunos.add(result.getString("nome"));
            }
        }
        
        
        return alunos;
    }
        
    public int pegaIdCampi(String nomeCampi) throws SQLException{
           
        String sql = "SELECT id FROM campi WHERE nome = (?) AND ativo = 'S'"; 
        
        PreparedStatement declaracao = conexao.prepareStatement(sql);
        declaracao.setString(1, nomeCampi);
        
        ResultSet rs = declaracao.executeQuery();
        rs.next();
        int idCampi = rs.getInt("id");
        
        return idCampi;
    }
    
    public String pegaIdAluno(String nomeAluno) throws SQLException{
           
        String sql = "SELECT idCPF FROM alunos WHERE nome = (?) AND ativo = 'S'"; 
        
        PreparedStatement declaracao = conexao.prepareStatement(sql);
        declaracao.setString(1, nomeAluno);
        
        ResultSet rs = declaracao.executeQuery();
        rs.next();
        String idAluno = rs.getString("idCPF");
        
        return idAluno;
    }
    
    public String pegaNomeAcervo(int idAcervo) throws SQLException{
           
        String sql = "SELECT nome FROM acervo WHERE id =? AND ativo = 'S'"; 
        
        PreparedStatement declaracao = conexao.prepareStatement(sql);
        declaracao.setInt(1, idAcervo);
        
        ResultSet rs = declaracao.executeQuery();
        rs.next();
        String nomeAcervo = rs.getString("nome");
        
        return nomeAcervo;
    }
    
    public String pegaIdAcervo(String nomeAcervo) throws SQLException{
           
        String sql = "SELECT id FROM acervo WHERE nome=? AND ativo = 'S'"; 
        
        PreparedStatement declaracao = conexao.prepareStatement(sql);
        declaracao.setString(1, nomeAcervo);
        
        ResultSet rs = declaracao.executeQuery();
        rs.next();
        String idAluno = rs.getString("id");
        
        return idAluno;
    }
}
