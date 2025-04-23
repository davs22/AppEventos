package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dao.InscricaoDao;
import service.InscricaoService;
import table.Inscricao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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
        when(daoMock.inscreverParticipante(1, 100)).thenReturn("Inscrição realizada com sucesso!");

        String resultado = service.inscreverParticipante(1, 100);
        assertEquals("Inscrição realizada com sucesso!", resultado);
    }

    @Test
    public void testListarInscricoesPorParticipante() {
        Inscricao i1 = new Inscricao(1, 1, 100);
        Inscricao i2 = new Inscricao(2, 1, 101);
        when(daoMock.listarInscricoesPorParticipante(1)).thenReturn(Arrays.asList(i1, i2));

        List<Inscricao> resultado = service.listarInscricoesPorParticipante(1);

        assertEquals(2, resultado.size());
        assertEquals(100, resultado.get(0).getIdEvento());
    }

    @Test
    public void testVerificarInscricao() {
        when(daoMock.verificarInscricao(1, 100)).thenReturn(true);

        boolean resultado = service.verificarInscricao(1, 100);
        assertTrue(resultado);
    }

    @Test
    public void testExcluirInscricao() {
        when(daoMock.excluirInscricao(1, 100)).thenReturn("Inscrição excluída com sucesso!");

        String resultado = service.excluirInscricao(1, 100);
        assertEquals("Inscrição excluída com sucesso!", resultado);
    }

    @Test
    public void testSolicitarCertificado() {
        String certMsg = "CERTIFICADO";
        when(daoMock.solicitarCertificado(1, 100)).thenReturn(certMsg);

        String resultado = service.solicitarCertificado(1, 100);
        assertEquals(certMsg, resultado);
    }
}
