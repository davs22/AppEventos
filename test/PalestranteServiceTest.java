package test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.PalestranteDao;
import service.PalestranteService;
import table.Palestrante;

public class PalestranteServiceTest {

    private PalestranteDao daoMock;
    private PalestranteService service;

    @BeforeEach
    public void setUp() {
        daoMock = mock(PalestranteDao.class);
        service = new PalestranteService(daoMock); 
    }

    @Test
    public void testListarTodos() {
        Palestrante p1 = new Palestrante(1, "Ana", "Currículo A", "Educação");
        Palestrante p2 = new Palestrante(2, "Bruno", "Currículo B", "TI");

        when(daoMock.listarTodos()).thenReturn(Arrays.asList(p1, p2));

        List<Palestrante> resultado = service.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Ana", resultado.get(0).getNome());
    }

}
