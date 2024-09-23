package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    // Map to store Alpha-3 code as the key and country name as the value
    private Map<String, String> alpha3ToCountryMap;
    // Map to store country name as the key and Alpha-3 code as the value
    private Map<String, String> countryToAlpha3Map;

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {
        alpha3ToCountryMap = new HashMap<>();
        countryToAlpha3Map = new HashMap<>();

        try {
            // Load lines from the file
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            // Skip the header line and process the rest
            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split("\t");
                String country = parts[0].trim();
                String alpha3Code = parts[2].trim();
                // Populate both maps
                alpha3ToCountryMap.put(alpha3Code, country);
                countryToAlpha3Map.put(country.toLowerCase(), alpha3Code);
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException("Failed to load country codes", ex);
        }
    }

    /**
     * Returns the name of the country for the given Alpha-3 country code.
     * @param code the 3-letter Alpha-3 code of the country
     * @return the name of the country corresponding to the code, or null if not found
     */
    public String fromCountryCode(String code) {
        return alpha3ToCountryMap.get(code);
    }

    /**
     * Returns the Alpha-3 code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter Alpha-3 code of the country, or null if not found
     */
    public String fromCountry(String country) {
        return countryToAlpha3Map.get(country.toLowerCase());
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return alpha3ToCountryMap.size();
    }
}
