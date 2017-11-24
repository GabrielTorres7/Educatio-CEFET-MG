package blt.java.emprestimo.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Classe Model para um Acervo (acervo).
 *
 * @author Torres
 */
public class Acervo {
    
    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty tipo;
    private final StringProperty local;
    private final StringProperty ano;
    private final StringProperty editora;
    private final IntegerProperty paginas;

    /**
     *  Construtor padrão.
     */
    public Acervo() {
        this(0, null, null, null, null, null, 0);
    }
   
    /**
     * Construtor com os dados inicializados.
     * 
     */
    public Acervo(int id, String nome, String tipo, String local, String ano, String editora, int paginas) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.tipo = new SimpleStringProperty(tipo);
        this.local = new SimpleStringProperty(local);
        this.ano = new SimpleStringProperty(ano);
        this.editora = new SimpleStringProperty(editora);
        this.paginas = new SimpleIntegerProperty(paginas);
    }
    
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
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

    public String getTipo() {
        return tipo.get();
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }

    public StringProperty tipoProperty() {
        return tipo;
    }

    public String getLocal() {
        return local.get();
    }

    public void setLocal(String local) {
        this.local.set(local);
    }

    public StringProperty localProperty() {
        return local;
    }
    
    public String getAno() {
        return ano.get();
    }

    public void setAno(String ano) {
        this.ano.set(ano);
    }

    public StringProperty anoProperty() {
        return ano;
    }
    
    public String getEditora() {
        return editora.get();
    }

    public void setEditora(String editora) {
        this.editora.set(editora);
    }

    public StringProperty editoraProperty() {
        return editora;
    }
    
    public int getPaginas() {
        return paginas.get();
    }

    public void setPaginas(int paginas) {
        this.paginas.set(paginas);
    }

    public IntegerProperty paginasProperty() {
        return paginas;
    }


}