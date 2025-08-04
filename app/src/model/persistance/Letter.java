package model.persistance;

import java.util.Arrays;
import java.util.Objects;

import utils.Colors;

public class Letter {

    private int id = -1;
    private String character;
    private String characterAscii;

    public Letter(String character, String characterAscii) {

        if (character == null || characterAscii == null) {
            throw new IllegalArgumentException(Colors.error("both parameters cannot be null"));
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

    @Override
    public String toString() {
        return character + " -> " + characterAscii + " (" + id + ")";
    }

    @Override
    public int hashCode() {
        Object[] obj = {character, characterAscii};
        return Arrays.deepHashCode(obj);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Letter)) return false;
        Letter other = (Letter) obj;
        return id == other.id &&
               Objects.equals(character, other.character) &&
               Objects.equals(characterAscii, other.characterAscii);
    }
}
