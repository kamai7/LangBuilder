package model.algo;

import java.util.HashSet;

import model.persistance.Word;

class WordGenerator {

    Word word;
    HashSet<Word> find;
    HashSet<Word> links;
    HashSet<Word> radicals;

    public WordGenerator(Word word) {

        if (word == null){
            throw new IllegalArgumentException("word cannot be null");
        } 

        this.word = word;
        find = new HashSet<>();
        links = new HashSet<>(word.getLinks());
        radicals = new HashSet<>(word.getRadicals());
    }
}