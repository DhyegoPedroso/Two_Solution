package br.com.container.controle;

import br.com.container.dao.HibernateUtil;
import br.com.container.dao.ReservaDao;
import br.com.container.dao.ReservaDaoImpl;
import br.com.container.modelo.GraficoReservaPorTotalMesSala;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.Session;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;

/**
 *
 * @author silvio
 */
@ManagedBean(name = "atividadeC")
@ViewScoped
public class AtividadeControle implements Serializable {

    private ReservaDao reservaDao;
    private Session sessao;
    private CartesianChartModel graficoSalas;

    private void abreSessao() {
        if (sessao == null) {
            sessao = HibernateUtil.abreSessao();
        } else if (!sessao.isOpen()) {
            sessao = HibernateUtil.abreSessao();
        }
    }

    @PostConstruct
    public void init() {
        createCombinedModel();
    }

    public CartesianChartModel getGraficoSalas() {
        return graficoSalas;
    }

    private void createCombinedModel() {
        abreSessao();

        graficoSalas = new BarChartModel();

        BarChartSeries sala = new BarChartSeries();
        sala.setLabel("Quantidade de salas Reservadas");
        
        reservaDao = new ReservaDaoImpl();
        List<GraficoReservaPorTotalMesSala> resultado = reservaDao.totalMesSalas(sessao);

        sala.set("Janeiro", resultado.get(0).getQuantidade());
        sala.set("Fevereiro", resultado.get(1).getQuantidade());
        sala.set("Março", resultado.get(2).getQuantidade());
        sala.set("Abril", resultado.get(3).getQuantidade());
        sala.set("Maio", resultado.get(4).getQuantidade());
        sala.set("Junho", resultado.get(5).getQuantidade());
        sala.set("Julho", resultado.get(6).getQuantidade());
        sala.set("Agosto", resultado.get(7).getQuantidade());
        sala.set("Setembro", resultado.get(8).getQuantidade());
        sala.set("Outubro", resultado.get(9).getQuantidade());
        sala.set("Novembro", resultado.get(10).getQuantidade());
        sala.set("Dezembro", resultado.get(11).getQuantidade());

        graficoSalas.addSeries(sala);

        graficoSalas.setTitle("Reservas de Salas");
        graficoSalas.setLegendPosition("nw");
        graficoSalas.setMouseoverHighlight(true);
        graficoSalas.setShowDatatip(true);
        graficoSalas.setShowPointLabels(true);
        graficoSalas.setAnimate(true);
        graficoSalas.setZoom(true);
        Axis yAxis = graficoSalas.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(250);
    }

}
