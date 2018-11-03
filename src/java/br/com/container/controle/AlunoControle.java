package br.com.container.controle;

import br.com.container.dao.AlunoDao;
import br.com.container.dao.AlunoDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.Aluno;
import br.com.container.modelo.Endereco;
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
@ManagedBean(name = "alunoC")
@ViewScoped
public class AlunoControle implements Serializable {

    private Aluno aluno;
    private AlunoDao alunoDao;
    private Endereco endereco;
    private Session sessao;
    private DataModel<Aluno> modelAlunos;
    private List<Aluno> alunos;
    private boolean mostra_toolbar;
    private String pesqNome = "";
    private String pesqCpf = "";

    public AlunoControle() {
        alunoDao = new AlunoDaoImpl();
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
        mostra_toolbar = !mostra_toolbar;
        aluno = modelAlunos.getRowData();
        endereco = aluno.getEndereco();
    }

    public void pesquisar() {
        alunoDao = new AlunoDaoImpl();
        try {
            abreSessao();

            if (!pesqNome.equals("") || !pesqCpf.equals("")) {
                alunos = alunoDao.pesqPorNomeOuCpf(pesqNome, pesqCpf, sessao);
            } else {
                Mensagem.mensagemError("Erro ao Pesquisar\num dos campos abaixo é obrigatorio");
            }

            modelAlunos = new ListDataModel(alunos);
            pesqNome = null;
            pesqCpf = null;
        } catch (HibernateException ex) {
            System.err.println("Erro pesquisa Aluno:\n" + ex.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limpar() {
        aluno = new Aluno();
        endereco = new Endereco();
    }

    public void excluir() {
        aluno = modelAlunos.getRowData();
        alunoDao = new AlunoDaoImpl();
        try {
            abreSessao();
            alunoDao.remover(aluno, sessao);
            Mensagem.excluir("Aluno " + aluno.getNome());
            aluno = new Aluno();
        } catch (HibernateException ex) {
            System.err.println("Erro ao excluir aluno:\n" + ex.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void salvar() {
        alunoDao = new AlunoDaoImpl();
        abreSessao();
        try {
            aluno.setEndereco(endereco);
            endereco.setPessoa(aluno);
            alunoDao.salvarOuAlterar(aluno, sessao);
            Mensagem.salvar("Aluno " + aluno.getNome());
            aluno = null;
            endereco = null;

        } catch (HibernateException e) {
            boolean isLoginDuplicado = e.getCause().getMessage().contains("'email_UNIQUE'");
            if (isLoginDuplicado) {
                Mensagem.campoExiste("E-mail");
            }
            System.out.println("Erro ao salvar aluno " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro no salvar alunoDao Controle "
                    + e.getMessage());
        } finally {
            sessao.close();
        }
        limpar();
    }

    //getters e setters
    public Aluno getAluno() {
        if (aluno == null) {
            aluno = new Aluno();
        }
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
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

    public DataModel<Aluno> getModelAlunos() {
        return modelAlunos;
    }

    public void setModelAlunos(DataModel<Aluno> modelUsuarios) {
        this.modelAlunos = modelUsuarios;
    }

    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }

    public List<Aluno> getAlunos() {
        return alunos;
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

}
