package model.persistance;

import java.util.Objects;

public class Letter {

    private int id = -1;
    private String character;
    private String characterAscii;

    public Letter(String character, String characterAscii) {

        if (character == null || characterAscii == null) {
            throw new IllegalArgumentException("both parameters cannot be null");
        }

        this.character = character;
        this.characterAscii = characterAscii;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharacter() {
        return character;
    }

    public String getCharacterAscii() {
        return characterAscii;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setCharacterAscii(String characterAscii) {
        this.characterAscii = characterAscii;
    }
/*
    public void updateValues() {

        LetterDAO letterDAO = new LetterDAO();
        id = letterDAO.findId(this);

        if (character == null) {
            this.character = letterDAO.findCharacter(this);
        }

        if(characterAscii == null) {
            this.characterAscii = letterDAO.findCharacterAscii(this);
        }
    }
*/
    public String toString() {
        return character + " -> " + characterAscii + " (" + id + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
}
