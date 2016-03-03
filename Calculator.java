/* 
 * Calculator.java 
 * 
 * Version: 
 *     1.1 
 * 
 */
import java.util.*;

/**
 * This program calculates the given expression according to mathematical rules
 *
 * @author      Shweta Yakkali
 * @author      Aishwarya Desai
 */

/** 
 * Class Calculator has a function main where the expression is taken from the user, and the method solve is called which
 * uses stacks and precedence method to determine the output of the expression.

 * @author      Shweta Yakkali 
 * @author      Aishwarya Desai 
 */

public class Calculator {

/**
 * The solve method converts the expression into a character array and uses it for checking and evaluation.
 * Two stacks are created one for storing the integers and another for storing the operators.
 * Several conditions are specified for handling the parenthesis and other operations.

 @return                integer

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
 * The chackPrecedence method gives the precedence of the stack top and the current character in the exppr array during the processing of the operation.

 @param    opr1         character
 @param    opr2         character

 @return                boolean

 */

	public static boolean checkPrecedence(char opr1, char opr2) {		// Returns true if opr1 >= opr2 else return false
		if (opr2 == '(' || opr2 == ')') return false;
		if ((opr1 == '^') && (opr2 == '*' || opr2 == '/' || opr2 == '+' || opr2 == '-' || opr2 == '%')) return false;    	
		if ((opr1 == '*' || opr1 == '/') && (opr2 == '+' || opr2 == '-')) return false;	
		else return true;		
	}


/**
 * The calculate method calculates the subexpression and returns the integer value.

 @param    op         character
 @param    b          integer
 @param    c          integer

 @return              integer

 */
	public static int calculate(char op, int b, int a) {			// Calculates the values 
		switch (op) {
			case '%': return a % b;
			case '+': return a + b;
			case '-': return a - b;
			case '*': return a * b;
			case '/': if (b == 0) throw new UnsupportedOperationException("Cannot divide by zero"); return a / b;	//to handle divide by zero
			case '^': return (int)Math.pow(b,a);
		}
		return 0;
	}


/**
 * The main method takes the expression from the user and calls the solve method and displays the final answer
 @param    op         character
 @param    b          integer
 @param    c          integer

 @return              integer

 */
	
	public static void main(String[] args) { 				// Main method
		String in;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter an expression with spaces");	        // Like 2 * ( ( 3 + 4 ) / 7 ) % 3 and not 2*(3+4)/7)%3
		in = sc.nextLine();
		System.out.println("Answer = " + Calculator.solve(in));	
	}
}
