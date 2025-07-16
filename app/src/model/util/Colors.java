package model.util;

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

}
