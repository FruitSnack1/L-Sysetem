package main;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import model.LSystem;
import model.Turtle;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Renderer implements GLEventListener {

    private Turtle turtle;
    private float angle = 25;

    public Renderer(Turtle turtle){
        this.turtle = turtle;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glPushMatrix();
        String sentence = turtle.getSentence();
        for (int i =0; i< sentence.length(); i++){
            char current = sentence.charAt(i);
            if(current == 'F'){
                gl.glBegin (GL2.GL_LINES);//static field
                gl.glVertex2f(0,0);
                gl.glVertex2f(0,.05f);
                gl.glEnd();
                gl.glTranslatef(0,.05f,0);
            }
            if(current == '+'){
                gl.glRotatef(-angle,0,0,1);
            }
            if(current == '-'){
                gl.glRotatef(angle,0,0,1);
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
        //method body
    }

    @Override
    public void init(GLAutoDrawable arg0) {
        // method body
        final GL2 gl = arg0.getGL().getGL2();
        gl.glTranslatef(0,-1,0);
    }




}//end of classimport javax.media.opengl.GL2;