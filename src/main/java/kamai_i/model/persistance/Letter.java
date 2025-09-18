package kamai_i.model.persistance;

import java.util.Arrays;

import kamai_i.utils.Colors;

public class Letter {

    private int id = -1;
    private String character;
    private String characterAscii;

    public Letter(String character, String characterAscii) {

        if (character == null || characterAscii == null) {
            throw new IllegalArgumentException(Colors.error("both parameters cannot be null"));
        }
        if (character.trim().length() == 0 || characterAscii.trim().length() == 0) {
            throw new IllegalArgumentException(Colors.error("both parameters cannot be empty"));
        }

        this.character = character;
        this.characterAscii = characterAscii;
    }

    public Letter() {
        character = "";
        characterAscii = "";
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
        if (character == null) {
            throw new IllegalArgumentException(Colors.error("Letter.setCharacter:","character cannot be null"));
        }
        if (character.trim().length() == 0) {
            throw new IllegalArgumentException(Colors.error("Letter.setCharacter:","character cannot be empty"));
        }
        this.character = character;
    }

    public void setCharacterAscii(String characterAscii) {
        if (characterAscii == null) {
            throw new IllegalArgumentException(Colors.error("Letter.setCharacterAscii:","characterAscii cannot be null"));
        }
        if (characterAscii.trim().length() == 0) {
            throw new IllegalArgumentException(Colors.error("Letter.setCharacterAscii:","characterAscii cannot be empty"));
        }
        this.characterAscii = characterAscii;
    }

    @Override
    public String toString() {
        return character + " " + characterAscii + " (" + id + ")";
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
        return character.equals(other.character) && characterAscii.equals(other.characterAscii);
    }
}
