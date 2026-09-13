package com.jumbo.store.locator.db;

import tools.jackson.databind.JsonNode;

import java.sql.PreparedStatement;
import java.sql.Types;

public class SeedStoresHelper {

    public static String requireText(JsonNode node, String field) {
        JsonNode value = node.get(field);
        if (value == null || value.isNull()) {
            throw new IllegalStateException("Missing required field '" + field + "' in stores.json entry: " + node);
        }
        return value.asText();
    }

    public static void setNullableString(PreparedStatement ps, int index, JsonNode node, String field) throws java.sql.SQLException {
        JsonNode value = node.get(field);
        if (value == null || value.isNull()) {
            ps.setNull(index, Types.VARCHAR);
        } else {
            ps.setString(index, value.asText());
        }
    }
}
