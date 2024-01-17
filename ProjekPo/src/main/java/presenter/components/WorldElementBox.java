package presenter.components;


import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


import javafx.scene.image.Image;
import model.Genotype;
import model.animal.Animal;
import model.animal.AnimalComparator;
import model.Vector2d;
import model.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class WorldElementBox {

    private final int startEnergy;
    private final WorldMap map;
    private final AnimalComparator ANIMAL_COMPARATOR = new AnimalComparator();

    private static final Image LOW = new Image("images/low.png");
    private static final Image SEMI = new Image("images/semi.png");
    private static final Image MID = new Image("images/mid.png");
    private static final Image MID_MORE = new Image("images/mid-more.png");
    private static final Image FULL = new Image("images/full.png");
    private static final Image GRASS = new Image("images/trawa.png");

    public WorldElementBox(int startEnergy, WorldMap map) {
        this.map = map;
        this.startEnergy = startEnergy;
    }

    public VBox createVbox(Vector2d vector2d, float cellWidth, float cellHeight, int option) {
        VBox vBox;
        Image img = fileNameMatch(vector2d);
        if (img != null) {
            ImageView imageView = createImage(img, cellWidth, cellHeight);
            vBox = new VBox(imageView);
            vBox.setAlignment(Pos.CENTER);
        } else {
            vBox = new VBox();
        }
        if (option == 1) {
            if (genotypeAtPosition(vector2d)) {
                vBox.setStyle("-fx-background-color: #34CFE7;");
            }
        } else if (option == 2) {
            if (vector2d.getY() >= (int) Math.floor(map.getUpperBoundary().getY() * 0.4) && vector2d.getY() < (int) Math.floor(map.getUpperBoundary().getY() * 0.4) + (int) Math.round(map.getUpperBoundary().getY() * 0.2)) {
                vBox.setStyle("-fx-background-color: #88F979;");
            }
        }
        vBox.setMaxWidth(cellWidth);
        vBox.setMaxHeight(cellHeight);
        return vBox;
    }

    private Image fileNameMatch(Vector2d vector2d) {
        ArrayList<Animal> animals = map.getAnimals().get(vector2d);
        if (!animals.isEmpty()) {
            Animal animal = findStrongestAnimal(animals);
            return animalTexture(animal.getEnergyLevel());
        } else if (map.getPlants().get(vector2d) != null) {
            return GRASS;
        }
        return null;
    }

    private Animal findStrongestAnimal(ArrayList<Animal> animalsOnPosition) {
        Animal maxEnergyAnimal;
        maxEnergyAnimal = animalsOnPosition.get(0);
        for (Animal animal : animalsOnPosition) {
            if (ANIMAL_COMPARATOR.compare(maxEnergyAnimal, animal) > 0) {
                maxEnergyAnimal = animal;
            }
        }
        return maxEnergyAnimal;
    }

    private boolean genotypeAtPosition(Vector2d vector2d) {
        ArrayList<Animal> animals = map.getAnimals().get(vector2d);
        List<Genotype> mostPopularGenotypes = map.getStatsKeeper().getAllMostPopularGenotypes();
        if (!Objects.isNull(mostPopularGenotypes)) {
            for (Animal animal : animals) {
                if (mostPopularGenotypes.contains(animal.getGenotype())) {
                    return true;
                }
            }
        }
        return false;
    }

    private Image animalTexture(int energy) {
        float energyLevel = (float) energy / startEnergy;
        if (energyLevel <= 0.2) {
            return LOW;
        } else if (energyLevel <= 0.4) {
            return SEMI;
        } else if (energyLevel <= 0.6) {
            return MID;
        } else if (energyLevel <= 0.8) {
            return MID_MORE;
        } else {
            return FULL;
        }
    }

    private ImageView createImage(Image image, float cellWidth, float cellHeight) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(cellWidth);
        imageView.setFitHeight(cellHeight);
        return imageView;
    }
}
