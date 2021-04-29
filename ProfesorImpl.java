


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
        if(!(c.isRatedBy(this))){
            throw new UnratedFavoriteCasaDeBurritoException();
        }
            favourites.add(c);
            return this;
    }


    public Collection<OOP.Provided.CasaDeBurrito> favorites() {
        return favourites;
    }

    public OOP.Provided.Profesor addFriend(OOP.Provided.Profesor p)
            throws SameProfesorException, ConnectionAlreadyExistsException {

        if (friends.contains(p)) throw new ConnectionAlreadyExistsException();

        if (this.equals(p)) throw new SameProfesorException();

        friends.add(p);

        return this;
    }

    public Set<Profesor> getFriends() {
        return friends;
    }

    public Set<Profesor> filteredFriends(Predicate<Profesor> p) {
        Iterator<Profesor> it = friends.iterator();
        while (it.hasNext()) {
            Profesor prof = it.next();
            if (p.test(prof) == false) {
                friends.remove(prof);

            }
        }
        return friends;

    }

    public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p) {
        Iterator<CasaDeBurrito> it = favourites.iterator();
        while (it.hasNext()) {
            CasaDeBurrito casa = it.next();
            if (p.test(casa) == false) {
                favourites.remove(casa);

            }


        }
        List<CasaDeBurrito> sortedList = new ArrayList<>(favourites);
        Collections.sort(sortedList, comp);
        return sortedList;
    }


/** Both favourites by-X  methods are sorted using  filter and sort method above**/


   public Collection<CasaDeBurrito> favoritesByRating(int rLimit){
        Comparator<CasaDeBurrito> comp_rate_dist_id =
                //TODO finish comparator and send it + predicate to filterandSortFavourites
    }

    public Collection<CasaDeBurrito> favoritesByDist(int dLimit){

    }

    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }

        if(!(o instanceof  Profesor)){
            return false;
        }

        Profesor p = (Profesor) o;
        return (this.id == p.getId());
    }


    @Override
    public int compareTo(Profesor p){
        return (this.id - p.getId());
    }

}