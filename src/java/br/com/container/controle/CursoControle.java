/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.CursoDao;
import br.com.container.dao.CursoDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.Curso;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author cel05
 */
@ManagedBean(name = "cursoC")
@ViewScoped
public class CursoControle implements Serializable {

    
    private Curso curso;
    private CursoDao cursoDao;
    private Session sessao;
    private DataModel<Curso> modelCursos;
    private List<Curso> cursos;
    private boolean mostra_toolbar;

    private void abreSessao() {
        if (sessao == null) {
            sessao = HibernateUtil.abreSessao();
        } else if (!sessao.isOpen()) {
            sessao = HibernateUtil.abreSessao();
        }
    }

    public void novo() {
        mostra_toolbar = !mostra_toolbar;
    }

    public void novaPesquisa() {
        mostra_toolbar = !mostra_toolbar;
    }

    public void preparaAlterar() {
        mostra_toolbar = !mostra_toolbar;
    }

    public void pesquisar() {
        cursoDao = new CursoDaoImpl();
        try {
            abreSessao();
            cursos = cursoDao.pesquisaPorNome(curso.getNome(), sessao);
            modelCursos = new ListDataModel(cursos);
            curso.setNome(null);
        } catch (Exception e) {
            System.out.println("erro ao pesquisar curso por nome: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limpar() {
        curso = new Curso();
    }

    public void carregarParaAlterar() {
        mostra_toolbar = !mostra_toolbar;
        curso = modelCursos.getRowData();
    }

    public void excluir() {
        curso = modelCursos.getRowData();
        cursoDao = new CursoDaoImpl();
        abreSessao();
        try {
            cursoDao.remover(curso, sessao);
            cursos.remove(curso);
            modelCursos = new ListDataModel(cursos);
            Mensagem.excluir("Curso");
            limpar();
        } catch (Exception e) {
            System.out.println("erro ao excluir curso: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void salvar() {
        cursoDao = new CursoDaoImpl();
        abreSessao();
        try {
            cursoDao.salvarOuAlterar(curso, sessao);
            Mensagem.salvar("Curso " + curso.getNome());
            curso = null;
        } catch (HibernateException e) {
            System.out.println("Erro ao salvar curso " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro no salvar curso " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limparTela() {
        limpar();
    }

    //getters e setters
    public Curso getCurso() {
        if (curso == null) {
            curso = new Curso();
        }
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public DataModel<Curso> getModelCursos() {
        return modelCursos;
    }

    public void setModelCursos(DataModel<Curso> modelCursos) {
        this.modelCursos = modelCursos;
    }

    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }

    public List<Curso> getCursos() {
        return cursos;
    }
}
