package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    Sandwich sandwich = new Sandwich();

    private TextView mOriginalNameTextView;
    private TextView mAlternativeNamesTextView;
    private TextView mIngredientsTextView;
    private TextView mPlaceOfOriginTextView;
    private TextView mSandwichDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mOriginalNameTextView = findViewById(R.id.origin_tv);
        mAlternativeNamesTextView = findViewById(R.id.also_known_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mPlaceOfOriginTextView = findViewById(R.id.place_of_origin_tv);
        mSandwichDescriptionTextView = findViewById(R.id.description_tv);

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
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        mOriginalNameTextView.setText(sandwich.getMainName());
        mSandwichDescriptionTextView.setText(sandwich.getDescription());

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            mPlaceOfOriginTextView.setText(R.string.unknown_origin);
        } else {
            mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        }

        List alternativeNames = sandwich.getAlsoKnownAs();
        if (alternativeNames.isEmpty()) {
            mAlternativeNamesTextView.setText(R.string.no_other_names);
        } else {
            for (int i = 0; i < alternativeNames.size(); i++) {
                String currentName = String.valueOf(alternativeNames.get(i));
                mAlternativeNamesTextView.append(" \"" + currentName + "\" ");
            }
        }

        List ingredients = sandwich.getIngredients();
        for (int i = 0; i < ingredients.size(); i++) {
            String currentIngredient = String.valueOf(ingredients.get(i));
            mIngredientsTextView.append(" \"" + currentIngredient + "\" ");
        }
    }
}

