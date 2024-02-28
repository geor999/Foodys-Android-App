package Listeners;


import java.util.List;

import Foods.SimilarRoot;

public interface SimilarRecipesListener {
    void didGood(List<SimilarRoot> thing, String msg);

    void didBad(String msg) ;
}
