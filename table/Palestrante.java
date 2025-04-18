package table;

public class Palestrante {
    private Integer id;
    private String nome;
    private String curriculo;
    private String areaAtuacao;
    
    public Palestrante() {
    }

    public Palestrante(Integer id, String nome, String curriculo, String areaAtuacao) {
        this.id = id;
        this.nome = nome;
        this.curriculo = curriculo;
        this.areaAtuacao = areaAtuacao;
    }
    
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCurriculo() {
        return curriculo;
    }
    public void setCurriculo(String curriculo) {
        this.curriculo = curriculo;
    }
    public String getAreaAtuacao() {
        return areaAtuacao;
    }
    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
}
