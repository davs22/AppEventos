package service;

import java.sql.SQLException;
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

    public List<Participante> listarPorParamentro(String tipo, String valor) {
        return this.dao.listarPorParametro(tipo, valor);
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
        try {
            if (dao.emailJaExiste(email)) {
                return "email_existe";
            }

            String senhaCriptografada = BCrypt.hashpw(senha, BCrypt.gensalt());

            String resultado = dao.inserir(nome, sexo, email, celular, senhaCriptografada, tipo);
            if (!"sucesso".equalsIgnoreCase(resultado)) {
                return "erro_insercao";
            }

            return "sucesso";

        } catch (Exception e) {
            e.printStackTrace(); 
            return "erro_excecao";
        }
    }

    public String inserir(String nome, String sexo, String email, String celular, String senha) {
        return inserir(nome, sexo, email, celular, senha, "participante");

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

    try {
        Participante participanteAtual = this.dao.buscarPorId(idParticipante);

        String senhaParaSalvar;
        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            senhaParaSalvar = participanteAtual.getSenha();
        } else {
            senhaParaSalvar = BCrypt.hashpw(novaSenha, BCrypt.gensalt()); 
        }

        return this.dao.atualizarParticipante(idParticipante, novoNome, novoSexo, novoEmail, novoTelefone, senhaParaSalvar);
    } catch (Exception e) {
        e.printStackTrace();
        return "Erro ao atualizar dados.";
    }
}


    public boolean participanteExiste(int participanteId) {
        try {
            return this.dao.participanteExiste(participanteId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Participante buscarParticipantePorId(int id) throws SQLException {
       
        List<Participante> participanteEncontrados = dao.listarPorParametro("id", String.valueOf(id));
        if (participanteEncontrados != null && !participanteEncontrados.isEmpty()) {
            return participanteEncontrados.get(0); 
        }
        return null; 
    }

    public boolean excluirParticipante(int id) {
    return this.dao.excluirParticipantePorId(id);
}

    public String criptografarSenha(String senhaPura) {
        return BCrypt.hashpw(senhaPura, BCrypt.gensalt());
    }

    public String buscarNomePorId(int idParticipante) {
        Participante p = dao.buscarPorId(idParticipante);
        return p != null ? p.getNome() : "Desconhecido";
    }

}
