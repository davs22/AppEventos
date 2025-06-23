package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import dao.PalestranteDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.PalestranteService;
import table.Palestrante;

public class PalestranteServiceTest {
    
    private PalestranteDao daoMock;
    private PalestranteService service;

    @BeforeEach
    public void setup() {
        daoMock = mock(PalestranteDao.class);
        service = new PalestranteService(daoMock);
    }

    @Test
    public void testInserirPalestrante_ComDadosValidos_DeveRetornarSucesso() {
        when(daoMock.inserir("Maria Lorena", "Especialista em Machine Language", "Tecnologia"))
        .thenReturn("sucesso");

        String resultado = service.inserir("Maria Lorena", "Especialista em Machine Language", "Tecnologia");

        assertEquals("sucesso", resultado);
        verify(daoMock).inserir("Maria Lorena","Especialista em Machine Language","Tecnologia");

    }
    @Test
    public void testInserirPalestrante_ComCamposInvalidos_DeveRetornarErro() {
        String resultado = service.inserir("","currículo","área");

        assertEquals("Erro: todos os campos são obrigatórios.", resultado);
        verify(daoMock, never()).inserir(any(), any(), any());
    }

    @Test
    public void testListarTodos_DeveRetornarListadePalestrantes() {
        List<Palestrante> mockList = Arrays.asList(
        new Palestrante(1, "João", "Currículo 1", "TI"),
        new Palestrante(2,"Ana","2","Fisioterapia")
        );

        when(daoMock.listarTodos()).thenReturn(mockList);

        List<Palestrante> resultado = service.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("João", resultado.get(0).getNome());
    }

    @Test
    public void testPalestranteExiste_true() throws SQLException {
        when(daoMock.palestranteExiste(1)).thenReturn(true);

        boolean existe = service.palestranteExiste(1);

        assertTrue(existe);
    }
}
