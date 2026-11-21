package co.edu.unbosque.taller.dto.adapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

/**
 * Convierte siempre a lista de textos un campo que la API puede enviar con
 * distintos tipos (pasa con attack_bonus y armor_bonus de las unidades):
 * <ul>
 * <li>Arreglo ["+2 eagles", "+2 buildings"]: se deja igual.</li>
 * <li>Texto "+3 spearmen" o número 8: lista de un elemento.</li>
 * <li>Objeto {"melee":3,"pierce":"camel"}: ["melee: 3", "pierce: camel"].</li>
 * </ul>
 * Así el front siempre recibe una lista (o null si la unidad no tiene bonus).
 */
public class ListaTextoFlexibleAdapter implements JsonDeserializer<List<String>> {

	@Override
	public List<String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
		if (json == null || json.isJsonNull()) {
			return null;
		}
		List<String> lista = new ArrayList<>();
		if (json.isJsonArray()) {
			for (JsonElement item : json.getAsJsonArray()) {
				if (!item.isJsonNull()) {
					lista.add(item.isJsonPrimitive() ? item.getAsString() : item.toString());
				}
			}
		} else if (json.isJsonObject()) {
			for (Map.Entry<String, JsonElement> e : json.getAsJsonObject().entrySet()) {
				JsonElement v = e.getValue();
				lista.add(e.getKey() + ": " + (v.isJsonPrimitive() ? v.getAsString() : v.toString()));
			}
		} else {
			lista.add(json.getAsString());
		}
		return lista;
	}

}
