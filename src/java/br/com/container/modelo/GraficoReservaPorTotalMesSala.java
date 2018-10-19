package br.com.container.modelo;

import java.io.Serializable;

/**
 *
 * @author Dhyego Pedroso
 */
public class GraficoReservaPorTotalMesSala implements Serializable {

    private int mes;
    private long quantidade;

    public GraficoReservaPorTotalMesSala() {
    }

    public GraficoReservaPorTotalMesSala(int mes, long quantidade) {
        this.mes = mes;
        this.quantidade = quantidade;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }
    
    
}
