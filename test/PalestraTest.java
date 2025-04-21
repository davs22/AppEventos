import java.time.LocalTime;;

public class PalestraTest{

    @Test
    void criarPalestracomHorário() {
    Palestra palestra = new Palestra("Java Avançado", LocalTime.of(10,0), LocalTime.of(12,0));
    assertEquals("Java Avançado". palestra.getTitulo());
    assertEquals(LocalTime.of(10,0),palestra.getInicio());
    assertEquals(LocalTime.of(12,0),palestra.getFim());
    }
}  
