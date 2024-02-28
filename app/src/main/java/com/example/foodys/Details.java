package com.example.foodys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Adapters.IngredientAdapter;
import Adapters.InstructionsAdapter;
import Adapters.SimilarRecipesAdapter;
import Adapters.StepAdapter;
import Foods.AnalyzedInstruction;
import Foods.Root;
import Foods.SimilarRoot;
import Listeners.InstructionsListener;
import Listeners.RecipeClickListener;
import Listeners.RecipeDetailsListener;
import Listeners.SimilarRecipesListener;
import Listeners.SimilarRecipesOnClickListener;

public class Details extends AppCompatActivity {
    int id;
    private View decorview;
    TextView MealName,mealsource;
    ImageView mealimage;
    RecyclerView mealingredients,similarrecipes,stepinstructions;
    ProgressDialog dialog;
    Requests requests;
    //adapters for ingredients,similar meals and the steps
    IngredientAdapter ingredientAdapter;
    SimilarRecipesAdapter similarRecipesAdapter;
    InstructionsAdapter instructionAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_recipe_details);
        super.onCreate(savedInstanceState);
        //initialize decorview
        decorview = getWindow().getDecorView();
        decorview.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility==0)
                {
                    decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            |View.SYSTEM_UI_FLAG_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                }
            }
        });
        //getting the id from the intent
        id=Integer.parseInt(getIntent().getStringExtra("id"));
        MealName= (TextView)findViewById(R.id.MealName);
        mealsource= (TextView)findViewById(R.id.MealSource);
        mealimage= (ImageView) findViewById(R.id.MealImage);
        similarrecipes=(RecyclerView) findViewById(R.id.SimilarMeals);
        mealingredients=  (RecyclerView) findViewById(R.id.MealIngredients);
        stepinstructions=(RecyclerView) findViewById(R.id.InstructionsMeals);
        //request manager for the api
        requests = new Requests(this);
        requests.getDetails(RecipeDetailsListener,id);
        requests.getSimilar(similarRecipesListener,id);
        requests.getInstructions(instructionsListener,id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("please wait");
        dialog.show();
    }
    //Listener for the details to find the ingredients
    private final RecipeDetailsListener RecipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didGood(Root response, String msg) {
            dialog.dismiss();
            MealName.setText(response.title);
            mealsource.setText("Source: "+response.sourceName);
            Picasso.get().load(response.image).into(mealimage);
            mealingredients.setHasFixedSize(false);
            mealingredients.setLayoutManager(new LinearLayoutManager(Details.this,LinearLayoutManager.VERTICAL,false));
            ingredientAdapter = new IngredientAdapter(Details.this,response.extendedIngredients);
            mealingredients.setAdapter(ingredientAdapter);
        }

        @Override
        public void didBad(String msg) {

        }
    };
    //Listener for the details to find the similar meals
    private final SimilarRecipesListener similarRecipesListener = new SimilarRecipesListener() {
        @Override
        public void didGood(List<SimilarRoot> thing, String msg) {
            dialog.dismiss();
            similarrecipes.setHasFixedSize(true);
            similarrecipes.setLayoutManager(new LinearLayoutManager(Details.this, LinearLayoutManager.HORIZONTAL,false));
            similarRecipesAdapter = new SimilarRecipesAdapter(Details.this, thing, RecipeClickListener);
            similarrecipes.setAdapter(similarRecipesAdapter);
        }

        @Override
        public void didBad(String msg) {
            Toast.makeText(Details.this,"Lathos",Toast.LENGTH_SHORT);
        }
    };
    //listener for clicking on a recipe
    private final Listeners.RecipeClickListener  RecipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClick(String id) {
            Intent i = new Intent(Details.this,Details.class).putExtra("id",id);
            startActivity(i);
        }
    };
    //listener for the instructions
    private final InstructionsListener instructionsListener = new InstructionsListener() {
        @Override
        public void didGood(List<AnalyzedInstruction> thing, String msg) {
            stepinstructions.setHasFixedSize(true);
            stepinstructions.setLayoutManager(new LinearLayoutManager(Details.this,LinearLayoutManager.VERTICAL,false));
            instructionAdapter=new InstructionsAdapter(Details.this,thing);
            stepinstructions.setAdapter(instructionAdapter);
        }

        @Override
        public void didBad(String msg) {

        }
    };
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if( hasFocus){
            decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }
}
