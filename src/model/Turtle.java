package model;

import java.util.ArrayList;

public class Turtle {
    private String sentence;
    private ArrayList<Rule> ruleset= new ArrayList<>();
    private int generation = 0;

    public Turtle(String sentence, ArrayList<Rule> ruleset){
        this.sentence = sentence;
        this.ruleset = ruleset;
    }

    public void nextGen(){
        String nextgen = "";
        for (int i = 0; i < sentence.length(); i++){
            char current = sentence.charAt(i);

            String replace = ""+current;

            for (int j = 0; j < ruleset.size(); j++){
                char a = ruleset.get(j).getA();
                if(a == current)
                    replace = ruleset.get(j).getB();
                break;
            }
            nextgen+= replace;
        }
        sentence = nextgen;
        generation++;
        System.out.println(generation+": "+sentence);
    }

    public String getSentence() {
        return sentence;
    }

    public int getGeneration() {
        return generation;
    }
}
