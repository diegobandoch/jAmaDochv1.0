package relogio;

import utils.OperacoesDatas;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import relogio.entity.Enumerations;
import relogio.entity.Issues;
import relogio.repo.EnumerationsJpaController;
import relogio.repo.IssuesJpaController;
import service.ArquivoXMLService;

public class FXMLDocumentController implements Initializable {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final ArquivoXMLService arquivoXMLService = new ArquivoXMLService();
    
    @FXML
    private ListView listView;
    @FXML
    private TextField tfTicket;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private TextField tfData;
    @FXML
    private TextField tfKey;
    @FXML
    private TextArea taErro;
    @FXML
    private ComboBox cbAtividade;

    private Atividade atividade;

    private EntityManagerFactory emf;

    @FXML
    private void handleFindAction(ActionEvent event) {
        atualizaLista();
    }

    @FXML
    public void handleStartAction(ActionEvent event) {

        try {
            if ("".equals(tfTicket.getText())) {
                taErro.setText("Insira o numero do ticket.");
                return;
            }

            Integer ticket = Integer.valueOf(tfTicket.getText());

            IssuesJpaController issuesJpaController = new IssuesJpaController(emf);
            Issues issues = issuesJpaController.findIssues(ticket);

            Enumerations enumeration = (Enumerations) cbAtividade.getValue();

            if (issues == null) {
                taErro.setText("Não existe o ticket " + ticket + ".");
                return;
            }
            if (enumeration == null) {
                taErro.setText("Selecione o tipo da atividade.");
                return;
            }
            taErro.setText("");
            Atividade atividade2 = new Atividade(ticket, (Enumerations) cbAtividade.getValue());
            tfData.setText(sdf.format(new Date()));
            gravaArquivo(atividade2);
            this.atividade = atividade2;
            atualizaLista();
        } catch (NumberFormatException numberFormatException) {
            System.err.println(numberFormatException);
        }
    }

