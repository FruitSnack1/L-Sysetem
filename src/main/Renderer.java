package main;

import com.jogamp.opengl.*;
import model.Turtle;

public class Renderer implements GLEventListener {
    private Turtle turtle;

    public Renderer(Turtle turtle){
        this.turtle = turtle;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        //Překreslení pozadí
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        //Uložení aktuálního stavu pohledové maticce
        gl.glPushMatrix();
        //Renderer provádí operace podle znaků
        String sentence = turtle.getSentence();
        for (int i =0; i< sentence.length(); i++){
            char current = sentence.charAt(i);
            if(current == 'F'){
                gl.glBegin(GL2.GL_LINES);//static field
                gl.glVertex2f(0,0);
                gl.glVertex2f(0,turtle.getLength());
                gl.glEnd();
                gl.glTranslatef(0,turtle.getLength(),0);
            }
            if(current == '+'){
                gl.glRotatef(-turtle.getAngle(),0,0,1);
            }
            if(current == '-'){
                gl.glRotatef(turtle.getAngle(),0,0,1);
            }
            if(current == '['){
                gl.glPushMatrix();
            }
            if(current == ']'){
                gl.glPopMatrix();
            }
        }
        gl.glPopMatrix();
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void dispose(GLAutoDrawable arg0) {

    }

    @Override
    public void init(GLAutoDrawable arg0) {
        final GL2 gl = arg0.getGL().getGL2();
        //Nastavení barvy pozadí
        gl.glClearColor(.21f,.22f,.25f,1f);
        //Posunutí počátku dolů
        gl.glTranslatef(0,-1,0);
    }

    public Turtle getTurtle() {
        return turtle;
    }

}