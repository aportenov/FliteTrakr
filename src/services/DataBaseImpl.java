package services;

import models.Airport;
import models.Flight;

import java.util.*;

public class DataBaseImpl implements DataBase {

    private Map<String, Airport> airports;
    private List<List<String>> result;
    private int routeCount = 0;

    public DataBaseImpl() {
        this.airports = new HashMap<>();
        result = new ArrayList<>();
    }

    @Override
    public void addAirport(Airport airport) {
        this.airports.put(airport.getName(), airport);
    }

    @Override
    public List<List<String>> findAllConnections(String departedAirportName, String destinationAirportName, String price) {
        this.result.clear();
        int limitPrice = Integer.parseInt(price);
        Airport departureAirport = this.airports.get(departedAirportName);
        isAirportsExist(destinationAirportName, departureAirport);

        Map<String, Flight> flightMap = departureAirport.getFlights();
        for (Flight flight : flightMap.values()) {
            List<String> tempResult = new ArrayList<>();
            tempResult.add(departedAirportName);
            findFlights(flight, destinationAirportName, tempResult, limitPrice, 0);
        }

        isFlightFound();
        return this.result;
    }

    @Override
    public int findDifferentConnections(String filter, String numberOfConnections,
                                        String departedAirportName, String destinationAirportName) {
        this.routeCount = 0;
        Airport departureAirport = this.airports.get(departedAirportName);
        isAirportsExist(destinationAirportName, departureAirport);
        Map<String, Flight> flightMap = departureAirport.getFlights();
        for (Flight flight : flightMap.values()) {
            List<String> tempArray = new ArrayList<>();
            tempArray.add(departedAirportName);
            findFlightWithCondition(flight, destinationAirportName, tempArray,
                    filter, numberOfConnections);
        }

        return routeCount;

    }

    @Override
    public List<String> findCheapConnection(String departedAirportName, String destinationAirportName) {
        this.result.clear();
        int minPrice = Integer.MAX_VALUE;
        Airport departureAirport = this.airports.get(departedAirportName);
        isAirportsExist(destinationAirportName, departureAirport);

        Map<String, Flight> flightMap = departureAirport.getFlights();
        for (Flight flight : flightMap.values()) {
            List<String> tempResult = new ArrayList<>();
            tempResult.add(departedAirportName);
            findFlights(flight, destinationAirportName, tempResult, minPrice, 0);
        }

        isFlightFound();
        return this.result.stream().sorted(Comparator.comparingInt(f -> Integer.parseInt(f.get(f.size() - 1)))).findFirst().get();
    }

    @Override
    public int findPrice(String... flights) {
        Airport airport;
        int price = 0;
        if (flights.length < 2) {
            throw new IllegalArgumentException("No such connection found!");
        }

        if (this.airports.containsKey(flights[0])) {
            airport = this.airports.get(flights[0]);
            for (int i = 1; i < flights.length; i++) {
                if (!airport.hasFlight(flights[i])) {
                    throw new IllegalArgumentException("No such connection found!");
                }

                Flight flight = airport.getFlight(flights[i]);
                airport = flight.getDestinationAirport();
                price += flight.getFlightPrice();
            }
        }

        return price;
    }

    @Override
    public Airport getAirport(String departureAirportName) {
        return this.airports.get(departureAirportName);
    }


    private void findFlights(Flight flight, String destinationAirportName,
                             List<String> flights, int price, int currentPrice) {
        Airport nextAirport = flight.getDestinationAirport();
        flights.add(nextAirport.getName());
        currentPrice += flight.getFlightPrice();
        if (hasBeenThere(destinationAirportName, flights, nextAirport)) {
            return;
        }

        if (currentPrice > price) {
            return;
        }

        if (nextAirport.getName().equals(destinationAirportName)) {
            flights.add(String.valueOf(currentPrice));
            this.result.add(new ArrayList<>(flights));
            removeLast(flights);
            return;
        }

        for (int i = 0; i < nextAirport.getFlights().size(); i++) {
            findFlights(nextAirport.getFlight(nextAirport.getFlightIndex(i)),
                    destinationAirportName, flights, price, currentPrice);
            removeLast(flights);
        }
    }


    private void findFlightWithCondition(Flight flight, String destinationAirportName,
                                         List<String> flights, String filter, String numberOfConnections) {
        Airport nextAirport = flight.getDestinationAirport();
        flights.add(nextAirport.getName());
        if (hasBeenThere(destinationAirportName, flights, nextAirport)) {
            return;
        }

        if ((filter.equals("maximum") && flights.size() - 2 > Integer.parseInt(numberOfConnections)) ||
                (filter.equals("exactly") && flights.size() - 2 > Integer.parseInt(numberOfConnections))) {
            return;
        }

        if (nextAirport.getName().equals(destinationAirportName)) {
            if (filter.equals("minimum") && flights.size() - 2 < Integer.parseInt(numberOfConnections) ||
                    (filter.equals("exactly") && flights.size() - 2 != Integer.parseInt(numberOfConnections))) {
                return;
            }

            this.routeCount++;
            return;
        }


        for (int i = 0; i < nextAirport.getFlights().size(); i++) {
            findFlightWithCondition(nextAirport.getFlight(nextAirport.getFlightIndex(i)),
                    destinationAirportName, flights, filter, numberOfConnections);
            removeLast(flights);
        }
    }

    private boolean hasBeenThere(String destinationAirportName, List<String> flights, Airport nextAirport) {
        return (flights.subList(0, flights.size() - 1).indexOf(nextAirport.getName()) != -1 &&
                !nextAirport.getName().equals(destinationAirportName));
    }

    private void removeLast(List<String> flights) {
        flights.remove(flights.size() - 1);
    }


    private void isFlightFound() {
        if (this.result.isEmpty()) {
            throw new IllegalArgumentException("No such connection found!");
        }
    }

    private void isAirportsExist(String destinationAirportName, Airport departureAirport) {
        if (departureAirport == null || this.airports.get(destinationAirportName) == null) {
            throw new IllegalArgumentException("No such connection found!");
        }
    }
}

