package services;


import models.Airport;

import java.util.List;

public interface DataBase {

    void addAirport(Airport airport);

    List<List<String>> findAllConnections(String departedAirport, String destinationAirport, String price);

    int findDifferentConnections(String filter, String numberOfConnections, String departedAirport, String destinationAirport);

    List<String> findCheapConnection(String departedAirport, String destinationAirport);

    int findPrice(String... flights);

    Airport getAirport(String airportName);
}
