/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.EquipamentoDao;
import br.com.container.dao.EquipamentoDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.dao.SalaDao;
import br.com.container.dao.SalaDaoImpl;
import br.com.container.modelo.Equipamento;
import br.com.container.modelo.Sala;
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
@ManagedBean(name = "equipamentoC")
@ViewScoped
public class EquipamentoControle {
    
    private Equipamento equipamento;
    private EquipamentoDao equipamentoDao;
    private Session sessao;
    private DataModel<Equipamento> modelEquipamento;
    private List<Equipamento> equipamentos;
    private List<Sala> salas;
    private boolean mostra_toolbar;
    private Sala sala;
    private List<SelectItem> comboSala;

    private void abreSessao() {
        if (sessao == null) {
            sessao = HibernateUtil.abreSessao();
        } else if (!sessao.isOpen()) {
            sessao = HibernateUtil.abreSessao();
        }
    }
    
    @PostConstruct
    public void inicializar() {
        carregaComboSala();
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
    
    private void carregaComboSala() {
        List<Sala> todosPerfis;
        try {
            abreSessao();
            comboSala = new ArrayList();
            SalaDao salaDao = new SalaDaoImpl();
            todosPerfis = salaDao.listaTodos(sessao);
            todosPerfis.stream().forEach((perf) -> {
                comboSala.add(new SelectItem(perf.getNome(), perf.getNome()));
            });
        } catch (HibernateException hi) {
            System.out.println("Erro ao carregar combo sala " + hi.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void pesquisar() {
        equipamentoDao = new EquipamentoDaoImpl();
        try {
            abreSessao();
            equipamentos = equipamentoDao.listaTodos(sessao);
            modelEquipamento = new ListDataModel(equipamentos);
        } catch (Exception e) {
            System.out.println("erro ao pesquisar empresa por nome: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limpar() {
        equipamento = new Equipamento();
    }

    public void carregarParaAlterar() {
        mostra_toolbar = !mostra_toolbar;
        equipamento = modelEquipamento.getRowData();
    }

    public void excluir() {
        equipamento = modelEquipamento.getRowData();
        equipamentoDao = new EquipamentoDaoImpl();
        abreSessao();
        try {
            equipamentoDao.remover(equipamento, sessao);
            equipamentos.remove(equipamento);
            modelEquipamento = new ListDataModel(equipamentos);
            Mensagem.excluir("Sala");
            limpar();
        } catch (Exception e) {
            System.out.println("erro ao excluir sala: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void salvar() {
        equipamentoDao = new EquipamentoDaoImpl();
        abreSessao();
           
        try {
            equipamento.setDescricaoDefeito(equipamento.getDescricaoDefeito() + " - Local: " + sala.getNome());
            equipamentoDao.salvarOuAlterar(equipamento, sessao);
            Mensagem.salvar("Equipamento " + equipamento.getNome());
            equipamento = null;
        } catch (HibernateException e) {
            System.out.println("Erro ao salvar Equipamento " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro no salvar Equipamento Controle " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limparTela() {
        limpar();
    }

    public Equipamento getEquipamento() {
        if(equipamento == null){
            equipamento = new Equipamento();
        }
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }
    
    public Sala getSala() {
        if (sala == null){
            sala = new Sala();
        }
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public EquipamentoDao getEquipamentoDao() {
        return equipamentoDao;
    }

    public void setEquipamentoDao(EquipamentoDao equipamentoDao) {
        this.equipamentoDao = equipamentoDao;
    }

    public Session getSessao() {
        return sessao;
    }

    public void setSessao(Session sessao) {
        this.sessao = sessao;
    }

    public DataModel<Equipamento> getModelEquipamento() {
        return modelEquipamento;
    }

    public void setModelEquipamento(DataModel<Equipamento> modelEquipamento) {
        this.modelEquipamento = modelEquipamento;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(List<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }

    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }

    public List<SelectItem> getComboSala() {
        return comboSala;
    }

    public void setComboSala(List<SelectItem> comboSala) {
        this.comboSala = comboSala;
    }
    
}
