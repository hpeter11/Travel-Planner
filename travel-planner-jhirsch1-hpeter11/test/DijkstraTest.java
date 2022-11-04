package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sol.Dijkstra;
import sol.Transport;
import sol.TravelController;
import src.IDijkstra;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Your Dijkstra's tests should all go in this class!
 * The test we've given you will pass if you've implemented Dijkstra's correctly, but we still
 * expect you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class DijkstraTest {

    private static final double DELTA = 0.001;

    private SimpleGraph graph;
    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;
    private TravelController g1;
    private TravelController g2;
    private TravelController g3;
    private TravelController g4;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
     * TODO: create more setup methods!
     */
    private void createSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);

        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.b));
        this.graph.addEdge(this.a, new SimpleEdge(3, this.a, this.c));
        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.e));
        this.graph.addEdge(this.c, new SimpleEdge(6, this.c, this.b));
        this.graph.addEdge(this.c, new SimpleEdge(2, this.c, this.d));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.b));
        this.graph.addEdge(this.d, new SimpleEdge(5, this.e, this.d));
    }

    @Before
    public void graphSetup(){
        TravelController tc1 = new TravelController();
        TravelController tc2 = new TravelController();
        TravelController tc3 = new TravelController();
        TravelController tc4 = new TravelController();
        tc1.load("/Users/scottpetersen/Desktop/CS200/travel-planner-jhirsch1-hpeter11/data/cities1.csv",
                "/Users/scottpetersen/Desktop/CS200/travel-planner-jhirsch1-hpeter11/data/transport1.csv");
        this.g1 = tc1;
        tc2.load("/Users/scottpetersen/Desktop/CS200/travel-planner-jhirsch1-hpeter11/data/cities2.csv",
                "/Users/scottpetersen/Desktop/CS200/travel-planner-jhirsch1-hpeter11/data/transport2.csv");
        this.g2 = tc2;
        tc3.load("/Users/scottpetersen/Desktop/CS200/travel-planner-jhirsch1-hpeter11/data/cities3.csv",
                "/Users/scottpetersen/Desktop/CS200/travel-planner-jhirsch1-hpeter11/data/transport3.csv");
        this.g3 = tc3;
        tc4.load("/Users/scottpetersen/Desktop/CS200/travel-planner-jhirsch1-hpeter11/data/cities4.csv",
                "/Users/scottpetersen/Desktop/CS200/travel-planner-jhirsch1-hpeter11/data/transport4.csv");
        this.g4 = tc4;
    }

    @Test
    public void testSimple() {
        this.createSimpleGraph();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        // a -> c -> d -> b
        List<SimpleEdge> path =
            dijkstra.getShortestPath(this.graph, this.a, this.b, edgeWeightCalculation);
        assertEquals(6, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(3, path.size());

        // c -> d -> b
        path = dijkstra.getShortestPath(this.graph, this.c, this.b, edgeWeightCalculation);
        assertEquals(3, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(2, path.size());
    }

    // TODO: write more tests + make sure you test all the cases in your testing plan!

    //within travel controller

    /**
     *
     * Tests various testing plan edge cases for fastest route (cases
     * documented below)
     *
     */

    @Test
    public void testFastestRoute() {
        //city to itself
        LinkedList<Transport> lEmpty = new LinkedList<>();
        Assert.assertEquals(this.g2.mostDirectRoute("Durham", "Durham"), lEmpty);
        //Check for city that can't get anywhere
        Assert.assertEquals(this.g2.fastestRoute("Durham", "Boston"), lEmpty);
        //simple fast test
        Assert.assertTrue(this.g1.fastestRoute("Boston", "Providence").get(0).getMinutes()
                == 80.0);
        Assert.assertTrue(this.g1.fastestRoute("Providence", "Boston").get(0).getMinutes()
                == 80.0);
        //Edge case where most direct is not fastest in minutes
        Assert.assertEquals(this.g1.fastestRoute("New York City", "Providence").size(), 2);
        //Multiple best paths
        Assert.assertEquals(this.g4.fastestRoute("Philadelphia", "D.C.").size(), 2);

    }

    /**
     *
     * Tests various testing plan edge cases for cheapest route (cases
     * documented below)
     *
     */

    @Test
    public void testCheapestRoute() {
        //city to itself
        LinkedList<Transport> lEmpty = new LinkedList<>();
        Assert.assertEquals(this.g2.mostDirectRoute("Durham", "Durham"), lEmpty);
        //Check for city that can't get anywhere
        Assert.assertEquals(this.g2.cheapestRoute("Durham", "Boston"), lEmpty);
        //simple cheap test
        Assert.assertTrue(this.g1.cheapestRoute("Boston", "Providence").get(0).getPrice()
                == 7.0);
        Assert.assertTrue(this.g1.cheapestRoute("Providence", "Boston").get(0).getPrice()
                == 7.0);
        //Edge case where most direct is not cheapest
        Assert.assertEquals(this.g1.cheapestRoute("New York City", "Boston").size(), 2);
        //Multiple best paths
        Assert.assertEquals(this.g4.cheapestRoute("Philadelphia", "D.C.").size(), 2);

    }

}
