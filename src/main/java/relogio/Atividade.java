package relogio;

import javax.xml.bind.annotation.XmlRootElement;
import relogio.entity.Enumerations;

@XmlRootElement
public class Atividade {

    private Integer ticket;
    private Periodo periodo;
    private boolean logado;
    private Enumerations enumerations;

    public Atividade(Integer ticket, Enumerations enumerations) {
        this.ticket = ticket;
        periodo = new Periodo();
        this.enumerations = enumerations;
    }

    public Atividade() {

    }

    public Enumerations getEnumerations() {
        return enumerations;
    }

    public void setEnumerations(Enumerations enumerations) {
        this.enumerations = enumerations;
    }

    public Integer getTicket() {
        return ticket;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public boolean getLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

//</editor-fold>
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getTicketAsString());
        sb.append(getTipoAtividade());
        sb.append(periodo.toString());
        sb.append(getLogadoAsString());
        return sb.toString();
    }

    private String getTicketAsString() {
        return "Ticket=" + String.format("%04d", ticket);
    }

    private String getLogadoAsString() {
        return logado ? "\t Lançado no RedMine" : "";
    }

    private String getTipoAtividade() {
        return "\t" + String.format("%30s", enumerations.toString());
    }

    public boolean isLogado() {
        return logado;
    }

    public boolean isAberto() {
        return getPeriodo().getFim() == null;
    }

    public String getAsXML() {
        return "<time_entry >"
                + "  <issue_id>"+getTicket()+"</issue_id>"
                + "  <spent_on>"+getPeriodo().getInicio()+"</spent_on>"
                + "  <hours>"+getPeriodo().getPeriodoTrabalhadoFracionado()+"</hours>"
                + "  <activity_id>"+enumerations.getId()+"</activity_id>"
                + "  <comments></comments>"
                + "</time_entry >";

    }

}
