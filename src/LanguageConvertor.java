import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LanguageConvertor {

	private static final String LANG_TO_CONVERT = "polish";
	
	public static JsonObject convertFileToJSON (String fileName){

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
	
	public static void writeFile(JsonObject obj, String path) throws Exception {
		FileWriter file = new FileWriter(path); 
		System.out.println(obj.toString());
		file.write(obj.toString());
		file.close();
	}
	
	public static void main(String[] args) throws Exception {
		JsonObject convertFileToJSON = convertFileToJSON("src/data.json");
		jsonConvert("", convertFileToJSON, convertFileToJSON);
		writeFile(convertFileToJSON, "src/result.json");
		System.out.println("Done!!");
	}
	
	private static void jsonConvert(String jsonKey, JsonElement jsonElement, JsonElement parent) {
			if(jsonElement.isJsonObject()) {
				Set<Entry<String,JsonElement>> entrySet = jsonElement.getAsJsonObject().entrySet();
				for(Entry<String,JsonElement> entry : entrySet) {
					String key = entry.getKey();
					JsonElement value = entry.getValue();
					jsonConvert(key, value, jsonElement);
				}
			}else if(jsonElement.isJsonArray()){
				Iterator<JsonElement> iterator = jsonElement.getAsJsonArray().iterator();
				while(iterator.hasNext()) {
					jsonConvert(jsonKey, iterator.next(), jsonElement);
				}
			}else {
				System.out.println(jsonKey+" : "+jsonElement.getAsString());
				Language language = ExcelToMemory.getLanguageValue(jsonElement.getAsString());
				if(language == null) {					
					parent.getAsJsonObject().addProperty(jsonKey, jsonElement.getAsString());
				}else {
					
					if(LANG_TO_CONVERT.equals("usEnglish")) {
						parent.getAsJsonObject().addProperty(jsonKey, language.getUsEnglish());						
					}else if(LANG_TO_CONVERT.equals("canadianFrench")) {
						parent.getAsJsonObject().addProperty(jsonKey, language.getCanadianFrench());						
					}else if(LANG_TO_CONVERT.equals("french")) {
						parent.getAsJsonObject().addProperty(jsonKey, language.getFrench());						
					}else if(LANG_TO_CONVERT.equals("indonasian")) {
						parent.getAsJsonObject().addProperty(jsonKey, language.getIndonasian());						
					}else if(LANG_TO_CONVERT.equals("simplifiedChinese")) {
						parent.getAsJsonObject().addProperty(jsonKey, language.getSimplifiedChinese());						
					}else if(LANG_TO_CONVERT.equals("traditionalChinese")) {
						parent.getAsJsonObject().addProperty(jsonKey, language.getTraditionalChinese());						
					}else if(LANG_TO_CONVERT.equals("maxicanSpenish")) {
						parent.getAsJsonObject().addProperty(jsonKey, language.getMaxicanSpenish());						
					}else if(LANG_TO_CONVERT.equals("vietnamese")) {
						parent.getAsJsonObject().addProperty(jsonKey, language.getVietnamese());						
					}else if(LANG_TO_CONVERT.equals("polish")) {
						parent.getAsJsonObject().addProperty(jsonKey, language.getPolish());						
					} 

				}
			}
	}
}
