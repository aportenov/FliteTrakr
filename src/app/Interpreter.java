package app;

import app.io.Reader;
import app.io.Writer;
import app.models.Airport;
import app.services.DataBase;
import app.models.Flight;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter implements Runnable {


    private DataBase dataBase;
    private Reader reader;
    private Writer writer;

    public Interpreter(Reader reader, Writer writer, DataBase dataBase) {
        this.dataBase = dataBase;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void run() {
        StringBuilder lines = this.reader.readLine();
        Arrays.stream(lines.toString().split("\n")).forEach(input -> {
            try {
                Matcher matcher;
                String[] formatedInput = input.split(Regex.BY_ROUTE);
                if (formatedInput[0].equalsIgnoreCase(Regex.CONNECTIONS)) {
                    for (int i = 1; i < formatedInput.length; i++) {
                        String[] flights = formatedInput[i].split(Regex.BY_FLIGHT);
                        if (flights.length > 2) {
                            String departureAirportName = flights[0].trim();
                            String destinationAirportName = flights[1].trim();
                            int price = Integer.parseInt(flights[2].trim());

                            Airport departureAirport = getAirport(departureAirportName);
                            Airport destinationAirport = getAirport(destinationAirportName);
                            departureAirport.addFlight(new Flight(departureAirport, destinationAirport, price));
                        }
                    }
                } else {
                    if (input.contains(Regex.PRICE) &&
                            (matcher = Pattern.compile(Regex.CONNECTION_PRICE).matcher(input)).find()) {
                        findPrice(matcher.group(0));
                    } else if (input.contains(Regex.CHEAP) && (
                            matcher = Pattern.compile(Regex.CHEAP_CONNECTION).matcher(input)).find()) {
                        findCeapestConnection(matcher.group(1), matcher.group(2));
                    } else if (input.contains(Regex.DIFFERENT) &&
                            (matcher = Pattern.compile(Regex.DIFFERENT_CONNECTIONS).matcher(input)).find()) {
                        findDifferentConnections(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
                    } else if (input.contains(Regex.FIND_ALL) &&
                            (matcher = Pattern.compile(Regex.ALL_CONNECTIONS).matcher(input)).find()) {
                        findAllConnections(matcher.group(1), matcher.group(2), matcher.group(3));
                    } else {
                        throw new IllegalArgumentException("Invalid request");
                    }

                }

            } catch (IllegalArgumentException e) {
                this.writer.writeSingleLine(e.getMessage());
            }
        });
    }

    private Airport getAirport(String airportName) {
        Airport airport = this.dataBase.getAirport(airportName);
        if (airport == null) {
            airport = new Airport(airportName);
            this.dataBase.addAirport(airport);
        }

        return airport;
    }

    private void findAllConnections(String departedAirport, String destinationAirport, String price) {
        List<List<String>> result = this.dataBase.findAllConnections(departedAirport, destinationAirport, price);
        StringBuilder sb = new StringBuilder();
        for (List<String> flight : result) {
            sb.append(String.join(Regex.FLIGHT_SEPARATOR, flight))
                    .append(", ");
        }

        sb.deleteCharAt(sb.length() - 2);
        this.writer.writeSingleLine(sb.toString());
    }

    private void findDifferentConnections(String filter, String numberOfConnections,
                                          String departedAirport, String destinationAirport) {
        int result = this.dataBase.findDifferentConnections(filter, numberOfConnections, departedAirport, destinationAirport);
        this.writer.writeSingleLine(String.valueOf(result));
    }

    private void findCeapestConnection(String departedAirport, String destinationAirport) {
        List<String> result = this.dataBase.findCheapConnection(departedAirport, destinationAirport);
        this.writer.writeSingleLine(String.join(Regex.FLIGHT_SEPARATOR, result));
    }

    private void findPrice(String flight) {
        String[] flights = flight.split(Regex.BY_FLIGHT);
        int flightPrice = this.dataBase.findPrice(flights);
        this.writer.writeSingleLine(String.valueOf(flightPrice));
    }
}
