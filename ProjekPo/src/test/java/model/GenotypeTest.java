package model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GenotypeTest {

    @Test
    void swapGene() {
        Genotype genotype = new Genotype(new int[]{1,2,3,4});
        genotype.swapGene(0,2);
        assertArrayEquals(new int[]{3,2,1,4}, genotype.getGenes());
    }

    @Test
    void changeGeneChangeRandom() {
        Genotype genotype = new Genotype(new int[]{1,2,3,4});
        genotype.setRandomSeed(1000);
        genotype.changeGene(2);
        assertArrayEquals(new int[]{1,2,1,4},genotype.getGenes());
    }
    @Test
    void changeGeneSwapTwo() {
        Genotype genotype = new Genotype(new int[]{1,2,3,4});
        genotype.setRandomSeed(1500);
        genotype.changeGene(2);
        assertArrayEquals(new int[]{3,2,1,4},genotype.getGenes());
    }

    @Test
    void geneAt() {
        Genotype genotype = new Genotype(new int[]{1,2,3,4});
        genotype.swapGene(0,2);
        assertEquals(2, genotype.geneAt(1));
    }
}