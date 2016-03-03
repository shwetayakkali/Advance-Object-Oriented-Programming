package connect;
/* 
 * MVCCalculatorDemo.java 
 * 
 * Version: 1.1
 */

import java.util.Scanner;
import java.util.Stack;

/** 
 *This MVCCalculatorDemo class contains our main and has calls to the other classes like CalculatorController,CalculatorView.
 * 
 * @author      Aishwarya Desai 
 * @author      Shweta Yakkali
 * 
 */
public class MVCCalculatorDemo {
  /**
   * The main program.
   *
   * @param    args    command line arguments (ignored)
   */
    public static void main(String args[]){ 
        CalculatorView view =new CalculatorView();
        String input=view.display();
        CalculatorController controller=new CalculatorController();
        int answer=controller.callModel(input);
        view.displayanswer(answer);
          
    }
}//main() class

/** 
 * This class CalculatorController contains  the method callModel.
 * @author      Aishwarya Desai 
 * @author      Shweta Yakkali
 * 
 */

class CalculatorController{
    
/**
 * The callModel method calls the method solve and this value is stored in the answer variable.
 *
 * @param       input      This is the expression entered by the user which is of string type and is passed into solve.
 * 
 * @return      answer    Returns the value after the operation is performed
 *
 */
    
    public int callModel(String input){
        CalculatorModel model=new CalculatorModel();
        int answer=model.solve(input);
        return answer;
    }
}

/** 
 * This class CalculatorView contains the method display.
 * 
 * @author      Aishwarya Desai 
 * @author      Shweta Yakkali
 * 
 */
class CalculatorView{
/**
 * The display method asks the user to enter the expression he wants to evaluate
 * 
 * @return      in    The next character
 *
 */  
    
     public String display(){
            String in;
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Enter an expression with spaces");	         // Like 2 * ( ( 3 + 4 ) / 7 ) % 3 and not 2*(3+4)/7)%3
	    in = sc.nextLine();
            return in;
    }
     
/**
 * The displayanswer() method displays the answer to the user
 * 
 * @param     answer    This variable has the answer which has to displayed to the user after his expression is evaluated
 * 
 * @return    in        The next character
 *
 *
 */
     
     public void displayanswer(int answer){
            
            System.out.println("The answer of the expression is\t:"+ answer);
         
     }
    
}

/** 
 *This CalculatorModel class contains the method solve which solves the user defined expression.
 * 
 * @author      Aishwarya Desai 
 * @author      Shweta Yakkali
 * 
 */

class CalculatorModel {
/**
 * The solve method accepts the expression which the user gives and evaluates it
 *
 * @param       Input        This is the expression entered by the user which is of string type and is passed into solve.
 * 
 * @return      val.pop()    After evaluation of a bit the expression it is popped out from the stack
 *
 *
 */
    public static int solve(String input) {


		char[] expr = input.toCharArray();

		Stack<Integer> val = new Stack<Integer>(); 					// Stack for numbers 
		Stack<Character> opr = new Stack<Character>(); 
												// Stack for Operators
		for (int i = 0; i < expr.length; i++) {
			if (expr[i] == ' ') continue;					        // Skipping spaces
			if (expr[i] >= '0' && expr[i] <= '9') {					// If a number occurs, push it to number stack 'val'

				StringBuffer temp = new StringBuffer();
				while (i < expr.length && expr[i] >= '0' && expr[i] <= '9') temp.append(expr[i++]); 	// For more than one digit number	
				val.push(Integer.parseInt(temp.toString()));
			}
			else if (expr[i] == '(') 
                                opr.push(expr[i]); 							// Opening parenthesis, directly push it in 'opr'

			else if (expr[i] == ')') {									// Closing parenthesis, solve the contents of the parenthesis
				while (opr.peek() != '(') 
                                   val.push(calculate(opr.pop(), val.pop(), val.pop()));
				opr.pop();                                                                              // pop the open bracket
			}

			else if (expr[i] == '%' || expr[i] == '+' || expr[i] == '-' || expr[i] == '*' || expr[i] == '/' || expr[i] == '^') {       // Operator push it in 'opr'
				while (!opr.empty() && checkPrecedence(expr[i], opr.peek()))
                                      val.push(calculate(opr.pop(), val.pop(), val.pop())); // Loop activated when top of stack precedence is greater in precedence
				opr.push(expr[i]);
			}
		}
		while (!opr.empty()) val.push(calculate(opr.pop(), val.pop(), val.pop())); 			// Remaining Calculation in expression
		return val.pop(); 										// Top of stack 'val' contains the answer
	}
/**
 * The checkPrecedence method compares to operators and checks for their precedence.
 *
 * @param       opr1      The 1st operator encountered.
 * @param       opr2      The 2nd operator encountered.
 * 
 * @return      true      Returns a boolean value of true
 *
 *
 */
    public static boolean checkPrecedence(char opr1, char opr2) {		// Returns true if opr1 >= opr2 else return false
		if (opr2 == '(' || opr2 == ')') return false;
		if ((opr1 == '^') && (opr2 == '*' || opr2 == '/' || opr2 == '+' || opr2 == '-' || opr2 == '%')) return false;    	
		if ((opr1 == '*' || opr1 == '/') && (opr2 == '+' || opr2 == '-')) return false;	
		else return true;		
	}

/**
 * The calculate method has a switch case which returns a particular case when encountered with it .
 *
 * @param       op      The option/the case selected
 * @param       b       The 1st operand encountered.
 * @param       a       The 2nd operand encountered.
 * 
 * @return      0      Returns a 0
 *
 *
 */
    public static int calculate(char op, int b, int a) {			// Calculates the values 
		switch (op) {
			case '%': return a % b;
			case '+': return a + b;
			case '-': return a - b;
			case '*': return a * b;
			case '/': if (b == 0) throw new UnsupportedOperationException("Cannot divide by zero"); return a / b;	//to handle divide by zero
			case '^': return (int)Math.pow(a,b);
		}
		return 0;
	}
    
    
    
}