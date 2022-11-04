package sol;

import src.IGraph;

import java.io.IOException;
import java.util.*;

public class TravelGraph implements IGraph<City, Transport> {
    private HashSet<City> vertices;
    private HashSet<Transport> transportEdges;

    // constructor for the TravelGraph class, takes in no inputs, creates two new hashSets,
    // one of vertices/cities and one of edges/transports
    public TravelGraph() {
        this.vertices = new HashSet<City>();
        this.transportEdges = new HashSet<Transport>();
    }


    @Override
    public void addVertex(City vertex) {
        this.vertices.add(vertex);
    }

    @Override
    public void addEdge(City origin, Transport edge) {
        for (City c : this.vertices) {
            if (c.equals(origin)) {
                c.addOut(edge);
            }
        }
        this.transportEdges.add(edge);
    }

    @Override
    public Set<City> getVertices() {
        return this.vertices;
    }

    @Override
    public City getEdgeSource(Transport edge) {
        return edge.getSource();
    }

    @Override
    public City getEdgeTarget(Transport edge) {
        return edge.getTarget();
    }

    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        return fromVertex.getOutgoing();
    }

    // TODO: feel free to add your own methods here!

    /**
     *
     * getter for testing purposes only
     *
     * @return
     */

    public Set<Transport> getTransportEdges() {
        return this.transportEdges;
    }

    /**
     * finds a city on the graph given an inputted string
     * @param name the name of the city you are looking for
     * @return the city, if it can be found, and throw an error otherwise
     */
    public City findCity(String name) {
        // for every city in the graph
        for (City c : this.vertices) {
            // if the name matches the inputted string, return the city
            if (c.nameMatch(name)) {
                return c;
            }
        }
        // if the input string doesn't match any names, throw a error
        throw new NoSuchElementException("City not found");
    }

}