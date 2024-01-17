package presenter;

import interfaces.MapChangeListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.*;
import model.animal.Animal;
import model.simulation.Simulation;
import model.simulation.SimulationEngine;
import presenter.components.WorldElementBox;
import presenter.utils.FontResizer;

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
    private WorldElementBox worldElementBox;
    private static final int GRID_WIDTH = 500;
    private static final int GRID_HEIGHT = 500;
    private float cellWidth;
    private float cellHeight;
    private boolean animalStatsPrint = false;
    private boolean statsPrint = false;
    private Animal trackedAnimal;

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(this::drawMap);
    }

    public void setWorldMap(WorldMap map) {
        this.map = map;
        Vector2d lowerBoundary = map.getLowerBoundary();
        Vector2d upperBoundary = map.getUpperBoundary();
        int numberColumns = upperBoundary.getX() - lowerBoundary.getX();
        int numberRows = upperBoundary.getY() - lowerBoundary.getY();
        cellWidth = (float) GRID_WIDTH / numberColumns;
        cellHeight = (float) GRID_HEIGHT / numberRows;
        mapGrid.getChildren().add(gridCreator(false,0));
    }

    public void setWorldSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void setWorldElementBox(WorldElementBox worldElementBox) {
        this.worldElementBox = worldElementBox;
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
        GridPane gridPane = new GridPane();
        Label label;
        String[] namesRow = {"Liczba zwierząt żywych","Liczba zwierząt martwych","Liczba roślin","Liczba wolnych pól",
                "Średni poziom Energi","Średnia długość życia","Średnia liczba dzieci","Najpopularniejszy genotyp"};
        String[] values = map.getStatsKeeper().stringArgumentsGet();
        gridPane.getColumnConstraints().add(new ColumnConstraints(firstColumnWidth));
        gridPane.getColumnConstraints().add(new ColumnConstraints(secondColumnWidth));
        RowConstraints rowConstraints = new RowConstraints(rowHeight);
        for (int i = 0; i < 8; i++) {
            gridPane.getRowConstraints().add(rowConstraints);
            label = scaleFontLabel(namesRow[8-i-1],firstColumnWidth);
            gridPane.add(label, 0, 8-i-1);
            GridPane.setHalignment(label, HPos.CENTER);
            label = scaleFontLabel(values[8-i-1],secondColumnWidth);
            gridPane.add(label, 1, 8-i-1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        return gridPane;
    }

    private GridPane gridAnimalStatsCreator(){
        int firstColumnWidth = 130;
        int secondColumnWidth = 70;
        int rowHeight = 50;
        GridPane gridPane = new GridPane();
        Label label;
        String[] namesRow = {"Pozycja","Orientacja","Genotyp","Aktywny genom","Energia","Zjedzona trawa",
        "Ilość dzieci","Ilość krewnych","Wiek","Data urodzenia"};
        String[] values = trackedAnimal.getAnimalStats().animalStringStats();
        gridPane.getColumnConstraints().add(new ColumnConstraints(firstColumnWidth));
        gridPane.getColumnConstraints().add(new ColumnConstraints(secondColumnWidth));
        RowConstraints rowConstraints = new RowConstraints(rowHeight);
        for (int i = 0; i < 10; i++) {
            gridPane.getRowConstraints().add(rowConstraints);
            label = scaleFontLabel(namesRow[10-i-1],firstColumnWidth);
            gridPane.add(label, 0, 10-i-1);
            GridPane.setHalignment(label, HPos.CENTER);
            label = scaleFontLabel(values[10-i-1],secondColumnWidth);
            gridPane.add(label, 1, 10-i-1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        return gridPane;
    }



    private GridPane gridCreator(boolean canClick,int option) {
        Vector2d lowerBoundary = map.getLowerBoundary();
        Vector2d upperBoundary = map.getUpperBoundary();
        int numberColumns = upperBoundary.getX() - lowerBoundary.getX();
        int numberRows = upperBoundary.getY() - lowerBoundary.getY();
        GridPane gridPane = new GridPane();
        prepareGridFrame(gridPane,numberRows,numberColumns,lowerBoundary);
        prepareGridValues(gridPane,numberRows,numberColumns,lowerBoundary,canClick,option);
        gridPane.setGridLinesVisible(true);
        return gridPane;
    }

    private void prepareGridFrame(GridPane gridPane,int numberRows,int numberColumns,Vector2d lowerBoundary){
        Label label;
        gridPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        gridPane.getRowConstraints().add(new RowConstraints(cellHeight));
        for (int i = 0; i < numberRows; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(cellHeight));
            label = scaleFontLabel((lowerBoundary.getY() + i) + "",cellWidth);
            gridPane.add(label, 0, numberRows - i);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        for (int i = 0; i < numberColumns; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
            label = scaleFontLabel((lowerBoundary.getX() + i + ""),cellWidth);
            gridPane.add(label, 1 + i, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        label = scaleFontLabel("y\\x",cellWidth);
        gridPane.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
    }

    private void prepareGridValues(GridPane gridPane,int numberRows,int numberColumns,Vector2d lowerBoundary,boolean canClick,int option){
        int cordX, cordY;
        Vector2d vector2d;
        for (int i = 0; i < numberRows; i++) {
            for (int j = 0; j < numberColumns; j++) {
                cordY = lowerBoundary.getY() + i;
                cordX = lowerBoundary.getX() + j;
                vector2d = new Vector2d(cordX, cordY);
                VBox vBox = worldElementBox.createVbox(vector2d,cellWidth,cellHeight,option);
                if (canClick && !map.getAnimals().get(vector2d).isEmpty()){
                    Vector2d finalVector2d = vector2d;
                    vBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleMouseClick(finalVector2d));
                }
                gridPane.add(vBox, j + 1, numberRows - i);
                GridPane.setHalignment(vBox, HPos.CENTER);
            }
        }
    }

    private void handleMouseClick(Vector2d vector2d) {
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

    private Label scaleFontLabel(String message,float cellWidth){
        Label label = new Label((message));
        double adjustedFontSize = FontResizer.calculateOptimalFontSize(label.getText(),label.getFont(),cellWidth);
        label.setFont(new Font(adjustedFontSize));
        return label;
    }

}
