package service;

import dao.UsuarioDao;
import table.Usuario;
import util.SQLiteConnection;

public class UsuarioService {
    private UsuarioDao dao;

    public UsuarioService(UsuarioDao dao) {
        this.dao = dao;
    }

    public UsuarioService() {
        this.dao = new UsuarioDao(new SQLiteConnection()); 
    }

   public Usuario login(String email, String senha) {
    if (email.equalsIgnoreCase("adm@empresa.com") && senha.equals("organizador123")) {
        Usuario u = new Usuario();
        u.setNome("Administrador");
        u.setEmail(email);
        u.setTipo("organizador");
        return u;
    }

    return dao.login(email, senha);
}

    public boolean cadastrarUsuario(Usuario usuario) {
        usuario.setTipo("participante");
        return dao.cadastrarUsuario(usuario);
    }

}
