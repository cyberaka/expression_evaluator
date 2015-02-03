package com.cyberaka.math;

import java.util.Stack;

import com.sun.istack.internal.logging.Logger;

/**
 * Evaluates a mathematical expression.
 *  
 * @author cyberaka
 * @date 4 Feb, 15
 */
public class ExpressionEvaluator {

	private Logger log = Logger.getLogger(getClass());
	
	/**
	 * The only public method of this class. It accepts an expression, removes all space from it
	 * and then parses the expression.
	 * 
	 * @param exp The expression to be parsed. 
	 * @return double The parsed value. 
	 */
	public double eval(String exp) {
		String trimmedExp = eatSpace(exp);
		log.info("Expression to evaluate: " + trimmedExp);
		double result = evaluate(trimmedExp);
		log.info("Result = " + result);
		return result;
	}

	/**
	 * Remove spaces from the expression and validates that the expression comprises of only numeris
	 * and operands.
	 * 
	 * @param exp The expression to be checked. 
	 * @return String The updated expression with all space removed. 
	 */
	private String eatSpace(String exp) {
		StringBuffer sb = new StringBuffer();
		int length = exp.length();
		for (int i=0; i<length; i++) {
			char ch = exp.charAt(i);
			switch (ch) {
			case ' ':
				break;
			case '+':
			case '-':
			case '/':
			case '*':
			case '(':
			case ')':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '.':
				sb.append(ch);
				break;
			default:
				String error = "Invalid character " + ch + " found at index " + i;
				System.err.println(error);
				throw new IllegalArgumentException(error);
			}
		}
		return sb.toString();
	}

	/**
	 * Evaluates the expression passed as parameter.
	 * 
	 * @param exp the expression to evaluate. 
	 * @return double The parsed value. 
	 */
	private double evaluate(String exp) {
		log.fine("Evaluating: " + exp);
		if (exp == null || exp.length() == 0) {
			return 0;
		}
		Stack<String> stack = new Stack<String>();
		int length = exp.length();
		int bracketDepth = 0;
		int bracketStartIndex = -1;
		boolean lastEntryOperand = false;
		for (int i=0; i<length; i++) {
			char ch = exp.charAt(i);
			if (ch == '(') { // Bracket started so increment depth
				if (bracketDepth == 0) {
					bracketStartIndex = i;
				}
				bracketDepth++;
				continue;
			}
			if (ch == ')') { // Bracket finished so decrement depth
				bracketDepth--;
				if (bracketDepth == 0) { // The bracket's content should now be checked. 
					double value = evaluate(exp.substring(bracketStartIndex + 1, i));
					stack.push("" + value);
					lastEntryOperand = false;
					bracketStartIndex = -1;
				}
				continue;
			}
			if (bracketDepth > 0) { // If bracket is being evaluated we should not parse it's content.
				continue;
			}
			if (ch == '-' || ch == '+' || ch == '/' || ch == '*') { 
				lastEntryOperand = true;
				stack.push("" + ch);
			} else {
				if (lastEntryOperand || stack.isEmpty()) {
					stack.push("" + ch);
				} else {
					stack.push(stack.pop() + ch);
				}
				lastEntryOperand = false;
			}
		}
		// Normalization complete. Now analyze the stack.
		int size = stack.size();
		double value = 0;
		char op = 0;
		for (int i=0; i<size; i+=2) {
			double temp = Double.parseDouble(stack.get(i));
			if (op == 0) {
				value = temp;
			} else {
				switch(op) {
				case '+':
					value += temp;
					break;
				case '-':
					value -= temp;
					break;
				case '/':
					value /= temp;
					break;
				case '*':
					value *= temp;
					break;
				}
			}
			if (i < stack.size() - 1) {
				op = stack.get(i + 1).charAt(0);
			}
		}
		return value;
	}

}
