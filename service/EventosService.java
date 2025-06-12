package service;

import dao.EventosDao;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import table.Eventos;

public class EventosService {

    private EventosDao dao;

    public EventosService(EventosDao dao) {
        this.dao = dao;
    }

    public EventosService() {
        this.dao = new EventosDao();
    }

    public List<Eventos> listarEventos() throws SQLException {
        return this.dao.listarEventos();
    }

    public List<Eventos> listarPorParametro(String tipo, String valor) {
        return dao.listarPorParametro(tipo, valor);
    }

    public void criarEvento(String nome, String descricao, String dataString, String local, int palestranteId, int capacidade) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {

            Date dataEvento = sdf.parse(dataString);

            Eventos evento = new Eventos();
            evento.setNome(nome);
            evento.setDescricao(descricao);
            evento.setData(dataEvento);
            evento.setLocal(local);
            evento.setPalestranteId(palestranteId);
            evento.setCapacidade(capacidade);

            dao.criarEvento(evento);
            System.out.println("Evento criado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao criar o evento: " + e.getMessage());
        }
    }

    // Método para editar evento
    public String editarEvento(int eventoId, String nome, String descricao, Date data, String local, int palestranteId,
            int capacidade) {
        Eventos evento = new Eventos();
        evento.setId(eventoId);
        evento.setNome(nome);
        evento.setDescricao(descricao);
        evento.setData(data);
        evento.setLocal(local);
        evento.setPalestranteId(palestranteId);
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
    public Boolean eventoExiste(int eventId) {
        try {
            return this.dao.eventoExiste(eventId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
