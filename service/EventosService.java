package service;

import dao.EventosDao;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import table.Eventos;

public class EventosService {
    private EventosDao dao;

    public EventosService() {
        this.dao = new EventosDao();
    }

    public List<Eventos> listarEventos() throws SQLException {
        return this.dao.listarEventos();
    }

    public Eventos listarPorParametro (String nome, String data) {
        return (Eventos) this.dao.listarPorParametro(nome, data);
    }

    public String criarEvento(String nome, String descricao, Date data, String local, String capacidade) {
    Eventos evento = new Eventos();
    evento.setNome(nome);
    evento.setDescricao(descricao);
    evento.setData(data);
    evento.setLocal(local);
    evento.setCapacidade(capacidade);
    
    try {
        this.dao.criarEvento(evento);
        return "Evento criado com sucesso!";
    } catch (SQLException e) {
        e.printStackTrace();
        return "Erro ao criar evento: " + e.getMessage();
    }
}
 // Método para editar evento
 public String editarEvento(int eventoId, String nome, String descricao, Date data, String local, String capacidade) {
    Eventos evento = new Eventos();
    evento.setId(eventoId);
    evento.setNome(nome);
    evento.setDescricao(descricao);
    evento.setData(data);
    evento.setLocal(local);
    evento.setCapacidade(capacidade);

    try {
        this.dao.editarEvento(evento);
        return "Evento atualizado com sucesso!";
    } catch (SQLException e) {
        e.printStackTrace();
        return "Erro ao atualizar evento: " + e.getMessage();
    }
}

public String excluirEvento(int eventoId) {
    try {
        this.dao.excluirEvento(eventoId);
        return "Evento excluído com sucesso!";
    } catch (SQLException e) {
        e.printStackTrace();
        return "Erro ao excluir evento: " + e.getMessage();
    }
}


 public String associarPalestrante(int eventoId, int palestranteId) {
    Eventos evento = new Eventos();
    evento.setId(eventoId);
    evento.setPalestranteId(palestranteId);

    try {
        this.dao.associarPalestrante(evento);
        return "Palestrante associado com sucesso!";
    } catch (SQLException e) {
        e.printStackTrace();
        return "Erro ao associar palestrante: " + e.getMessage();
    }
}
public boolean palestranteExiste(int palestranteId) {
    try {
        return this.dao.palestranteExiste(palestranteId);
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // ou lançar uma exceção customizada se preferir
    }
}


}
