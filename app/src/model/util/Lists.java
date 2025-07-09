package model.util;

import java.util.ArrayList;

public class Lists {

    public static <U> ArrayList<U> intersect(ArrayList<U> a, ArrayList<U> b){
        ArrayList<U> ret = new ArrayList<U>();
        for (U u : a) {
            if(b.contains(u)){
                ret.add(u);
            }
        }
        return ret;
    }
    
}
