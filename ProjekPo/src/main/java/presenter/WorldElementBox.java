package presenter;


import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


import javafx.scene.image.Image;
import model.Animal;
import model.AnimalComparator;
import model.Vector2d;
import model.WorldMap;

import java.util.ArrayList;


public class WorldElementBox {

    private final int startEnergy;
    private final WorldMap map;
    private final AnimalComparator ANIMAL_COMPARATOR = new AnimalComparator();

    private static final Image LOW = new Image("low.png");
    private static final Image SEMI = new Image("semi.png");
    private static final Image MID = new Image("mid.png");
    private static final Image MID_MORE = new Image("mid-more.png");
    private static final Image FULL = new Image("full.png");
    private static final Image GRASS = new Image("trawa.png");

    public WorldElementBox(int startEnergy,WorldMap map) {
        this.map = map;
        this.startEnergy = startEnergy;
    }
    public VBox createVbox(Vector2d vector2d,float cellWidth,float cellHeight){
        VBox vBox;
        Image img = fileNameMatch(vector2d);
        if (img != null){
            ImageView imageView = createImage(img,cellWidth,cellHeight);
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

    private Image fileNameMatch(Vector2d vector2d){
        ArrayList<Animal> animals = map.getAnimals().get(vector2d);
        if (!animals.isEmpty()){
            Animal animal = findStrongestAnimal(animals);
            return animalTexture(animal.getEnergyLevel());
        }
        else if (map.getPlants().get(vector2d) != null) {
            return GRASS;
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

    private Image animalTexture(int energy){
        float energyLevel = (float) energy/startEnergy;
        if (energyLevel <= 0.2){
            return LOW;
        }
        else if (energyLevel <= 0.4){
            return SEMI;
        }
        else if (energyLevel <= 0.6){
            return MID;
        }
        else if (energyLevel <= 0.8){
            return MID_MORE;
        }
        else {
            return FULL;
        }
    }

    private ImageView createImage(Image image,float cellWidth,float cellHeight){
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(cellWidth);
        imageView.setFitHeight(cellHeight);
        return imageView;
    }
}
