package app.models;

public class Flight {

    private Airport departureAirport;
    private Airport destinationAirport;
    private int flightPrice;

    public Flight(Airport departureAirport, Airport destinationAirport, int flightPrice) {
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.flightPrice = flightPrice;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public int getFlightPrice() {
        return flightPrice;
    }

}
