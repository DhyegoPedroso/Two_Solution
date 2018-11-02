/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.EmpresaDao;
import br.com.container.dao.EmpresaDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.Empresa;
import br.com.container.modelo.Endereco;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author felipedania
 */
@ManagedBean(name = "empresaC")
@ViewScoped
public class EmpresaControle {
    
    private Empresa empresa;
    private EmpresaDao empresaDao;
    private Session sessao;
    private DataModel<Empresa> modelEmpresas;
    private List<Empresa> empresas;
    private boolean mostra_toolbar;
    private Endereco endereco;

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
        empresaDao = new EmpresaDaoImpl();
        try {
            abreSessao();
            empresas = empresaDao.listaTodos(sessao);
            modelEmpresas = new ListDataModel(empresas);
        } catch (Exception e) {
            System.out.println("erro ao pesquisar empresa por nome: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limpar() {
        empresa = new Empresa();
    }

    public void carregarParaAlterar() {
        mostra_toolbar = !mostra_toolbar;
        empresa = modelEmpresas.getRowData();
        endereco = empresa.getEndereco();
    }

    public void excluir() {
        empresa = modelEmpresas.getRowData();
        empresaDao = new EmpresaDaoImpl();
        abreSessao();
        try {
            empresaDao.remover(empresa, sessao);
            empresas.remove(empresa);
            modelEmpresas = new ListDataModel(empresas);
            Mensagem.excluir("Sala");
            limpar();
        } catch (Exception e) {
            System.out.println("erro ao excluir sala: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void salvar() {
        empresaDao = new EmpresaDaoImpl();
        abreSessao();
        try {
            empresa.setEndereco(endereco);
            endereco.setEmpresa(empresa);
            empresaDao.salvarOuAlterar(empresa, sessao);
            Mensagem.salvar("Empresa " + empresa.getNomeEmpresa());
            empresa = null;
        } catch (HibernateException e) {
            System.out.println("Erro ao salvar Empresa " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro no salvar Empresa Controle " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limparTela() {
        limpar();
    }

    public Empresa getEmpresa() {
        if(empresa == null){
            empresa = new Empresa();
        }
        return empresa;
    }

    public Endereco getEndereco() {
        if(endereco == null){
            endereco = new Endereco();
        }
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public EmpresaDao getEmpresaDao() {
        return empresaDao;
    }

    public void setEmpresaDao(EmpresaDao empresaDao) {
        this.empresaDao = empresaDao;
    }

    public Session getSessao() {
        return sessao;
    }

    public void setSessao(Session sessao) {
        this.sessao = sessao;
    }

    public DataModel<Empresa> getModelEmpresas() {
        return modelEmpresas;
    }

    public void setModelEmpresas(DataModel<Empresa> modelEmpresas) {
        this.modelEmpresas = modelEmpresas;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }
    
}
