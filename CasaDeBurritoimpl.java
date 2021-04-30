
import java.util.*;
import java.util.function.*;
import OOP.Provided.*;

public class CasaDeBurritoimpl implements OOP.Provided.CasaDeBurrito
{
    int id;
    String name;
    int dist;
    Set<String> menu;
    Map<Profesor, Rating> ratings;

    static class Rating
    {
        int r;

        public Rating(int r) {
            this.r = r;
        }

        public int getR() {
            return r;
        }

        public void setR(int r) {
            if(r>5 || r<0) {
                throw new RateRangeException();
            }
            this.r = r;
        }
    }

    public CasaDeBurritoimpl(int id, String name, int dist, Set<String> menu) {
        this.id = id;
        this.name = name;
        this.dist = dist;
        this.menu = menu;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int distance()
    {
        return dist;
    }

    public boolean isRatedBy(Profesor p)
    {
        return ratings.containsKey(p);
    }

    public OOP.Provided.CasaDeBurrito rate(Profesor p, int r) throws RateRangeException {
        ratings.put(p, new Rating(r));
        return this;
    }

    public int numberOfRates()
    {
        return ratings.size();
    }

    public double averageRating()
    {
        int sum = 0;
        for(Rating r : ratings.values())
        {
            sum += r.getR();
        }
        return ((double)sum)/numberOfRates();
    }

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof CasaDeBurritoimpl)
            return false;
        return getId() == ((CasaDeBurrito)o).getId();
    }

    static class Pair<T1, T2>
    {
        T1 o1;
        T2 o2;

        public Pair(T1 o1, T2 o2)
        {
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

    public String toString()
    {
        Function<Pair<Collection<String>, String>, String> cat_all = (pair)->
        {
            Collection<String> collection = pair.getFirst();
            String spacer = pair.getSecond();
            String result = "";

            for(String s : collection)
                result += s + spacer;
            result = result.substring(0, result.length()-spacer.length());
            return result;
        };

        Set<String> lines = new HashSet<>(Arrays.asList(
            "CasaDeBurrito: " + getName(),
            "Id: " + getId(),
            "Distance: " + distance(),
            "Menu: " + cat_all.apply(new Pair(menu, ", "))
        ));

        return  cat_all.apply(new Pair(lines, ".\n"))+".\n";
    }

    public int getRatingOf(Profesor p)
    {
        return ratings.get(p).getR();
    }

    @Override
    public int compareTo(CasaDeBurrito other)
    {
        return this.getId() - other.getId();
    }
}
