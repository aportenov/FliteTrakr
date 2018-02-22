package app.models;

import java.util.*;

public class Airport {

    private String name;
    private Map<String, Flight> flightMap;

    public Airport(String name) {
        this.flightMap = new HashMap<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addFlight(Flight flight) {
        this.flightMap.put(flight.getDestinationAirport().getName().toUpperCase(), flight);
    }

    public boolean hasFlight(String flight) {
        return this.flightMap.containsKey(flight);
    }

    public Flight getFlight(String flight) {
        return this.flightMap.get(flight);
    }

    public Map<String, Flight> getFlights(){
        return Collections.unmodifiableMap(this.flightMap);
    }

    public String getFlightIndex(int index) {
        List<String> flights = new ArrayList<>(this.flightMap.keySet());
        if(index < flights.size()) {
            return flights.get(index);
        }

        return null;
    }
}
