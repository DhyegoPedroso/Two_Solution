package br.com.container.controle;

import br.com.container.dao.FuncaoDao;
import br.com.container.dao.FuncaoDaoImpl;
import br.com.container.dao.FuncionarioDao;
import br.com.container.dao.FuncionarioDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.Endereco;
import br.com.container.modelo.Funcao;
import br.com.container.modelo.Funcionario;
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
 * @author silvio
 */
@ManagedBean(name = "funcionarioC")
@ViewScoped
public class FuncionarioControle implements Serializable {

    private Funcionario funcionario;
    private FuncionarioDao funcionarioDao;
    private Funcao funcao;
    private Endereco endereco;
    private Session sessao;
    private DataModel<Funcionario> modelFuncionarios;
    private List<Funcionario> funcionarios;
    private List<SelectItem> funcoes;
    private boolean mostra_toolbar;
    private String pesqNome = "";

    @PostConstruct
    public void constroiTudo() {
        carregaComboFuncaoes();
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
        funcionarioDao = new FuncionarioDaoImpl();
        try {
            abreSessao();

            if (!pesqNome.equals("")) {
                funcionarios = funcionarioDao.pesquisaPorNome(pesqNome, sessao);
            } else {
                Mensagem.mensagemError("Erro ao Pesquisar\no campo abaixo é obrigatorio");
            }

            modelFuncionarios = new ListDataModel(funcionarios);
            pesqNome = null;
        } catch (Exception e) {
            System.out.println("erro ao pesquisar funcionario por nome: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limpar() {
        funcionario = new Funcionario();
        funcao = new Funcao();
        endereco = new Endereco();
    }

    public void carregarParaAlterar() {
        mostra_toolbar = !mostra_toolbar;
        funcionario = modelFuncionarios.getRowData();
        funcao = funcionario.getFuncao();
        endereco = funcionario.getEndereco();
    }

    public void carregarMaisInfo() {
        funcionario = modelFuncionarios.getRowData();
        funcao = funcionario.getFuncao();
        endereco = funcionario.getEndereco();
    }

    public void excluir() {
        funcionario = modelFuncionarios.getRowData();
        funcionarioDao = new FuncionarioDaoImpl();
        abreSessao();
        try {
            funcionarioDao.remover(funcionario, sessao);
            funcionarios.remove(funcionario);
            modelFuncionarios = new ListDataModel(funcionarios);
            Mensagem.excluir("Funcionário");
            limpar();
        } catch (Exception e) {
            System.out.println("erro ao excluir: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void salvar() {
        funcionario.setFuncao(funcao);
        funcionarioDao = new FuncionarioDaoImpl();
        abreSessao();
        try {
            funcionario.setEndereco(endereco);
            endereco.setPessoa(funcionario);
            funcionarioDao.salvarOuAlterar(funcionario, sessao);
            Mensagem.salvar("Funcionário " + funcionario.getNome());
            funcionario = null;
            funcao = null;
            endereco = null;

        } catch (HibernateException e) {
            boolean isLoginDuplicado = e.getCause().getMessage().contains("'email_UNIQUE'");
            if (isLoginDuplicado) {
                Mensagem.campoExiste("E-mail");
            }
            System.out.println("Erro ao salvar funcionario " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro no salvar funcionarioDao Controle "
                    + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    private void carregaComboFuncaoes() {
        List<Funcao> todasFuncoes;
        try {
            abreSessao();
            funcoes = new ArrayList();

            FuncaoDao funcaoDao = new FuncaoDaoImpl();
            todasFuncoes = funcaoDao.listaTodos(sessao);
            todasFuncoes.stream().forEach((perf) -> {
                funcoes.add(new SelectItem(perf.getId(), perf.getNome()));
            });
        } catch (HibernateException hi) {
            System.out.println("Erro ao carregar função " + hi.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limparTela() {
        limpar();
    }

    //getters e setters
    public Funcionario getFuncionario() {
        if (funcionario == null) {
            funcionario = new Funcionario();
        }
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Funcao getFuncao() {
        if (funcao == null) {
            funcao = new Funcao();
        }
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
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

    public DataModel<Funcionario> getModelFuncionarios() {
        return modelFuncionarios;
    }

    public void setModelFuncionarios(DataModel<Funcionario> modelUsuarios) {
        this.modelFuncionarios = modelUsuarios;
    }

    public List<SelectItem> getFuncoes() {
        return funcoes;
    }

    public void setFuncoes(List<SelectItem> funcoes) {
        this.funcoes = funcoes;
    }

    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public String getPesqNome() {
        return pesqNome;
    }

    public void setPesqNome(String pesqNome) {
        this.pesqNome = pesqNome;
    }

}
