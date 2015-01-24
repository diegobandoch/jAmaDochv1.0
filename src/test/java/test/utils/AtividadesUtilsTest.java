/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.utils;

import org.junit.Assert;
import relogio.Atividade;
import relogio.Periodo;

/**
 *
 * @author dibsantos
 */
public class AtividadesUtilsTest {

    public void validaAtividade(Atividade atividade, Integer numeroTicket, Boolean logado, Periodo periodo, String xml) {
        Assert.assertEquals("Ticket da atividade incorreto.", numeroTicket, atividade.getTicket());
        Assert.assertEquals("Logado incorreto", logado, atividade.getLogado());
        
        Assert.assertEquals("xml incorreto.", xml, atividade.getAsXML());
    }
    
}
