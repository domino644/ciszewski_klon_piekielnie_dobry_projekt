package presenter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;
import model.simulation.Simulation;
import model.simulation.SimulationInitialize;
import presenter.components.WorldElementBox;

import java.io.IOException;
public class StartPresenter {
    @FXML
    private Spinner<Integer> numberOfAnimalsSpinner;
    @FXML
    private Spinner<Integer> numberOfPlantsSpinner;
    @FXML
    private Spinner<Integer> numberOfPlantsGrowPerDaySpinner;
    @FXML
    private Spinner<Integer> startAnimalEnergySpinner;
    @FXML
    private Spinner<Integer> minimalEnergyToReproductionSpinner;
    @FXML
    private Spinner<Integer> reproducingEnergySpinner;
    @FXML
    private Spinner<Integer> minimalMutationNumberSpinner;
    @FXML
    private Spinner<Integer> maximalMutationNumberSpinner;
    @FXML
    private Spinner<Integer> genomeLengthSpinner;
    @FXML
    private Spinner<Integer> plantEnergySpinner;
    @FXML
    private Spinner<Integer> lostEnergyPerDaySpinner;
    @FXML
    private Spinner<Integer> heightSpinner;
    @FXML
    private Spinner<Integer> widthSpinner;
    private int simulationCount = 1;

    public void simulationStart() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot;
        Scene scene;
        try {
            viewRoot = loader.load();
            scene = new Scene(viewRoot);
        } catch (IOException e) {
            System.out.println("Couldn't load file");
            return;
        }
        SimulationPresenter presenter = loader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Darwin world # " + simulationCount);
        stage.minWidthProperty().bind(viewRoot.minWidthProperty());
        stage.minHeightProperty().bind(viewRoot.minHeightProperty());
        SimulationInitialize simulationInitialize = new SimulationInitialize(widthSpinner.getValue(),
                heightSpinner.getValue(),
                numberOfAnimalsSpinner.getValue(),
                numberOfPlantsSpinner.getValue(),
                numberOfPlantsGrowPerDaySpinner.getValue(),
                startAnimalEnergySpinner.getValue(),
                minimalEnergyToReproductionSpinner.getValue(),
                reproducingEnergySpinner.getValue(),
                minimalMutationNumberSpinner.getValue(),
                maximalMutationNumberSpinner.getValue(),
                genomeLengthSpinner.getValue(),
                plantEnergySpinner.getValue(),
                lostEnergyPerDaySpinner.getValue());
        WorldMap map = simulationInitialize.getWorldMap();
        WorldElementBox worldElementBox = new WorldElementBox(startAnimalEnergySpinner.getValue(),map);
        Simulation simulation = simulationInitialize.getSimulation();
        simulationCount++;
        presenter.setWorldElementBox(worldElementBox);
        presenter.setWorldMap(map);
        presenter.setWorldSimulation(simulation);
        map.addListener(presenter);
        stage.setOnCloseRequest(event -> handleCloseRequest(event,simulation));
        stage.show();
    }

    private void handleCloseRequest(WindowEvent event,Simulation simulation){
        System.out.println("Close Simulation");
        simulation.setSimulationPlay(false);
        simulation.setKillSimulation(true);
    }


}
