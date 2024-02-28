package Listeners;

import Foods.Rootee;

public interface Listener {
    // on all the listener we pass the recipes list from mainthing and a string msg

    void didGood(Rootee thing, String msg);

    void didBad(String msg) ;

}
