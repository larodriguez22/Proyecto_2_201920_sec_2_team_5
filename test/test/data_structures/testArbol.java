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
	public void testDelete(){
		setUp1();
		h.delete(3);
		assertFalse("hay un error",h.get(3)=="c");
		
	}
	public void testDeleteMax(){
		setUp1();
		h.deleteMax();
		assertFalse("hay un error",h.get(5).equals("e"));
		
	}
	public void testDeleteMin(){
		setUp1();
		h.deleteMin();
		assertFalse("hay un error",h.get(1).equals("a"));
		
	}

	
}