package table;

public class Inscricao {
    private int id;
    private int idParticipante;
    private int idEvento;
    private String nomeParticipante;
    private String nomeEvento;

    public Inscricao() {
    }

    public Inscricao(int id, int idParticipante, int idEvento) {
        this.id = id;
        this.idParticipante = idParticipante;
        this.idEvento = idEvento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getNomeParticipante() {
        return nomeParticipante;
    }

    public void setNomeParticipante(String nomeParticipante) {
        this.nomeParticipante = nomeParticipante;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

   @Override
    public String toString() {
        return "Inscricao{" +
                "id=" + id +
                ", idParticipante=" + idParticipante +
                " (" + nomeParticipante + ")" +
                ", idEvento=" + idEvento +
                " (" + nomeEvento + ")" +
                '}';
    }

}
