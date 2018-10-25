package br.com.container.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author silvio
 */
@Entity
@Table(name = "funcionario")
@PrimaryKeyJoinColumn(name = "idPessoa")
public class Funcionario extends Pessoa implements Serializable {

    @Temporal(TemporalType.DATE)
    private Date dtContratacao;

    @ManyToOne
    @JoinColumn(name = "idFuncao")
    private Funcao funcao;

    public Funcionario() {
    }

    public Funcionario(Date dtContratacao, Long id, String nome, String email, String foneFixo, String foneMovel, Boolean whatsapp) {
        super(id, nome, email, foneFixo, foneMovel, whatsapp);
        this.dtContratacao = dtContratacao;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public Date getDtContratacao() {
        return dtContratacao;
    }

    public void setDtContratacao(Date dtContratação) {
        this.dtContratacao = dtContratação;
    }

}
