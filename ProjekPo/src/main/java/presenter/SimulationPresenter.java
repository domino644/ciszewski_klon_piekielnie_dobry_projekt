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
import model.SimulationEngine;
import model.Vector2d;
import model.WorldMap;

import java.util.Arrays;
import java.util.Collections;

public class SimulationPresenter implements MapChangeListener {
    @FXML
    private Label messageLabel;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Button startButton;
    private WorldMap map;

    static final int CELL_WIDTH = 30;
    static final int CELL_HEIGHT = 30;

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap();
            messageLabel.setText(message);
        });
    }

    public void setWorldMap(WorldMap map) {
        this.map = map;
        mapGrid.getChildren().add(gridCreator());
    }

    public void drawMap(){
        clearGrid();
        mapGrid.getChildren().add(gridCreator());
    }

    private GridPane gridCreator(){
        Vector2d lowerBoundary = map.getLowerBoundary();
        Vector2d upperBoundary = map.getUpperBoundary();
        int numberColumns = upperBoundary.getX() - lowerBoundary.getX()+1;
        int numberRows = upperBoundary.getY() - lowerBoundary.getY()+1;
        GridPane gridPane = new GridPane();
        Label label;
        int cordX,cordY;
        gridPane.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        gridPane.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        for (int i = 0; i < numberRows; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
            label = new Label((lowerBoundary.getY()+i)+"");
            gridPane.add(label,0,numberRows-i);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        for (int i = 0; i < numberColumns; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
            label = new Label((lowerBoundary.getX()+i+""));
            gridPane.add(label,1+i,0);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        label =new Label("y\\x");
        gridPane.add(label,0,0);
        GridPane.setHalignment(label, HPos.CENTER);
        for (int i = 0; i < numberRows; i++) {
            for (int j = 0; j < numberColumns; j++) {
                cordY = lowerBoundary.getY()+i;
                cordX = lowerBoundary.getX()+j;
                String element = map.objectAt(new Vector2d(cordX,cordY));
                if (element != null){
                    label = new Label(element);
                }
                else{
                    label = new Label(" ");
                }
                gridPane.add(label,j+1,numberRows-i);
                GridPane.setHalignment(label, HPos.CENTER);
            }
        }
        gridPane.setGridLinesVisible(true);
        return gridPane;
    }

    public void onSimulationStartClicked() {
        SimulationEngine simulationEngine = new SimulationEngine(Collections.singletonList(map),1);
        simulationEngine.runAsyncNoWait();
        startButton.setDisable(true);

    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll();
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
}
