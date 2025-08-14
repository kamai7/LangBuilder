package utils;

import java.util.ArrayList;
import java.util.List;

public class ListsUtils {

    public static <T extends Number> T max(List<T> c){
        T max = c.get(0);
        for (T t : c) {
            if (t.doubleValue() > max.doubleValue()) {
                max = t;
            }
        }
        return max;
    }

    public static <T extends Number> T min(List<T> c){
        T min = c.get(0);
        for (T t : c) {
            if (t.doubleValue() < min.doubleValue()) {
                min = t;
            }
        }
        return min;
    }

    public static <T extends Number> List<Double> normalize(List<T> c){
        List<Double> ret = new ArrayList<>();
        T max = max(c);
        T min = min(c);
        for (T t : c) {
            ret.add((t.doubleValue() - min.doubleValue()) / (max.doubleValue() - min.doubleValue()));
        }
        return ret;
    }

    public static <T extends Number> T maxOccurrence(List<T> c){
        T max = c.get(0);
        int maxCount = 0;
        for (T t : c) {
            int count = 0;
            for (T t2 : c) {
                if (t.equals(t2)) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                max = t;
            }
        }
        return max;
    }
    
}
