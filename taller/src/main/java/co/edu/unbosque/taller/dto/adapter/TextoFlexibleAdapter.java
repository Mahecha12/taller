package co.edu.unbosque.taller.dto.adapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Convierte a texto un campo que la API puede enviar con distintos tipos.
 * <p>
 * Caso principal: la armadura llega como objeto {"melee":0,"pierce":1} y se
 * envía al front como "0/1" (cuerpo a cuerpo/perforación).
 * </p>
 * <ul>
 * <li>Texto o número: se devuelve como texto.</li>
 * <li>Objeto: sus valores unidos por "/".</li>
 * <li>Arreglo: sus elementos unidos por ", ".</li>
 * </ul>
 */
public class TextoFlexibleAdapter implements JsonDeserializer<String> {

	@Override
	public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
		if (json == null || json.isJsonNull()) {
			return null;
		}
		if (json.isJsonPrimitive()) {
			return json.getAsString();
		}
		List<String> partes = new ArrayList<>();
		if (json.isJsonObject()) {
			JsonObject obj = json.getAsJsonObject();
			for (Map.Entry<String, JsonElement> e : obj.entrySet()) {
				partes.add(deserialize(e.getValue(), typeOfT, context));
			}
			return String.join("/", partes);
		}
		for (JsonElement item : json.getAsJsonArray()) {
			partes.add(deserialize(item, typeOfT, context));
		}
		return String.join(", ", partes);
	}

}
