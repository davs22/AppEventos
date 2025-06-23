package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.InscricaoDao;
import service.InscricaoService;
import service.ParticipanteService;
import service.EventosService;

public class InscricaoServiceTest {

    private InscricaoDao daoMock;
    private ParticipanteService participanteServiceMock;
    private EventosService eventosServiceMock;
    private InscricaoService inscricaoService;
    
    @BeforeEach
    public void setup() {
        daoMock = mock(InscricaoDao.class);
        participanteServiceMock = mock(ParticipanteService.class);
        eventosServiceMock = mock(EventosService.class);

        inscricaoService = new InscricaoService(daoMock, participanteServiceMock, eventosServiceMock);

    }

    @Test 
    public void testInscreverParticipante_Sucesso() {
        int idParticipante = 1;
        int idEvento = 10;

        when(participanteServiceMock.participanteExiste(idParticipante)).thenReturn(true);
        when(eventosServiceMock.eventoExiste(idEvento)).thenReturn(true);
        when(daoMock.verificarInscricao(idParticipante, idEvento)).thenReturn(false);
        when(daoMock.inscreverParticipante(idParticipante, idEvento)).thenReturn("Inscrição realizada com sucesso!");
        
        String resultado = inscricaoService.inscreverParticipante(idParticipante, idEvento);

        assertEquals("Inscrição realizada com sucesso!", resultado);

        verify(participanteServiceMock).participanteExiste(idParticipante);
        verify(eventosServiceMock).eventoExiste(idEvento);
        verify(daoMock).verificarInscricao(idParticipante, idEvento);
        verify(daoMock).inscreverParticipante(idParticipante, idEvento);

    }
}
