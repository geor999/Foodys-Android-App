package Listeners;

import Foods.Root;

public interface RecipeDetailsListener {
    void didGood(Root response , String msg);
    void didBad(String msg);
}
