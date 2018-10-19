package br.com.container.dao;

import br.com.container.modelo.GraficoReservaPorTotalMesSala;
import java.util.List;
import org.hibernate.Session;
import org.junit.Test;

/**
 *
 * @author Dhyego Pedroso
 */
public class TesteGraficoSala {

    private Session sessao;
    private ReservaDao reservaDao;

    private void abreSessao() {
        if (sessao == null) {
            sessao = HibernateUtil.abreSessao();
        } else if (!sessao.isOpen()) {
            sessao = HibernateUtil.abreSessao();
        }
    }

    public TesteGraficoSala() {
        reservaDao = new ReservaDaoImpl();
    }
    
    @Test
    public void testarEssaPorra(){
        abreSessao();
        List<GraficoReservaPorTotalMesSala> resultados = reservaDao.totalMesSalas(sessao);
        
        for (GraficoReservaPorTotalMesSala resultado : resultados) {
            System.out.println("Mês: "+resultado.getMes());
            System.out.println("Qtd: "+resultado.getQuantidade());
        }
    }

}
