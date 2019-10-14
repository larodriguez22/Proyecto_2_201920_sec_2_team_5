package test.data_structures;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.LinearProbingHashST;
import model.data_structures.MaxPQ;
import model.data_structures.Queue;
import model.logic.Viaje;

public class testHash 
{
	private LinearProbingHashST<Integer, Viaje> hash;
	private final static int CANTIDAD=10;
	
	@Before
	public void setUp1() {
		hash=new LinearProbingHashST<>();
	}

	public void setUp2() {
		for(int i =0; i<CANTIDAD; i++){
			Viaje v = new Viaje(i, "1234"+i, "0976"+i, i+"9765", "345"+i+"435", "436");
			hash.put(i, v);
		}
	}
	
	@Test
	public void insertSize()
	{
		assertEquals(0, hash.size());
	}
	
	@Test
	public void insertTest1()
	{
		hash.put(3, new Viaje(1, "345", "57458", "84356", "0845", "34657"));
		hash.put(5, new Viaje(1, "345", "57458", "84356", "0845", "34657"));
		hash.put(2, new Viaje(1, "345", "57458", "84356", "0845", "34657"));
		assertTrue("No retorna la llave que es", hash.get(5).getDstid()==57458);
		assertFalse("No deberia haber agregado esa llave", hash.contains(7));
		assertTrue("No agrego la llave", hash.contains(2));
		Queue q=new Queue();
		q.enqueue(3);
		q.enqueue(5);
		q.enqueue(2);
		assertEquals(q, hash.keys());
		assertEquals(3, hash.size());
	}
	
	@Test
	public void insertTest2()
	{
		setUp2();
		assertEquals(10, hash.size());
	}
	
	@Test
	public void delete()
	{
		hash.put(3, new Viaje(1, "345", "57458", "84356", "0845", "34657"));
		hash.put(5, new Viaje(1, "345", "57458", "84356", "0845", "34657"));
		hash.put(2, new Viaje(1, "345", "57458", "84356", "0845", "34657"));
		hash.delete(5);
		assertEquals(2, hash.size());
	}
	
}
