package controller;

import java.util.Scanner;

import model.logic.MVCModelo;
import view.MVCView;

public class Controller {

	/* Instancia del Modelo*/
	private MVCModelo modelo;

	/* Instancia de la Vista*/
	private MVCView view;

	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new MVCView();
		modelo = new MVCModelo();
	}

	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String dato = "";
		String respuesta = "";

		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){
			case 1:
				System.out.println("--------- \nCargar los datos: ");
				modelo.cargar();
				break;

			case 2:
				System.out.println("--------- \nObtener las N letras más frecuentes por las que comienza el nombre de una zona ");
				System.out.println("Escriba el numero de datos que desea consultar:");
				int N=lector.nextInt();
				modelo.NLetrasFrecuentesZona(N);					
				break;

			case 3:
				System.out.println("--------- \nBuscar los nodos que delimitan las zonas por Localización Geográfica");
				System.out.println("Escriba la latitud de la zona que desea consultar:");
				String latitud=lector.next();
				System.out.println("Escriba la longitud de la zona que desea consultar:");
				String longitud=lector.next();
				modelo.buscarNodosDelimitanZona(latitud, longitud);
				break;
			
			case 4:
				System.out.println("--------- \nBuscar los tiempos promedio de viaje que están en un rango y que son del primer trimestre del 2018");
				System.out.println("Escriba el numero de datos que desea consultar:");
				N=lector.nextInt();	
				System.out.println("Escriba el limite bajo de tiempos promedio que desea consultar:");
				double limiteBajoTiempo=lector.nextDouble();
				System.out.println("Escriba el limite alto de tiempos promedio que desea consultar:");
				double limiteAltoTiempo=lector.nextDouble();
				modelo.tiempoPromedioRango(N, limiteBajoTiempo, limiteAltoTiempo);
				break;
	
			case 5: 
				System.out.println("--------- \nBuscar los N zonas que están más al norte");
				System.out.println("Escriba el numero de datos que desea consultar:");
				N=lector.nextInt();
				modelo.nZonasMasNorte(N);
				break;	

			case 6: 
				System.out.println("--------- \nBuscar nodos de la malla vial por Localización Geográfica (latitud, longitud)");
				System.out.println("Escriba la latitud de la zona que desea consultar:");
				Double platitud=lector.nextDouble();
				System.out.println("Escriba la longitud de la zona que desea consultar:");
				Double plongitud=lector.nextDouble();
				modelo.buscarNodos(platitud, plongitud);
				break;	

			case 7: 
				System.out.println("--------- \nBuscar los tiempos de espera que tienen una desviación estándar en un rango dado y que son del primer trimestre del 2018");
				System.out.println("Escriba el numero de datos que desea consultar:");
				N=lector.nextInt();
				System.out.println("Escriba el limite bajo de la desviacion estandar de los tiempos promedio que desea consultar:");
				double limiteBajoDesviacion=lector.nextDouble();
				System.out.println("Escriba el limite alto de la desviacion estandar de los tiempos promedio que desea consultar:");
				double limiteAltoDesviacion=lector.nextDouble();
				modelo.tiemposEsperaRangoDadoDeDesviaciones(N, limiteBajoDesviacion, limiteAltoDesviacion);
				break;	

			case 8: 
				System.out.println("--------- \nRetornar todos los tiempos de viaje promedio que salen de una zona dada y a una hora dada");
				System.out.println("Escriba la zona de origen de los viajes que desea consultar:");
				double zonaOrigen=lector.nextDouble();
				System.out.println("Escriba la hora de los viajes que desea consultar:");
				double hora=lector.nextDouble();
				modelo.tiemposZonaOrigenYHoraDada(zonaOrigen, hora);
				break;	

			case 9: 
				System.out.println("--------- \nRetornar todos los tiempos de viaje que llegan de una zona dada y en un rango de horas");
				System.out.println("Escriba la zona de destino de los viajes que desea consultar:");
				double zonaDestino=lector.nextDouble();
				System.out.println("Escriba el limite bajo del rango de horas que desea consultar:");
				double limiteBajoHora=lector.nextDouble();
				System.out.println("Escriba el limite alto del rango de horas que desea consultar:");
				double limiteAltoHora=lector.nextDouble();
				modelo.tiemposZonaDestinoRangoHoraDada(zonaDestino, limiteBajoHora, limiteAltoHora);
				break;	

			case 10: 
				System.out.println("--------- \nObtener las N zonas priorizadas por la mayor cantidad de nodos que definen su frontera");
				System.out.println("Escriba el numero de datos que desea consultar:");
				N=lector.nextInt();
				modelo.zonasPriorizadasMayorCantidadNodos(N);
				break;

			case 11: 
				System.out.println("--------- \nGráfica ASCII - Porcentaje de datos faltantes para el primer semestre 2018");
				System.out.println("Trimestre");
				double trimestre=lector.nextDouble();
				System.out.println("Hora");
				double Hora=lector.nextDouble();
				modelo.graficaASCII(trimestre, Hora);
				break;

			case 12: 
				System.out.println("--------- \n Hasta pronto !! \n---------"); 
				lector.close();
				fin = true;
				break;	

			default: 
				System.out.println("--------- \n Opcion Invalida !! \n---------");
				break;
			}
		}

	}	
}
