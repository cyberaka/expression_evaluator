package com.cyberaka.math;

import java.util.Iterator;
import java.util.Stack;

public class ExpressionEvaluator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExpressionEvaluator eEval = new ExpressionEvaluator();
		String expression = "10.1+20+30";
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

	private double evaluate(String exp) {
		if (exp == null || exp.length() == 0) {
			return 0;
		}
		Stack<String> stack = new Stack<String>();
		int length = exp.length();
		int bracketDepth = 0;
		int bracketStartIndex = 0;
		boolean lastEntryOperand = false;
		for (int i=0; i<length; i++) {
			char ch = exp.charAt(i);
			if (ch == '(') {
				if (bracketDepth == 0) {
					bracketStartIndex = i;
				}
				bracketDepth++;
				continue;
			}
			if (ch == ')') {
				bracketDepth--;
				if (bracketDepth == 0) {
					double value = evaluate(exp.substring(bracketStartIndex + 1, i - 1));
					stack.push("" + value);
					lastEntryOperand = false;
				}
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
		int size = stack.size();
		Iterator<String> itr = stack.iterator();
		System.out.println("Stack Dump============");
		while (itr.hasNext()) {
			String entry = itr.next();
			System.out.print(entry + " ");
		}
		System.out.println("\n======================");
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
