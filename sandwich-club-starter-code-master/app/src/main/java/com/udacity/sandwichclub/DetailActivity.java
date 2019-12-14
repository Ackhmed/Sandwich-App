package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;
import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //
        TextView alsoknownAsTitleTV = (TextView) findViewById(R.id.AlsoKnownAS_TitleTV);
        TextView alsoKnownAsTV = (TextView) findViewById(R.id.also_known_as_tv);

        //if sandwich has another name in array then display each name on a separate line
        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            for (String alsoKnownAs : sandwich.getAlsoKnownAs()) {
                alsoKnownAsTV.append(alsoKnownAs);
                alsoKnownAsTV.append("\n");
            }
            //else make the section invisible
        } else {
            alsoknownAsTitleTV.setVisibility(View.INVISIBLE);
            alsoKnownAsTV.setVisibility(View.INVISIBLE);

        }

        TextView placeofOriginTitleTV = (TextView) findViewById(R.id.Origin_Title);
        TextView placeOfOriginTV = (TextView) findViewById(R.id.place_of_origin_tv);
        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            placeOfOriginTV.setText(sandwich.getPlaceOfOrigin());
        } else {
            placeofOriginTitleTV.setVisibility(View.INVISIBLE);
            placeOfOriginTV.setVisibility(View.INVISIBLE);
        }


        TextView detailDescriptionTV = (TextView) findViewById(R.id.description_tv);
        detailDescriptionTV.setText(sandwich.getDescription());

//if sandwich has ingredients in array then display each name on a separate line
        TextView ingredientsTV = (TextView) findViewById(R.id.ingredients_tv);
        if (!sandwich.getIngredients().isEmpty()) {
            for (String ingredients : sandwich.getIngredients()) {
                ingredientsTV.append(ingredients);
                ingredientsTV.append("\n");
            }
        }
    }
}
