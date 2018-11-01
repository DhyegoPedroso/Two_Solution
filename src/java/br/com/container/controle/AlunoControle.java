package br.com.container.controle;

import br.com.container.dao.AlunoDao;
import br.com.container.dao.AlunoDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.Aluno;
import br.com.container.modelo.Endereco;
import java.io.Serializable;
import java.util.ArrayList;
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
@ManagedBean(name = "alunoC")
@ViewScoped
public class AlunoControle implements Serializable {

    private boolean mostraToolbar = false;
    private String pesqNome = "";
    private String pesqCpf = "";

    private Session session;
    private AlunoDao dao;

    private Aluno aluno;
    private Endereco endereco;
    private List<Aluno> alunos;
    private DataModel<Aluno> modelAlunos;

    private void abreSessao() {
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
    }

    public void mudaToolbar() {
        aluno = new Aluno();
        aluno.setWhatsapp(true);
        alunos = new ArrayList();
        pesqNome = "";
        pesqCpf = "";
        mostraToolbar = !mostraToolbar;
    }

    public void pesquisar() {
        dao = new AlunoDaoImpl();
        try {
            abreSessao();

            if (!pesqNome.equals("")) {
                alunos = dao.pesquisaPorNome(pesqNome, session);
            } else if (!pesqCpf.equals("")) {
                alunos = dao.pesqPorCpf(pesqCpf, session);
            } else {
                Mensagem.mensagemError("Erro ao Pesquisar\num dos campos abaixo é obrigatorio");
            }

            modelAlunos = new ListDataModel(alunos);
            pesqNome = null;
            pesqCpf = null;
        } catch (HibernateException ex) {
            System.err.println("Erro pesquisa Aluno:\n" + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void salvar() {
        dao = new AlunoDaoImpl();
        abreSessao();
        try {
            aluno.setEndereco(endereco);
            endereco.setPessoa(aluno);
            dao.salvarOuAlterar(aluno, session);
            Mensagem.salvar("Aluno " + aluno.getNome());
            aluno = null;
            endereco = null;
        } catch (HibernateException ex) {
            Mensagem.mensagemError("Erro ao salvar\nTente novamente");
            System.err.println("Erro pesquisa aluno:\n" + ex.getMessage());
        } finally {
            aluno = new Aluno();
            endereco = new Endereco();
            aluno.setWhatsapp(true);
            session.close();
        }
    }

    public void alterarAluno() {
        mostraToolbar = !mostraToolbar;
        aluno = modelAlunos.getRowData();
        endereco = aluno.getEndereco();
    }

    public void excluir() {
        aluno = modelAlunos.getRowData();
        dao = new AlunoDaoImpl();
        try {
            abreSessao();
            dao.remover(aluno, session);
            Mensagem.excluir("Aluno " + aluno.getNome());
            aluno = new Aluno();
        } catch (Exception ex) {
            System.err.println("Erro ao excluir aluno:\n" + ex.getMessage());
        } finally {
            session.close();
        }
    }

    //Getters e Setters
    public boolean isMostraToolbar() {
        return mostraToolbar;
    }

    public void setMostraToolbar(boolean mostraToolbar) {
        this.mostraToolbar = mostraToolbar;
    }

    public String getPesqNome() {
        return pesqNome;
    }

    public void setPesqNome(String pesqNome) {
        this.pesqNome = pesqNome;
    }

    public String getPesqCpf() {
        return pesqCpf;
    }

    public void setPesqCpf(String pesqCpf) {
        this.pesqCpf = pesqCpf;
    }

    public Aluno getAluno() {
        if (aluno == null) {
            aluno = new Aluno();
            aluno.setWhatsapp(true);
        }
        return aluno;
    }

    public void setProf(Aluno aluno) {
        this.aluno = aluno;
    }

    public List<Aluno> getAlunos() {
        if (alunos == null) {
            alunos = new ArrayList();
        }
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public DataModel<Aluno> getModelAlunos() {
        return modelAlunos;
    }

    public void setModelAlunos(DataModel<Aluno> modelAlunos) {
        this.modelAlunos = modelAlunos;
    }

    public Endereco getEndereco() {
        if (endereco == null) {
            endereco = new Endereco();
        }
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
