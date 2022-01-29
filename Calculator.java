/**
 * Calculator GUI- Assignment 2.
 *
 * @author (Sangraj Ranjit)
 * @version (28/01/2022)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.math.BigInteger;
import java.io.IOException;
//import java.io.BufferedReader;

public class Calculator extends JFrame implements ActionListener {
    JTextField display; //calculator's display
    JButton[] buttons; //Calculator buttons
    JButton off;
    JPanel panelTop,panelLeft,panelRight,panelButtom;
    //JButton backSpace;
    // Defining Calculator Buttons
    String[] buttonNames = {"1","2","3","4","5","6","7","8","9","+/-","0",".","=","+","<<","-","C","*","(","/",")","!","OFF"};
    String[] buttonCommands = {"1","2","3","4","5","6","7","8","9","posNeg","0",".","equal","+","back","-","clear","*","(","/",")","!","exit"};
    String[] operators={"+","-","/","(",")","*","+/-","!"};
    String userInput;
    
    double result;
    //Constructor for objects of class SimpleCalc
    
    public Calculator(){
        // initialise instance variables
        super("PROG5001 – Calculator");
        setSize(300, 200);
        setMinimumSize(new Dimension(350, 350));
        setMaximumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CreateCalcGUI();
        pack();
        setVisible(true);
        
        double result=0;

    }

    //A method to set up the GUI
    
    private void CreateCalcGUI() {
        //
        definePanels();
        setItems();
    }

    //Defining Panels
    public void definePanels(){
        panelTop = new JPanel();
        panelTop.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridLayout panelTopLayout = new GridLayout(0,1);
        panelTop.setLayout(panelTopLayout);

        //Laying out the left button panels.
        panelLeft = new JPanel(); 
        GridLayout panelLeftLayout = new GridLayout(5,3);
        panelLeft.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelLeft.setLayout(panelLeftLayout);        
        
        // If you want to have triple sized equal button make sure to use GridBagLayout for te right panel
        // Laying out the right panel.
        panelRight = new JPanel();
        GridLayout panelRightLayout = new GridLayout(5,3);
        //panelRight.setPreferredSize(new Dimension(300, 150));
        panelRight.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelRight.setLayout(panelRightLayout);

        panelButtom = new JPanel();
        GridLayout panelBLayout = new GridLayout(1,3);
        panelButtom.setLayout(panelBLayout);
        // Seperating the panels into different sides.
        BorderLayout mainLayout = new BorderLayout();
        setLayout(mainLayout);
        //setLayout(new GridBagLayout());
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
        //GridBagConstraints gbc = new GridBagConstraints();
        //creating Left panel buttons
        buttons = new JButton[23];
        for (int i = 0; i < 13; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(displayFont);
            buttons[i].setText(buttonNames[i]);
            buttons[i].setActionCommand(buttonCommands[i]);
            buttons[i].addActionListener(this);
            buttons[i].setPreferredSize(new Dimension(100, 50));
            buttons[i].setBackground(Color.GRAY);
            //if(buttonCommands[i].equals("equal")){
            //    gbc.gridx = 0;
            //    gbc.gridy = 2;
            //    gbc.gridwidth =3;
            //    gbc.fill= GridBagConstraints.HORIZONTAL;
            //}
            panelLeft.add(buttons[i]);
            
        }
        // creating right panel buttons which are the operators.
        for (int j = 13; j < 23; j++) {
            buttons[j] = new JButton();
            buttons[j].setFont(displayFont);
            buttons[j].setText(buttonNames[j]);
            buttons[j].setActionCommand(buttonCommands[j]);
            buttons[j].addActionListener(this);
            buttons[j].setPreferredSize(new Dimension(100, 50));
            buttons[j].setBackground(Color.GRAY);
            if(buttonCommands[j].equals("exit")){
                buttons[j].setBackground(Color.RED);
                buttons[j].setOpaque(true);
            }
            panelRight.add(buttons[j]);
        }
    }
// Defines what it does when the user clicks a button
    public void actionPerformed(ActionEvent e) {
        try{

        String displayText = display.getText();

        String command = e.getActionCommand();
            
    //display.setText(displayText);
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
            int temp = Integer.parseInt(displayText);
            temp=temp * -1;
            display.setText(String.valueOf(temp));
            break;
            
            //case("."):
            //if(!display.getText().contains(".")){
            //    display.setText(display.getText() + ".");
            //}
            //break;
            case("equal"):
            calculate();
            break;
            default:
            //Pattern pattern = Pattern.compile(displayText);
          //Matcher matcher = pattern.matcher(displayText);
           //StringBuilder sb = new StringBuilder();
        
        //while (matcher.find()) {
                    //sb.append(matcher.group(Integer.parseInt(displayText))).append("");
                //}
           //display.setText(sb.toString()+ command);
            display.setText(displayText+ command);
        
        }
    }
    catch(Exception ex){
        
    }
}
    public void calculate(){
        // try catch will show an error message.
        try{
            //Input from User
        userInput=display.getText();
        // String[] numbers=userInput.split("[^0-9]");
        // for(int i=0;i<numbers.length;i++){
        // System.out.println(numbers[i]);
         // }
        //String postfix = convert(userInput);
        String postfix = convert(userInput);
        double result = evaluate(postfix);
        display.setText(""+result);
            }

    catch(Exception e){
        JOptionPane.showMessageDialog(null,"Error",null, JOptionPane.ERROR_MESSAGE);
        }
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
            return 3;
        }
        return -1;
    }

    public String convert(String infix) {
        String postfix = "";
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i <infix.length() ; i++) {
            char c = infix.charAt(i);

            //check if char is operator
            if(isOperator(c)>0){
                while(stack.isEmpty()==false && isOperator(stack.peek())>=isOperator(c)){
                    postfix += stack.pop();
                }
                stack.push(c);
            }else if(c=='('){
                stack.push(c);
            }else if(c==')'){
                    //right parenthesis
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix += stack.pop();
                }
                stack.pop(); //take out the left parenthesis from the stack
                
            }
        
            else{
                //character is neither operator nor ( 
                postfix += c;
            }
            
        }
        for (int i = 0; i <=stack.size() ; i++) {
            postfix += stack.pop();
        }
        return postfix;
    }


public double evaluate(String postfix) {
        Stack<Double> stack = new Stack();
        double result = 0.0;
        double operand1=0.0;
        double operand2=0.0;
        for (int i = 1; i < postfix.length(); i++) {
            char c = postfix.charAt(i);
            if (isOperator(c) > 0) {
                //Addition
                if (c == '+') {
                    operand2 = Double.parseDouble(" " + stack.pop());
                    operand1 = Double.parseDouble(" " + stack.pop());
                    result = operand2 + operand1;
                } else
                //Substraction
                if (c == '-') {
                    operand2 = Double.parseDouble(" " + stack.pop());
                    operand1 = Double.parseDouble(" " + stack.pop());
                    result = operand1 - operand2;
                } else
                //Multiplication
                if (c == '*') {
                    operand2 = Double.parseDouble(" " + stack.pop());
                    operand1 = Double.parseDouble(" " + stack.pop());
                    result = operand1 * operand2;
                } else
                //Division
                if (c == '/') {
                    operand2 = Double.parseDouble(" " + stack.pop());
                     operand1 = Double.parseDouble(" " + stack.pop());
                    result = operand1 / operand2;
                }
                //Factorial
                if (c == '!') {
                    //for( operand2=1; operand2<=operand1; operand2++){
                      //  result = result*operand2;
                    //}
                double operand = Double.parseDouble("" + stack.pop());
                result=1;
                for(int k=1;k<=operand;k++){
                    result=result*k;
                }
            
                    
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

        result=stack.pop();
        // after all the calculation sends the final result to textfield.
        return result;
    }


    
    public static void main (String[] args) {
        Calculator calc = new Calculator();
        calc.setVisible(true);
    }
}