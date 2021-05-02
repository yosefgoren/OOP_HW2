package OOP.Solution1;

import OOP.Provided.*;
//import OOP.Provided.CasaDeBurrito;
//import OOP.Provided.Profesor;
import java.util.*;


public class CartelDeNachosImpl {
    Map<Integer,Profesor> profesorMap;
    Map<Integer,CasaDeBurrito> casaDeBurritoMap;

    public CartelDeNachosImpl(){
        profesorMap = new TreeMap<Integer, Profesor>() {
        };
        casaDeBurritoMap = new TreeMap<Integer, CasaDeBurrito>();
    }


    Profesor joinCartel(int id, String name)
            throws Profesor.ProfesorAlreadyInSystemException{

    Profesor p = new ProfesorImpl(id,name);

    if(profesorMap.containsKey(id)){
        throw  new Profesor.ProfesorAlreadyInSystemException();
    }

    profesorMap.put(id,p);

    return p;

    }

    CasaDeBurrito addCasaDeBurrito(int id, String name, int dist, Set<String> menu)
            throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException{

         CasaDeBurrito c = new CasaDeBurrito(id,name,dist,menu);

         if(casaDeBurritoMap.containsKey(id)){
             throw new CasaDeBurrito.CasaDeBurritoAlreadyInSystemException();
         }

        casaDeBurritoMap.put(id,c);

         return c;
    }

    Collection<Profesor> registeredProfesores(){
        return profesorMap.values();
    }

    Collection<CasaDeBurrito> registeredCasasDeBurrito(){
       return casaDeBurritoMap.values();
    }

    Profesor getProfesor(int id)
            throws Profesor.ProfesorNotInSystemException {

        Profesor p = profesorMap.get(id);
        if (p == null) throw new Profesor.ProfesorNotInSystemException();
        return p;
    }

    CasaDeBurrito getCasaDeBurrito(int id)
            throws CasaDeBurrito.CasaDeBurritoNotInSystemException{
        CasaDeBurrito c =casaDeBurritoMap.get(id);
        if (c == null) throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        return c;

    }

    CartelDeNachos addConnection(Profesor p1, Profesor p2)
            throws Profesor.ProfesorNotInSystemException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException{
        Profesor prof1 = profesorMap.get(p1);
        Profesor prof2 = profesorMap.get(p2);
        if (prof1.equals(prof2)) throw new Profesor.SameProfesorException();

        if(prof1 == null || prof2 == null) throw new Profesor.ProfesorNotInSystemException();




    }

}


