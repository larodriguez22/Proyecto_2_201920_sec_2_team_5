package test.data_structures;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.ArbolRojoNegro;
public class testArbol {
	private ArbolRojoNegro<Integer,String> h;
	
	public void setUp1() {
	h.put(1,"a");
	h.put(2,"b");
	h.put(3,"c");
	h.put(4,"d");
	h.put(5,"e");
	}
	
	public void testPut(){
		setUp1();
		assertEquals("hay un error",h.get(2).equals("b"));
		assertFalse("No hay un error",h.get(2).equals("c"));
	}
	
}