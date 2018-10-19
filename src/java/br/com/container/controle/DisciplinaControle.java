/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.CursoDao;
import br.com.container.dao.CursoDaoImpl;
import br.com.container.dao.DisciplinaDao;
import br.com.container.dao.DisciplinaDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.Curso;
import br.com.container.modelo.Disciplina;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author felipedania
 */
@ManagedBean(name = "disciplinaC")
@ViewScoped
public class DisciplinaControle implements Serializable{
    
    private Disciplina disciplina;
    private DisciplinaDao disciplinaDao;
    private Curso curso;
    private Session sessao;
    private DataModel<Disciplina> modelDisciplinas;
    private List<Disciplina> disciplinas;
    private List<SelectItem> cursos;
    private boolean mostra_toolbar;

    @PostConstruct
    public void inicializar() {
        carregaComboCurso();
    }

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
        disciplinaDao = new DisciplinaDaoImpl();
        try {
            abreSessao();
            disciplinas = disciplinaDao.pesquisaPorNome(disciplina.getNome(), sessao);
            modelDisciplinas = new ListDataModel(disciplinas);
            disciplina.setNome(null);
        } catch (Exception e) {
            System.out.println("erro ao pesquisar usuário por nome: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limpar() {
        disciplina = new Disciplina();
        curso = new Curso();
    }

    public void carregarParaAlterar() {
        mostra_toolbar = !mostra_toolbar;
        disciplina = modelDisciplinas.getRowData();
        curso = disciplina.getCurso();
    }

    public void excluir() {
        disciplina = modelDisciplinas.getRowData();
        disciplinaDao = new DisciplinaDaoImpl();
        abreSessao();
        try {
            disciplinaDao.remover(disciplina, sessao);
            disciplinas.remove(disciplina);
            modelDisciplinas = new ListDataModel(disciplinas);
            Mensagem.excluir("Disciplina");
            limpar();
        } catch (Exception e) {
            System.out.println("erro ao excluir: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void salvar() {
        disciplina.setCurso(curso);
        disciplinaDao = new DisciplinaDaoImpl();
        abreSessao();
        try {
            disciplinaDao.salvarOuAlterar(disciplina, sessao);
            Mensagem.salvar("Disciplina " + disciplina.getNome());
            disciplina = null;
            curso = null;

        } catch (HibernateException e) {
            System.out.println("Erro ao salvar Disciplina " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro no salvar Disciplina Controle "
                    + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    private void carregaComboCurso() {
        List<Curso> todosPerfis;
        try {
            abreSessao();
            cursos = new ArrayList();

            CursoDao cursoDao = new CursoDaoImpl();
            todosPerfis = cursoDao.listaTodos(sessao);
            todosPerfis.stream().forEach((perf) -> {
                cursos.add(new SelectItem(perf.getId(), perf.getCurso()));
            });
        } catch (HibernateException hi) {
            System.out.println("Erro ao carregar combo curso " + hi.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limparTela() {
        limpar();
    }

    //getters e setters
    public Disciplina getDisciplina() {
        if (disciplina == null) {
            disciplina = new Disciplina();
        }
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Curso getCurso() {
        if (curso == null) {
            curso = new Curso();
        }
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public DataModel<Disciplina> getModelDisciplinas() {
        return modelDisciplinas;
    }

    public void setModelDisciplinas(DataModel<Disciplina> modelDisciplinas) {
        this.modelDisciplinas = modelDisciplinas;
    }

    public List<SelectItem> getCursos() {
        return cursos;
    }

    public void setCursos(List<SelectItem> cursos) {
        this.cursos = cursos;
    }

    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }
    
    

}
