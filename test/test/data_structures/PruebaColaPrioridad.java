package test.data_structures;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.MaxPQ;

public class PruebaColaPrioridad {

	private MaxPQ ColaH;
	private final static int cantidad=10;
	private int size;

	@Before
	public void setUp1() {
		ColaH=new MaxPQ<>();
		size++;
	}

	public void setUp2() {
		for(int i =0; i<cantidad; i++){
			ColaH.insert(i);
			size++;
		}
	}
	@Test
	public void TestQueue(){
		size=0;
		assertTrue(ColaH!=null);
		assertEquals(0, ColaH.size());
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestInsert(){
		setUp2();
		ColaH.insert(10);
		size++;
		assertTrue("no se modifico el tamaño de la pila ",size==ColaH.size());
		int dato=0;
		while(size!=0){
			dato=(Integer)ColaH.delMax();
		}
		assertTrue("no se agrego correctamente a la pila", dato==10);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void TestPush2(){
		setUp1();
		ColaH.insert(7);
		ColaH.insert(5);
		ColaH.insert(8);
		ColaH.insert(4);
		assertTrue("no se modifico el tamaño de la pila ",size==ColaH.size());
		assertFalse("no se agrego correctamente a la pila", (Integer)ColaH.delMax()==8);


	}

	public void TestDequeue(){
		setUp2();
		int i=9;
		while(size!=0){
			assertTrue("no salio correctamente a la pila",(Integer) ColaH.delMax()==i);

			i++;
		}
	}

	public void TestDequeue2(){
		setUp1();
		ColaH.insert(5);
		ColaH.insert(3);
		ColaH.insert(7);
		assertTrue("no salio correctamente a la pila", (Integer)ColaH.delMax()==7);
		assertFalse("La pila quedo vacia ",ColaH.isEmpty());

	}
	public void Testinsert(){
		setUp1();
		ColaH.insert(1);
		ColaH.insert(2);
		ColaH.insert(3);
		assertTrue("No se agregaron correctamente",ColaH.size()==3);
	}
	public void TestDelMax(){
		setUp2();
		assertTrue("La cola no saco el maximo",(Integer)ColaH.delMax()==9);
	}
}
