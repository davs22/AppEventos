package service;

import java.sql.SQLException;
import java.util.List;
import dao.PalestranteDao;
import table.Palestrante;

public class PalestranteService {
    private PalestranteDao dao;

    public PalestranteService() {
        this.dao = new PalestranteDao();
    }

    public PalestranteService(PalestranteDao dao) {
        this.dao = dao;
    }

    public List<Palestrante> listarTodos() {
        return this.dao.listarTodos();
    }
    public List<Palestrante> listarPorParametro(String tipo, String valor) {
        return this.dao.listarPorParametro(tipo, valor);
    }

    public Palestrante buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            System.err.println("ID inválido para busca.");
            return null;
        }
        return this.dao.buscarPorId(id);
    }

    public Palestrante buscarPorCurriculo(String curriculo) {
        if (curriculo == null || curriculo.isBlank()) {
            System.err.println("Currículo inválido para busca.");
            return null;
        }
        return this.dao.buscarPorCurriculo(curriculo);
    }

    public Palestrante buscarPorAreaAtuacao(String areaAtuacao) {
        if (areaAtuacao == null || areaAtuacao.isBlank()) {
            System.err.println("Área de atuação inválida para busca.");
            return null;
        }
        return this.dao.buscarPorAreaAtuacao(areaAtuacao);
    }

    public String inserir(String nome, String curriculo, String areaAtuacao) {
        if (nome == null || nome.isBlank() ||
            curriculo == null || curriculo.isBlank() ||
            areaAtuacao == null || areaAtuacao.isBlank()) {
            return "Erro: todos os campos são obrigatórios.";
        }
        return this.dao.inserir(nome, curriculo, areaAtuacao);
    }

    public boolean excluirPalestrante(int id) {
        return this.dao.excluirPalestrantePorId(id);
    }

    public String atualizarPalestrante(int idPalestrante, String novoNome, String novoCurriculo, String novaAreaAtuacao) {
        if (idPalestrante <= 0 ||
            novoNome == null || novoNome.isBlank() ||
            novoCurriculo == null || novoCurriculo.isBlank() ||
            novaAreaAtuacao == null || novaAreaAtuacao.isBlank()) {
            return "Erro: todos os campos são obrigatórios e válidos para atualização.";
        }
        if (!palestranteExiste(idPalestrante)) {
            return "Erro: palestrante com ID " + idPalestrante + " não encontrado.";
        }
        return this.dao.atualizarPalestrante(idPalestrante, novoNome, novoCurriculo, novaAreaAtuacao);
    }

    public boolean palestranteExiste(int palestranteId) {
        if (palestranteId <= 0) {
            System.err.println("ID inválido para verificação.");
            return false;
        }
        try {
            return this.dao.palestranteExiste(palestranteId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Palestrante buscarPalestrantePorId(int id) throws SQLException {
        List<Palestrante> palestrantesEncontrados = dao.listarPorParametro("id", String.valueOf(id));
        if (palestrantesEncontrados != null && !palestrantesEncontrados.isEmpty()) {
            return palestrantesEncontrados.get(0);
        }
        return null;
    }
}
