package kamai_i.model.algo;

import kamai_i.utils.Colors;

public class Neuron {
    
    /**
     * Neuron inputs weights
     */
    private final double[] weights;

    /**
     * Neuron inputs neurons
     */
    private Neuron[] inputs;

    private double bias;

    private double value;

    public Neuron(Neuron[] inputs) {
        if (inputs == null) {
            throw new IllegalArgumentException(Colors.error("Neuron:", "inputs cannot be null"));
        }
        this.weights = new double[inputs.length];
        this.inputs = inputs;
    }

    public Neuron(int size, double bias) {
        if (inputs == null) {
            throw new IllegalArgumentException(Colors.error("Neuron:", "inputs cannot be null"));
        } if (bias < -1 || bias > 1) {
            throw new IllegalArgumentException(Colors.error("Neuron:", "bias must be between -1 and 1"));
        }
        this.weights = new double[size];
        this.bias = bias;
    }

    public double get() {
        return value;
    }

    public void setBias(double bias) {
        if (bias < -1 || bias > 1) {
            throw new IllegalArgumentException(Colors.error("Neuron:", "bias must be between -1 and 1"));
        }
        this.bias = bias;
    }

    public double getBias() {
        return bias;
    }

    public double[] getWeights() {
        return weights;
    }

    public void set(double value){
        this.value = Math.max(0, Math.min(1, value));
    }
}
