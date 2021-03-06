package blt.java.emprestimo.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Classe Model para um Emprestimo (emprestimo).
 *
 * @author Torres
 */
public class Emprestimo {
    
    private final LongProperty multa;
    private final IntegerProperty idAcervo;
    private final IntegerProperty idCampi;
    private final StringProperty idAluno;
    private final StringProperty dataEmprestimo;
    private final StringProperty dataPrevisaoDevolucao;
    private final StringProperty dataDevolucao;
    private final StringProperty nome;

    /**
     *  Construtor padrão.
     */
    public Emprestimo() {
        this(null, 0, 0L, 0, null, null, null, null);
    }

    /**
     * Construtor com os dados inicializados.
     * 
     */
    public Emprestimo(String nome, Integer idAcervo, Long multa, Integer idCampi, String idAluno, String dataEmprestimo, String dataDevolucao, String dataPrevisaoDevolucao) {
        this.nome = new SimpleStringProperty(nome);
        this.idAcervo = new SimpleIntegerProperty(idAcervo);
        this.idCampi = new SimpleIntegerProperty(idCampi);
        this.multa = new SimpleLongProperty(multa);
        this.idAluno = new SimpleStringProperty(idAluno);
        this.dataEmprestimo = new SimpleStringProperty(dataEmprestimo);
        this.dataDevolucao = new SimpleStringProperty(dataDevolucao);
        this.dataPrevisaoDevolucao = new SimpleStringProperty(dataPrevisaoDevolucao);
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public StringProperty nomeProperty() {
        return nome;
    }
    
    public int getIdAcervo() {
        return idAcervo.get();
    }

    public void setIdAcervo(int idAcervo) {
        this.idAcervo.set(idAcervo);
    }

    public IntegerProperty idAcervoProperty() {
        return idAcervo;
    }
    
     public int getIdCampi() {
        return idCampi.get();
    }

    public void setIdCampi(int idCampi) {
        this.idCampi.set(idCampi);
    }

    public IntegerProperty idCampiProperty() {
        return idCampi;
    }
    
    public Long getMulta() {
        return multa.get();
    }

    public void setMulta(Long multa) {
        this.multa.set(multa);
    }

    public LongProperty multaProperty() {
        return multa;
    }

    public String getIdAluno() {
        return idAluno.get();
    }

    public void setIdAluno(String idAluno) {
        this.idAluno.set(idAluno);
    }

    public StringProperty idAlunoProperty() {
        return idAluno;
    }
    
    public String getDataEmprestimo() {
        return dataEmprestimo.get();
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo.set(dataEmprestimo);
    }

    public StringProperty dataEmprestimoProperty() {
        return dataEmprestimo;
    }
    
    public String getDataPrevisaoDevolucao() {
        return dataPrevisaoDevolucao.get();
    }

    public void setDataPrevisaoDevolucao(String dataPrevisaoDevolucao) {
        this.dataPrevisaoDevolucao.set(dataPrevisaoDevolucao);
    }

    public StringProperty dataPrevisaoDevolucaoProperty() {
        return dataPrevisaoDevolucao;
    }
    
    public String getDataDevolucao() {
        return dataDevolucao.get();
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao.set(dataDevolucao);
    }

    public StringProperty dataDevolucaoProperty() {
        return dataDevolucao;
    }


}