/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package process_algorithm;

import java.util.Scanner;

/**
 *
 * @author apsq
 */
public class Process_Algorithm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     
        int aux, numProc;
        double tiempo_espera = 0;
        String[] parse = { "FCFS", "SJF", "SRTF", "PriorityScheduling", "RoundRobin" }; 
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Menu.");
        for(int i = 0; i < parse.length; i++ )
                System.out.println(String.format("%d. %s.", i+1, parse[i])); 
        
        System.out.print("\n> ");
        aux = scan.nextInt();
        Algorithms algorithms = new Algorithms(parse[aux-1]);
     
        System.out.print(String.format("%s\nNumero de procesos: ", algorithms.nombre()));
        numProc = scan.nextInt();
        Proceso[] procesos = new Proceso[numProc];
        for(int i = 0; i < numProc; i++)
                procesos[i] = new Proceso();
        
        for(int i = 0; i < numProc; i++)
        {
            System.out.println(String.format("Proceso #%d:", i+1));
            System.out.print("\tNombre: ");
            procesos[i].nombre = scan.next();
            System.out.print("\tPrioridad: ");
            procesos[i].prioridad = scan.nextInt();
            System.out.print("\tRafaga: ");
            procesos[i].rafaga = scan.nextInt();
            
        }
        
        Proceso[] new_procesos = algorithms.run(procesos);
        
        System.out.print("Orden de llegada a la cola de procesos: ");
        for(int i = 0; i < numProc; i++)
            System.out.print(String.format("%s ", procesos[i].nombre));
        
        numProc = new_procesos.length;
        for(int i = 0, espera = 0; i < numProc; i++)
        {
            new_procesos[i].tEspera = espera;
            espera += new_procesos[i].rafaga;
            new_procesos[i].tDuracion = espera;
            
            tiempo_espera += new_procesos[i].tEspera;
        }
        
        System.out.println("\nSalida de Gant para la planificacion: ");
        
        for(int i = 0; i < numProc; i++)
        {
            System.out.print(String.format("|%s", new_procesos[i].nombre));
            for(int j = 0; j <= new_procesos[i].tDuracion; j++)
                    System.out.print(" ");
        }
        System.out.print("\n");
        for(int i = 0, j = 0; i < numProc; i++)
        {
            System.out.print(j);
            for(; j < new_procesos[i].tDuracion; j++)
                    System.out.print("  ");
        }
        
        tiempo_espera /= numProc;
        System.out.println(String.format("\n\nTiempo de espera: %f", tiempo_espera ));

    }
    
}
