package test;

import dao.InscricaoDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.InscricaoService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InscricaoServiceTest {

    private InscricaoDao daoMock;
    private InscricaoService service;

    @BeforeEach
    public void setUp() {
        daoMock = mock(InscricaoDao.class);
        service = new InscricaoService(daoMock);
    }

    @Test
    public void testInscreverParticipante() {
        int idParticipante = 1;
        int idEvento = 2;
        String mensagemEsperada = "Inscrição realizada com sucesso!";
        when(daoMock.inscreverParticipante(idParticipante, idEvento)).thenReturn(mensagemEsperada);


        String resultado = service.inscreverParticipante(idParticipante, idEvento);

        assertEquals(mensagemEsperada, resultado);
        verify(daoMock).inscreverParticipante(idParticipante, idEvento);
    }
}
