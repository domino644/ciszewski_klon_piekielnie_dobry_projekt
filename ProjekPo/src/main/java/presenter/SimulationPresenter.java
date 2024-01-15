package presenter;

import interfaces.MapChangeListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Simulation;
import model.SimulationEngine;
import model.Vector2d;
import model.WorldMap;

import java.util.Collections;
import java.util.Objects;

public class SimulationPresenter implements MapChangeListener {
    @FXML
    private Label messageLabel;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button trackButton;
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
        mapGrid.getChildren().add(gridCreator(false));
    }

    public void setWorldSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void setWorldElementBox(WorldElementBox worldElementBox) {
        this.worldElementBox = worldElementBox;
    }

    public void drawMap() {
        clearGrid();
        mapGrid.getChildren().add(gridCreator(false));
    }

    private GridPane gridCreator(boolean canClick) {
        Vector2d lowerBoundary = map.getLowerBoundary();
        Vector2d upperBoundary = map.getUpperBoundary();
        int numberColumns = upperBoundary.getX() - lowerBoundary.getX();
        int numberRows = upperBoundary.getY() - lowerBoundary.getY();
        GridPane gridPane = new GridPane();
        prepareGridFrame(gridPane,numberRows,numberColumns,lowerBoundary);
        prepareGridValues(gridPane,numberRows,numberColumns,lowerBoundary,canClick);
        gridPane.setGridLinesVisible(true);
        return gridPane;
    }

    private void prepareGridFrame(GridPane gridPane,int numberRows,int numberColumns,Vector2d lowerBoundary){
        Label label;
        gridPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        gridPane.getRowConstraints().add(new RowConstraints(cellHeight));
        for (int i = 0; i < numberRows; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(cellHeight));
            label = scaleFontLabel((lowerBoundary.getY() + i) + "");
            gridPane.add(label, 0, numberRows - i);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        for (int i = 0; i < numberColumns; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
            label = scaleFontLabel((lowerBoundary.getX() + i + ""));
            gridPane.add(label, 1 + i, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        label = scaleFontLabel("y\\x");
        gridPane.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
    }

    private void prepareGridValues(GridPane gridPane,int numberRows,int numberColumns,Vector2d lowerBoundary,boolean canClick){
        int cordX, cordY;
        Vector2d vector2d;
        for (int i = 0; i < numberRows; i++) {
            for (int j = 0; j < numberColumns; j++) {
                cordY = lowerBoundary.getY() + i;
                cordX = lowerBoundary.getX() + j;
                vector2d = new Vector2d(cordX, cordY);
                VBox vBox = worldElementBox.createVbox(vector2d,cellWidth,cellHeight);
                if (canClick && !map.getAnimals().get(vector2d).isEmpty()){
                    vBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleMouseClick());
                }
                gridPane.add(vBox, j + 1, numberRows - i);
                GridPane.setHalignment(vBox, HPos.CENTER);
            }
        }
    }

    private void handleMouseClick() {
        System.out.println("Mouse Click detected on VBox!");
        // Add any additional actions you want to perform on mouse click
    }

    public void trackAnimal(){
        clearGrid();
        mapGrid.getChildren().add(gridCreator(true));
    }



    public void onSimulationStartClicked() {
        simulation.setSimulationPlay(true);
        SimulationEngine simulationEngine = new SimulationEngine(Collections.singletonList(simulation), 1);
        simulationEngine.runAsyncNoWait();
        startButton.setDisable(true);
        trackButton.setDisable(true);
        stopButton.setDisable(false);
    }

    public void onSimulationStopClicked() {
        simulation.setSimulationPlay(false);
        startButton.setDisable(false);
        trackButton.setDisable(false);
        stopButton.setDisable(true);
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll();
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private Label scaleFontLabel(String message){
        Label label = new Label((message));
        double adjustedFontSize = FontResizer.calculateOptimalFontSize(label.getText(),label.getFont(),cellWidth);
        label.setFont(new Font(adjustedFontSize));
        return label;
    }
}
