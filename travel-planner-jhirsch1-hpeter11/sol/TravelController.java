package sol;

import src.ITravelController;
import src.TransportType;
import src.TravelCSVParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TravelController implements ITravelController<City, Transport> {
    private TravelGraph graph;

    public TravelController() {
    }

    /**
     *
     * Takes two input CSV files and parses through each, converting every
     * line to a city vertex and then a travel edge, linking them together
     * in a final graph and set of lists within the graph which is set as the
     * graph field in the constructor.
     *
     * @param citiesFile    the filename of the cities csv
     * @param transportFile the filename of the transportations csv
     * @return string confirming file success, but mainly updates graph field
     */

    @Override
    public String load(String citiesFile, String transportFile) {
        this.graph = new TravelGraph();
        TravelCSVParser parser = new TravelCSVParser();

        Function<Map<String, String>, Void> addVertex = map -> {
            this.graph.addVertex(new City(map.get("name")));
            return null; // need explicit return null to account for Void type
        };

        // TODO: create a variable that is of type Function<Map<String, String>, Void>
        //       that will build connections between nodes in a graph. Keep in mind
        //       that the input to this function is a hashmap that relates the
        //       COLUMN NAMES of the csv to the VALUE IN THE COLUMN of the csv.
        //       It might be helpful to write a method in this class that takes the
        //       information from the csv needed to create an edge and uses that to
        //       build the edge on the graph.

        Function<Map<String, String>, Void> addOutgoing = map -> {
            City origin = this.graph.findCity(map.get("origin"));
            City dest = this.graph.findCity(map.get("destination"));
            TransportType transport = TransportType.fromString((map.get("type")));
            Double price = Double.parseDouble(map.get("price"));
            Double duration = Double.parseDouble(map.get("duration"));
            Transport t = new Transport(origin, dest, transport, price, duration);
            this.graph.addEdge(origin, t);
            return null;
        };

        try {
            // pass in string for CSV and function to create City (vertex) using city name
            parser.parseLocations(citiesFile, addVertex);
        } catch (IOException e) {
            return "Error parsing file: " + citiesFile;
        }

        // TODO: call parseTransportation with the second Function variable as an argument and
        //  the right file
        try {
            parser.parseTransportation(transportFile, addOutgoing);
        } catch (IOException e) {
        return "Error parsing file: " + citiesFile;
    }

        return "Successfully loaded cities and transportation files.";
    }

    /**
     *
     * fastest route takes in a source name and destination name, matches them
     * to cities within the graph, and using a function to get the minutes of
     * the transport objects, uses a Dijkstra object to compute the fastest
     * route based on given graph
     *
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return ordered List of transport object in an organized path
     */

    @Override
    public List<Transport> fastestRoute(String source, String destination) {

        Function<Transport, Double> timeToDest = transport -> transport.getMinutes();

        Dijkstra<City, Transport> newDijkstra = new Dijkstra<City, Transport>();
        return newDijkstra.getShortestPath(
                this.graph, this.graph.findCity(source),
                this.graph.findCity(destination), timeToDest);

        //return new ArrayList<>();
    }

    /**
     *
     * cheapest route takes in a source name and destination name, matches them
     * to cities within the graph, and using a function to get the price of
     * the transport objects, uses a Dijkstra object to compute the cheapest
     * route based on given graph
     *
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return ordered List of transport object in an organized path
     */

    @Override
    public List<Transport> cheapestRoute(String source, String destination) {
        // TODO: implement this method!

        Function<Transport, Double> priceToDest = transport -> transport.getPrice();

        Dijkstra<City, Transport> newDijkstra = new Dijkstra<City, Transport>();
        return newDijkstra.getShortestPath(
                this.graph, this.graph.findCity(source),
                this.graph.findCity(destination), priceToDest);

        //return new ArrayList<>();
    }

    /**
     *
     * most direct route takes in a source name and destination name, matches
     * them to cities within the graph, and uses a BFS object to compute the
     * cheapest route based on given graph
     *
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return organized List of Cities in a direct path
     */

    @Override
    public List<Transport> mostDirectRoute(String source, String destination) {
        BFS<City, Transport> newBFS = new BFS<City, Transport>();
        return newBFS.getPath(this.graph, this.graph.findCity(source), this.graph.findCity(destination));
    }

    /**
     *
     * Getter used for testing
     *
     * @return this TravelGraph
     */
    public TravelGraph getGraph() {
        return this.graph;
    }
}
