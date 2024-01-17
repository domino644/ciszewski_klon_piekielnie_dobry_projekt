package presenter;

import interfaces.MapChangeListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import model.WorldMap;
import model.Vector2d;
import model.animal.Animal;
import model.simulation.Simulation;
import model.simulation.SimulationEngine;
import presenter.components.GridCreator;
import presenter.components.WorldElementBox;

import java.util.ArrayList;
import java.util.Collections;

public class SimulationPresenter implements MapChangeListener {
    @FXML
    private GridPane mapGrid;
    @FXML
    private GridPane gridAnimalStats;
    @FXML
    private GridPane gridMapStats;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button trackButton;
    @FXML
    private Button stopTrackButton;
    @FXML
    private Button showStatsButton;
    @FXML
    private Button hideStatsButton;
    @FXML
    private Button showGrass;
    @FXML
    private Button showGenotype;
    private WorldMap map;
    private Simulation simulation;
    private GridCreator gridCreator;
    private static final int GRID_WIDTH = 500;
    private static final int GRID_HEIGHT = 500;
    private boolean animalStatsPrint = false;
    private boolean statsPrint = false;
    private Animal trackedAnimal;

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(this::drawMap);
    }

    public void setWorldMapAndWorldElementBox(WorldMap map,WorldElementBox worldElementBox) {
        this.map = map;
        Vector2d lowerBoundary = map.getLowerBoundary();
        Vector2d upperBoundary = map.getUpperBoundary();
        int numberColumns = upperBoundary.getX() - lowerBoundary.getX();
        int numberRows = upperBoundary.getY() - lowerBoundary.getY();
        float cellWidth = (float) GRID_WIDTH / (numberColumns+1);
        float cellHeight = (float) GRID_HEIGHT / (numberRows+1);
        gridCreator = new GridCreator(worldElementBox,map,cellWidth,cellHeight,this);
        mapGrid.getChildren().add(gridCreator(false,0));
    }

    public void setWorldSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void drawMap() {
        clearGrid(mapGrid);
        mapGrid.getChildren().add(gridCreator(false,0));
        if (animalStatsPrint){
            clearGrid(gridAnimalStats);
            gridAnimalStats.getChildren().add(gridAnimalStatsCreator());
        }
        if (statsPrint){
            clearGrid(gridMapStats);
            gridMapStats.getChildren().add(gridMapStatsCreator());
        }
    }

    private GridPane gridMapStatsCreator(){
        int firstColumnWidth = 130;
        int secondColumnWidth = 70;
        int rowHeight = 55;
        String[] namesRow = {"Liczba zwierząt żywych","Liczba zwierząt martwych","Liczba roślin","Liczba wolnych pól",
                "Średni poziom Energi","Średnia długość życia","Średnia liczba dzieci","Najpopularniejszy genotyp"};
        String[] values = map.getStatsKeeper().stringArgumentsGet();
        return GridCreator.informationTableCreate(firstColumnWidth,secondColumnWidth,rowHeight,namesRow,values);
    }

    private GridPane gridAnimalStatsCreator(){
        int firstColumnWidth = 130;
        int secondColumnWidth = 70;
        int rowHeight = 50;
        String[] namesRow = {"Pozycja","Orientacja","Genotyp","Aktywny genom","Energia","Zjedzona trawa",
        "Ilość dzieci","Ilość krewnych","Wiek","Data urodzenia"};
        String[] values = trackedAnimal.getAnimalStats().animalStringStats();
        return GridCreator.informationTableCreate(firstColumnWidth,secondColumnWidth,rowHeight,namesRow,values);
    }

    private GridPane gridCreator(boolean canClick,int option) {
        Vector2d lowerBoundary = map.getLowerBoundary();
        Vector2d upperBoundary = map.getUpperBoundary();
        int numberColumns = upperBoundary.getX() - lowerBoundary.getX();
        int numberRows = upperBoundary.getY() - lowerBoundary.getY();
        return gridCreator.createGrid(canClick,option,numberColumns,numberRows,lowerBoundary);
    }

    public void handleMouseClick(Vector2d vector2d) {
        ArrayList<Animal> animalArrayList= map.getAnimals().get(vector2d);
        trackedAnimal = map.findStrongestAnimal(animalArrayList);
        animalStatsPrint = true;
        stopTrackButton.setDisable(false);
    }

    public void trackAnimal(){
        clearGrid(mapGrid);
        mapGrid.getChildren().add(gridCreator(true,0));
        if (animalStatsPrint){
            clearGrid(gridAnimalStats);
            gridAnimalStats.getChildren().add(gridAnimalStatsCreator());
        }
    }

    public void stopTrackAnimal(){
        animalStatsPrint = false;
        clearGrid(gridAnimalStats);
        stopTrackButton.setDisable(true);
    }

    public void showStatistic(){
        statsPrint = true;
        showStatsButton.setDisable(true);
        hideStatsButton.setDisable(false);
    }

    public void hideStatistic(){
        statsPrint = false;
        showStatsButton.setDisable(false);
        hideStatsButton.setDisable(true);
        clearGrid(gridMapStats);
    }

    public void onSimulationStartClicked() {
        simulation.setSimulationPlay(true);
        SimulationEngine simulationEngine = new SimulationEngine(Collections.singletonList(simulation), 1);
        simulationEngine.runAsyncNoWait();
        startButton.setDisable(true);
        trackButton.setDisable(true);
        stopButton.setDisable(false);
        stopTrackButton.setDisable(true);
        showGrass.setDisable(true);
        showGenotype.setDisable(true);
    }

    public void onSimulationStopClicked() {
        simulation.setSimulationPlay(false);
        startButton.setDisable(false);
        trackButton.setDisable(false);
        stopButton.setDisable(true);
        if (animalStatsPrint){
            stopTrackButton.setDisable(false);
        }
        showGrass.setDisable(false);
        showGenotype.setDisable(false);
    }

    public void grassFieldShow(){
        clearGrid(mapGrid);
        mapGrid.getChildren().add(gridCreator(false,2));
    }

    public void genotypeShow(){
        if (map.getStatsKeeper().getMostPopularGenotype() != null){
            clearGrid(mapGrid);
            mapGrid.getChildren().add(gridCreator(false,1));
        }
    }

    private void clearGrid(GridPane gridPane) {
        gridPane.getChildren().retainAll();
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
    }

}
