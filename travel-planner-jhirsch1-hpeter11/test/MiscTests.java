package test;

import org.junit.Assert;
import org.junit.Test;
import sol.Transport;
import sol.TravelController;
import sol.TravelGraph;
import sol.City;
import src.TransportType;

import java.util.LinkedList;
import java.util.Set;

public class MiscTests {

    //within city class

    /**
     *
     * Tests that add out adds edges to cities
     *
     */

    @Test
    public void testAddOut() {
        City a = new City("city");
        City b = new City("city");
        Transport t1 = new Transport(a, b, TransportType.BUS, 1, 1);
        Transport t2 = new Transport(a, b, TransportType.TRAIN, 1, 1);
        a.addOut(t1);
        Assert.assertTrue(a.getOutgoing().contains(t1));
        Assert.assertFalse(a.getOutgoing().contains(t2));
        Assert.assertFalse(b.getOutgoing().contains(t1));
        a.addOut(t2);
        Assert.assertTrue(a.getOutgoing().contains(t2));

    }

    /**
     *
     * Tests various edge cases to ensure name match is functioning
     *
     */

    @Test
    public void testNameMatch() {
        City c = new City("Raleigh");
        City f = new City("Raleigh");
        Assert.assertTrue(c.nameMatch("Raleigh"));
        Assert.assertFalse(c.nameMatch("raleigh"));
        Assert.assertFalse(c.nameMatch("f"));

    }

    //within travel controller

    /**
     *
     * Tests that load properly creates a travelgraph with matching parameters
     * to the input CSV file.
     *
     */

    @Test
    public void testLoad() {
        TravelController tc = new TravelController();
        tc.load("/Users/scottpetersen/Desktop/CS200/travel-planner-jhirsch1-hpeter11/data/cities4.csv",
                "/Users/scottpetersen/Desktop/CS200/travel-planner-jhirsch1-hpeter11/data/transport4.csv");
        Set<City> cities = tc.getGraph().getVertices();
        Set<Transport> transport = tc.getGraph().getTransportEdges();
        Assert.assertTrue(cities.size() == 4);
        Assert.assertTrue(transport.size() == 4);

    }
}
