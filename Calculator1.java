import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
/**
 * Write a description of class SimpleCalc here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Calculator1 extends JFrame implements ActionListener {
    JTextField display; //calculator's display
    JButton[] buttons;
    JButton off;
    JPanel panelTop,panelLeft,panelRight,panelButtom;
    //JButton backSpace;
    String[] buttonNames = {"1","2","3","4","5","6","7","8","9","+/-","0",".","=","+","<<","-","C","*","(","/",")","!","OFF"};
    String[] buttonCommands = {"1","2","3","4","5",
            "6","7","8","9","posNeg","0",".","equal","+","back","-","clear","*","(","/",")","!","exit"};
    String[] operators={"+","-","/","(",")","*","+/-","!"};
    String userInput;
    
    double result;
    /**
     * Constructor for objects of class SimpleCalc
     */
    public Calculator1(){
        // initialise instance variables
        super("PROG5001 – Calculator");
        setSize(300, 200);
        setMinimumSize(new Dimension(350, 350));
        setMaximumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CreateCalcGUI();
        pack();
        setVisible(true);
        
        result=0;

    }

    /**
     * A method to set up the GUI
     */
    private void CreateCalcGUI() {
        //
        definePanels();
        setItems();
    }

    public void definePanels(){
        panelTop = new JPanel();
        panelTop.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridLayout panelTopLayout = new GridLayout(0,1);
        panelTop.setLayout(panelTopLayout);

        //
        panelLeft = new JPanel(); 
        GridLayout panelLeftLayout = new GridLayout(5,3);
        panelLeft.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelLeft.setLayout(panelLeftLayout);        
        
        // If you want to have triple sized equal button make sure to use GridBagLayout for te right panel
        panelRight = new JPanel();
        GridLayout panelRightLayout = new GridLayout(5,2);
        //panelRight.setPreferredSize(new Dimension(300, 150));
        panelRight.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelRight.setLayout(panelRightLayout);

        panelButtom = new JPanel();
        GridLayout panelBLayout = new GridLayout(1,3);
        panelButtom.setLayout(panelBLayout);

        BorderLayout mainLayout = new BorderLayout();
        setLayout(mainLayout);
        add(panelTop, BorderLayout.NORTH);
        add(panelLeft, BorderLayout.CENTER);
        add(panelRight,BorderLayout.EAST);
        add(panelButtom,BorderLayout.SOUTH);
    }

    public void setItems(){

        //create display
        display = new JTextField();        
        Font displayFont = new Font("Arial", Font.BOLD, 24);
        display.setFont(displayFont);
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);        
        display.setPreferredSize(new Dimension(100,35));
        panelTop.add(display);
        //create buttons
        buttons = new JButton[23];
        for (int i = 0; i < 13; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(displayFont);
            buttons[i].setText(buttonNames[i]);
            buttons[i].setActionCommand(buttonCommands[i]);
            buttons[i].setBackground(Color.GRAY);
            buttons[i].addActionListener(this);
            buttons[i].setPreferredSize(new Dimension(50, 50));
            panelLeft.add(buttons[i]);
        }

        for (int j = 13; j < 23; j++) {
            buttons[j] = new JButton();
            buttons[j].setFont(displayFont);
            buttons[j].setText(buttonNames[j]);
            buttons[j].setActionCommand(buttonCommands[j]);
            buttons[j].setBackground(Color.GRAY);
            buttons[j].addActionListener(this);
            if(buttonCommands[j].equals("exit")){
                buttons[j].setBackground(Color.RED);
                buttons[j].setOpaque(true);
            }
            panelRight.add(buttons[j]);
        }
    }

    public void actionPerformed(ActionEvent e) {
        String displayText = display.getText();
        String command = e.getActionCommand();
        switch(command){
            case("clear"):
            display.setText("");
            break;
            
            case("back"):
            int len = displayText.length();
            displayText = displayText.substring(0, len-1);
            display.setText(displayText);
            break;
            
            case("exit"):
            System.exit(0);
            break;
            
            case("posNeg"):
            //call the method of pos neg which multiply the number by -1
            break;
            case("equal"):
            calculate();
            break;
            default: 
            display.setText(displayText + command);
        }
    }
    public void calculate(){
        userInput=display.getText();
        // String[] numbers=userInput.split("[^0-9]");
        // for(int i=0;i<numbers.length;i++){
        // System.out.println(numbers[i]);
         // }

        String postfix = convert(userInput);
        double result = evaluate(postfix);
        display.setText(""+result);

    }
    private int isOperator(char c) {
        switch (c) {
            case '+':                
            case '-':
            return 1;
            case '*':
            case '/':
            return 2;
            case '!': 
            return 0;
        }
        return -1;
    }

    public String convert(String infix) {
        Stack<Character> stack = new Stack();
        String postfix = "";
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            if (isOperator(c) > 0) {
                //operator
                while (!stack.isEmpty() && (isOperator(c) <= isOperator(stack.peek()))) {
                    postfix = postfix + stack.pop();
                }
                stack.push(c);
            } else 
            if (c == '(') {
                //left parenthesis
                stack.push(c);
            } else
            if (c == ')') {
                //right parenthesis
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix = postfix + stack.pop();
                }
                stack.pop(); //take out the left parenthesis from the stack
            } 
            else {
                //operand
                postfix = postfix + c;
            }            
        }
        while (!stack.isEmpty()) {
            postfix = postfix + stack.pop();
        }

        return postfix;
    }

    public double evaluate(String postfix) {
        Stack<Double> stack = new Stack();
        double result = 0;
        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);
            if (isOperator(c) > 0) {
                double operand2 = Double.parseDouble("" + stack.pop());
                double operand1 = Double.parseDouble("" + stack.pop());
                if (c == '+') {
                    result = operand1 + operand2;
                } else
                if (c == '-') {
                    result = operand1 - operand2;
                } else
                if (c == '*') {
                    result = operand1 * operand2;
                } else
                if (c == '/') {
                    result = operand1 / operand2;
                }
                stack.push(result);
            }
            else if(isOperator(c) < 0) {
                //c is an operand
                stack.push(Double.parseDouble("" + c));                
            }  
            else{
                double operand = Double.parseDouble("" + stack.pop());
                result=1;
                for(int k=1;k<=operand;k++){
                    result=result*k;
                }
                stack.push(result);
            }          
        }

        result = stack.pop();

        return result;
    }

    public static void main (String[] args) {
        Calculator1 calc = new Calculator1();
        calc.setVisible(true);
    }
}