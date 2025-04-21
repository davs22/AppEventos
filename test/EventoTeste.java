public class EventoTeste {
    
    @Test
    void criarEventoDadosValidos{
        Evento evento =new evento ("Dev Summit", "online");
        assertEquals("Dev Summit", evento.getNome());
        assertEquals("online", evento.getLocal());
    }
}
