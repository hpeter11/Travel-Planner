package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sol.City;
import sol.Transport;
import sol.TravelController;
import sol.TravelGraph;
import src.TransportType;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Your Graph method tests should all go in this class!
 * The test we've given you will pass, but we still expect you to write more tests using the
 * City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class GraphTest {
    private SimpleGraph graph;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;

    private SimpleEdge edgeAB;
    private SimpleEdge edgeBC;
    private SimpleEdge edgeCA;
    private SimpleEdge edgeAC;
    private TravelController g1;
    private TravelController g2;
    private TravelController g3;

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

        this.a = new SimpleVertex("A");
        this.b = new SimpleVertex("B");
        this.c = new SimpleVertex("C");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);

        // create and insert edges
        this.edgeAB = new SimpleEdge(1, this.a, this.b);
        this.edgeBC = new SimpleEdge(1, this.b, this.c);
        this.edgeCA = new SimpleEdge(1, this.c, this.a);
        this.edgeAC = new SimpleEdge(1, this.a, this.c);

        this.graph.addEdge(this.a, this.edgeAB);
        this.graph.addEdge(this.b, this.edgeBC);
        this.graph.addEdge(this.c, this.edgeCA);
        this.graph.addEdge(this.a, this.edgeAC);
    }

    @Before
    public void graphSetup(){
        TravelController tc1 = new TravelController();
        TravelController tc2 = new TravelController();
        TravelController tc3 = new TravelController();
        tc1.load("/Users/scottpetersen/Desktop/CS200/travel-planner-jhirsch1-hpeter11/data/cities1.csv",
                "/Users/scottpetersen/Desktop/CS200/travel-planner-jhirsch1-hpeter11/data/transport1.csv");
        this.g1 = tc1;
        tc2.load("/Users/scottpetersen/Desktop/CS200/travel-planner-jhirsch1-hpeter11/data/cities2.csv",
                "/Users/scottpetersen/Desktop/CS200/travel-planner-jhirsch1-hpeter11/data/transport2.csv");
        this.g2 = tc2;
        tc3.load("/Users/scottpetersen/Desktop/CS200/travel-planner-jhirsch1-hpeter11/data/cities3.csv",
                "/Users/scottpetersen/Desktop/CS200/travel-planner-jhirsch1-hpeter11/data/transport3.csv");
        this.g3 = tc3;
    }

    @Test
    public void testGetVertices() {
        this.createSimpleGraph();

        // test getVertices to check this method AND insertVertex
        assertEquals(this.graph.getVertices().size(), 3);
        assertTrue(this.graph.getVertices().contains(this.a));
        assertTrue(this.graph.getVertices().contains(this.b));
        assertTrue(this.graph.getVertices().contains(this.c));
    }

    // TODO: write more tests + make sure you test all the cases in your testing plan!

    /**
     *
     * Test that assures that add vertex increases the size of the vertices list
     *
     */
    @Test
    public void testAddVertex() {
        City c = new City("c");
        TravelGraph g = g1.getGraph();
        Assert.assertEquals(g.getVertices().size(), 3);
        g.addVertex(c);
        Assert.assertEquals(g.getVertices().size(), 4);
    }

    /**
     *
     * Checks that add edge modifies size of edge list. NOTE: this cannot be
     * used to test for the size of the vertices list because it is impossible
     * to manually create a matching city input with all of the same links
     *
     */

    @Test
    public void testAddEdge() {
        City c = new City("c");
        City d = new City("d");
        Transport t = new Transport(c, d, TransportType.BUS, 1.0, 1.0);
        TravelGraph g = g1.getGraph();
        Assert.assertEquals(g.getTransportEdges().size(), 7);
        g.addEdge(c, t);
        Assert.assertEquals(g.getTransportEdges().size(), 8);

    }

    /**
     *
     * Tests that find city works in the functioning case (below tests
     * citynotfound)
     *
     */

    @Test
    public void testFindCity() {
        Assert.assertTrue(g1.getGraph().findCity("Boston").nameMatch("Boston"));
    }

    /**
     *
     * Tests that a city with any kind of different name will throw a
     * nosuchelement exception.
     *
     */

    @Test(expected = NoSuchElementException.class)
    public void testFindCityNotFound() {
        g1.getGraph().findCity("boston");
    }

}
