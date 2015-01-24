/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import relogio.Atividades;
import relogio.FXMLDocumentController;

/**
 *
 * @author dibsantos
 */
public class ArquivoXMLService {

    public Atividades getAtividades(String caminhoArquivo) {
        try {
            JAXBContext context = JAXBContext.newInstance(Atividades.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Atividades) unmarshaller.unmarshal(new File(caminhoArquivo));
        } catch (JAXBException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Atividades();
    }

}
