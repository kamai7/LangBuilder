package kamai_i.model.algo;
import java.util.ArrayList;

public class NeuronalNetwork {

    ArrayList<ArrayList<Neuron>> layers;

    public NeuronalNetwork(int... layers) {
        this.layers = new ArrayList<>(layers.length);
        for (int i = 0; i < layers.length; i++) {
            ArrayList<Neuron> layer = new ArrayList<>(layers[i]);
            int connections = 0;
            if (i < layers.length - 1) {
                connections = layers[i + 1];
            }
            for (int j = 0; j < layers[i]; j++) {
                //layer.add(new Neuron(connections));
            }
        }
    }
    
}
