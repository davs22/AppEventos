package table;

import java.util.Date;

public class Eventos {
    private String nome;
    private String descricao;
    private Date data;
    private String local;
    private int palestranteId;
    private int capacidade;
    private Integer id;

    public Eventos() {
    }

    public Eventos(Integer id, String nome, String descricao, Date data, String local, int palestranteId,
            int capacidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.local = local;
        this.palestranteId = palestranteId;
        this.capacidade = capacidade;
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

    public int getPalestranteId() {
        return palestranteId;
    }

    public void setPalestranteId(int palestranteId) {
        this.palestranteId = palestranteId;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
