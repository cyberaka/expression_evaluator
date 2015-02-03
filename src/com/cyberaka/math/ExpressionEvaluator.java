package com.cyberaka.math;

import java.util.Stack;

public class ExpressionEvaluator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExpressionEvaluator eEval = new ExpressionEvaluator();
		String expression = "1+2+3";
		double value = eEval.eval(expression);
		System.out.println(expression = " = " + value);
	}

	private double eval(String exp) {
		String trimmedExp = eatSpace(exp);
		System.out.println("Expression to evaluate: " + trimmedExp);
		return evaluate(exp);
	}

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

	private double evaluate(String exp) {
		if (exp == null || exp.length() == 0) {
			return 0;
		}
		Stack<String> stack = new Stack<String>();
		int length = exp.length();
		int bracketDepth = 0;
		int bracketStartIndex = 0;
		for (int i=0; i<length; i++) {
			char ch = exp.charAt(i);
			if (ch == '(') {
				if (bracketDepth == 0) {
					bracketStartIndex = i;
				}
				bracketDepth++;
			}
			if (ch == ')') {
				bracketDepth--;
				if (bracketDepth == 0) {
					double value = evaluate(exp.substring(bracketStartIndex, i));
					stack.push("" + value);
				}
			}
		}
		return 0;
	}

}
