package service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

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

    public String inserir(String nome, String sexo, String email, String celular, String senha, String tipo) {
        if (dao.emailJaExiste(email)) {
            return "E-mail já cadastrado.";
        }
        String senhaCriptografada = BCrypt.hashpw(senha, BCrypt.gensalt());
        String resultado = dao.inserir(nome, sexo, email, celular, senhaCriptografada, tipo);
        if (!"sucesso".equalsIgnoreCase(resultado)) {
            return "Erro ao inserir no banco.";
        }
        return null;
    }

<<<<<<< HEAD
    public String inserir(String nome, String sexo, String email, String celular){
        return inserir(nome, sexo, email, celular, senha,"participante");
=======
    public String inserir(String nome, String sexo, String email, String celular, String senha) {
        return inserir(nome, sexo, email, celular, senha, "participante");
>>>>>>> 4775caef788fb4474f2e525b4025b747670a3af9
    }

    public Participante login(String email, String senha) {
        if (email.equalsIgnoreCase("adm@empresa.com") && senha.equals("organizador123")) {
            Participante u = new Participante();
            u.setNome("Administrador");
            u.setEmail(email);
            u.setTipo("organizador");
            return u;
        }
        return dao.login(email, senha);
    }

    public String atualizarParticipante(int idParticipante, String novoNome, String novoSexo, String novoEmail,
            String novoTelefone, String novaSenha) {
        return this.dao.atualizarParticipante(idParticipante, novoNome, novoSexo, novoEmail, novoTelefone, novaSenha);
    }

    public String atualizarParticipante(int i, String string, String string2, String string3, String string4) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizarParticipante'");
    }
}
