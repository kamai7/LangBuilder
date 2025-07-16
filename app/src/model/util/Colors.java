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
    public static final String UNDERLINE_RED = "\033[4;31m";
    public static final String UNDERLINE_GREEN = "\033[4;32m";


    public static String error(String message){
        return RED + message + RESET;
    }

    public static String error(String message, String error){
        return BOLD_RED + message + RESET + " ->" + UNDERLINE_RED + error + RESET;
    }

    public static String success(String message){
        return GREEN + message + RESET;
    }

    public static String success(String message, String success){
        return BOLD_GREEN + message + RESET + " ->" + UNDERLINE_GREEN + success + RESET;
    }

    public static String warning(String message){
        return YELLOW + message + RESET;
    }

}
