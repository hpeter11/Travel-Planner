package sol;

import src.IDijkstra;
import src.IEdge;
import src.IGraph;
import src.IVertex;

import java.util.*;
import java.util.function.Function;

public class Dijkstra<V extends IVertex<E>, E extends IEdge<V>> implements IDijkstra<V, E> {

    // TODO: implement the getShortestPath method!

    /**
     *
     * Takes a graph, destination and start, and a weight function. The weight
     * function is used to determine the value that the dijkstra will minimize.
     * Then, using a route hashmap and a priority queue, creates routes,
     * keeping the minimum value at the bottom, constructs a path List that
     * is outputted.
     *
     * @param graph       the graph including the vertices
     * @param source      the source vertex
     * @param destination the destination vertex
     * @param edgeWeight
     * @return a list of Transport edges to be passed to pathFinder
     */

    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {
        // when you get to using a PriorityQueue, remember to remove and re-add a vertex to the
        // PriorityQueue when its priority changes!

        // create a new hashmap mapping cities to distance from source city
        HashMap<V, Double> cityDist = new HashMap<>();
        //start with all values as 0, just as a placeholder
        for (V city : graph.getVertices()) {
            cityDist.put(city, 0.0);
        }

        // create a new hashmap mapping cities to transports,
        // to be able to backtrack and find the best route from source to destination
        HashMap<V, E> route = new HashMap<>();
        // create a new linked list that will contain the ideal path
        LinkedList<E> path = new LinkedList<>();

        // comparator that compares distance from source city
        Comparator<V> numHigher = (V1, V2) -> {
            return Integer.compare(cityDist.get(V1).intValue(), cityDist.get(V2).intValue());
        };

        // priority queue using numHigher comparator
        PriorityQueue<V> cityQueue =  new PriorityQueue<>(numHigher);

        // for all  the cities in the graph
        for (V c : graph.getVertices()) {
            // set their value in the cityDist hashmap as infinity and add them to the priority queue
            cityDist.put(c, Double.POSITIVE_INFINITY);
            cityQueue.add(c);
            if (c == source) {
                // set the source value as 0, because the source is 0 away from itself
                cityDist.put(c, 0.0);
                cityQueue.remove(c);
                cityQueue.add(c);
                // since priority queues do not reorganize automatically, we have to remove
                // the source and then add it again
                // we will end up here with a priority queue with the source first and then
                // all the other cities after
            }
        }

        // add the source to the hashmap, because otherwise it will not be added in
        route.put(source, null);

        //while there are still cities to check in the priority queue
        while (!cityQueue.isEmpty()) {
            // remove the vertex/city with the lowest distance from the origin, which will be the first
            // item in the priority queue
            V checkingV = cityQueue.remove();

            // for all of that vertex's edges (for all  the transports going out of that city)
            for (E route1 : checkingV.getOutgoing()) {
                // if the distance from the origin to the neighbor through the checking city is
                // less than the current distance from the origin to the neighbor
                Double newDist = cityDist.get(checkingV) + edgeWeight.apply(route1);
                if (newDist < cityDist.get(route1.getTarget())) {
                    // update the hashmap, and by extension the priority queue
                    cityDist.put(route1.getTarget(), newDist);
                    cityQueue.remove(route1.getTarget());
                    cityQueue.add(route1.getTarget());
                    //add the route to the route hashmap
                    route.put(route1.getTarget(), route1);
                }
            }
        }
        if (route.containsKey(destination)) {
            return this.pathFinder(route, destination, source, path);
        } else {
            return path;
        }

    }

    // TODO: feel free to add your own methods here!

    /**
     * "backtracks" after the inputted end vertex is found using the Dijkstra algorithm to
     * find the correct path
     * @param h - the hashmap linking cities to transports
     * @param dest - the end vertex that was found using Dijkstra algorithm
     * @param first - the start vertex that we are now looking to backtrack to
     * @param path - the linked list of cities that will be filled out with the optimal path
     * @return the linked list path, which contains the correct path from start to end
     */
    public List<E> pathFinder(HashMap<V, E> h, V dest, V first, LinkedList<E> path) {
        // if the destination equals the origin, return the path
        if (dest.equals(first)) {
            return path;
        }
        // add the route to the path list
        path.addFirst(h.get(dest));
        // recurse through the hashmap to get the vertex that the destination came from
        // this will get one step closer to the origin point
        return this.pathFinder(h, h.get(dest).getSource(), first, path);
    }
}
