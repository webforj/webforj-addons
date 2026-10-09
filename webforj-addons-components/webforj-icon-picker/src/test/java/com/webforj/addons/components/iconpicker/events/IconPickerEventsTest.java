package com.webforj.addons.components.iconpicker.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.webforj.addons.components.iconpicker.IconPicker;
import com.webforj.component.icons.Icon;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("IconPicker events")
class IconPickerEventsTest {

  private IconPicker picker;

  @BeforeEach
  void setUp() {
    picker = new IconPicker();
  }

  @Nested
  @DisplayName("IconChangedEvent")
  class IconChangedEventTests {

    @Test
    @DisplayName("gives the picked icon's pool and name, variant prefix included")
    void mapsPick() {
      final var event =
          new IconChangedEvent(picker, Map.of("pool", "tabler", "name", "filled-star"));

      final Icon icon = event.getValue();

      assertEquals("tabler", icon.getPool());
      assertEquals("filled-star", icon.getName());
      assertSame(picker, event.getComponent());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("payloadsWithoutIcon")
    @DisplayName("gives no icon for a removal")
    void mapsPayloadWithoutIcon(Map<String, Object> payload) {
      final var event = new IconChangedEvent(picker, payload);

      assertNull(event.getValue());
    }

    static Stream<Named<Map<String, Object>>> payloadsWithoutIcon() {
      final Map<String, Object> removal = new HashMap<>();
      removal.put("pool", null);
      removal.put("name", null);
      return Stream.of(Named.of("removal", removal), Named.of("payload without keys", Map.of()));
    }
  }

  @Nested
  @DisplayName("OpenedChangedEvent")
  class OpenedChangedEventTests {

    @ParameterizedTest(name = "{0} -> opened {1}")
    @MethodSource("openedPayloads")
    @DisplayName("maps the dropdown state")
    void mapsOpenedState(Map<String, Object> payload, boolean isOpened) {
      final var event = new OpenedChangedEvent(picker, payload);

      assertEquals(isOpened, event.isOpened());
    }

    static Stream<Arguments> openedPayloads() {
      return Stream.of(
          Arguments.of(Named.of("opened", Map.of("opened", true)), true),
          Arguments.of(Named.of("closed", Map.of("opened", false)), false),
          Arguments.of(Named.of("state sent as a string", Map.of("opened", "true")), true),
          Arguments.of(Named.of("payload without a state", Map.of()), false));
    }
  }
}
