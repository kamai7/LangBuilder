package kamai_i.model.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kamai_i.utils.Colors;

public class Vector<T> {

    private List<T> vector;

    public Vector(T[] vector) {
        if (vector == null) {
            throw new IllegalArgumentException(Colors.error("Vector.Vector", "vector is null"));
        }
        this.vector = new ArrayList<>(Arrays.asList(vector));
    }

    public Vector(ArrayList<T> vector) {
        if (vector == null) {
            throw new IllegalArgumentException(Colors.error("Vector.Vector", "vector is null"));
        }
        this.vector = vector;
    }

    public Vector(int dimensions) {
        this.vector = new ArrayList<>(dimensions);

        for (int i = 0; i < dimensions; i++) {
            this.vector.add(null);
        }

    }
    
}
