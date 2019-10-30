package model.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
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
	private ArbolRojoNegro<String, Feature> FeatureCollections ;
	private FeatureCollection copia;

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public MVCModelo()
	{
		colaMes= new MaxPQ<>();
		colaDia= new MaxPQ<>();
		colaHora= new MaxPQ<>();
		nodos= new LinearProbingHashST<>();
		
		FeatureCollections=new ArbolRojoNegro<String, Feature>();
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

	public void cargarJsonArbol() throws IOException
	{
		String path = "./data/bogota_cadastral.json";
		Gson gson = new Gson();
		JsonReader reader;

		try {
			
			reader = new JsonReader(new FileReader(path));
			reader.beginObject();
			// __________
			ArrayList<Coordinate> co = new ArrayList<Coordinate>();
			List<Feature> features = new ArrayList<Feature>();
			Properties pr = null;
			Geometry geo = null;
			Feature fe = null;
			String type3 = "";
			String type2 = "";
			String type = "";
			int i = 0;
			String key = "";

			// __________
			
			while (reader.hasNext()) {
				
				String name = reader.nextName();
				if (name.equals("type")) {
					type = reader.nextString();
				}
				if (name.equals("features")) {
					
					reader.beginArray();
					while (reader.hasNext()) {
						
						
						// name= reader.nextName();
						reader.beginObject();

						while (reader.hasNext()) {

							

							name = reader.nextName();
							if (name.equals("type")) {

								type2 = reader.nextString();
								
							}
							if (name.equals("geometry")) {
								
								reader.beginObject();

								while (reader.hasNext()) {
									

									name = reader.nextName();
									if (name.equals("type")) {
										type3 = reader.nextString();
									}

									if (name.equals("coordinates")) {
										
										reader.beginArray();
										while (reader.hasNext()) {
											reader.beginArray();
											while (reader.hasNext()) {
												reader.beginArray();
												while (reader.hasNext()) {
													reader.beginArray();

													double longitud = reader.nextDouble();

													double latitud = reader.nextDouble();

													Coordinate c = new Coordinate(longitud, latitud);
													co.add(c);

													reader.endArray();

												}

												
												reader.endArray();
											}

											

											reader.endArray();
										}
										
										reader.endArray();
									}

								}
								reader.endObject();

								geo = new Geometry(type3, co);
								
							}
							if (name.equals("properties")) {
								i++;
								
								reader.beginObject();

								int a = 0, c = 0;
								String b = "", d = "", g = "", h = "";
								double e = 0, f = 0;
								while (reader.hasNext()) {
									
									name = reader.nextName();
									if (name.equals("cartodb_id")) {
										a = reader.nextInt();
									}
									if (name.equals("scacodigo")) {
										b = reader.nextString();
									}
									if (name.equals("scatipo")) {
										c = reader.nextInt();
									}

									if (name.equals("scanombre")) {
										d = reader.nextString();
									}

									if (name.equals("shape_leng")) {
										e = reader.nextDouble();
									}

									if (name.equals("shape_area")) {
										f = reader.nextDouble();
									}

									if (name.equals("MOVEMENT_ID")) {
										g = reader.nextString();
										key = g;
									}

									if (name.equals("DISPLAY_NAME")) {
										h = reader.nextString();

									}
									
								}
								pr = new Properties(a, b, c, d, e, f, g, h);
								reader.endObject();

								fe = new Feature(type2, geo, pr);
								
								reader.endObject();
								// ___________________
								if (i < 1160) {
									reader.beginObject();
								}
								// _____________________
								features.add(fe);
							}
							

						}

					}
					reader.endArray();
				}

			}
			reader.endObject();
			copia = new FeatureCollection(features, type);
			for (int j = 0; j < copia.getFeatures().size(); j++) {
				FeatureCollections.put(copia.getFeatures().get(j).getProperty().getMOVEMENTID(),copia.getFeatures().get(j));

			}
			System.out.println(""+FeatureCollections.size());

		}

		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("error catch cargar");
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
		
		ArrayList<ArrayList<String>> auxT= new ArrayList<ArrayList<String>>();
		ArrayList<String> car=new ArrayList<String>();
		char caracter=' ';
		char caracter2=' ';
		String nombre="";
		
		for(int i=1;i<FeatureCollections.size();i++){
			ArrayList<String> auxnombres= new ArrayList<String>();
			String conver=""+i;
			caracter=FeatureCollections.get(conver).getProperty().getScanombre().charAt(0);
			
			if(!car.contains(caracter+"")){
				car.add(caracter+"");
			
			for(int j=1;j<FeatureCollections.size();j++){
				
					String conver2=""+j;
					nombre=FeatureCollections.get(conver2).getProperty().getScanombre();
					caracter2=nombre.charAt(0);
					
					if(caracter==caracter2){
						
						auxnombres.add(nombre);
						
					}
			}		
				
			
			auxT.add(auxnombres);
			
			}
		
		}
		boolean terminado=false;
		int contadorp=0;
		while (!terminado){
		int mayor=0;
		int posicion=0;
			for (int k=0;k<auxT.size();k++){
				if(mayor<k){
					mayor=auxT.get(k).size();
					posicion=k;
				}
			}
			System.out.println("Cantidad elementos: "+auxT.get(posicion).size());
			System.out.println("Caracter: "+auxT.get(posicion).get(0).charAt(0));
			System.out.println("Nombres: ");
			for (int j=0;j<auxT.size();j++){
				System.out.print(auxT.get(posicion).get(j)+", ");
			}
			auxT.remove(posicion);
			contadorp++;
			if(contadorp==n){
				terminado=true;
			}
			
		}
		

	}


	public void buscarNodosDelimitanZona(String latitud, String longitud) {
		// TODO Auto-generated method stub
		
		String nuevaLatitud=truncarNumero(Double.parseDouble(latitud));
		String nuevaLongitud=truncarNumero(Double.parseDouble(longitud));
		int contador=0;
		//System.out.println(nuevaLatitud);
		//System.out.println(nuevaLongitud);
		for(int i=1;i<FeatureCollections.size();i++){
			
			for(int j=1;j<FeatureCollections.get(Integer.toString(i)).getGeometry().getCoordinates().size();j++){
				
				String Tlatitud=truncarNumero(FeatureCollections.get(i+"").getGeometry().getCoordinates().get(i).latitud);
				String Tlongitud=truncarNumero(FeatureCollections.get(i+"").getGeometry().getCoordinates().get(i).longitud);
				if(nuevaLatitud.equals(Tlatitud)&&nuevaLongitud.equals(Tlongitud)){
					System.out.println("Longitud: " +FeatureCollections.get(i+"").getGeometry().getCoordinates().get(i).latitud);
					System.out.print(", Latitud: "+FeatureCollections.get(i+"").getGeometry().getCoordinates().get(i).longitud);
					System.out.print(", nombre: "+FeatureCollections.get(i+"").getProperty().getScanombre());
					contador++;
				}
			}
		}
		System.out.println("numero de nodos:"+contador);
		
	}
	public String truncarNumero(double n){
		// TODO Auto-generated method stub
		
		String nString=Double.toString(n);
		
		char[] aux=nString.toCharArray();
		int contador=0;
		String numero="";
		boolean empezo=false;
		boolean terminado = false;
		for (int i=0;i<aux.length&!terminado;i++){
			
			if(aux[i]=='.'){
				empezo= true;
			
			}
			if(empezo){
				if(contador<4){
					numero=numero+aux[i];
				}
				
				if(contador>=4){
					
					terminado=true;
				}
				contador++;
			}
			
			else{
				numero=numero+aux[i];
			}
			
		}
		return numero;
		
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
		ArrayList<Double>auxLatitud=new ArrayList<Double>();
		ArrayList<Double>auxLongitud=new ArrayList<Double>();;
		ArrayList<String>auxNombres=new ArrayList<String>();;
		
		
		for(int i=1;i<FeatureCollections.size();i++){
			
			double maxLatitud=0;
			double maxLongitud=0;
			String nombre="";
			System.out.println("-----------------Latitudes----------------------------");
			for(int j=1;j<FeatureCollections.get(Integer.toString(i)).getGeometry().getCoordinates().size();j++){
				System.out.println(FeatureCollections.get(i+"").getGeometry().getCoordinates().get(i).latitud);
				if(FeatureCollections.get(i+"").getGeometry().getCoordinates().get(i).latitud>maxLatitud){
					maxLatitud=FeatureCollections.get(i+"").getGeometry().getCoordinates().get(i).latitud;
					maxLongitud=FeatureCollections.get(i+"").getGeometry().getCoordinates().get(i).longitud;
					nombre=FeatureCollections.get(i+"").getProperty().getScanombre();
					System.out.println("-----------------Maxima latitud----------------------------");
					System.out.println(maxLatitud);
				}
				
				
			}
			auxLatitud.add(maxLatitud);
			auxLongitud.add(maxLongitud);
			auxNombres.add(nombre);
			
		}
		
		int mayor=0;
		int posicion=0;
		for(int j=0;j<n;j++){
			for(int i=0;i<auxLatitud.size();i++){
				if(mayor<auxLatitud.get(i)){
					posicion=i;
				}
			}
			System.out.print("Nombre: "+auxNombres.get(posicion)+", ");
			System.out.print("Latitud: "+auxLatitud.get(posicion)+", ");
			System.out.println("Longitud: "+auxLongitud.get(posicion)+".");
			
			auxNombres.remove(posicion);
			auxLatitud.remove(posicion);
			auxLongitud.remove(posicion);
		}
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
		ArrayList<Feature>aux=new ArrayList<Feature>();
		Feature a=null;
		int contador=0;
		while(contador<n){
			int mayor=0;
			for (int i=0;i<FeatureCollections.size();i++){
				if(aux.contains(FeatureCollections.get(Integer.toString(i)))==false){
					if(mayor<FeatureCollections.get(Integer.toString(i)).getGeometry().getCoordinates().size()){
						mayor=FeatureCollections.get(Integer.toString(i)).getGeometry().getCoordinates().size();
						a=FeatureCollections.get(Integer.toString(i));
					}
				}
			}
			aux.add(a);
			contador++;
		}
		for (int i=0;i<aux.size();i++){
			System.out.print("Nombre: "+aux.get(i).getProperty().getScanombre());
			System.out.println("Cantidad nodos: "+aux.get(i).getGeometry().getCoordinates().size());
		}
		
	}
	public double maximaZona(){
		Iterator<Viaje> e=colaHora.iterator();
		double mayor=0;
		while(e.hasNext()){
			double aux=e.next().getSourceid();
			if(aux>mayor){
				mayor=aux;
			}
		}
		return mayor;
	}
	public void graficaASCII(double trimestre, double hod) {
		// TODO Auto-generated method stub
		for(double i=0;i<colaHora.size();i++){
		Iterator<Viaje> e=colaHora.iterator();
		double contador=0;
		while(e.hasNext()){
			Viaje aux=e.next();
			if(aux.getDstid()==i){
				if(aux.getTrimestre()==trimestre){
					if(aux.getInfo()==hod){
						contador++;
					}
				}
			}
		}
		double maximo=maximaZona();
		double faltantes=maximo-contador;
		double porcentaje=(faltantes/maximo)*100;
				System.out.println("i |");
				for(int j=0;j<=porcentaje;j=j+2){
					System.out.print("*");
				}
		}

	}
	

}