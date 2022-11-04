package sol;

import src.IEdge;
import src.ITransport;
import src.IVertex;
import src.TransportType;

/**
 * A Transport class representing the edge of a travel graph
 */
public class Transport implements IEdge<City>, ITransport {
    private City source;
    private City dest;
    private TransportType type;
    private double price;
    private double mins;

    // constructor for the Transport field
    public Transport(City source, City destination, TransportType type, double price,
                     double minutes) {
        this.source = source;
        this.dest = destination;
        this.type = type;
        this.price = price;
        this.mins = minutes;
    }


    @Override
    public City getSource() {
        return this.source;
    }


    @Override
    public City getTarget() {
        return this.dest;
    }


    @Override
    public double getPrice() {
        return this.price;
    }


    @Override
    public double getMinutes() {
        return this.mins;
    }


    @Override
    public String getType() {
        return this.type.getLabel();
    }

    @Override
    public String toString() {
        return this.getSource().toString() + " -> " + this.getTarget().toString() +
                ", Type: " + this.getType() +
                ", Cost: $" + this.getPrice() +
                ", Duration: " + this.getMinutes() + " minutes";
    }
}
