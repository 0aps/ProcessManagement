/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package process_algorithm;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.ArrayDeque;

interface cpu_algorithms
{
    public Proceso[] method(Proceso[] procesos);
}

/**
 *
 * @author apsq
 */
public class Algorithms {
    
    private final String algo_selected;
    private final Map<String, cpu_algorithms> selected = new HashMap<>();
    
    public Algorithms(String algo)
    {
        selected.put("FCFS", new cpu_algorithms() {
           @Override
            public Proceso[] method(Proceso[] procesos)
           {
               return procesos;
           }
        });
        
        selected.put("SJF", new cpu_algorithms() {
            @Override
            public Proceso[] method(Proceso[] procesos)
            {    
                Proceso.flag = false;
                
                Arrays.sort(procesos);
                return procesos;
            }
        });
        
        selected.put("SRTF", new cpu_algorithms() {
            @Override
            public Proceso[] method(Proceso[] procesos)
            {
                //in the meanwhile
                
                return procesos;
            }
        });
        
        selected.put("PriorityScheduling", new cpu_algorithms() {
            @Override
            public Proceso[] method(Proceso[] procesos)
            {   
                
                Proceso.flag = true;
                
                Arrays.sort(procesos);
                return procesos;
            }
        });
        
        selected.put("RoundRobin", new cpu_algorithms() {
            @Override
            public Proceso[] method(Proceso[] procesos)
            {    
                final int QUANTUM = 4;
                Proceso tmp_proc;
                ArrayList<Proceso> list = new ArrayList<>();
                ArrayDeque<Proceso> temp = new ArrayDeque<>();
                
                for(int i = 0; i < procesos.length; i++)
                {
                    temp.add(procesos[i]);
                }
                
                while( !temp.isEmpty() )
                {
                    
                   tmp_proc = new Proceso(temp.getFirst());
                   
                   if( tmp_proc.rafaga-QUANTUM > 0)
                   {
                       tmp_proc.rafaga = QUANTUM; 
                       list.add(tmp_proc);
                       
                       tmp_proc = temp.getFirst();
                       tmp_proc.rafaga -= QUANTUM;
                       
                       temp.pop();
                       temp.add(tmp_proc);                       
                   }else
                   {
                       temp.pop();
                       list.add(tmp_proc);
                   }
                       
                 }
                
                Proceso[] new_procesos = new Proceso[list.size()];
                
                for(int i = 0; i < list.size(); i++)
                    new_procesos[i] = list.get(i);
                
                return new_procesos;
            }
        });
        
        algo_selected = algo;
    }
    
    public Proceso[] run(Proceso[] procesos)
    {
        return selected.get(algo_selected).method(procesos);
    }
    
    public String nombre()
    {
        return algo_selected;
    }
}
