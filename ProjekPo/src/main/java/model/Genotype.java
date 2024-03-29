package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Genotype {
    private final int[] genes;
    Random random = new Random();

    public Genotype(int[] genes) {
        this.genes = genes;
    }

    public Genotype(int numberOfGenes){
        ArrayList<Integer> genes = new ArrayList<>();
        for (int i = 0; i < numberOfGenes; i++) {
            genes.add(randomGene());
        }
        this.genes = genes.stream().mapToInt(Integer::intValue).toArray();
    }

    public void setRandomSeed(int seed){
        random.setSeed(seed);
    }

    public int[] getGenes() {
        return genes.clone();
    }

    public int getGeneLength() {
        return genes.length;
    }

    public void swapGene(int index1, int index2) {
        if (index1 < 0 || index2 < 0 || index1 >= genes.length || index2 >= genes.length) {
            throw new IndexOutOfBoundsException("Indeksy genów wykraczają poza tablice genotypu");
        }
        int temp = genes[index1];
        genes[index1] = genes[index2];
        genes[index2] = temp;
    }

    public void changeGene(int index){
        int randomNumber = random.nextInt(6);
        if (randomNumber != 0){
            if (index < 0 ||  index >= genes.length) {
                throw new IndexOutOfBoundsException("Indeks genu wykraczają poza tablice genotypu");
            }
            genes[index] = randomGene();
        }
        else {
            int randomIndex = random.nextInt(genes.length);
            swapGene(index,randomIndex);
        }
    }

    public void changeGene(){
        changeGene(random.nextInt(genes.length));
    }

    private int randomGene(){
        return random.nextInt(8);
    }

    public int geneAt(int index){
        if (index < 0 || index >= genes.length) {
            throw new IndexOutOfBoundsException("Indeks wykracza poza tablice genotypu");
        }
        return genes[index];
    }
    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        if(((Genotype) other).getGeneLength() != getGeneLength()) return false;
        for(int i = 0; i < getGeneLength(); i++){
            if (geneAt(i) != ((Genotype) other).geneAt(i)) return false;
        }
        return true;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<");
        for(int gene : genes){
            stringBuilder.append(gene);
        }
        stringBuilder.append(">");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode(){
        return Arrays.hashCode(genes);
    }
}
