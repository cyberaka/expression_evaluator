package com.cyberaka.math;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A test class to test various combination of expression evaluator.
 * 
 * @author cyberaka
 * @date 4 Feb, 15
 */
public class ExpressionEvaluatorTest {

	private ExpressionEvaluator eEval;
	
	@Before
	public void setUp() throws Exception {
		eEval = new ExpressionEvaluator();
	}

	@After
	public void tearDown() throws Exception {
		eEval = null;
	}

	@Test
	public void testSimple() {
		assertEquals(eEval.eval("1+2+3+(5+(6+7))"), 24, 0);
	}
	
	@Test
	public void testWithoutBracket() {
		assertEquals(eEval.eval("1+2+3"), 6, 0);
		assertEquals(eEval.eval("10.10+20.20+30.30"), 60.6, 0.1);
		assertEquals(eEval.eval("123.123+456.456+789.789"), 1369.368, 0);
		assertEquals(eEval.eval("1-2-3"), -4, 0);
		assertEquals(eEval.eval("10.10-20.20-30.30"), -40.4, 0);
		assertEquals(eEval.eval("123.123-456.456-789.789"), -1123.122, 0);
		assertEquals(eEval.eval("1*2*3"), 6, 0);
		assertEquals(eEval.eval("10.10*20.20*30.30"), 6181.806, 0);
		assertEquals(eEval.eval("123.123*456.456*789.789"), 44386325.1, 0.1);
		assertEquals(eEval.eval("1/2/3"), 0.1666666667, 0.01);
		assertEquals(eEval.eval("10.10/20.20/30.30"), 0.01650165017, 0.01);
		assertEquals(eEval.eval("123.123/456.456/789.789"), 0.0003415302595, 00.1);
	}
	
	@Test
	public void testWithBracket() {
		assertEquals(eEval.eval("1+2+3+((10.10+20.20+30.30)+(123.123+456.456+789.789))"), 1435.968, 0.01);
		assertEquals(eEval.eval("1-2-3-((10.10-20.20-30.30) + (123.123-456.456-789.789))"), 1159.522, 0.01);
		assertEquals(eEval.eval("1*2*3*((10.10*20.20*30.30)*(123.123*456.456*789.789))"), 1646325904947l, 1);
		assertEquals(eEval.eval("1/2/3+((10.10/20.20/30.30)+(123.123/456.456/789.789))"), 0.1835098471, 0.01);
	}

}
