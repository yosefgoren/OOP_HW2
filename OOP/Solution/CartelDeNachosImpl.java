package OOP.Solution;

import OOP.Provided.*;
//import OOP.Provided.CasaDeBurrito;
//import OOP.Provided.Profesor;
import java.util.*;
import java.util.stream.Stream;


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

         CasaDeBurrito c = new CasaDeBurritoImpl(id,name,dist,menu);

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
        prof1.addFriend(prof2);
        prof2.addFriend(prof1);

        return (CartelDeNachos) this;


    }

    /**
     * Gets Professor's friends first, sorts them by ID
     * Gets CasaDeBurritos and sorts them by Rating
     * Then for each friend, check if the resturant exists in its fav and remove it from the list if it ain't
     * @param p
     * @return  sorted and filtered resturants
     * @throws Profesor.ProfesorNotInSystemException
     */
    Collection<CasaDeBurrito> favoritesByRating(Profesor p)
            throws Profesor.ProfesorNotInSystemException {
        if(!profesorMap.containsValue(p)) throw new Profesor.ProfesorNotInSystemException();
        Collection<CasaDeBurrito> filteredSortedBurritos = p.favoritesByRating(0);
        ArrayList<Profesor> sortedList = new ArrayList<>(p.getFriends());


        Collections.sort(sortedList, Comparator.comparing(Profesor::getId));
        for (Profesor p_1 : sortedList) {
            for (CasaDeBurrito c_it : filteredSortedBurritos) {  //Might Need to iterate first to find those to remove
                if (!p_1.favorites().contains(c_it)){
                    filteredSortedBurritos.remove(c_it);
                }
            }

        }
        return filteredSortedBurritos;
    }



    boolean getRecommendation(Profesor p, CasaDeBurrito c, int t)
            throws Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CartelDeNachos.ImpossibleConnectionException{
    if(!profesorMap.containsValue(p)) throw new Profesor.ProfesorNotInSystemException();
    if(!casaDeBurritoMap.containsValue(c)) throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
    if(t<0) throw new CartelDeNachos.ImpossibleConnectionException();
        ArrayList<Profesor> visited = new ArrayList<>();
    try {
        BFS(p,c,t,visited);
    }catch(Exception e) {
        return false;
    }
        return true;
    }


    public void BFS(Profesor p,CasaDeBurrito c,int t,ArrayList<Profesor> visited) throws CartelDeNachos.ImpossibleConnectionException {
        if (t <= 0) throw new CartelDeNachos.ImpossibleConnectionException();
        visited.add(p);
        if(!visited.contains(p)) {
            for (Profesor p_it : p.getFriends()) {
                if (p.favorites().contains(c)) ;
            }
            for (Profesor p_it : p.getFriends()) {
                BFS(p_it, c, t - 1, visited);
            }

        }
    }


    List<Integer> getMostPopularRestaurantsIds(){
    Map<CasaDeBurrito,Integer> resturantPoints = new TreeMap<>();
        for (CasaDeBurrito c:casaDeBurritoMap.values()) {
            int i=0;
            for (Profesor p1:profesorMap.values()) {
                for (Profesor p2:p1.getFriends()){
                    if(p2.favorites().contains(c)){
                        i++;
                    }
                }//TODO
            }
            resturantPoints.put(c,i);
        }

    return ;
    }

    @Override
    String toString() {
        String toReturn = "\n";
        toReturn += "Registered profesores: " + Profs() + ".\n";
        toReturn += "Registered casas de burrito: " + Casas() + ".\n";
        toReturn += "Profesores:\n";
        for (Profesor p_it : profesorMap.values()) {
            toReturn += p_it.getId() + " -> [";
            for (Profesor p_it2 : p_it.getFriends()) {
             //TODO
            }


        }
    }
    public String Profs() {

        List<Profesor> sortedList = new ArrayList<>(profesorMap.values());
        Collections.sort(sortedList, Comparator.comparing(Profesor::getId));
        Iterator<Profesor> it = sortedList.iterator();
        String toReturn = "";
        while (it.hasNext()) {
            int temp = it.next().getId();
            if (it.hasNext()) {
                toReturn += temp + ", ";
            } else {
                toReturn += temp;
            }

        }
        return toReturn;
    }

    public String Casas() {

        List<CasaDeBurrito> sortedList = new ArrayList<>(casaDeBurritoMap.values());
        Collections.sort(sortedList, Comparator.comparing(CasaDeBurrito::getId));
        Iterator<CasaDeBurrito> it = sortedList.iterator();
        String toReturn = "";
        while (it.hasNext()) {
            int temp = it.next().getId();
            if (it.hasNext()) {
                toReturn += temp + ", ";
            } else {
                toReturn += temp;
            }

        }
        return toReturn;
    }


}


