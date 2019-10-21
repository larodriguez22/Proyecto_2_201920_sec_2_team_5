package model.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.opencsv.CSVReader;

import model.data_structures.ArbolRojoNegro;
import model.data_structures.LinearProbingHashST;
import model.data_structures.MaxPQ;
import model.data_structures.Queue;
import model.logic.Feature;
import model.logic.Properties;
import model.logic.Geometry;

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
	private LinearProbingHashST<Integer, NodoMallavial> nodos;
	private ArbolRojoNegro<Integer, Feature> FeatureCollection ;

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
		String path = "./data/bogota_cadastral.json";
		Gson gson = new Gson();
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(path));
			FeatureCollection = gson.fromJson(reader,  ArbolRojoNegro.class );
			System.out.println(""+FeatureCollection.size());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("la cague");
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
			NodoMallavial nodoAux= new NodoMallavial(partes[0], partes[1], partes[2]);
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
				reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-"+i+"-All-MonthlyAggregate.csv"));
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
				reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-"+i+"-All-HourlyAggregate.csv"));
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
		MaxPQ<Viaje> aux=colaMes;
		Queue<Viaje> lista=new Queue<>();
		for(int i=0; i<aux.size()&&lista.size()<n;i++)
		{
			Viaje v=aux.delMax();
			if(v.getMean_travel_time()>=limiteBajoTiempo&&v.getMean_travel_time()<=limiteAltoTiempo)
			{
				lista.enqueue(v);
			}
		}
		for(int i=lista.size(); i>0;i--)
		{
			Viaje v=lista.dequeue();
			System.out.println("zona de origen: "+v.getSourceid()+", zona de destino: "+v.getDstid()+", mes: "+v.getInfo()+", tiempo promedio: "+v.getMean_travel_time());
		}
	}

	public void nZonasMasNorte(int n) {
		// TODO Auto-generated method stub

	}

	public void buscarNodos(double latitud, double longitud) 
	{
		// TODO Auto-generated method stub
		Queue<NodoMallavial> lista=new Queue<>();
		for(int i=0; i<nodos.size(); i++)
		{
			DecimalFormat df = new DecimalFormat("#.###");		
			if(df.format(nodos.get(i).getLatitud())==df.format(latitud)&&df.format(nodos.get(i).getLongitud())==df.format(longitud))
			{
				lista.enqueue(nodos.get(i));
			}
		}
		System.out.println("Numero de nodos: "+lista.size());
		for(int i=lista.size();i>0;i--)
		{
			NodoMallavial n=lista.dequeue();
			System.out.println("ID: "+n.getId()+", latitud: "+n.getLatitud()+", longitud: "+n.getLongitud());
		}
	}

	public void tiemposEsperaRangoDadoDeDesviaciones(int n, double limiteBajoDesviacion, double limiteAltoDesviacion) {
		// TODO Auto-generated method stub
		MaxPQ<Viaje> aux=colaMes;
		Queue<Viaje> lista=new Queue<>();
		for(int i=aux.size(); i>0&&lista.size()<n;i++)
		{
			Viaje v=aux.delMax();
			if(v.getStandard_deviation_travel_time()>=limiteBajoDesviacion&&v.getStandard_deviation_travel_time()<=limiteAltoDesviacion)
			{
				lista.enqueue(v);
			}
		}
		for(int i=lista.size(); i>0;i--)
		{
			Viaje v=lista.dequeue();
			System.out.println("zona de origen: "+v.getSourceid()+", zona de destino: "+v.getDstid()+", mes: "+v.getInfo()+", desviacion estandar: "+v.getStandard_deviation_travel_time());
		}
	}

	public void tiemposZonaOrigenYHoraDada(double zonaOrigen, double hora) {
		// TODO Auto-generated method stub
		MaxPQ<Viaje> aux=colaHora;
		for(int i=aux.size(); i>0; i++)
		{
			Viaje v=aux.delMax();
			if(v.getSourceid()==zonaOrigen&&v.getInfo()==hora)
			{
				System.out.println("zona de origen: "+v.getSourceid()+", zona de destino: "+v.getDstid()+", hora: "+v.getInfo()+", tiempo promedio: "+v.getMean_travel_time());
			}
		}
	}

	public void tiemposZonaDestinoRangoHoraDada(double zonaDestino, double limiteBajoHora, double limiteAltoHora) 
	{
		// TODO Auto-generated method stub
		MaxPQ<Viaje> aux=colaHora;
		for(int i=aux.size(); i>0; i++)
		{
			Viaje v=aux.delMax();
			if(v.getDstid()==zonaDestino&&(v.getInfo()>=limiteBajoHora&&v.getInfo()<=limiteAltoHora))
			{
				System.out.println("zona de origen: "+v.getSourceid()+", zona de destino: "+v.getDstid()+", hora: "+v.getInfo()+", tiempo promedio: "+v.getMean_travel_time());
			}
		}
	}

	public void zonasPriorizadasMayorCantidadNodos(int n) {
		// TODO Auto-generated method stub

	}

	public void graficaASCII() {
		// TODO Auto-generated method stub

	}

}