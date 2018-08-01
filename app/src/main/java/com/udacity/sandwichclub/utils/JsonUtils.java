package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonUtils {

    private static List<String> alsoKnownAs;

    private static List<String> ingredients;

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();

        try {
            //        Create a base JSON object for the current sandwich
            JSONObject baseJson = new JSONObject(json);

            //        Extract the JSON object associated with the key "name"
            JSONObject name = baseJson.getJSONObject("name");

//            Extract the String values of sandwich main name, place of origin, description and image from their associated key
            String mainName = name.getString("mainName");
            String placeOfOrigin = name.getString("placeOfOrigin");
            String description = name.getString("description");
            String image = name.getString("image");

//            Extract the alternative names of the current sandwich from the JSONArray associated with the key "alsoKnowAs"
            JSONArray alternativeNamesArray = name.getJSONArray("alsoKnownAs");
            for (int i = 0; i < alternativeNamesArray.length(); i++) {
                alsoKnownAs.add(alternativeNamesArray.getString(i));
            }

//        Extract the ingredients of the current sandwich from the JSONArray associated with the key "ingredients"
            JSONArray ingredientsArray = name.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.getString(i));
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
