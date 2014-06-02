/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package process_algorithm;

import java.lang.Comparable;

/**
 *
 * @author apsq
 */
public class Proceso implements Comparable<Proceso> {
    
    String nombre;
    int prioridad,
        rafaga,
        tEspera,
        tDuracion;
    
    public static boolean flag;
    
    public Proceso()
    {
        //dummy constructor, seriously?
    }
    
    public Proceso(Proceso other)
    {
        this.nombre = other.nombre;
        this.prioridad = other.prioridad;
        this.rafaga = other.rafaga;
        this.tEspera = other.tEspera;
        this.tDuracion = other.tDuracion;
    }
        
     public int compareTo(Proceso other) {
         if(flag)
            return ( this.prioridad - other.prioridad);
         else
            return (this.rafaga - other.rafaga); 
     }
}
