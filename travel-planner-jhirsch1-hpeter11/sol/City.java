package sol;

import src.IEdge;
import src.IVertex;

import java.util.HashSet;
import java.util.Set;

/**
 * A City class representing the vertex of a travel graph
 */
public class City implements IVertex<Transport> {
    // name of the city
    private String name;
    // HashSet of Transports, represents the ways to get out of the city
    private Set<Transport> outgoingEdges;

    // constructor for the City class - takes in a string, which becomes the name field
    // and initializes a HashSet of transports
    public City(String name) {
        this.name = name;
        this.outgoingEdges = new HashSet<Transport>();
    }


    @Override
    public Set<Transport> getOutgoing() {
        return this.outgoingEdges;
    }


    @Override
    public void addOut(Transport outEdge) {
        this.outgoingEdges.add(outEdge);
    }

    /**
     * Finds if the city name is the same as another name
     * @param name the name that you are checking with
     * @return a boolean; true if the names match, false otherwise
     */
    public boolean nameMatch(String name) {
        return this.name.equals(name);
    }

    @Override
    public String toString() {
        return this.name;

    }
}
