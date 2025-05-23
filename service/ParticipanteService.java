package service;

import java.util.List;

import dao.ParticipanteDao;
import table.Participante;

public class ParticipanteService {
    
    private ParticipanteDao dao;
    
    public ParticipanteService(ParticipanteDao dao) {
        this.dao = dao;
    }
    
    public ParticipanteService() {
        this.dao = new ParticipanteDao();
    }

    public List<Participante> listarTodos() {
        return this.dao.listarTodos();
    }

    public List<Participante> listarPorParamentro(String nome, String sexo) {
        return this.dao.listarPorParametro(nome, sexo);
    }

    public Participante buscarPorId(Integer id) {
        return this.dao.buscarPorId(id);
    }

    public Participante buscarPorEmail(String email) {
        return this.dao.buscarPorEmail(email);
    }

    public Participante buscarPorCelular(String celular) {
        return this.dao.buscarPorCelular(celular);
    }

    public String inserir(String nome, String sexo, String email, String celular) {
        return this.dao.inserir(nome, sexo, email, celular);
    }

    public String atualizarParticipante(int idParticipante, String novoNome, String novoSexo, String novoEmail, String novoTelefone) {
        return this.dao.atualizarParticipante(idParticipante, novoNome, novoSexo, novoEmail, novoTelefone);
    }
}
