import java.beans.Transient;
import java.time.LocalTime;;

public class PalestraTest{

<<<<<<< HEAD
    //@Test
    public void criarPalestra(){
        System.out.println("salamaleiko se loko falta algumas coisas");
    } 
}
=======
    @Test
    void criarPalestracomHorário() {
      Palestra palestra = new Palestra("Java Avançado", LocalTime.of(10,0), LocalTime.of(12,0));
         assertEquals("Java Avançado", palestra.getTitulo());
         assertEquals(LocalTime.of(10,0),palestra.getInicio());
         assertEquals(LocalTime.of(12,0),palestra.getFim());
    }
    
    @Test
    void LancarHorárioVálido {
      assertThrows(IlegalArgumentException.class, () -> new Palestra("Spring Boot", LOcalTime));
   }
}  
>>>>>>> 09bf6fbc3c1eb5a44a1139589b313ba8a101714c
