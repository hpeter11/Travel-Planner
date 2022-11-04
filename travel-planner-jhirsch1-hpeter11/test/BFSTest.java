package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sol.BFS;
import sol.Transport;
import sol.TravelController;
import sol.TravelGraph;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Your BFS tests should all go in this class!
 * The test we've given you will pass if you've implemented BFS correctly, but we still expect
 * you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class BFSTest {

    private static final double DELTA = 0.001;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;
    private SimpleVertex f;
    private SimpleGraph graph;
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

    public void makeSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");
        this.f = new SimpleVertex("f");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);
        this.graph.addVertex(this.f);

        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.b));
        this.graph.addEdge(this.b, new SimpleEdge(1, this.b, this.c));
        this.graph.addEdge(this.c, new SimpleEdge(1, this.c, this.e));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.e));
        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.f));
        this.graph.addEdge(this.f, new SimpleEdge(100, this.f, this.e));
    }

    @Test
    public void testBasicBFS() {
        this.makeSimpleGraph();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.a, this.e);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 200.0, DELTA);
        assertEquals(path.size(), 2);
    }

    // TODO: write more tests + make sure you test all the cases in your testing plan!

    //within travel controller

    /**
     *
     * Tests various testing plan edge cases for most direct route
     * (cases documented below)
     *
     */

    @Test
    public void testMostDirectRoute() {
        //city to itself
        LinkedList<Transport> lEmpty = new LinkedList<>();
        Assert.assertEquals(this.g2.mostDirectRoute("Durham", "Durham"), lEmpty);
        //Check for city that can't get anywhere
        Assert.assertEquals(this.g2.mostDirectRoute("Durham", "Boston"), lEmpty);
        //Check that city goes in most direct path (simple)
        //also checks for loops
        Assert.assertTrue(this.g1.mostDirectRoute("Boston", "Providence").size() == 1);
        //Second loop test
        Assert.assertTrue(this.g3.mostDirectRoute("Alexandria", "Philadelphia").size() == 2);
        //More complicated direct route
        //Multiple paths are best
        Assert.assertTrue(this.g2.mostDirectRoute("Boston", "Chicago").size() == 2);

    }

}
