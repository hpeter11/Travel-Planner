package sol;

public class CityRoute<V> {

    private Double routeDist;
    private V city;

    public CityRoute(V city) {
        this.city = city;
        this.routeDist = 0.0;
    }

    public Double getRouteDist() {
        return this.routeDist;
    }

    public void setRouteDist(Double i) {
        this.routeDist = i;
    }

    public V getCity() {
        return this.city;
    }
}
