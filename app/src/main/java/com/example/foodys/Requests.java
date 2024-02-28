package com.example.foodys;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import Foods.AnalyzedInstruction;
import Foods.Root;
import Foods.Rootee;
import Foods.SimilarRoot;
import Listeners.InstructionsListener;
import Listeners.Listener;
import Listeners.RecipeDetailsListener;
import Listeners.SimilarRecipesListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class Requests {
    Context context;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.spoonacular.com/").addConverterFactory(GsonConverterFactory.create()).build();
    // On device
    public Requests(Context context) {
        this.context = context;
    }
    public void getRecipe(Listener listener, List<String>tags) {
        //pass and obj from the listener.
        //creating an instance of the RRec
        RRec callRecipes = retrofit.create(RRec.class);
        //create a call obj for the mainthing class and passing the apiKey and the number of random recipes we want to be shown.
        Call<Rootee> call = callRecipes.showRecipe(context.getString(R.string.apikey),"5",tags);
        //call (an item of data awaiting processing) to the queue and create a new call back for mainthing.
        call.enqueue(new Callback<Rootee>() {

            @Override
            public void onResponse(Call<Rootee> call, Response<Rootee> response) {
                // if not successfil we send the msg from the Listener saying so, and below we do the opposite
                if(!response.isSuccessful()){
                    listener.didBad("8");
                    return;
                }
                listener.didGood(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<Rootee> call, Throwable t) {
                listener.didBad("7");
            }
        });
    }

    //create  a new listener for the recipe details and pass it as a par + id
    public void getDetails(RecipeDetailsListener recipeDetailsListener,int id)
    {
        CallDetails callDetails = retrofit.create(CallDetails.class);
        Call<Root> call= callDetails.callRD(id,context.getString(R.string.apikey));
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if(!response.isSuccessful())
                {
                    recipeDetailsListener.didBad("1");
                }
                recipeDetailsListener.didGood(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                recipeDetailsListener.didBad("3");
            }
        });

    }
    public void getSimilar(SimilarRecipesListener similarRecipesListener,int id)
    {
        CallSimilar callSimilar= retrofit.create(CallSimilar.class);
        Call<List<SimilarRoot>> call= callSimilar.callSR(id,"5",context.getString(R.string.apikey));
        call.enqueue(new Callback<List<SimilarRoot>>() {
            @Override
            public void onResponse(Call<List<SimilarRoot>> call, Response<List<SimilarRoot>> response) {
                if(!response.isSuccessful())
                {
                    similarRecipesListener.didBad("1");
                    return;
                }
                similarRecipesListener.didGood(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<List<SimilarRoot>> call, Throwable t) {
                similarRecipesListener.didBad("3");
            }
        });
    }
    public void getInstructions(InstructionsListener instructionsListener,int id)
    {
        CallSteps callSteps=retrofit.create(CallSteps.class);
        Call<List<AnalyzedInstruction>> call= callSteps.callCS(id,context.getString(R.string.apikey));
        call.enqueue(new Callback<List<AnalyzedInstruction>>() {
            @Override
            public void onResponse(Call<List<AnalyzedInstruction>> call, Response<List<AnalyzedInstruction>> response) {
                if(!response.isSuccessful())
                {
                    instructionsListener.didBad("1");
                    return;
                }
                instructionsListener.didGood(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<List<AnalyzedInstruction>> call, Throwable t) {
                instructionsListener.didBad("3");
            }
        });
    }
    private interface  RRec{
        //we will be getting the recipes from the api website
        @GET("recipes/random")
        // we will be using the tags and number from the api for the random recipes.
        Call<Rootee> showRecipe (
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }
    private interface CallDetails{
        @GET("recipes/{id}/information")
        Call<Root> callRD(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
    private interface CallSimilar{
        @GET("recipes/{id}/similar")
        Call<List<SimilarRoot>> callSR(
                @Path("id") int id,
                @Query("number") String number,
                @Query("apiKey") String apiKey
        );
    }
    private interface CallSteps{
        @GET("recipes/{id}/analyzedInstructions")
        Call<List<AnalyzedInstruction>> callCS(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
}

