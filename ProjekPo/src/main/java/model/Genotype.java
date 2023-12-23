package model;

import enums.MapDirection;

public class Genotype {
    private final MapDirection[] genes;

    public Genotype(MapDirection[] genes) {
        this.genes = genes;
    }

    public MapDirection[] getGenes() {
        return genes.clone();
    }

    public int getGeneLength() {
        return genes.length;
    }

    public void swapGene(int index1, int index2) {
        if (index1 < 0 || index2 < 0 || index1 >= genes.length || index2 >= genes.length) {
            throw new IndexOutOfBoundsException("Indeksy genów wykraczają poza tablice genotypu");
        }
        MapDirection temp = genes[index1];
        genes[index1] = genes[index2];
        genes[index2] = temp;
    }

    public MapDirection geneAt(int index){
        if (index < 0 || index >= genes.length) {
            throw new IndexOutOfBoundsException("Indeks wykracza poza tablice genotypu");
        }
        return genes[index];
    }
}
