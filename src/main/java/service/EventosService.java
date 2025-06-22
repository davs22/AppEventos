package service;

import dao.EventosDao;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import table.Eventos;

public class EventosService {

    private EventosDao dao;
    private PalestranteService palestranteService;

    public EventosService(EventosDao dao) {
        this.dao = dao;
        this.palestranteService = new PalestranteService();
    }

    public EventosService() {
        this.dao = new EventosDao();
        this.palestranteService = new PalestranteService();
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
            // Verificação se o palestrante existe
            if (!palestranteService.palestranteExiste(palestranteId)) {
                System.out.println("Erro: Palestrante com ID " + palestranteId + " não existe.");
                return;
            }

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

    public String editarEvento(int eventoId, String nome, String descricao, Date data, String local, int palestranteId,
                                int capacidade) {
        try {
            // Verificações
            if (!eventoExiste(eventoId)) {
                return "Erro: Evento com ID " + eventoId + " não existe.";
            }

            if (!palestranteService.palestranteExiste(palestranteId)) {
                return "Erro: Palestrante com ID " + palestranteId + " não existe.";
            }

            Eventos evento = new Eventos();
            evento.setId(eventoId);
            evento.setNome(nome);
            evento.setDescricao(descricao);
            evento.setData(data);
            evento.setLocal(local);
            evento.setPalestranteId(palestranteId);
            evento.setCapacidade(capacidade);

            this.dao.editarEvento(evento);
            return "Evento atualizado com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao atualizar evento: " + e.getMessage();
        }
    }

    public String excluirEvento(int eventoId) {
        try {
            if (!eventoExiste(eventoId)) {
                return "Erro: Evento com ID " + eventoId + " não existe.";
            }

            this.dao.excluirEvento(eventoId);
            return "Evento excluído com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao excluir evento: " + e.getMessage();
        }
    }

    public String associarPalestrante(int eventoId, int palestranteId) {
        try {
            if (!eventoExiste(eventoId)) {
                return "Erro: Evento com ID " + eventoId + " não existe.";
            }

            if (!palestranteService.palestranteExiste(palestranteId)) {
                return "Erro: Palestrante com ID " + palestranteId + " não existe.";
            }

            Eventos evento = new Eventos();
            evento.setId(eventoId);
            evento.setPalestranteId(palestranteId);

            this.dao.associarPalestrante(evento);
            return "Palestrante associado com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao associar palestrante: " + e.getMessage();
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