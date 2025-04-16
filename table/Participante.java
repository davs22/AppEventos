package table;

public class Participante {
    private String nome;
    private String email;
    private String sexo;
    private Integer idade;
    public Participante(String nome, String email, String sexo, Integer idade) {
        this.nome = nome;
        this.email = email;
        this.sexo = sexo;
        this.idade = idade;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public Integer getIdade() {
        return idade;
    }
    public void setIdade(Integer idade) {
        this.idade = idade;
    }
   
}
