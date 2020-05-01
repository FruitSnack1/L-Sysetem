package model;

import java.util.ArrayList;
import java.util.Random;

public class Turtle {
    private ArrayList<Rule> ruleset= new ArrayList<>();
    private int generation = 0;
    private String axiom;
    private int angle = 25;
    private float length;

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public ArrayList<Rule> getRuleset() {
        return ruleset;
    }

    public void setRuleset(ArrayList<Rule> ruleset) {
        this.ruleset = ruleset;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public String getAxiom() {
        return axiom;
    }

    public void setAxiom(String axiom) {
        this.axiom = axiom;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public Turtle(String axiom, ArrayList<Rule> ruleset, int angle, float length){
        this.axiom = axiom;
        this.ruleset = ruleset;
        this.angle = angle;
        this.length = length;
    }

    public Turtle(String axiom, Rule rule, int angle, float length){
        this.axiom = axiom;
        this.ruleset.add(rule);
        this.angle = angle;
        this.length = length;
    }

    public String getGen(int gens){
        String sentence = axiom;
        for(int i = 0; i < gens; i++){
            String nextgen = "";
            for (int j = 0; j < sentence.length(); j++){
                char current = sentence.charAt(j);

                String replace = ""+current;

                for (int k = 0; k < ruleset.size(); k++){
                    char a = ruleset.get(k).getA();
                    if(a == current){
                        if(ruleset.get(k).getB().contains(",")){
                            String[] rules = ruleset.get(k).getB().split(",");
                            Random rand = new Random();
                            if(rand.nextBoolean())
                                replace = rules[0];
                            else
                                replace = rules[1];
                        }else{
                            replace = ruleset.get(k).getB();
                        }
                        break;
                    }
                }
                nextgen+= replace;
            }
            sentence = nextgen;
        }
        return sentence;
    }


    public Turtle copy(){
        return new Turtle(axiom, ruleset, angle, length);
    }
}
