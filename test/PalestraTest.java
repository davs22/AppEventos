import java.time.LocalTime;
import org.juni.jupiter.api.Test;
imoort static org.junit.jupiter.api.Assertions.*;
import table.Palestrante.

public class PalestraTest{

    @Test
    void criarPalestracomHorário() {
      Palestra palestra = new Palestra("Java Avançado", LocalTime.of(10,0), LocalTime.of(12,0));
         assertEquals("Java Avançado", palestra.getTitulo());
         assertEquals(LocalTime.of(10,0),palestra.getInicio());
         assertEquals(LocalTime.of(12,0),palestra.getFim());
    }
    
    @Test
    void LancarHorárioVálido {
      assertThrows(IllegalArgumentException.class, () -> new Palestra("Spring Boot", LocalTime));
   }
}  
