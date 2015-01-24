package relogio;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Relogio extends Application {

    private TrayIcon trayIcon;

    @Override
    public void start(Stage stage) throws Exception {
        geraScene(stage);
        Platform.setImplicitExit(false);
        createTrayIcon(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void createTrayIcon(final Stage stage) {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            java.awt.Image image = Toolkit.getDefaultToolkit().getImage(Relogio.class.getResource("/images/redmine.png"));
            stage.getIcons().add(new Image("/images/redmine24.png"));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (SystemTray.isSupported()) {
                                stage.hide();
                                showProgramIsMinimizedMsg(stage);
                            } else {
                                System.exit(0);
                            }
                        }
                    });
                }
            });
            // create a action listener to listen for default action executed on the tray icon
            final ActionListener closeListener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.exit(0);
                }
            };

            ActionListener showListener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stage.show();
                        }
                    });
                }
            };

            // create a popup menu
            PopupMenu popup = new PopupMenu();

            MenuItem showItem = new MenuItem("Show");
            showItem.addActionListener(showListener);
            popup.add(showItem);

            MenuItem closeItem = new MenuItem("Exit");
            closeItem.addActionListener(closeListener);
            popup.add(closeItem);

            trayIcon = new TrayIcon(image, "jAMADOCH", popup);
            trayIcon.addActionListener(showListener);
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stage.show();
                        }
                    });
                }
            });

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }

        }
    }

    public void showProgramIsMinimizedMsg(Stage stage) {
        trayIcon.displayMessage("Message.", "jAMADOCH em execução." , TrayIcon.MessageType.INFO);
    }

     private void geraScene(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/styles.css");
        stage.setScene(scene);
        stage.setTitle("jAMADOCH");
        stage.show();
    }
    
}
