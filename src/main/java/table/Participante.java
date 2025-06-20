package table;

public class Participante {
    private Integer id;
    private String nome;
    private String sexo;
    private String email;
    private String celular;
    private String senha;
    private String tipo;

    public Participante() {
    }

    public Participante(Integer id, String nome, String sexo, String email, String celular, String senha, String tipo) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.email = email;
        this.celular = celular;
        this.senha = senha;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
