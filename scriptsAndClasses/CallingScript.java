package com.leargroovy;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CallingScript {
	public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");

        engine.put("name", "Vencat");

        System.out.println("Calling script from Java");
        try {
            engine.eval("println \"Hello ${name} from Groovy \"");
        } catch (ScriptException ex) {
            System.out.println(ex);
        }
    }
}