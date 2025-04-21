import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParticipanteTest {

    @Test
    void CriarParticipante() {
        Participante participante = new Participante();
        assertEquals("João", participante.getNome());
        assertEquals("João@gmail.com", participante.getemail());
    }
}