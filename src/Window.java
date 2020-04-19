import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.Animator;
import main.Renderer;
import model.LSystem;
import model.Rule;
import model.Turtle;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Window {
    public static void main(String[] args) {
//        ArrayList<Rule> ruleset = new ArrayList<>(){
//            {
//                add(new Rule('A',"ABA"));
//            }
//        };
//        LSystem system = new LSystem("A",ruleset);
//        system.nextGen();
//        system.nextGen();
//        system.nextGen();
//        system.nextGen();
//        system.nextGen();
//        system.nextGen();
//        system.nextGen();
//        system.nextGen();
//        system.nextGen();

        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);


        // The GLJpanel class
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        ArrayList<Rule> ruleset = new ArrayList<>(){
                        {
                add(new Rule('F',"FF+[+F-F-F]-[-F+F+F]"));
            }
        };
        Turtle turtle = new Turtle("FF+[+F-F-F]-[-F+F+F]", ruleset);
        turtle.nextGen();
        turtle.nextGen();
        turtle.nextGen();
        Renderer renderer = new Renderer(turtle);
        glcanvas.addGLEventListener(renderer);
        glcanvas.setSize(800, 800);

        final Animator animator = new Animator();
        animator.add(glcanvas);

        //creating frame
        final JFrame frame = new JFrame ("L-Systémy");

        //adding canvas to it
        frame.getContentPane().add( glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();

    }
}
