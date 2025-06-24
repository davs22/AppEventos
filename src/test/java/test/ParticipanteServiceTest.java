package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import service.ParticipanteService;
import table.Participante;

import java.util.List;

public class ParticipanteServiceTest {

    private ParticipanteService participanteService;
    private static final String EMAIL_TESTE = "teste@example.com";

    @BeforeEach
    public void setup() {
        participanteService = new ParticipanteService();

        // Garantir que não exista o usuário de testes
        List<Participante> existentes = participanteService.listarPorParamentro("email", EMAIL_TESTE);
        for (Participante p : existentes) {
            participanteService.excluirParticipante(p.getId());
        }
    }

    @Test
    public void testInserirParticipante() {
        String resultado = participanteService.inserir(
            "João Teste", "M", EMAIL_TESTE, "999999999", "senha123", "participante"
        );

        assertEquals("sucesso", resultado);

        Participante p = participanteService.buscarPorEmail(EMAIL_TESTE);
        assertNotNull(p);
        assertEquals("João Teste", p.getNome());
    }

    @Test
    public void testLoginParticipante() {
        participanteService.inserir("Maria Teste", "F", "maria@test.com", "988888888", "minhasenha", "participante");

        Participante login = participanteService.login("maria@test.com", "minhasenha");

        assertNotNull(login);
        assertEquals("maria@test.com", login.getEmail());
        assertEquals("participante", login.getTipo());
    }

    @Test
    public void testAtualizarParticipante() {
        participanteService.inserir("Carlos", "M", "carlos@teste.com", "977777777", "1234", "participante");
        Participante p = participanteService.buscarPorEmail("carlos@teste.com");

        String resultado = participanteService.atualizarParticipante(
            p.getId(), "Carlos Atualizado", "M", "carlos@teste.com", "977777777", ""
        );

        assertTrue(resultado.toLowerCase().contains("sucesso"));

        Participante atualizado = participanteService.buscarPorEmail("carlos@teste.com");
        assertEquals("Carlos Atualizado", atualizado.getNome());
    }

    @Test
    public void testExcluirParticipante() {
        participanteService.inserir("Ana", "F", "ana@teste.com", "966666666", "senha", "participante");
        Participante p = participanteService.buscarPorEmail("ana@teste.com");

        boolean excluido = participanteService.excluirParticipante(p.getId());
        assertTrue(excluido);

        Participante excluidoBusca = participanteService.buscarPorEmail("ana@teste.com");
        assertNull(excluidoBusca.getId());
    }
}
