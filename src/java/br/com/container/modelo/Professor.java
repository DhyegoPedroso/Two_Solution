/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.modelo;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author cel05
 */
@Entity
@Table(name = "professor")
@PrimaryKeyJoinColumn(name = "idPessoa")
public class Professor extends Pessoa implements Serializable {

    private String disciplinas;
    public Professor() {
    }

    public Professor(String disciplinas, Long id, String nome, String email, String foneFixo, String foneMovel, Boolean whatsapp) {
        super(id, nome, email, foneFixo, foneMovel, whatsapp);
        this.disciplinas = disciplinas;
    }

    public String getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(String disciplinas) {
        this.disciplinas = disciplinas;
    }


}
