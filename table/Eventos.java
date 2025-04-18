package table;
import java.util.Date;

public class Eventos {
    private String nome;
    private String descricao;
    private Date data;
    private String local;
    private int palestranteId;
    
    public Eventos() {
    }

    public Eventos(String nome, String descricao, Date data, String local, int palestranteId) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.local = local;
        this.palestranteId = palestranteId;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }
    public int getpalestranteId() {
        return palestranteId;
    }
    public void setPalestrantes(int palestranteId) {
        this.palestranteId = palestranteId;
    }
 
    
}
