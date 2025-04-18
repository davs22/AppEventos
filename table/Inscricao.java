package table;

public class Inscricao {
    private int id;
    private int idParticipante;
    private int idEvento;

    public Inscricao() {}

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

    @Override
    public String toString() {
        return "Inscricao{" +
                "id=" + id +
                ", idParticipante=" + idParticipante +
                ", idEvento=" + idEvento +
                '}';
    }
}
