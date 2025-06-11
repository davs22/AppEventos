package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dao.EventosDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.EventosService;
import table.Eventos;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class EventosServiceTest {

    private EventosService service;
    private EventosDao daoMock;

    @BeforeEach
    public void setUp() {
        daoMock = mock(EventosDao.class);
        service = new EventosService(daoMock); 
    }
    
    @Test
    public void testListarEventos() throws SQLException {
        Eventos evento1 = new Eventos();
        Eventos evento2 = new Eventos();
    
        when(daoMock.listarEventos()).thenReturn(Arrays.asList(evento1, evento2));
    
        List<Eventos> eventos = service.listarEventos();
        assertEquals(2, eventos.size());
    }
    
    @Test
    public void testListarPorParametro() {
    Eventos evento = new Eventos();
    List<Eventos> eventos = Arrays.asList(evento);

    when(daoMock.listarPorParametro("Tech", "2024-05-10", null)).thenReturn(eventos);

    List<Eventos> result = service.listarPorParametro("Tech", "2024-05-10", null);

    assertNotNull(result);
    assertEquals(1, result.size());
 }
    
} 
