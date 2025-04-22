import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Asseetions;
import table.Inscricao;

public class Inscricao {

    @Test
    void confirmarInscricaoComSucesso() {
      Inscricao inscricao = new Inscricao ();
      AssertTrue(inscricao.inscricaoComSucesso());
}

    @Test
    void lancarExcecoesSeEventoOuParticipanteForNulo() {
    assertThrows(IllegalArgumentException.class, () -> new Inscricao)
    assertThrows(IllegalArgumentException.class, () -> new Inscricao)
  }
}