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
                Proceso tmp_proc, tmp_proc2;
                ArrayList<Proceso> listo = new ArrayList<>();
                ArrayDeque<Proceso> standby = new ArrayDeque<>();
                ArrayDeque<Proceso> ready = new ArrayDeque<>();
                int execution = 0, timeLimit = 0, interruption = 1;
                
                Proceso.flag = true;
                Arrays.sort(procesos);
                
                for(int i = 0; i < procesos.length; i++)
                {
                    standby.add(procesos[i]);
                    timeLimit += procesos[i].rafaga;
                }
                
                while( execution < timeLimit )
                {
                    //aun quedan procesos por llegar?
                    if ( !standby.isEmpty()  && execution >= standby.getLast().prioridad )
                    {
                        while( !standby.isEmpty() )
                        {
                            ready.add(standby.getFirst());
                            standby.pop();
                        }
                        
                    }
                    
                    if(interruption != 0 && !standby.isEmpty())
                    {
                        tmp_proc = new Proceso(standby.getFirst());
                        standby.pop();
                    }
                    else
                    {  
                        tmp_proc = getMin(ready.clone());
                        ready.remove(tmp_proc);
                    }
                    
                    //si ya no quedan en standby
                    if( standby.isEmpty() )
                    {
                        listo.add(tmp_proc);
                         execution += tmp_proc.rafaga;
                    }
                    else
                    {
                           tmp_proc2 = standby.getFirst();
                           //si llegaron al mismo time
                           if( tmp_proc.prioridad ==  tmp_proc2.prioridad)
                           {
                               Proceso first_found;
                               ArrayDeque<Proceso> meh = standby.clone();
                               meh.pop();
                               
                               while(!meh.isEmpty())
                               {
                                   first_found = meh.getFirst();
                                   if(first_found.prioridad > tmp_proc2.prioridad )
                                   {
                                       tmp_proc2 = first_found;
                                       break;
                                   }
                                   
                                   standby.remove(first_found);
                                   meh.pop();
                               }
                               
                           }
                           
                           
                        //si p1 se puede ejecutar
                        if(execution+tmp_proc.rafaga <= tmp_proc2.prioridad)
                        {
                            listo.add(tmp_proc);
                            
                            execution += tmp_proc.rafaga;
                            interruption = 0; 
                        }
                        else
                        {
                            Proceso p_ready = new Proceso(tmp_proc);
                            p_ready.rafaga = tmp_proc2.prioridad-execution;
                            listo.add(p_ready);
                            
                            tmp_proc.rafaga -= p_ready.rafaga;
                            ready.add(tmp_proc);
                            
                            execution += p_ready.rafaga;
                            interruption = 1;
                        }
                    }
                   
                        
                }
                
                Proceso[] new_procesos = new Proceso[listo.size()];
                
                for(int i = 0; i < listo.size(); i++)
                    new_procesos[i] = listo.get(i);
                
                return new_procesos;
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
    
    public Proceso getMin(ArrayDeque<Proceso> queue)
    {
        Proceso menor = queue.getFirst();
        queue.pop();
        
        while( !queue.isEmpty() )
        {
            if(queue.getFirst().rafaga < menor.rafaga)
                    menor = queue.getFirst();
           
          queue.pop();  
        }
        
        return menor;
        
    }
}
