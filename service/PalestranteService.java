package service;

import java.util.List;

import dao.PalestranteDao;
import table.palestrante;

public class PalestranteService {
    private PalestranteDao dao;

    public PalestranteService() {
        this.dao = new PalestranteDao();
    }

    public List<palestrante> listarTodos() {
        return this.dao.listarTodos();
    }

    public List<palestrante> listarPorParamentro(String nome, String sexo) {
        return this.dao.listarPorParametro(nome, sexo);
    }

    public palestrante buscarPorId(Integer id) {
        return this.dao.buscarPorId(id);
    }

    public palestrante buscarPorCurriculo(String curriculo) {
        return this.dao.buscarPorCurriculo(curriculo);
    }

    public palestrante buscarPorAreaAtuacao(String areaAtuacao) {
        return this.dao.buscarPorAreaAtuacao(areaAtuacao);
    }

    public String inserir(String nome, String curriculo, String areadeatuacao) {
        return this.dao.inserir(nome, curriculo, areadeatuacao);
    }
}
