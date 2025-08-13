package model.algo;

import java.util.Map;

import model.persistance.Word;

public class Vector {

    private Word vector;

    /**
     * the weightmap of the word length
     * the key represents the possible lengths
     * and the value, the weight.
     */
    private Map<Integer, Double> lengthWeight;

    /**
     * the weightmap of the letters
     * the main key represents each possible word length
     * 
     * the secondary key represents the possible letters represented by their ids
     * and the last value, the weight
     */
    private Map<Integer, Map<Integer, Double>> vectorLetters;
    
}
