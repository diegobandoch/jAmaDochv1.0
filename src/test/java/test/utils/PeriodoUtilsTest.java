/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.junit.Assert;
import relogio.Periodo;

/**
 *
 * @author dibsantos
 */
public class PeriodoUtilsTest {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    public void validaPeriodo(Periodo periodo, String dataInicio, String dataFim) {
        String dataFormatadaInicio = sdf.format(periodo.getInicio());
        String dataFormatadaFim = sdf.format(periodo.getFim());

        Assert.assertTrue("Data Inicio Periodo incorreto.", dataFormatadaInicio.equals(dataInicio));
        Assert.assertTrue("Data Fim Periodo incorreto.", dataFormatadaFim.equals(dataFim));
    }

}
