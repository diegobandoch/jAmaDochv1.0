
package relogio;

import Utils.OperacoesDatas;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Periodo {

    private final SimpleDateFormat sdfHoras = new SimpleDateFormat("HH:mm");
    OperacoesDatas operacoesData = new OperacoesDatas();

    private Date inicio;
    private Date fim;

    public Periodo() {
        inicio = new Date();
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public String getTempoTotalAsString() {
        if (fim == null) {
            return "";
        }
        Calendar c = getPeriodoTrabalhado();

        return "\t Tempo: " + sdfHoras.format(c.getTime());
    }

    public Calendar getPeriodoTrabalhado() {
        
        Calendar calendar = operacoesData.getCalendarZerado();

        Long minutos = (fim.getTime() - inicio.getTime()) / 1000 / 60;
        Long horas = minutos / 60;
        minutos = minutos % 60;

        calendar.add(Calendar.MINUTE, minutos.intValue());
        calendar.add(Calendar.HOUR_OF_DAY, horas.intValue());
        return calendar;
    }

    public BigDecimal getPeriodoTrabalhadoFracionado() {
        Calendar periodoEmHoras = getPeriodoTrabalhado();

        BigDecimal horasFracionada = new BigDecimal(periodoEmHoras.get(Calendar.HOUR_OF_DAY));

        return horasFracionada.add(new BigDecimal(periodoEmHoras.get(Calendar.MINUTE)).multiply(new BigDecimal(100)).divide(new BigDecimal(6000), 2, RoundingMode.HALF_EVEN));
    }

    @Override
    public String toString() {
        return getInicioAsString() + getFimAsString() + getTempoTotalAsString();
    }

    private String getFimAsString() {
        return fim == null ? "" : "\t Fim=" + sdfHoras.format(fim) + "\t";
    }

    private String getInicioAsString() {
        return "\t Inicio=" + sdfHoras.format(inicio);
    }

    public Integer getSemanaDoAno() {
        return operacoesData.getSemanaDoAno(getInicio());
    }

    public Integer getMesDoAno() {
        return operacoesData.getMesDoAno(getInicio());
    }
    
    public Integer getAno() {
        return operacoesData.getMesDoAno(getInicio());
    }

}
