package com.webforj.addons.components.propertiespanel.schema.variants;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

/**
 * A custom Gson adapter for deserializing enum variants. This adapter expects the enum to have a
 * static method named {@code fromValue(String value)} that takes a string and returns the
 * corresponding enum constant.
 *
 * @param <T> the type of the enum
 */
public class VariantEnumAdapter<T extends Enum<T>> implements JsonDeserializer<T> {

  @Override
  public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    if (!(typeOfT instanceof Class<?> enumClass)) {
      throw new JsonParseException("Expected a Class<T>, but got " + typeOfT);
    }

    final var value = json.getAsString();
    try {
      final var fromStringMethod = enumClass.getMethod("fromValue", String.class);

      final var result = fromStringMethod.invoke(null, value);
      if (!enumClass.isInstance(result)) {
        throw new JsonParseException("Unexpected result type from 'fromString': " + result);
      }
      return (T) result;
    } catch (NoSuchMethodException e) {
      throw new JsonParseException(
          "Enum " + enumClass.getName() + " does not have a 'fromString' method.");
    } catch (Exception e) {
      throw new JsonParseException("Error deserializing enum " + enumClass.getName(), e);
    }
  }
}
