import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParticipanteTest {

    @Test
    void CriarParticipanteEmailValido() {
        Participante participante = new Participante();
        assertEquals("João", participante.getNome());
        assertEquals("João@email.com", participante.getemail());
    }

    @Test
    void CriarExcecaoParticipanteInvalido () {
        assertThrows(IlegalArgumentException.class, () --> new Participante("Ana","anaemail.com"));
    }
}