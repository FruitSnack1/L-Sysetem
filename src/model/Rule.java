package model;

//Pravidla podle kterých L-systém vytváří další generace
public class Rule {
    private char a;
    private String b;

    public Rule(char a, String b) {
        this.a = a;
        this.b = b;
    }

    public Rule(String rule){
        this.a = rule.charAt(0);
        //Odstranění prvního symbolu a =
        this.b= rule.substring(2,rule.length());
    }

    public char getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    @Override
    public String toString() {
        return a+"="+b;
    }
}
