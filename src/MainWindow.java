import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import main.Renderer;
import model.Rule;
import model.Turtle;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainWindow extends JFrame{
    private JPanel rootPanel;
    private JSpinner spAngle;
    private JTextField tfAxiom;
    private JTextArea taRules;
    private JSpinner spIterations;
    private JPanel renderPanel;
    private JPanel formPanel;
    private JButton btnDraw;
    private JList turtleList;
    private JButton btnLoadExample;
    private JSpinner spLength;
    private JButton btnSave;
    private JButton btnHint;

    private ArrayList<Turtle> turtleExamples = new ArrayList<>();
    private DefaultListModel exampleListModel = new DefaultListModel();
    private GLCanvas glcanvas;

    private Renderer renderer;

    public MainWindow(){

    }

    public void init(){
        add(rootPanel);
        setTitle("L-Systems");
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
        fillTurtleExamples();
        initListeners();
        initCanvas();



        ArrayList<Rule> ruleset = new ArrayList<>();
        renderer = new Renderer(new Turtle("", ruleset, 25, .2f));
        glcanvas.addGLEventListener(renderer);
        glcanvas.setSize(800, 800);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        renderPanel.add(glcanvas);
    }

    private void initCanvas(){
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The GLJpanel class
        glcanvas = new GLCanvas(capabilities);
    }

    private void initComponents(){
        SpinnerNumberModel spModel = new SpinnerNumberModel(3,0,10,1);
        spIterations.setModel(spModel);
        spAngle.getEditor().getComponent(0).setBackground(new Color(64,68,75));
        spAngle.getEditor().getComponent(0).setForeground(new Color(240,243,251));
        spAngle.setBorder(null);
        spIterations.getEditor().getComponent(0).setBackground(new Color(64,68,75));
        spIterations.getEditor().getComponent(0).setForeground(new Color(240,243,251));
        spIterations.setBorder(null);
        SpinnerNumberModel spLengthModel = new SpinnerNumberModel(.05,.01,1,.01);
        spLength.setModel(spLengthModel);
        spLength.getEditor().getComponent(0).setBackground(new Color(64,68,75));
        spLength.getEditor().getComponent(0).setForeground(new Color(240,243,251));
        spLength.setBorder(null);
        btnDraw.setBorder(null);
        btnHint.setBorder(null);
        btnSave.setBorder(null);
        btnLoadExample.setBorder(null);
        taRules.setFont(taRules.getFont().deriveFont(Font.BOLD, taRules.getFont().getSize()));
        tfAxiom.setFont(tfAxiom.getFont().deriveFont(Font.BOLD, tfAxiom.getFont().getSize()));
        tfAxiom.setCaretColor(new Color(240,243,251));
        taRules.setCaretColor(new Color(240,243,251));
        spAngle.setValue(45);
        turtleList.setModel(exampleListModel);
        turtleList.setBackground(new Color(64,68,75));
        tfAxiom.setBorder(null);
    }

    private void loadTurtle(Turtle turtle){
        spLength.setValue(((double) turtle.getLength()));
        renderer.setIterations(3);
        spIterations.setValue(3);
        spAngle.setValue(turtle.getAngle());
        tfAxiom.setText(turtle.getAxiom());
        String rules = "";
        for(int i=0; i < turtle.getRuleset().size(); i++){
            rules+=turtle.getRuleset().get(i).toString();
            if(i < turtle.getRuleset().size()-1){
                rules+=";\n";
            }
        }
        taRules.setText(rules);
    }

    private void fillTurtleExamples(){
        turtleExamples.add(new Turtle("F", new Rule('F',"F-F++F-F"),45,.1f ));
        exampleListModel.addElement("star");
        turtleExamples.add(new Turtle("FF+[+F-F-F]-[-F+F+F]", new Rule('F',"FF+[+F-F-F]-[-F+F+F]"),25, .04f ));
        exampleListModel.addElement("tree");
        ArrayList<Rule> ruleset = new ArrayList<>(){{
            add(new Rule('F',"FF"));
            add(new Rule('X',"F[+X][-X]FX"));
        }};
        turtleExamples.add(new Turtle("X", ruleset,25, .04f ));
        exampleListModel.addElement("arrow weed");
        ArrayList<Rule> ruleset2 = new ArrayList<>(){{
            add(new Rule('F',"FF"));
            add(new Rule('X',"F-[[X]+X]+F[+FX]-X,F+[[X]-X]-F[-FX]+X"));
        }};
        turtleExamples.add(new Turtle("X", ruleset2,25, .04f ));
        exampleListModel.addElement("weed random");
        turtleExamples.add(new Turtle("F-F-F-F", new Rule('F',"F[F]-F+F[--F]+F-F"),90,0.01f ));
        exampleListModel.addElement("fractal");
        turtleExamples.add(new Turtle("F", new Rule('F',"F[+F]F[-F]F,F[-F]F[-F]F"),25,0.02f ));
        exampleListModel.addElement("sea weed random");
    }

    private void initListeners(){
        spAngle.addChangeListener(changeEvent -> {
            renderer.getTurtle().setAngle((int) spAngle.getValue());
            glcanvas.display();
        });

        tfAxiom.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                update();
            }

            public void update(){
                renderer.getTurtle().setAxiom(tfAxiom.getText());
                glcanvas.display();
            }
        });

        taRules.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                update();
            }

            public void update(){
                String[] rules = taRules.getText().replaceAll("\n","").replaceAll(" ","").split(";");
                for( String rule : rules){
                    if( !rule.matches("[A-Z]=[A-Z\\-\\+\\[\\]\\;,]+") || countOccurrencesOf(rule, '[') != countOccurrencesOf(rule,']')){
                        taRules.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.red));
                        return;
                    }
                }
                taRules.setBorder(null);
                renderer.getTurtle().setRuleset(parseRules());
                glcanvas.display();
            }
        });

        spLength.addChangeListener(changeEvent -> {
            renderer.getTurtle().setLength(((Double) spLength.getValue()).floatValue());
            glcanvas.display();
        });

        btnDraw.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                glcanvas.display();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnDraw.setBackground(new Color(103,123,196));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnDraw.setBackground(new Color(114,137,218));
            }
        });

        spIterations.addChangeListener(changeEvent -> {
            renderer.setIterations((int) spIterations.getValue());
            glcanvas.display();
        });

        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveTurtle();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSave.setBackground(new Color(50,53,59));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSave.setBackground(new Color(79,84,92));
            }
        });

        btnHint.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showHint();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                btnHint.setBackground(new Color(50,53,59));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnHint.setBackground(new Color(79,84,92));
            }
        });

        btnLoadExample.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = turtleList.getSelectedIndex();
                if(index ==-1)
                    return;
                loadTurtle(turtleExamples.get(turtleList.getSelectedIndex()));
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLoadExample.setBackground(new Color(50,53,59));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLoadExample.setBackground(new Color(79,84,92));
            }
        });
    }

    private void showHint(){
        JOptionPane.showMessageDialog(rootPanel, "F : draw line \n+ : turn right \n- : turn left \n[ : save state \n] : restore saved state", "How to", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveTurtle(){
        String turtleName = JOptionPane.showInputDialog("Name your turtle");
        String axiom = tfAxiom.getText();
        int angle = (int) spAngle.getValue();
        float length = ((Double) spLength.getValue()).floatValue();
        turtleExamples.add(new Turtle("X", parseRules(),25, .04f ));
        exampleListModel.addElement(turtleName);
    }

    private ArrayList<Rule> parseRules(){
        ArrayList<Rule> ruleset = new ArrayList<>();
        String[] rules = taRules.getText().replaceAll("\n","").split(";");
        for (String s:rules){
            ruleset.add(new Rule(s));
        }
        return ruleset;
    }

    private int countOccurrencesOf(String s, char c){
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> new MainWindow().init());
    }
}
