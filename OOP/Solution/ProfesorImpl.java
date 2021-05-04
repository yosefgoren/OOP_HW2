
package OOP.Solution;

import java.util.*;
import java.util.function.Predicate;

import OOP.Provided.*;


public class ProfesorImpl implements OOP.Provided.Profesor {
    int id;
    String name;
    Collection<CasaDeBurrito> favourites;
    Set<Profesor> friends;

    public ProfesorImpl(int id, String name) {
        favourites = new HashSet<CasaDeBurrito>();
        friends = new HashSet<Profesor>();
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public OOP.Provided.Profesor favorite(CasaDeBurrito c) throws UnratedFavoriteCasaDeBurritoException {
        if (!(c.isRatedBy(this))) {
            throw new UnratedFavoriteCasaDeBurritoException();
        }
        favourites.add(c);
        return this;
    }


    public Collection<CasaDeBurrito> favorites() {
        Set<CasaDeBurrito> temp = new HashSet<>(favourites);
        return temp; //TODO Clone
    }

    public OOP.Provided.Profesor addFriend(OOP.Provided.Profesor p)
            throws SameProfesorException, ConnectionAlreadyExistsException {

        if (friends.contains(p)) throw new ConnectionAlreadyExistsException();

        if (this.equals(p)) throw new SameProfesorException();

        friends.add(p);

        return this;
    }

    public Set<Profesor> getFriends() {
        Set<Profesor> temp = new HashSet<>(friends);
        return temp; //TODO clone
    }


    public Set<Profesor> filteredFriends(Predicate<Profesor> p) {
        Set<Profesor> friendsTemp = new HashSet<>(friends);
        Iterator<Profesor> it = friendsTemp.iterator();
        while (it.hasNext()) {
            Profesor prof = it.next();
            if (p.test(prof) == false) {
                friendsTemp.remove(prof);

            }
        }
        return friendsTemp;

    }

    public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p) {
        Set<CasaDeBurrito> Temp = new HashSet<>(favourites);
        Iterator<CasaDeBurrito> it = favourites.iterator();
        while (it.hasNext()) {
            CasaDeBurrito casa = it.next();
            if (p.test(casa) == false) {
               Temp.remove(casa);
            }




        }
        List<CasaDeBurrito> sortedList = new ArrayList<>(Temp);
        Collections.sort(sortedList, comp);
        return sortedList;
    }


    /**
     * Both favourites by-X  methods are sorted using  filter and sort method above
     **/


    public Collection<CasaDeBurrito> favoritesByRating(int rLimit) {
        Comparator<CasaDeBurrito> comp_rate_dist_id = Comparator.comparing(CasaDeBurrito::averageRating)
                .reversed().thenComparing(CasaDeBurrito::distance).thenComparing(CasaDeBurrito::getId);


        return filterAndSortFavorites(comp_rate_dist_id, (c) -> c.averageRating() >= rLimit);
    }

    public Collection<CasaDeBurrito> favoritesByDist(int dLimit) {
        Comparator<CasaDeBurrito> comp_dist_rate_id = Comparator.comparing(CasaDeBurrito::distance)
                .thenComparing(CasaDeBurrito::averageRating).reversed().thenComparing(CasaDeBurrito::getId);


        return filterAndSortFavorites(comp_dist_rate_id, (c) -> c.distance() <= dLimit);
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof Profesor)) {
            return false;
        }

        Profesor p = (Profesor) o;
        return (this.id == p.getId());
    }


    @Override
    public int compareTo(Profesor p) {
        return (this.id - p.getId());
    }

    @Override
    public String toString() {

        String toReturn = "\n";
        toReturn += "Profesor: " + this.name + ".\n";
        toReturn += "Id: " + this.id + ".\n";
        toReturn += "Favorites: " + favouriteNames() +".\n";
        return toReturn;
    }


    public String favouriteNames() {

        List<CasaDeBurrito> sortedList = new ArrayList<>(favourites);
        Collections.sort(sortedList, Comparator.comparing(CasaDeBurrito::getName));
        Iterator<CasaDeBurrito> it = sortedList.iterator();
        String toReturn = "";
        while (it.hasNext()) {
            String temp = it.next().getName();
            if (it.hasNext()) {
                toReturn += temp + ", ";
            } else {
                toReturn += temp;
            }

        }
        return toReturn;
    }



}
