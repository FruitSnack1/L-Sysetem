package model;

import java.util.ArrayList;
import java.util.Random;

public class Turtle {
    private ArrayList<Rule> ruleset= new ArrayList<>();
    private String axiom;
    private int angle;
    private float length;
    private int iterations = 3;

    public Turtle(String axiom, ArrayList<Rule> ruleset, int angle, float length){
        this.axiom = axiom;
        this.ruleset = ruleset;
        this.angle = angle;
        this.length = length;
    }

    //Kounstruktor ulehčuje vytváření želvy s pouze jedním pravidlem
    public Turtle(String axiom, Rule rule, int angle, float length){
        this.axiom = axiom;
        this.ruleset.add(rule);
        this.angle = angle;
        this.length = length;
    }

    //Funkce vrací n-tou generaci L-Systému (umožňuje přepínaní mezi generacemi)
    public String getSentence(){
        String sentence = axiom;
        for(int i = 0; i < iterations; i++){
            String nextgen = "";
            //Projdi všechny znaky v aktuálním axiomu
            for (int j = 0; j < sentence.length(); j++){
                char current = sentence.charAt(j);
                //Jednoduché přetypování charu na String
                String replace = ""+current;

                //Projdi všechny pravidla
                for (int k = 0; k < ruleset.size(); k++){
                    char a = ruleset.get(k).getA();
                    //Pokud pro znak platí pravidlo
                    if(a == current){

                        //Řešení náhodnosti, pokud pravidlo obsahuje ¨,¨ vyberu náhodně jeho pravou nebo levou stranu
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
                        //Pokud jsem pro symbol našel pravidlo je zbytečné dále procházet polem
                        break;
                    }
                }
                nextgen+= replace;
            }
            sentence = nextgen;
        }
        return sentence;
    }

    public void setIterations(int iterations) { this.iterations = iterations; }

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
}
