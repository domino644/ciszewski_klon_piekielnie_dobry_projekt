package presenter;

import interfaces.MapChangeListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.*;

import java.util.Collections;

public class SimulationPresenter implements MapChangeListener {
    @FXML
    private Label animalQuantityLabel;
    @FXML
    private Label plantQuantityLabel;
    @FXML
    private Label freeFieldsQuantityLabel;
    @FXML
    private Label mostPopularGenotypeLabel;
    @FXML
    private Label averageEnergyLevelLabel;
    @FXML
    private Label averageLifetimeLabel;
    @FXML
    private Label averageNumberOfKidsLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    private WorldMap map;
    private Simulation simulation;
    private WorldElementBox worldElementBox;
    private static final int GRID_WIDTH = 500;
    private static final int GRID_HEIGHT = 500;
    private float cellWidth;
    private float cellHeight;

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap();
            messageLabel.setText(message);
            updateStats();
        });
    }

    public void setWorldMap(WorldMap map) {
        this.map = map;
        Vector2d lowerBoundary = map.getLowerBoundary();
        Vector2d upperBoundary = map.getUpperBoundary();
        int numberColumns = upperBoundary.getX() - lowerBoundary.getX();
        int numberRows = upperBoundary.getY() - lowerBoundary.getY();
        cellWidth = (float) GRID_WIDTH / numberColumns;
        cellHeight = (float) GRID_HEIGHT / numberRows;
        mapGrid.getChildren().add(gridCreator());
        this.worldElementBox = worldElementBox;
    }

    public void setWorldSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void setWorldElementBox(WorldElementBox worldElementBox) {
        this.worldElementBox = worldElementBox;
    }

    public void drawMap() {
        clearGrid();
        mapGrid.getChildren().add(gridCreator());
    }

    private GridPane gridCreator() {
        Vector2d lowerBoundary = map.getLowerBoundary();
        Vector2d upperBoundary = map.getUpperBoundary();
        int numberColumns = upperBoundary.getX() - lowerBoundary.getX();
        int numberRows = upperBoundary.getY() - lowerBoundary.getY();
        GridPane gridPane = new GridPane();
        Label label;
        int cordX, cordY;
        gridPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        gridPane.getRowConstraints().add(new RowConstraints(cellHeight));
        for (int i = 0; i < numberRows; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(cellHeight));
            label = new Label((lowerBoundary.getY() + i) + "");
            double adjustedFontSize = FontResizer.calculateOptimalFontSize(label.getText(),label.getFont(),cellWidth);
            label.setFont(new Font(adjustedFontSize));
            gridPane.add(label, 0, numberRows - i);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        for (int i = 0; i < numberColumns; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
            label = new Label((lowerBoundary.getX() + i + ""));
            double adjustedFontSize = FontResizer.calculateOptimalFontSize(label.getText(),label.getFont(),cellWidth);
            label.setFont(new Font(adjustedFontSize));
            gridPane.add(label, 1 + i, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        label = new Label("y\\x");
        double adjustedFontSize = FontResizer.calculateOptimalFontSize(label.getText(),label.getFont(),cellWidth);
        label.setFont(new Font(adjustedFontSize));
        gridPane.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
        for (int i = 0; i < numberRows; i++) {
            for (int j = 0; j < numberColumns; j++) {
                cordY = lowerBoundary.getY() + i;
                cordX = lowerBoundary.getX() + j;
//                String element = map.objectAt(new Vector2d(cordX, cordY));
//                label = new Label(Objects.requireNonNullElse(element, " "));
//                adjustedFontSize = FontResizer.calculateOptimalFontSize(label.getText(),label.getFont(),cellWidth);
//                label.setFont(new Font(adjustedFontSize));
                VBox vBox = worldElementBox.createVbox(new Vector2d(cordX, cordY),cellWidth,cellHeight);
                gridPane.add(vBox, j + 1, numberRows - i);
                GridPane.setHalignment(vBox, HPos.CENTER);
            }
        }
        gridPane.setGridLinesVisible(true);
        return gridPane;
    }

    public void onSimulationStartClicked() {
        simulation.setSimulationPlay(true);
        SimulationEngine simulationEngine = new SimulationEngine(Collections.singletonList(simulation), 1);
        simulationEngine.runAsyncNoWait();
        startButton.setDisable(true);
        stopButton.setDisable(false);
    }

    public void onSimulationStopClicked() {
        simulation.setSimulationPlay(false);
        startButton.setDisable(false);
        stopButton.setDisable(true);
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll();
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void updateStats(){
        StatsKeeper statsKeeper = map.getStatsKeeper();
        animalQuantityLabel.setText(String.valueOf(statsKeeper.getNumberOfAliveAnimals()));
        plantQuantityLabel.setText(String.valueOf(statsKeeper.getNumberOfPlants()));
        freeFieldsQuantityLabel.setText(String.valueOf(statsKeeper.getNumberOfFreeFields()));
        if (statsKeeper.getMostPopularGenotype() != null){
        mostPopularGenotypeLabel.setText(statsKeeper.getMostPopularGenotype().toString());}
        averageEnergyLevelLabel.setText(String.valueOf(statsKeeper.getAverageEnergyLevel()));
        averageLifetimeLabel.setText(String.valueOf(statsKeeper.getAverageLifetime()));
        averageNumberOfKidsLabel.setText(String.valueOf(statsKeeper.getAvgNumberOfKids()));
    }
}
