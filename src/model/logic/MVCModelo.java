package model.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.stream.JsonReader;
import com.opencsv.CSVReader;

import model.data_structures.ArbolRojoNegro;
import model.data_structures.LinearProbingHashST;
import model.data_structures.MaxPQ;

/**
 * Definicion del modelo del mundo
 *
 */
public class MVCModelo {
	/**
	 * Atributos del modelo del mundo
	 */
	private MaxPQ<Viaje> colaMes;
	private MaxPQ<Viaje> colaDia;
	private MaxPQ<Viaje> colaHora;
	private LinearProbingHashST<Integer, Nodo> nodos;
	private ArbolRojoNegro<Integer, Zona> zonas;

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public MVCModelo()
	{
		colaMes= new MaxPQ<>();
		colaDia= new MaxPQ<>();
		colaHora= new MaxPQ<>();
		nodos= new LinearProbingHashST<>();
	}

	public void cargar()
	{
		try {
			cargarCSVPriorityQueue();
			cargarTxtHash();
			cargarJsonArbol();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cargarJsonArbol()
	{
		String path = "./data/data.json";
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(path));
			Message[] lista3 = gson.fromJson(reader, Message[].class);
			System.out.println(Arrays.toString(lista3));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void cargarTxtHash() throws IOException
	{
		// Abre el archivo utilizando un FileReader
		FileReader reader = new FileReader( "./data/Nodes_of_red_vial-wgs84_shp.txt"  );
		// Utiliza un BufferedReader para leer por líneas
		BufferedReader lector = new BufferedReader( reader );   
		// Lee línea por línea del archivo
        String linea = lector.readLine( );
        while(linea!=null)
        {
        	// Parte la línea con cada coma
            String[] partes = linea.split( "," );
            Nodo nodoAux= new Nodo(partes[0], partes[1], partes[2]);
            int key=Integer.parseInt(partes[0]);
            nodos.put(key, nodoAux);
        	linea=lector.readLine();
        }
        // Cierra los lectores y devuelve el resultado
        lector.close( );
        reader.close( );
        System.out.println("El numero actual de nodos cargados es: "+nodos.size());
	}

	public void cargarCSVPriorityQueue() throws Exception 
	{
		System.out.println("paso 1");
		CSVReader reader = null;
		int i=1;
		int contador=0;
		try 
		{

			while(i<5)
			{
				Viaje val=null;
				reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-"+i+"-MonthlyAggregate.csv"));
				reader.readNext();
				for(String[] nextLine : reader) 
				{
					val=new Viaje(i,nextLine[0],nextLine[1],nextLine[2],nextLine[3],nextLine[4]);
					colaMes.insert(val);
					contador++;
				}
				reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-"+i+"-WeeklyAggregate.csv"));
				reader.readNext();
				for(String[] nextLine : reader) 
				{
					val=new Viaje(i,nextLine[0],nextLine[1],nextLine[2],nextLine[3],nextLine[4]);
					colaDia.insert(val);
					contador++;
				}
				reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-"+i+"-HourlyAggregate.csv"));
				reader.readNext();
				for(String[] nextLine : reader) 
				{
					val=new Viaje(i,nextLine[0],nextLine[1],nextLine[2],nextLine[3],nextLine[4]);
					colaHora.insert(val);
					contador++;
				}
				i++;
			}
			System.out.println("Numero actual del total de elementos cargados de los viajes es" + contador + "\n---------");	
			System.out.println("El total de viajes cargados de los datos de mes es " + colaMes.size() + "\n---------");	
			System.out.println("El total de viajes cargados de los datos de los dias de la semana es " + colaDia.size() + "\n---------");
			System.out.println("El total de viajes cargados de los datos de hora es " + colaHora.size() + "\n---------");
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("entro en el catch 1");
			e.printStackTrace();
		} 
		finally
		{
			if (reader != null) 
			{
				try 
				{
					System.out.println("entro en el try 2");
					reader.close();
				} 
				catch (IOException e) 
				{
					System.out.println("entro en el catch 2");
					e.printStackTrace();
				}
			}
		}

	}

	public void NLetrasFrecuentesZona(int n) {
		// TODO Auto-generated method stub
		
	}

	public void buscarNodosDelimitanZona(double latitud, double longitud) {
		// TODO Auto-generated method stub
		
	}

	public void tiempoPromedioRango(int n, double limiteBajoTiempo, double limiteAltoTiempo) {
		// TODO Auto-generated method stub
		
	}

	public void nZonasMasNorte(int n) {
		// TODO Auto-generated method stub
		
	}

	public void buscarNodos(double latitud, double longitud) {
		// TODO Auto-generated method stub
		
	}

	public void tiemposEsperaRangoDadoDeDesviaciones(int n, double limiteBajoDesviacion, double limiteAltoDesviacion) {
		// TODO Auto-generated method stub
		
	}

	public void tiemposZonaOrigenYHoraDada(double zonaOrigen, double hora) {
		// TODO Auto-generated method stub
		
	}

	public void tiemposZonaDestinoRangoHoraDada(double zonaDestino, double limiteBajoHora, double limiteAltoHora) {
		// TODO Auto-generated method stub
		
	}

	public void zonasPriorizadasMayorCantidadNodos(int n) {
		// TODO Auto-generated method stub
		
	}

	public void graficaASCII() {
		// TODO Auto-generated method stub
		
	}

}