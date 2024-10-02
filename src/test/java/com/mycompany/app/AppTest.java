package com.mycompany.app;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void testParseJsonPathWithValidQuery() {
        String json = "{\"weather-data\": {\"date\": \"2023-10-01\", \"temperature\": {\"high\": 25, \"low\": 15}, \"precipitation\": 0.0, \"wind\": {\"speed\": 10, \"direction\": \"N\"}}}";
        String query = "$.weather-data.date";

        Object result = App.parseJsonPath(json, query);

        assertEquals("2023-10-01", result);
    }

    @Test
    void testParseJsonPathWithListResult() {
        String json = "{\"weather-data\": [{\"date\": \"2023-10-01\", \"temperature\": {\"high\": 25, \"low\": 15}}, {\"date\": \"2023-10-02\", \"temperature\": {\"high\": 26, \"low\": 16}}]}";
        String query = "$.weather-data[*].date";

        Object result = App.parseJsonPath(json, query);

        assertTrue(result instanceof List);
        assertEquals(2, ((List<?>) result).size());
        assertEquals("2023-10-01", ((List<?>) result).get(0));
        assertEquals("2023-10-02", ((List<?>) result).get(1));
    }

    @Test
    void testParseJsonPathWithMapResult() {
        String json = "{\"weather-data\": {\"date\": \"2023-10-01\", \"temperature\": {\"high\": 25, \"low\": 15}, \"precipitation\": 0.0, \"wind\": {\"speed\": 10, \"direction\": \"N\"}}}";
        String query = "$.weather-data.temperature";

        Object result = App.parseJsonPath(json, query);

        assertTrue(result instanceof Map);
        assertEquals(25, ((Map<?, ?>) result).get("high"));
        assertEquals(15, ((Map<?, ?>) result).get("low"));
    }
}