    @FXML
    private void handleLogarAction(ActionEvent event) {

        try {
            
            String caminhoArquivo = "c:/install/horas" + tfData.getText().replace("/", "") + ".xml";
            List<Atividade> atividades = arquivoXMLService.getAtividades(caminhoArquivo).getAtividade();

            for (Atividade atividade2 : atividades) {
                if (!atividade2.isLogado() && atividade2.getPeriodo().getPeriodoTrabalhadoFracionado().compareTo(BigDecimal.ZERO) > 0) {
                    taErro.setText("");
                    if (atividade2.getPeriodo().getPeriodoTrabalhadoFracionado().compareTo(new BigDecimal(8)) > 0) {
                        taErro.setText("Ticket " + atividade2.getTicket() + " logada com mais de 8 horas. Favor validar.");
                    }

                    criaTimeEntries(atividade2);
                    atividade2.setLogado(Boolean.TRUE);
                }
                if (atividade2.getPeriodo().getPeriodoTrabalhadoFracionado().compareTo(BigDecimal.ZERO) < 1) {
                    atividade2.setLogado(Boolean.TRUE);
                }

            }
            Atividades atividades1 = new Atividades();
            atividades1.setAtividade(atividades);
            gravaArquivo(atividades1);

            atualizaLista();
        } catch (NumberFormatException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void criaTimeEntries(Atividade atividade1) {
        WebTarget webTarget;
        Client client;

        Properties props = openProperties();
        String baseUri = props.getProperty("urlRedMineWS");
        if (baseUri == null){
            taErro.setText("Url RedMine WS não setado no arquivo de propriedades.");
        }
        client = ClientBuilder.newClient();
        webTarget = client.target(baseUri).path("/time_entries.xml");
        try {
            webTarget.request(MediaType.APPLICATION_XML_TYPE)
                    .header("X-Redmine-API-Key", tfKey.getText())
                    .post(Entity.xml(atividade1.getAsXML()), Response.class);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private Properties openProperties() {
        Properties props = new Properties();
        try (FileInputStream file = new FileInputStream("/install/dados.properties")) {
            props.load(file);
        } catch (FileNotFoundException ex) {
            taErro.setText("Arquivo de propriedades não encontrado.");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return props;
    }

    @FXML
    private void handleStopAction(ActionEvent event) {
        tfData.setText(sdf.format(new Date()));
        
        String caminhoArquivo = "c:/install/horas" + tfData.getText().replace("/", "") + ".xml";
        Atividades atividades = arquivoXMLService.getAtividades(caminhoArquivo);
            
        Atividade atividade2 = atividades.getAtividade().get(atividades.getAtividade().size() - 1);
        Periodo periodo = atividade2.getPeriodo();
        periodo.setFim(new Date());
        gravaArquivo(atividades);
        this.atividade = atividade2;
        atualizaLista();
    }

    @FXML
    private void handleAtualizarKeyAction(ActionEvent event) {
        try {
            Properties props = new Properties();
            props.put("key", tfKey.getText());
            FileOutputStream file;
            file = new FileOutputStream("/install/dados.properties");
            props.store(file, "gravado");
            tfKey.setText(props.getProperty("key"));
            taErro.setText("Chave atualizada.");
            file.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfData.setText(sdf.format(new Date()));
        taErro.setEditable(false);

        String caminhoArquivo = "c:/install/horas" + tfData.getText().replace("/", "") + ".xml";
        Atividades atividades = arquivoXMLService.getAtividades(caminhoArquivo);
        
        if (atividades.getAtividade().isEmpty()) {
            atividade = new Atividade();
        } else {
            atividade = atividades.getUltimaAtividade();
        }
        atualizaLista();

        emf = Persistence.createEntityManagerFactory("relogioPU");
        EnumerationsJpaController enumerationsJpaController = new EnumerationsJpaController(emf);
        List<Enumerations> findEnumerationsEntities = enumerationsJpaController.findByTiposAtivos();
        cbAtividade.setItems(FXCollections.observableArrayList(findEnumerationsEntities));

        Properties props = openProperties();
        tfKey.setText(props.getProperty("key"));

    }

    private void gravaArquivo(Atividade atividade) {
        String caminhoArquivo = "c:/install/horas" + tfData.getText().replace("/", "") + ".xml";
        Atividades atividades = arquivoXMLService.getAtividades(caminhoArquivo);
        
        atividades.add(atividade);
        gravaArquivo(atividades);

    }

    private void gravaArquivo(Atividades atividades) {
        try {
            JAXBContext context = JAXBContext.newInstance(Atividades.class
            );
            Marshaller marshaller = context.createMarshaller();

            marshaller.marshal(atividades,
                    new File("c:/install/horas" + tfData.getText().replace("/", "") + ".xml"));
        } catch (JAXBException ex) {
            Logger.getLogger(FXMLDocumentController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    private ObservableList<Atividade> lerArquivo() {
        
        String caminhoArquivo = "c:/install/horas" + tfData.getText().replace("/", "") + ".xml";
        List<Atividade> atividades = arquivoXMLService.getAtividades(caminhoArquivo).getAtividade();        
        
        if (!atividades.isEmpty()) {
            startButton.setDisable(atividade.isAberto());
            stopButton.setDisable(!atividade.isAberto());
        } else {
            stopButton.setDisable(true);
        }
        return FXCollections.observableArrayList(atividades);
    }

    private void atualizaLista() {

        ObservableList<Atividade> atividades = lerArquivo();
        try {
            Date dataBusca = sdf.parse(tfData.getText());
            if (!OperacoesDatas.isDiaMesEAnoIguais(dataBusca, new Date())) {
                for (Atividade atividade1 : atividades) {
                    if (atividade1.getPeriodo().getFim() == null) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(dataBusca);
                        calendar.set(Calendar.HOUR, 18);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);

                        atividade1.getPeriodo().setFim(calendar.getTime());
                    }
                }
                List atividadesList = Arrays.asList(atividades.toArray());
                Atividades atividadesObject = new Atividades();
                atividadesObject.setAtividade(atividadesList);
                gravaArquivo(atividadesObject);

            }
        } catch (ParseException ex) {
            Logger.getLogger(FXMLDocumentController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        listView.setItems(atividades);

    }
}
