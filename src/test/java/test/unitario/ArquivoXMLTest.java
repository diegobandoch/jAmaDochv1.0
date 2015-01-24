package test.unitario;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




import java.util.Calendar;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import relogio.Atividade;
import relogio.Atividades;
import relogio.Periodo;
import service.ArquivoXMLService;
import test.utils.AtividadesUtilsTest;
import test.utils.PeriodoUtilsTest;
import utils.OperacoesDatas;

/**
 *
 * @author dibsantos
 */
public class ArquivoXMLTest {
    
    ArquivoXMLService arquivoXMLService  = new ArquivoXMLService();
    
    AtividadesUtilsTest atividadesUtilsTest = new AtividadesUtilsTest();
    PeriodoUtilsTest periodoUtilsTest = new PeriodoUtilsTest();
    OperacoesDatas operacoesDatas = new OperacoesDatas();
    
    
    @Test
    public void getAtividades(){
        
        Atividades atividadesObj = arquivoXMLService.getAtividades("src/test/resources/horas23012015.xml");
        
        List<Atividade> atividades = atividadesObj.getAtividade();
        Assert.assertEquals("Quantidade de Atividades incorreta.", 2, atividades.size());
        
        String asXML = "<time_entry >  <issue_id>4</issue_id>  <spent_on>Fri Jan 23 16:30:15 BRST 2015</spent_on>  <hours>0.00</hours>  <activity_id>9</activity_id>  <comments></comments></time_entry >";
        atividadesUtilsTest.validaAtividade(atividades.get(0), 4, Boolean.TRUE, new Periodo(), asXML);
        periodoUtilsTest.validaPeriodo(atividades.get(0).getPeriodo(), "23/01/2015 04:30:15", "23/01/2015 04:30:19");
        
        asXML = "<time_entry >  <issue_id>4</issue_id>  <spent_on>Fri Jan 23 16:36:07 BRST 2015</spent_on>  <hours>0.02</hours>  <activity_id>9</activity_id>  <comments></comments></time_entry >";
        atividadesUtilsTest.validaAtividade(atividades.get(1), 4, Boolean.FALSE, new Periodo(), asXML);
        periodoUtilsTest.validaPeriodo(atividades.get(1).getPeriodo(), "23/01/2015 04:36:07", "23/01/2015 04:37:12");
    }
    
    
}
