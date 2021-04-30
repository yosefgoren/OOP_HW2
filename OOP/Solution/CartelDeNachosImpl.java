package OOP.Solution;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import java.util.*;


public class CartelDeNachosImpl {
    Set<Profesor> profesorSet;
    Set<CasaDeBurrito> casaDeBurritoSet;

    public CartelDeNachosImpl(){
        profesorSet = new HashSet<>();
        casaDeBurritoSet = new HashSet<>();
    }


    Profesor joinCartel(int id, String name)
            throws Profesor.ProfesorAlreadyInSystemException{

    Profesor p = new ProfesorImpl(id,name);

    if(profesorSet.contains(p)){
        throw  new Profesor.ProfesorAlreadyInSystemException();
    }

    profesorSet.add(p);

    return p;

    }

    CasaDeBurrito addCasaDeBurrito(int id, String name, int dist, Set<String> menu)
            throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException{

         CasaDeBurrito c = new CasaDeBurrito(id,name,dist,menu);

         if(casaDeBurritoSet.contains(c)){
             throw new CasaDeBurrito.CasaDeBurritoAlreadyInSystemException();
         }

        casaDeBurritoSet.add(c);

         return c;
    }


}


