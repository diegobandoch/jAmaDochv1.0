package relogio;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Atividades {

    private List<Atividade> atividade = new ArrayList();

    public List<Atividade> getAtividade() {
        return atividade;
    }

    public void setAtividade(List<Atividade> atividades) {
        this.atividade = atividades;
    }

    public void add(Atividade atividade) {
        this.atividade.add(atividade);
    }

    public Atividade getUltimaAtividade(){
        
        return atividade.get(atividade.size() - 1);
    }
    
}
