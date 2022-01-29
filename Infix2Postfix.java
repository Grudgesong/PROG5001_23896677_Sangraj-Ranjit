import java.util.*;

/**
 * Write a description of class Infix2Postfix here.
 *
 * @author (your name)
 * @version (28/01/2022)
 */
public class Infix2Postfix {
    /**
     * Constructor for objects of class Infix2Postfix
     */
    public Infix2Postfix(){
        // initialise instance variables
    }

    private int isOperator(char c) {
        switch (c) {
            case '+':                
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1;
    }
    
       /**
     * convert expression in the infix format to postfix format
     * @param: infix
     * @output: postfix
     */
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
            } else {
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
            } else {
                //c is an operand
                stack.push(Double.parseDouble("" + c));                
            }            
        }
        
        result = stack.pop();
        
        return result;
    }
    /**
     * the main method
     */
    public static void main(String[] args) {
        Infix2Postfix i2p = new Infix2Postfix();
        String expression = "1+(2*3)-4";
        //String expression = "1.8 + ( 2 * 3 ) - 4.0";
        //expression.split(" "); //--> ["1.8","+',"(", "2", "*", "3", ")", "-", "4.0"]
        
        String postfix = i2p.convert(expression);
        System.out.println(postfix);
        
        double result = i2p.evaluate(postfix);
        System.out.println(expression + " = " + result);
    }
}