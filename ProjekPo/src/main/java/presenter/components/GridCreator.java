package presenter.components;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Vector2d;
import model.WorldMap;
import presenter.SimulationPresenter;
import presenter.utils.FontResizer;

import java.util.ArrayList;

public class GridCreator {

    private final WorldElementBox worldElementBox;
    private final float cellWidth;
    private final float cellHeight;
    private final WorldMap map;
    private final SimulationPresenter simulationPresenter;

    public GridCreator(WorldElementBox worldElementBox,WorldMap map,float cellWidth,float cellHeight,SimulationPresenter simulationPresenter){
        this.worldElementBox = worldElementBox;
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;
        this.map = map;
        this.simulationPresenter = simulationPresenter;
    }

    public static GridPane informationTableCreate(int firstColumnWidth,int secondColumnWidth,int rowHeight,
    String[] rowName,String[] values){
        GridPane gridPane = new GridPane();
        Label label;
        gridPane.getColumnConstraints().add(new ColumnConstraints(firstColumnWidth));
        gridPane.getColumnConstraints().add(new ColumnConstraints(secondColumnWidth));
        RowConstraints rowConstraints = new RowConstraints(rowHeight);
        int size = values.length;
        for (int i = 0; i < size; i++) {
            gridPane.getRowConstraints().add(rowConstraints);
            label = scaleFontLabel(rowName[size-i-1],firstColumnWidth);
            gridPane.add(label, 0, size-i-1);
            GridPane.setHalignment(label, HPos.CENTER);
            label = scaleFontLabel(values[size-i-1],secondColumnWidth);
            gridPane.add(label, 1, size-i-1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        return gridPane;
    }

    public GridPane createGrid(boolean canClick,int option,int numberColumns,int numberRows,Vector2d lowerBoundary){
        GridPane gridPane = new GridPane();
        prepareGridFrame(gridPane,numberRows,numberColumns,lowerBoundary);
        prepareGridValues(gridPane,numberRows,numberColumns,lowerBoundary,canClick,option);
        gridPane.setGridLinesVisible(true);
        return gridPane;
    }

    private void prepareGridFrame(GridPane gridPane, int numberRows, int numberColumns, Vector2d lowerBoundary){
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
                    vBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> simulationPresenter.handleMouseClick(finalVector2d));
                }
                gridPane.add(vBox, j + 1, numberRows - i);
                GridPane.setHalignment(vBox, HPos.CENTER);
            }
        }
    }

    private static Label scaleFontLabel(String message,float cellWidth){
        Label label = new Label((message));
        double adjustedFontSize = FontResizer.calculateOptimalFontSize(label.getText(),label.getFont(),cellWidth);
        label.setFont(new Font(adjustedFontSize));
        return label;
    }
}
