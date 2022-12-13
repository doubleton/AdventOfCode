package aoc2022.day13;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PackerParser {

    public static JSONArray parse(String input) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONArray) parser.parse(input);
    }

}
