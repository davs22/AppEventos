package test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dao.ParticipanteDao;
import service.ParticipanteService;
import table.Participante;

public class ParticipanteServiceTest {
    private ParticipanteDao participanteDaoMock;
    private ParticipanteService participanteService;

    @Before
public void setUp() {
    participanteDaoMock = mock(ParticipanteDao.class);
    participanteService = new ParticipanteService(participanteDaoMock);
}

    @Test
    public void testListarTodos() {
        Participante p1 = new Participante(1, "João", "M", "joao@email.com", "9999-9999");
        Participante p2 = new Participante(2, "Maria", "F", "maria@email.com", "8888-8888");
        when(participanteDaoMock.listarTodos()).thenReturn(Arrays.asList(p1, p2));

        List<Participante> participantes = participanteService.listarTodos();

        assertEquals(2, participantes.size());
        assertEquals("João", participantes.get(0).getNome());
        verify(participanteDaoMock).listarTodos();
    }

    @Test
    public void testBuscarPorId() {
        Participante participante = new Participante(1, "João", "M", "joao@email.com", "9999-9999");
        when(participanteDaoMock.buscarPorId(1)).thenReturn(participante);

        Participante resultado = participanteService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals("João", resultado.getNome());
        verify(participanteDaoMock).buscarPorId(1);
    }

    @Test
    public void testBuscarPorEmail() {
        Participante participante = new Participante(1, "João", "M", "joao@email.com", "9999-9999");
        when(participanteDaoMock.buscarPorEmail("joao@email.com")).thenReturn(participante);

        Participante resultado = participanteService.buscarPorEmail("joao@email.com");

        assertEquals("João", resultado.getNome());
        verify(participanteDaoMock).buscarPorEmail("joao@email.com");
    }

    @Test
    public void testInserir() {
        when(participanteDaoMock.inserir("Ana", "F", "ana@email.com", "7777-7777")).thenReturn("sucesso");

        String resultado = participanteService.inserir("Ana", "F", "ana@email.com", "7777-7777");

        assertEquals("sucesso", resultado);
        verify(participanteDaoMock).inserir("Ana", "F", "ana@email.com", "7777-7777");
    }

    @Test
    public void testAtualizarParticipante() {
        when(participanteDaoMock.atualizarParticipante(1, "Ana", "F", "ana@email.com", "7777-7777"))
            .thenReturn("Dados atualizados com sucesso!");

        String resultado = participanteService.atualizarParticipante(1, "Ana", "F", "ana@email.com", "7777-7777");

        assertEquals("Dados atualizados com sucesso!", resultado);
        verify(participanteDaoMock).atualizarParticipante(1, "Ana", "F", "ana@email.com", "7777-7777");
    }
}
