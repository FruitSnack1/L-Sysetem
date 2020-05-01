package main;

import com.jogamp.opengl.*;
import model.Turtle;

public class Renderer implements GLEventListener {
    private Turtle turtle;
    private int iterations = 3;

    public Renderer(Turtle turtle){
        this.turtle = turtle;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        for (int j = 0; j< iterations; j++){

            gl.glPushMatrix();
            String sentence = turtle.getGen(j);
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
        }
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
        gl.glClearColor(.21f,.22f,.25f,1f);
        gl.glTranslatef(0,-1,0);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

    }

    public Turtle getTurtle() {
        return turtle;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
}