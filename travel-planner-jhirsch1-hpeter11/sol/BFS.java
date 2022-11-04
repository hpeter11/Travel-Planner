package sol;

import src.IBFS;
import src.IEdge;
import src.IGraph;
import src.IVertex;

import java.util.*;

public class BFS<V extends IVertex<E>, E extends IEdge<V>> implements IBFS<V, E> {

    // TODO: implement the getPath method!

    /**
     *
     * Takes a graph and a start and end city. Then, slowly iterates through
     * neighboring out cities and edges, removing first every time. Duplicate
     * neighbor cities are not added to the list if they have been visited.
     * If the city is not found, returns null. Once the city is found, list
     * is passed to pathFinder.
     *
     * @param graph the graph including the vertices
     * @param start the start vertex
     * @param end   the end vertex
     * @return List of Edges that will be used to backtrack through to get
     * final path
     */

    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {
        // create a new linked list that will contain the ideal path
        LinkedList<E> path = new LinkedList<>();
        // create a new linked list of cities that have already been checked
        LinkedList<V> visited = new LinkedList<>();
        // create a new linked list of cities to check
        LinkedList<V> toCheck = new LinkedList<>();
        // create a new hashmap mapping cities to transports,
        // to be able to backtrack and find the best route from source to destination
        HashMap<V, E> route = new HashMap<>();

        toCheck.addLast(start);
        // while toCheck still has items in it, keep looping through the computations
        while (!toCheck.isEmpty()) {
            // remove the first item in the toCheck list
            V checkingVertex = toCheck.removeFirst();
            // if the visited list doesn't have anything in it, put checkingVertex in it,
            // and set the route as null because that is the first item
            if (visited.size() == 0) {
                route.put(checkingVertex, null);
            }
            // otherwise
            else {
                // for every location in visited
                for (V location : visited) {
                    // for each transport coming out of the location in visited
                    for (E route1 : location.getOutgoing()) {
                        // if that route has a destination that is the checkingVertex
                        if (route1.getTarget().equals(checkingVertex)) {
                            // add checkingVertex to the route hashmap
                            // with value being the route leading to it
                            route.put(checkingVertex, route1);
                        }
                    }
                }
            }
            // add checking vertex to the visited list
            visited.addFirst(checkingVertex);
            // if the checkingVertex is the end/destination, trace and return the path that led to it
            if (end.equals(checkingVertex)) {
                return this.pathFinder(route, end, start, path); //
            }

            // for each edge coming out of the checkingVertex
            for (E edge : checkingVertex.getOutgoing()) {
                // if the target of that edge isn't already in visited or toCheck
                if ((!visited.contains(edge.getTarget())) && (!toCheck.contains(edge.getTarget()))) {
                    // add the target to toCheck
                    toCheck.addLast(edge.getTarget());
                }
            }
        }
        //return this.pathFinder(route, end, start, path);
        // if there is no path, return null
        return path;
    }

    // TODO: feel free to add your own methods here!
    // hint: maybe you need to get a City by its name

    /**
     * "backtracks" after the inputted end vertex is found using the BFS algorithm to
     * find the correct path
     * @param h - the hashmap linking cities to transports
     * @param dest - the end vertex that was found using BFS algorithm
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
