package br.com.container.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Dhyego Pedroso
 */
@Entity
@Table(name = "aluno")
@PrimaryKeyJoinColumn(name = "idPessoa")
public class Aluno extends Pessoa implements Serializable {

    private String cpf;

    public Aluno() {
    }

    public Aluno(String cpf, Long id, String nome, String email, String foneFixo, String foneMovel, Boolean whatsapp) {
        super(id, nome, email, foneFixo, foneMovel, whatsapp);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
