package OOP.Solution;

import OOP.Provided.*;
//import OOP.Provided.CasaDeBurrito;
//import OOP.Provided.Profesor;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;


public class CartelDeNachosImpl implements CartelDeNachos {
    Map<Integer,Profesor> profesorMap;
    Map<Integer,CasaDeBurrito> casaDeBurritoMap;

    public CartelDeNachosImpl(){
        profesorMap = new TreeMap<Integer, Profesor>() {
        };
        casaDeBurritoMap = new TreeMap<Integer, CasaDeBurrito>();
    }


    public  Profesor joinCartel(int id, String name)
            throws Profesor.ProfesorAlreadyInSystemException{

    Profesor p = new ProfesorImpl(id,name);

    if(profesorMap.containsKey(id)){
        throw  new Profesor.ProfesorAlreadyInSystemException();
    }

    profesorMap.put(id,p);

    return p;

    }

    public CasaDeBurrito addCasaDeBurrito(int id, String name, int dist, Set<String> menu)
            throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException{

         CasaDeBurrito c = new CasaDeBurritoImpl(id,name,dist,menu);

         if(casaDeBurritoMap.containsKey(id)){
             throw new CasaDeBurrito.CasaDeBurritoAlreadyInSystemException();
         }

        casaDeBurritoMap.put(id,c);

         return c;
    }

    public Collection<Profesor> registeredProfesores(){
        return profesorMap.values();
    }

    public Collection<CasaDeBurrito> registeredCasasDeBurrito(){
       return casaDeBurritoMap.values();
    }

    public Profesor getProfesor(int id)
            throws Profesor.ProfesorNotInSystemException {

        Profesor p = profesorMap.get(id);
        if (p == null) throw new Profesor.ProfesorNotInSystemException();
        return p;
    }

    public CasaDeBurrito getCasaDeBurrito(int id)
            throws CasaDeBurrito.CasaDeBurritoNotInSystemException{
        CasaDeBurrito c =casaDeBurritoMap.get(id);
        if (c == null) throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        return c;

    }

    public CartelDeNachos addConnection(Profesor p1, Profesor p2)
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
    public Collection<CasaDeBurrito> favoritesByRating(Profesor p)
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

    public Collection<CasaDeBurrito> favoritesByDist(Profesor p)
            throws Profesor.ProfesorNotInSystemException{
        if(!profesorMap.containsValue(p)) throw new Profesor.ProfesorNotInSystemException();
        Collection<CasaDeBurrito> filteredSortedBurritos = p.favoritesByDist(0);
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


    public boolean getRecommendation(Profesor p, CasaDeBurrito c, int t)
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


    public List<Integer> getMostPopularRestaurantsIds(){
    ArrayList<PairInt> resturantPoints = new ArrayList<>();
        for (CasaDeBurrito c:casaDeBurritoMap.values()) {
            int i=0;
            for (Profesor p1:profesorMap.values()) {
                for (Profesor p2:p1.getFriends()){
                    if(p2.favorites().contains(c)){
                        i++;
                    }
                }
            }
            resturantPoints.add(new PairInt(i,c.getId()));
        }
        Collections.sort(resturantPoints);
        ArrayList<Integer> toReturn = new ArrayList<>();
        for (PairInt p:resturantPoints){
            toReturn.add(p.getSecond());
        }

    return toReturn;
    }

    @Override
    public String toString() {

        Function<Pair<Collection<String>, String>, String> cat_all = (pair) ->
        {
            Collection<String> collection = pair.getFirst();
            String spacer = pair.getSecond();
            String result = "";

            for (String s : collection)
                result += s + spacer;
            result = result.substring(0, result.length() - spacer.length());
            return result;
        };


        String toReturn = "";
        toReturn += "Registered profesores: " +  cat_all.apply(new Pair(profesorMap,", "))+".\n";
        toReturn += "Registered casas de burrito: " + cat_all.apply(new Pair(casaDeBurritoMap,", "))+".\n";
        toReturn += "Profesores:\n";
        for (Profesor p_it : profesorMap.values()) {
            toReturn += p_it.getId() + " -> ["+ cat_all.apply(new Pair(profesorMap,", "))+"].\n";
            }
        toReturn+="End profesores\n";
            return toReturn;
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

    static class PairInt implements Comparable<PairInt> {
        Integer o1;
        Integer o2;

        public PairInt(Integer o1, Integer o2) {
            this.o1 = o1;
            this.o2 = o2;
        }

        public Integer getFirst() {
            return o1;
        }

        public Integer getSecond() {
            return o2;
        }

        public void setFirst(Integer o1) {
            this.o1 = o1;
        }

        public void setSecond(Integer o2) {
            this.o2 = o2;
        }

        @Override
        public int compareTo(PairInt p) {
            if(this.getFirst()!=p.getFirst()){
                return this.getFirst()-p.getFirst();
            }
            return this.getSecond()-p.getSecond();
        }
    }

    static class Pair<T1, T2> {
        T1 o1;
        T2 o2;

        public Pair(T1 o1, T2 o2) {
            this.o1 = o1;
            this.o2 = o2;
        }

        public T1 getFirst() {
            return o1;
        }

        public T2 getSecond() {
            return o2;
        }

        public void setFirst(T1 o1) {
            this.o1 = o1;
        }

        public void setSecond(T2 o2) {
            this.o2 = o2;
        }
    }


}


