
public class Regex {

    public static final String CONNECTION_PRICE = "[A-Z-]{3,}";
    public static final String CHEAP_CONNECTION = "\\D+([A-Z]{3})\\D+([A-Z]{3})";
    public static final String DIFFERENT_CONNECTIONS = "(maximum|minimum|exactly)\\D+(\\d+)\\D+([A-Z]{3})\\D+([A-Z]{3})";
    public static final String ALL_CONNECTIONS = "([A-Z]{3})\\s+\\D+([A-Z]{3})\\D+(\\d+)";
    public static final String FIND_ALL = "Find all";
    public static final String PRICE = "price";
    public static final String CHEAP = "cheap";
    public static final String DIFFERENT = "different";
    public static final String CONNECTIONS = "connections";
    public static final String BY_ROUTE = "[,:\\s]+";
    public static final String BY_FLIGHT = "-";
    public static final String FLIGHT_SEPARATOR = "-";

}
