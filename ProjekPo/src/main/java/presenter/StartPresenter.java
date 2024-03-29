package presenter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.WorldMap;
import model.records.AllParameters;
import model.simulation.Simulation;
import model.simulation.SimulationInitialize;
import presenter.components.WorldElementBox;
import presenter.utils.CSVHandler;
import presenter.utils.JSONHandler;

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
    @FXML
    private TextField fileNameTextField;
    @FXML
    private TextField statsNameTextField;
    @FXML
    private CheckBox saveStatsCheckBox;
    private int simulationCount = 1;

    public void simulationStart() {
        if (!(saveStatsCheckBox.isSelected() && statsNameTextField.getText().trim().isEmpty())) {
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
                    lostEnergyPerDaySpinner.getValue(),
                    saveStatsCheckBox.isSelected(),
                    statsNameTextField.getText());
            WorldMap map = simulationInitialize.getWorldMap();
            WorldElementBox worldElementBox = new WorldElementBox(startAnimalEnergySpinner.getValue(), map);
            Simulation simulation = simulationInitialize.getSimulation();
            simulationCount++;
            presenter.setWorldMapAndWorldElementBox(map,worldElementBox);
            presenter.setWorldSimulation(simulation);
            map.addListener(presenter);
            stage.setOnCloseRequest(event -> handleCloseRequest(event, simulation));
            stage.show();
        }else{
            System.out.println("Podaj nazwe pliku do zapisu statystyk");
        }
    }

    private void handleCloseRequest(WindowEvent event, Simulation simulation) {
        System.out.println("Close Simulation");
        simulation.setSimulationPlay(false);
        simulation.setKillSimulation(true);
        if(simulation.saveStats){
            CSVHandler.writeStatsToCSV(simulation.stats,simulation.fileName);
        }
    }

    @FXML
    public void saveConfiguration() {
        if (!fileNameTextField.getText().trim().isEmpty()) {
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
                    lostEnergyPerDaySpinner.getValue(),
                    false,
                    "");
            AllParameters params = simulationInitialize.getAllParameters();
            JSONHandler.objectToFile(params, fileNameTextField.getText().trim());
        }
    }

    @FXML
    public void loadConfiguration() {
        if (!fileNameTextField.getText().trim().isEmpty()) {
            AllParameters allParameters = JSONHandler.allParametersFromFile(fileNameTextField.getText());
            if (allParameters != null) {
                heightSpinner.getValueFactory().setValue(allParameters.worldParameters().height());
                widthSpinner.getValueFactory().setValue(allParameters.worldParameters().width());
                numberOfAnimalsSpinner.getValueFactory().setValue(allParameters.worldParameters().numberOfAnimals());
                numberOfPlantsSpinner.getValueFactory().setValue(allParameters.worldParameters().numberOfPlants());
                numberOfPlantsGrowPerDaySpinner.getValueFactory().setValue(allParameters.simulationParameters().numberOfPlantsGrowPerDay());
                startAnimalEnergySpinner.getValueFactory().setValue(allParameters.worldParameters().startAnimalEnergy());
                minimalEnergyToReproductionSpinner.getValueFactory().setValue(allParameters.simulationParameters().minimalEnergyToReproduction());
                reproducingEnergySpinner.getValueFactory().setValue(allParameters.simulationParameters().reproducingEnergy());
                minimalMutationNumberSpinner.getValueFactory().setValue(allParameters.simulationParameters().minimalMutationNumber());
                maximalMutationNumberSpinner.getValueFactory().setValue(allParameters.simulationParameters().maximalMutationNumber());
                genomeLengthSpinner.getValueFactory().setValue(allParameters.worldParameters().genomeLength());
                plantEnergySpinner.getValueFactory().setValue(allParameters.simulationParameters().plantEnergy());
                lostEnergyPerDaySpinner.getValueFactory().setValue(allParameters.simulationParameters().lostEnergyPerDay());
            }
        }
    }


}
