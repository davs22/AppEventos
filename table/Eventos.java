package table;

public class Eventos {
    private String nome;
    private String descricao;
    private Integer data;
    private String local;
    private palestrante palestrantes;
    
    public Eventos() {
    }

    public Eventos(String nome, String descricao, Integer data, String local, palestrante palestrantes) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.local = local;
        this.palestrantes = palestrantes;
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
    public Integer getData() {
        return data;
    }
    public void setData(Integer data) {
        this.data = data;
    }
    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }
    public palestrante getPalestrantes() {
        return palestrantes;
    }
    public void setPalestrantes(palestrante palestrantes) {
        this.palestrantes = palestrantes;
    }
 
    
}
