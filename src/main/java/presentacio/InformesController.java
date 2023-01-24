/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package presentacio;

import entitats.RowItem;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Izan
 */
public class InformesController implements Initializable {

    @FXML
    private VBox container;
    @FXML
    private MenuBar menu;
    @FXML
    private MenuItem menuitem_importar, menuitem_sortir, menuitem_llistar, menuitem_informes;
    @FXML
    private Menu menu_registres, menu_informes, menu_arxiu;
    @FXML
    private ComboBox<RowItem> selector_ordenar = new ComboBox<RowItem>();
    @FXML
    private Button btnExportarInforme;
    @FXML
    private BarChart<?, ?> barChartEvolució;
    @FXML
    private PieChart cake;
    @FXML
    private Label maxPoblacio, minPoblació;

    private ArrayList<RowItem> llista = ImportarController.getDades();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Forço el responsive de la taula
        VBox.setVgrow(container, Priority.ALWAYS);

        fillDropDownList();

        //importar dades a la cacke
        ObservableList<PieChart.Data> cackeData
                = FXCollections.observableArrayList(new PieChart.Data("HOMES", 40), new PieChart.Data("DONES", 60));
        cake.setData(cackeData);

        //importar dades al barChart
        XYChart.Series dada1 = new XYChart.Series();
        dada1.setName("Primera dada");
        dada1.getData().add(new XYChart.Data("2009", 10000));
        dada1.getData().add(new XYChart.Data("2010", 9000));
        dada1.getData().add(new XYChart.Data("2011", 14400));
        dada1.getData().add(new XYChart.Data("2012", 3500));

        barChartEvolució.getData().addAll(dada1);

    }

    /**
     * Porta a l'apartat 'Importar XML', que permet a l'usuari obrir un nou
     * arxiu XML amb el que treballar.
     *
     * @param event Acció que afecti al 'MenuItem' (ex: clicar)
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Izan Jimenez - Implementació
     */
    @FXML
    private void pantalla_importar(ActionEvent event) throws IOException {
        VBox view = FXMLLoader.load(getClass().getResource("/presentacio/importar.fxml"));
        container.getChildren().setAll(view);
    }

    /**
     * Tanca l'aplicació.
     *
     * @param event Acció que afecti al 'MenuItem' (ex: clicar)
     *
     * @author Izan Jimenez - Implementació
     */
    @FXML
    private void tancar_aplicacio(ActionEvent event) {
        System.out.println(">> L'usuari ha tancat l'aplicació.");
        ((Stage) container.getScene().getWindow()).close();
    }

    /**
     * Porta a l'apartat 'Llistat de registres', que permet a l'usuari
     * visualitzar, cercar, ordenar i exportar els registres de l'XML carrgeat.
     *
     * @param event Acció que afecti al 'MenuItem' (ex: clicar)
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Izan Jimenez - Implementació
     */
    @FXML
    private void pantalla_registres(ActionEvent event) throws IOException {
        VBox view = FXMLLoader.load(getClass().getResource("/presentacio/registres.fxml"));
        container.getChildren().setAll(view);
    }

    /**
     * Porta a l'apartat 'Informes', que permet a l'usuari visualitzar un
     * conjunt de gràfiques i estadístiques de l'XML carrgeat.
     *
     * @param event Acció que afecti al 'MenuItem' (ex: clicar)
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Izan Jimenez - Implementació
     */
    @FXML
    private void pantalla_informes(ActionEvent event) throws IOException {
        VBox view = FXMLLoader.load(getClass().getResource("/presentacio/informes.fxml"));
        container.getChildren().setAll(view);
    }

    @FXML
    private void btnExportarInformePressed(ActionEvent event) {
    }

    /**
     * Emplena el comboBox amb la llista de països del XML
     * 
     * @author Víctor García
     * @author Pablo Morante
     */
    private void fillDropDownList() {
        Collection<RowItem> filtered = llista.stream()
                .collect(Collectors.toMap(
                        RowItem::getPaisDeResidencia, p -> p, (p1, p2) -> p2))
                .values();
        ArrayList<RowItem> filteredList = new ArrayList<>(filtered);
        for (int i = 0; i < filteredList.size(); i++) {
            if (filteredList.get(i).getPaisDeResidencia() == null) {
                filteredList.remove(i);
            }
        }
        Collections.sort(filteredList, Comparator.comparing(RowItem::getPaisDeResidencia));
        
        ObservableList<RowItem> olc = (FXCollections.observableList(filteredList));
        selector_ordenar.setItems(olc);

        selector_ordenar.setConverter(new StringConverter<RowItem>() {
            @Override
            /**
             * {@inheritDoc}
             */
            public String toString(RowItem rowItem) {
                if (rowItem == null) {
                    return null;
                } else {
                    return rowItem.getPaisDeResidencia();
                }
            }

            @Override
            /**
             * {@inheritDoc}
             */
            public RowItem fromString(String id) {
                return null;
            }
        });
    }

}
