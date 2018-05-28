package com.utility.nls;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LanguageTranslator {

	private static final String LANG_TO_CONVERT = "polish";

	/**
	 * Read JSON object from file
	 * @param fileName
	 * @return JSON object of input data
	 */
	public static JsonObject readJSONObjectFromFile(String fileName) {

		JsonObject jsonObject = new JsonObject();

		try {
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(new FileReader(fileName));
			jsonObject = jsonElement.getAsJsonObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	/**
	 * Write final translated object into file
	 * @param obj - translated object
	 * @param path - location to store output file
	 * @throws Exception
	 */
	public static void writeTranslatedObjectToFile(JsonObject obj, String path) throws Exception {
		FileWriter file = new FileWriter(path);
		System.out.println(obj.toString());
		file.write(obj.toString());
		file.close();
	}

	public static void main(String[] args) throws Exception {
		JsonObject jsonObject = readJSONObjectFromFile("src/data.json");
		doTranslation("", jsonObject, jsonObject);
		writeTranslatedObjectToFile(jsonObject, "src/result.json");
		System.out.println("Done!!");
	}

	/**
	 * Perform translation on i/p object in specified language.
	 * Responsible to load master sheet, do search analysis to
	 * fetch specific language strings to update current 
	 * JSON object hierarchy
	 * 
	 * @param jsonKey - Key to parse
	 * @param jsonElement - element to parse
	 * @param parent - parent element in JSON hierarchy
	 */
	private static void doTranslation(String jsonKey, JsonElement jsonElement, JsonElement parent) {
		if (jsonElement.isJsonObject()) {
			Set<Entry<String, JsonElement>> entrySet = jsonElement.getAsJsonObject().entrySet();
			for (Entry<String, JsonElement> entry : entrySet) {
				String key = entry.getKey();
				JsonElement value = entry.getValue();
				doTranslation(key, value, jsonElement);
			}
		} else if (jsonElement.isJsonArray()) {
			Iterator<JsonElement> iterator = jsonElement.getAsJsonArray().iterator();
			while (iterator.hasNext()) {
				doTranslation(jsonKey, iterator.next(), jsonElement);
			}
		} else {
			System.out.println(jsonKey + " : " + jsonElement.getAsString());
			LanguagePresenter language = ExcelToMemory.getLanguageValue(jsonElement.getAsString());
			if (language == null) {
				parent.getAsJsonObject().addProperty(jsonKey, jsonElement.getAsString());
			} else {

				if (LANG_TO_CONVERT.equals("usEnglish")) {
					parent.getAsJsonObject().addProperty(jsonKey, language.getUsEnglish());
				} else if (LANG_TO_CONVERT.equals("canadianFrench")) {
					parent.getAsJsonObject().addProperty(jsonKey, language.getCanadianFrench());
				} else if (LANG_TO_CONVERT.equals("french")) {
					parent.getAsJsonObject().addProperty(jsonKey, language.getFrench());
				} else if (LANG_TO_CONVERT.equals("indonasian")) {
					parent.getAsJsonObject().addProperty(jsonKey, language.getIndonasian());
				} else if (LANG_TO_CONVERT.equals("simplifiedChinese")) {
					parent.getAsJsonObject().addProperty(jsonKey, language.getSimplifiedChinese());
				} else if (LANG_TO_CONVERT.equals("traditionalChinese")) {
					parent.getAsJsonObject().addProperty(jsonKey, language.getTraditionalChinese());
				} else if (LANG_TO_CONVERT.equals("maxicanSpenish")) {
					parent.getAsJsonObject().addProperty(jsonKey, language.getMaxicanSpenish());
				} else if (LANG_TO_CONVERT.equals("vietnamese")) {
					parent.getAsJsonObject().addProperty(jsonKey, language.getVietnamese());
				} else if (LANG_TO_CONVERT.equals("polish")) {
					parent.getAsJsonObject().addProperty(jsonKey, language.getPolish());
				}
			}
		}
	}
}
