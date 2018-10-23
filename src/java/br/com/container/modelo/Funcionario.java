package br.com.container.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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

    private static final long serialVersionUID = 1L;
    
    @Temporal(TemporalType.DATE)
    private Date dtContratacao;
    
    @ManyToOne
    @JoinColumn(name = "idFuncao")
    private Funcao funcao;
    
    @OneToOne(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private Pessoa pessoa;
    
    public Funcionario() {
    }

    public Funcionario(Date dtContratacao, Funcao funcao, Pessoa pessoa, Long id, String nome, String email, String foneFixo, String foneMovel, Boolean whatsapp, Endereco endereco) {
        super(id, nome, email, foneFixo, foneMovel, whatsapp, endereco);
        this.dtContratacao = dtContratacao;
        this.funcao = funcao;
        this.pessoa = pessoa;
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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }


}
