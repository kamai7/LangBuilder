package model.algo;

import java.util.HashSet;

import model.persistance.Word;

class WordGenerator {

    Word word;
    HashSet<Word> find;
    HashSet<Word> links;
    HashSet<Word> roots;

    public WordGenerator(Word word) {

        if (word == null){
            throw new IllegalArgumentException("word cannot be null");
        } 

        this.word = word;
        find = new HashSet<>();
        links = new HashSet<>(word.getLinks());
        roots = new HashSet<>(word.getRoots());
    }
}