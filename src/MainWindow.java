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
    private DefaultListModel exampleListModel;
    private GLCanvas glcanvas;
    private Turtle turtle;

    private Renderer renderer;

    public MainWindow(){
        add(rootPanel);
        setTitle("L-Systems");
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Nastavení komponentů
        initComponents();
        //Přidání příkaldů
        fillTurtleExamples();
        initListeners();
        initCanvas();

        setVisible(true);
        renderPanel.add(glcanvas);
    }

    //Vytvoření GLcanvasu, rendereru
    private void initCanvas(){
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        glcanvas = new GLCanvas(capabilities);
        ArrayList<Rule> ruleset = new ArrayList<>();
        turtle = new Turtle("", ruleset, 25, .2f);
        renderer = new Renderer(turtle);
        glcanvas.addGLEventListener(renderer);
        glcanvas.setSize(800, 800);
    }

    //Nastavení modelů, barev, borderů pro komponenty
    private void initComponents(){
        SpinnerNumberModel spIterationsModel = new SpinnerNumberModel(3,0,10,1);
        spIterations.setModel(spIterationsModel);
        spIterations.getEditor().getComponent(0).setBackground(new Color(64,68,75));
        spIterations.getEditor().getComponent(0).setForeground(new Color(240,243,251));
        spIterations.setBorder(null);

        SpinnerNumberModel spAngleModel = new SpinnerNumberModel(45,0,360,1);
        spAngle.setModel(spAngleModel);
        spAngle.getEditor().getComponent(0).setBackground(new Color(64,68,75));
        spAngle.getEditor().getComponent(0).setForeground(new Color(240,243,251));
        spAngle.setBorder(null);

        SpinnerNumberModel spLengthModel = new SpinnerNumberModel(.05,.01,1,.01);
        spLength.setModel(spLengthModel);
        spLength.getEditor().getComponent(0).setBackground(new Color(64,68,75));
        spLength.getEditor().getComponent(0).setForeground(new Color(240,243,251));
        spLength.setBorder(null);

        btnDraw.setBorder(null);
        btnHint.setBorder(null);
        btnSave.setBorder(null);
        btnLoadExample.setBorder(null);

        taRules.setCaretColor(new Color(240,243,251));
        taRules.setFont(taRules.getFont().deriveFont(Font.BOLD, taRules.getFont().getSize()));
        taRules.setBorder(null);

        tfAxiom.setCaretColor(new Color(240,243,251));
        tfAxiom.setFont(tfAxiom.getFont().deriveFont(Font.BOLD, tfAxiom.getFont().getSize()));
        tfAxiom.setBorder(null);

        exampleListModel = new DefaultListModel();
        turtleList.setModel(exampleListModel);
        turtleList.setBackground(new Color(64,68,75));
    }

    //Načtení želví grafiky z listu příkladů
    private void loadTurtle(Turtle turtle){
        spLength.setValue(((double) turtle.getLength()));
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

    //Přidaní příkladů do listu příkladů
    private void fillTurtleExamples(){
        turtleExamples.add(new Turtle("F", new Rule('F',"F-F++F-F"),45,.1f ));
        exampleListModel.addElement("star");
        turtleExamples.add(new Turtle("FF+[+F-F-F]-[-F+F+F]", new Rule('F',"FF+[+F-F-F]-[-F+F+F]"),25, .04f ));
        exampleListModel.addElement("tree");
        ArrayList<Rule> ruleset = new ArrayList<Rule>(){{
            add(new Rule('F',"FF"));
            add(new Rule('X',"F[+X][-X]FX"));
        }};
        turtleExamples.add(new Turtle("X", ruleset,25, .04f ));
        exampleListModel.addElement("arrow weed");
        ArrayList<Rule> ruleset2 = new ArrayList<Rule>(){{
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
                updateRules();
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
            turtle.setIterations((int) spIterations.getValue());
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

    //Zobrazení nápovědy
    private void showHint(){
        JOptionPane.showMessageDialog(rootPanel, "F : draw line \n+ : turn right \n- : turn left \n[ : save state \n] : restore saved state", "How to", JOptionPane.INFORMATION_MESSAGE);
    }

    //Uložení aktuální želví grafiky do listu příkladů
    private void saveTurtle(){
        String turtleName = JOptionPane.showInputDialog("Name your turtle");
        String axiom = tfAxiom.getText();
        int angle = (int) spAngle.getValue();
        float length = ((Double) spLength.getValue()).floatValue();
        turtleExamples.add(new Turtle(axiom, parseRules(),angle, length ));
        exampleListModel.addElement(turtleName);
    }

    //Převedení pravidel z textu do objektů
    private ArrayList<Rule> parseRules(){
        ArrayList<Rule> ruleset = new ArrayList<>();
        String[] rules = taRules.getText().replaceAll("\n","").split(";");
        for (String s:rules){
            ruleset.add(new Rule(s));
        }
        return ruleset;
    }

    //Aktualizuje pravidla
    private void updateRules(){
        //Odstranění white space znaků
        String[] rules = taRules.getText().replaceAll("\n","").replaceAll(" ","").split(";");
        //Pro každé pravidlo zjistíme zda splňuje pravidla regexpu, pravidlo musí obsahovat stejný počet "[" a "]" aby renderer fungoval správně
        for( String rule : rules){
            if( !rule.matches("[A-Z]=[A-Z\\-\\+\\[\\]\\;,]+") || countOccurrencesOf(rule, '[') != countOccurrencesOf(rule,']')){
                taRules.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.red));
                return;
            }
            if( rule.contains(",") && !rule.matches("[A-Z]=[A-Z\\-\\+\\[\\]]*,[A-Z\\-\\+\\[\\]\\;,]+")){
                taRules.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.red));
                return;
            }
        }
        taRules.setBorder(null);
        renderer.getTurtle().setRuleset(parseRules());
        glcanvas.display();
    }

    //Pomocná metoda pro zpočítání znaků v řetězci
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
        SwingUtilities.invokeLater(()-> new MainWindow());
    }
}
