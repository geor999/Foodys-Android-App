package Listeners;

import java.util.List;

import Foods.AnalyzedInstruction;

public interface InstructionsListener {
    void didGood(List<AnalyzedInstruction> thing, String msg);

    void didBad(String msg) ;
}
