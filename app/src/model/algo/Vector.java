package model.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import model.persistance.Letter;
import model.persistance.Word;
import utils.Colors;
import utils.ListsUtils;

public class Vector {

    private Word vector;

    /**
     * the weightmap of the word length
     * the key represents the possible lengths
     * and the value, the weight.
     */
    private Map<Integer, Double> lengthWeight;

    /**
     * List of weightmap of the letters, each element of the list is a possible letter for the word
     * 
     * the key represents the possible letters represented by their ids, to save efficiency and memory
     * and the value, the weight
     */
    private ArrayList<Map<Integer, Double>> vectorLetters;

    /**
     * each letters that are used in the whole vector. letters index represent their id in the letters weightmap
     */
    private ArrayList<Letter> existingLetters;

    private int length = -1;

    public int findLength() {
        Collection<Double> weightCollection = lengthWeight.values();
        ArrayList<Double> weightList = new ArrayList<>(weightCollection);
        double max = ListsUtils.max(weightList);
        this.length = weightList.indexOf(max) + 1;
        return this.length;
    }

    public int getLength() {
        if (length == -1) {
            throw new RuntimeException(Colors.error("Verctor.getLength","length must be calculated first (hint: findLength())"));
        }
        return length;
    }
    
}
