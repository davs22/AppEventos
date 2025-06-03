package service;

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

    public List<Palestrante> listarPorParamentro(String nome, String sexo) {
        return this.dao.listarPorParametro(nome, sexo);
    }

    public Palestrante buscarPorId(Integer id) {
        return this.dao.buscarPorId(id);
    }

    public Palestrante buscarPorCurriculo(String curriculo) {
        return this.dao.buscarPorCurriculo(curriculo);
    }

    public Palestrante buscarPorAreaAtuacao(String areaAtuacao) {
        return this.dao.buscarPorAreaAtuacao(areaAtuacao);
    }

    public String inserir(String nome, String curriculo, String areaAtuacao) {
        return this.dao.inserir(nome, curriculo, areaAtuacao);
    }

    public String excluirPalestrante(Integer id) {
        return dao.excluir(id);
    }

    public String atualizarPalestrante(int idPalestrante, String novoNome, String novoCurriculo, String novaAreaAtuacao) {
        return this.dao.atualizarPalestrante(idPalestrante, novoNome, novoCurriculo, novaAreaAtuacao);
    }
}
