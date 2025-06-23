package test;

import org.junit.jupiter.api.*;
import service.EventosService;
import dao.EventosDao;
import table.Eventos;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventosServiceTest {

    private EventosService eventosService;

    @BeforeEach
    public void setup() {
        eventosService = new EventosService(new EventosDao());
    }

    @Test
    public void testCriarEListarEvento() {
        String nome = "Workshop Java";
        String descricao = "Evento da Java básico";
        String data = "25-12-2025";
        String local = "Sala 101";
        int palestranteid = 1;
        int capacidade = 50; 

        eventosService.criarEvento(nome, descricao, data, local, palestranteid, capacidade);
        try {
            List<Eventos> eventos = eventosService.listarEventos();
            boolean encontrado = eventos.stream()
            .anyMatch(e -> nome.equals(e.getNome()) && local.equals(e.getLocal()));
            assertTrue(encontrado, "O evento criado estará na lista.");
        } catch (Exception e) {
            fail("Erro ao listar os eventos:" + e.getMessage());
        }
    }
}
