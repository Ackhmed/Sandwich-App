package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {


    public static Sandwich parseSandwichJson(String json) throws JSONException {


        Sandwich sandwichObject = new Sandwich();

        // Identify all variables going into the sandwich object
        String mainName;
        ArrayList<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        ArrayList<String> ingredients = new ArrayList<>();

        // Making a json object
        JSONObject jsonSandwichObject = new JSONObject(json);
        JSONObject jsonNameObject = jsonSandwichObject.getJSONObject("name");


        mainName = jsonNameObject.getString("mainName");

        JSONArray alsoKnownAsArray;
        alsoKnownAsArray = jsonNameObject.getJSONArray("alsoKnownAs");

        //getting value from the Array and adding  to the list variable
        for (int i = 0; i < alsoKnownAsArray.length();
             i++) {
            alsoKnownAs.add(alsoKnownAsArray.getString(i));
        }


        placeOfOrigin = jsonSandwichObject.getString("placeOfOrigin");
        description = jsonSandwichObject.getString("description");
        image = jsonSandwichObject.getString("image");


        JSONArray ingredientsArray;
        ingredientsArray = jsonSandwichObject.getJSONArray("ingredients");

        for (int i = 0; i < ingredientsArray.length(); i++) {
            ingredients.add(ingredientsArray.getString(i));
        }

        sandwichObject.setAlsoKnownAs(alsoKnownAs);
        sandwichObject.setDescription(description);
        sandwichObject.setImage(image);
        sandwichObject.setIngredients(ingredients);
        sandwichObject.setMainName(mainName);
        sandwichObject.setPlaceOfOrigin(placeOfOrigin);

        return sandwichObject;
    }


}
