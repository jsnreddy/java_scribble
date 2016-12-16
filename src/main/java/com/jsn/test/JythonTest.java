package com.jsn.test;

import java.util.Map;

import org.python.util.PythonInterpreter;

class JythonTest {
	public static void main(String a[]) {

		PythonInterpreter python = new PythonInterpreter();

		int number1 = 10;
		int number2 = 32;

		//		python.set("number1", new PyInteger(number1));
		//		python.set("number2", new PyInteger(number2));
		//				python.exec("number3 = number1+number2");
		//		python.execfile("/home/abzooba/work/workspace/xpresso/resources/equian/python/xpresso_ML.py");
		//		PyObject number3 = python.get("number3");
		//		System.out.println("val : " + number3.toString());
		Map<String, String> envVar = System.getenv();
		System.out.println("envVar : " + envVar.toString());
		String pythonPath = envVar.get("PYTHON_PATH");
		System.out.println("pythonPath : " + pythonPath);
	}
}
