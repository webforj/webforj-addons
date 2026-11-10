package com.webforj.addons.components.iconpicker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.webforj.addons.components.iconpicker.CustomPool.PoolIcon;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CustomPool")
class CustomPoolTest {

  @Test
  @DisplayName("serializes names without tags as icons with empty tags")
  void serializesNames() {
    final var pool = CustomPool.of("brand", "Brand", List.of("logo", "wordmark"));

    final JsonElement json = new Gson().toJsonTree(pool);

    assertEquals(
        JsonParser.parseString(
            "{\"name\":\"brand\",\"label\":\"Brand\",\"icons\":["
                + "{\"name\":\"logo\",\"tags\":[]},"
                + "{\"name\":\"wordmark\",\"tags\":[]}]}"),
        json);
  }

  @Test
  @DisplayName("serializes names with tags in the map's order")
  void serializesNamesWithTags() {
    final Map<String, List<String>> namesWithTags = new LinkedHashMap<>();
    namesWithTags.put("wordmark", List.of("text"));
    namesWithTags.put("logo", List.of("brand", "mark"));
    final var pool = CustomPool.withTags("brand", "Brand", namesWithTags);

    final JsonElement json = new Gson().toJsonTree(pool);

    assertEquals(
        JsonParser.parseString(
            "{\"name\":\"brand\",\"label\":\"Brand\",\"icons\":["
                + "{\"name\":\"wordmark\",\"tags\":[\"text\"]},"
                + "{\"name\":\"logo\",\"tags\":[\"brand\",\"mark\"]}]}"),
        json);
  }

  @Test
  @DisplayName("copies the given names so later changes don't reach the pool")
  void copiesNames() {
    final var names = new ArrayList<>(List.of("logo"));
    final var pool = CustomPool.of("brand", "Brand", names);

    names.add("wordmark");

    assertEquals(List.of("logo"), pool.getIcons().stream().map(PoolIcon::getName).toList());
  }
}
