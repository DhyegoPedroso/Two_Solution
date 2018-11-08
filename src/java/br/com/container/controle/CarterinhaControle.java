package br.com.container.controle;

import br.com.container.dao.AlunoDaoImpl;
import br.com.container.dao.CarterinhaDao;
import br.com.container.dao.CarterinhaDaoImpl;
import br.com.container.dao.CursoDao;
import br.com.container.dao.CursoDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.Aluno;
import br.com.container.modelo.Carterinha;
import br.com.container.modelo.Curso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author cel05
 */
@ManagedBean(name = "carterinhaC")
@ViewScoped
public class CarterinhaControle implements Serializable {

    private Session sessao;

    private CarterinhaDao carterinhaDao;
    private Carterinha carterinha;
    private Curso curs;
    private Aluno aluno;

    private DataModel<Carterinha> modelCarterinhas;
    private List<Carterinha> carterinhas;
    private DataModel<Aluno> modelAlunos;
    private List<Aluno> alunos;
    private List<SelectItem> cursos;

    private boolean mostra_toolbar;
    private String pesqNome = "";
    private String pesqMatricula = "";
    private String pesqAluno = "";

    @PostConstruct
    public void inicializar() {
        carregaComboCurso();
    }

    public CarterinhaControle() {
        carterinhaDao = new CarterinhaDaoImpl();
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
        limpar();
        gerarValidade();
    }

    public void novaPesquisa() {
        mostra_toolbar = !mostra_toolbar;
        limpar();
    }

    public void preparaAlterar() {
        mostra_toolbar = !mostra_toolbar;
        limpar();
    }

    public void carregarParaAlterar() {
        isMostra_toolbar();
        carterinha = modelCarterinhas.getRowData();
        curs = carterinha.getCurso();
    }

    public void carregarAluno() {
        mostra_toolbar = !mostra_toolbar;
        aluno = modelAlunos.getRowData();
    }

    public void pesquisar() {
        try {
            abreSessao();

            if (!pesqNome.equals("") || !pesqMatricula.equals("")) {
                carterinhas = carterinhaDao.pesqPorAlunoOuMatricula(pesqNome, pesqMatricula, sessao);
            } else {
                Mensagem.mensagemError("Erro ao pesquisar um dos campos abaixo é obrigatorio");
            }

        } catch (HibernateException e) {
            System.err.println("Erro ao pesquisar Carterinha");
        } finally {
            sessao.close();
        }
    }

    public void pesquisarAluno() {
        AlunoDaoImpl alunoDaoImpl = new AlunoDaoImpl();
        try {
            abreSessao();

            if (!pesqAluno.equals("")) {
                alunos = alunoDaoImpl.pesquisaPorNome(pesqAluno, sessao);
            } else {
                Mensagem.mensagemError("Erro ao pesquisar o campo abaixo é obrigatorio");
            }

        } catch (HibernateException e) {
            System.err.println("Erro ao pesquisar Aluno");
        } finally {
            sessao.close();
        }
    }

    public void limpar() {
        carterinha = new Carterinha();
        curs = new Curso();
    }

  

    public void excluir() {
        carterinha = modelCarterinhas.getRowData();
        try {
            abreSessao();
            carterinhaDao.remover(carterinha, sessao);
            Mensagem.excluir("Carterinha do aluno " + carterinha.getAluno().getNome());
            carterinha = new Carterinha();
        } catch (HibernateException e) {
            System.err.println("Erro ao excluir a carterinha");
        } finally {
            sessao.close();
        }
    }

    public void salvar() {
        try {
            abreSessao();
            carterinha.setCurso(curs);
            carterinhaDao.salvarOuAlterar(carterinha, sessao);
            Mensagem.salvar("Cartetinha ");
            carterinha = null;
            curs = null;
            gerarValidade();
        } catch (HibernateException e) {
            System.err.println("Erro ao salvar Carterinha");
        } finally {
            sessao.close();
        }
        limpar();
    }

    public void gerarValidade() {
        Date data;
        Calendar validade = Calendar.getInstance();
        validade.set(Calendar.YEAR, validade.get(Calendar.YEAR) + 1);
        data = validade.getTime();
        carterinha.setValidade(data);
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

    //getters e setters
    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }

    public String getPesqNome() {
        return pesqNome;
    }

    public void setPesqNome(String pesqNome) {
        this.pesqNome = pesqNome;
    }

    public String getPesqMatricula() {
        return pesqMatricula;
    }

    public void setPesqMatricula(String pesqMatricula) {
        this.pesqMatricula = pesqMatricula;
    }

    public Carterinha getCarterinha() {
        if (carterinha == null) {
            carterinha = new Carterinha();
        }
        return carterinha;
    }

    public void setCarterinha(Carterinha carterinha) {
        this.carterinha = carterinha;
    }

    public Curso getCurs() {
        if (curs == null) {
            curs = new Curso();
        }
        return curs;
    }

    public void setCurs(Curso curs) {
        this.curs = curs;
    }

    public Aluno getAluno() {
        if (aluno == null) {
            aluno = new Aluno();
        }
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public DataModel<Carterinha> getModelCarterinhas() {
        return modelCarterinhas;
    }

    public void setModelCarterinhas(DataModel<Carterinha> modelCarterinhas) {
        this.modelCarterinhas = modelCarterinhas;
    }

    public List<Carterinha> getCarterinhas() {
        return carterinhas;
    }

    public DataModel<Aluno> getModelAlunos() {
        return modelAlunos;
    }

    public void setModelAlunos(DataModel<Aluno> modelAlunos) {
        this.modelAlunos = modelAlunos;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public List<SelectItem> getCursos() {
        return cursos;
    }

    public void setCursos(List<SelectItem> cursos) {
        this.cursos = cursos;
    }

    public String getPesqAluno() {
        return pesqAluno;
    }

    public void setPesqAluno(String pesqAluno) {
        this.pesqAluno = pesqAluno;
    }

}
