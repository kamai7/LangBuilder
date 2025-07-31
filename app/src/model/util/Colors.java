package model.util;

import javafx.scene.paint.Color;

public class Colors {
    public static final String RED = "\033[31m";
    public static final String RESET = "\033[0m";
    public static final String GREEN = "\033[32m";
    public static final String YELLOW = "\033[33m";
    public static final String BLUE = "\033[34m";
    public static final String PURPLE = "\033[35m";
    public static final String CYAN = "\033[36m";
    public static final String BOLD_RED="\033[1;31m";
    public static final String BOLD_GREEN="\033[1;32m";
    public static final String BOLD_YELLOW="\033[1;33m";
    public static final String BOLD_BLUE="\033[1;34m";
    public static final String BOLD_PURPLE="\033[1;35m";
    public static final String BOLD_CYAN="\033[1;36m";
    public static final String UNDERLINE_YELLOW = "\033[4;33m";
    public static final String UNDERLINE_RED = "\033[4;31m";
    public static final String UNDERLINE_GREEN = "\033[4;32m";
    public static final String UNDERLINE_BLUE = "\033[4;34m";
    public static final String UNDERLINE_PURPLE = "\033[4;35m";
    public static final String UNDERLINE_CYAN = "\033[4;36m";


    public static String error(String message){
        return RED + message + RESET;
    }

    public static String error(String message, String error){
        return BOLD_RED + message + " -> " + RESET + UNDERLINE_RED + error + RESET;
    }

    public static String success(String message){
        return GREEN + message + RESET;
    }

    public static String success(String message, String success){
        return BOLD_GREEN + message + " -> " + RESET + UNDERLINE_GREEN + success + RESET;
    }

    public static String info(String message){
        return YELLOW + message + RESET;
    }

    public static String info(String message, String warning){
        return BOLD_YELLOW + message + " -> " + RESET + UNDERLINE_YELLOW + warning + RESET;
    }

    public static Color[] calcGradient(Color color){
        double distance = 0.08;
        double col1R = Math.min(Math.max(0.0,color.getRed() - color.getRed() * distance),1.0);
        double col1G = Math.min(Math.max(0.0,color.getGreen() - color.getGreen() * distance),1.0);
        double col1B = Math.min(Math.max(0.0,color.getBlue() - color.getBlue() * distance),1.0);
        double col2R = Math.min(Math.max(0.0,color.getRed() + (color.getRed() - col1R)),1.0);
        double col2G = Math.min(Math.max(0.0,color.getGreen() + (color.getGreen() - col1G)),1.0);
        double col2B = Math.min(Math.max(0.0,color.getBlue() + (color.getBlue() - col1B)),1.0);
        Color[] ret = new Color[]{new Color(col1B, col1G, col1R, color.getOpacity()), new Color(col2B, col2R, col2G, color.getOpacity())};
        return ret;
    }

    public static Color convertRGBAToColor(int[] rgba) {
        return new Color(rgba[0] / 255.0, rgba[1] / 255.0, rgba[2] / 255.0, rgba[3] / 255.0);
    }

    public static String colorToHex(Color color) {
        return "#" + color.toString().substring(2);
    }

    public static String radialGradient(Color color1, Color color2) {
        return "radial-gradient(focus-distance 0%, center 60% 100%, radius 80%, " + colorToHex(color1) + ", " + colorToHex(color2) + ")";
    }

    public static String linearGradient(Color color1, Color color2) {
        return "linear-gradient(to bottom right, " + colorToHex(color1) + ", " + colorToHex(color2) + ")";
    }

}
