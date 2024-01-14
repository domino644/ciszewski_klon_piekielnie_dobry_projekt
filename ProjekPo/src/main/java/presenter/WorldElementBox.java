package presenter;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


import javafx.scene.image.Image;
import javafx.scene.text.Font;
import model.Animal;
import model.AnimalComparator;
import model.Vector2d;
import model.WorldMap;

import java.util.ArrayList;


public class WorldElementBox {

    private final int startEnergy;
    private final WorldMap map;
    private final AnimalComparator ANIMAL_COMPARATOR = new AnimalComparator();

    public WorldElementBox(int startEnergy,WorldMap map) {
        this.map = map;
        this.startEnergy = startEnergy;
    };
    public VBox createVbox(Vector2d vector2d,float cellWidth,float cellHeight){
        VBox vBox;
        String fileName = fileNameMatch(vector2d);
        if (fileName != null){
//            Label label = new Label(vector2d.toString());
//            double adjustedFontSize = FontResizer.calculateOptimalFontSize(label.getText(),label.getFont(),cellWidth);
//            label.setFont(new Font(adjustedFontSize));
            ImageView imageView = createImage(fileName,cellWidth,cellHeight);
            vBox = new VBox(imageView);
            vBox.setAlignment(Pos.CENTER);
        }
        else{
            vBox = new VBox();
        }
        vBox.setMaxWidth(0.9*cellWidth);
        vBox.setMaxHeight(0.9*cellHeight);
        return vBox;
    }

    private String fileNameMatch(Vector2d vector2d){
        ArrayList<Animal> animals = map.getAnimals().get(vector2d);
        if (!animals.isEmpty()){
            Animal animal = findStrongestAnimal(animals);
            return animalTexture(animal.getEnergyLevel());
        }
        else if (map.getPlants().get(vector2d) != null) {
            return "trawa.png";
        }
        return null;
    }

    private Animal findStrongestAnimal(ArrayList<Animal> animalsOnPosition){
        Animal maxEnergyAnimal;
        maxEnergyAnimal = animalsOnPosition.get(0);
        for (Animal animal : animalsOnPosition) {
            if (ANIMAL_COMPARATOR.compare(maxEnergyAnimal, animal) > 0){
                maxEnergyAnimal = animal;
            }
        }
        return maxEnergyAnimal;
    }

    private String animalTexture(int energy){
        float energyLevel = (float) energy/startEnergy;
        if (energyLevel <= 0.2){
            return "low.png";
        }
        else if (energyLevel <= 0.4){
            return "semi.png";
        }
        else if (energyLevel <= 0.6){
            return "mid.png";
        }
        else if (energyLevel <= 0.8){
            return "mid-more.png";
        }
        else {
            return "full.png";
        }
    }

    private ImageView createImage(String fileName,float cellWidth,float cellHeight){
        Image image = new Image(fileName);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(cellWidth);
        imageView.setFitHeight(cellHeight);
        return imageView;
    }
}
