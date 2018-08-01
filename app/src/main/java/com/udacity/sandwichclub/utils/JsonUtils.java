package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;

        final String SANDWICH_NAMES = "name";
        final String MAIN_NAME = "mainName";
        final String ALTERNATIVE_NAMES = "alsoKnownAs";
        final String PLACE_OF_ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE = "image";
        final String INGREDIENTS = "ingredients";

        try {
            //        Create a base JSON object for the current sandwich
            JSONObject baseJson = new JSONObject(json);

            //            Extract the String values of the sandwich place of origin, description and image from the baseJSON
            String placeOfOrigin = baseJson.getString(PLACE_OF_ORIGIN);
            String description = baseJson.getString(DESCRIPTION);
            String image = baseJson.getString(IMAGE);

            //        Extract the ingredients of the current sandwich from the JSONArray associated with the key "ingredients"
            JSONArray ingredientsArray = baseJson.getJSONArray(INGREDIENTS);
            List ingredients = new ArrayList<String>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.getString(i));
            }

            //        Extract the JSON object associated with the key "name"
            JSONObject nameJson = baseJson.getJSONObject(SANDWICH_NAMES);

//            Extract the String values of sandwich main name, from the nameJSON
            String mainName = nameJson.getString(MAIN_NAME);

//            Extract the alternative names of the current sandwich from the JSONArray associated with the key "alsoKnowAs"
            JSONArray alternativeNamesArray = nameJson.getJSONArray(ALTERNATIVE_NAMES);
            List alsoKnownAs = new ArrayList<String>();
            for (int i = 0; i < alternativeNamesArray.length(); i++) {
                alsoKnownAs.add(alternativeNamesArray.getString(i));
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
