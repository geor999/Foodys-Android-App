package com.example.foodys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import Adapters.rrAdapter;
import Foods.Recipe;
import Foods.Rootee;
import Listeners.Listener;
import Listeners.RecipeClickListener;

public class MainActivity extends AppCompatActivity {
    private View decorview;
    ProgressDialog dialog;
    Requests requests;
    rrAdapter rrAdapter;
    RecyclerView RecView;
    Spinner spinner;
    ArrayList<String> tags = new ArrayList<>();
    ArrayList<Recipe> arrayforreinitialize =null;
    SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Initialize decorview to disable uilayout from the upper part of the screen.
         */
        decorview = getWindow().getDecorView();
        decorview.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility==0)
                {
                    decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_FULLSCREEN);
                }
            }
        });
        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait...");
        /*if savedInstanceState is not null then we put the Arraylist we get from onSaveInstanceState on an empty arraylist so we can re-initialize the app
          when there is a change in the app's view
         */
        if(savedInstanceState!=null)
        {
            arrayforreinitialize =savedInstanceState.getParcelableArrayList("aa");
        }
        //constrution of the request manager for the api
        requests = new Requests(this);
        searchView = findViewById(R.id.search_view_main);
        dialog.show();
        //initialize the search bar
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onWindowFocusChanged(true);
                //clear tags, add the selected item to the tags and search it via the recipe listener and show it
                tags.clear();
                tags.add(query);
                requests.getRecipe(listener, tags);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //function to initialize the spinner so we can change the category
        spinnersettings();
    }

    public void spinnersettings() {
        //calling spinner obj and passing the id of the spinner in act main
        spinner = findViewById(R.id.Spinner1);
        //attaching and arrAdapt to the spinner
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.tags, R.layout.spinnerfood);
        //we will set the drop down res for the spinner
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inside);
        //attach the arrAdapt to the spinner
        spinner.setAdapter(arrayAdapter);
        // now we can select an item of our choice
        spinner.setOnItemSelectedListener(spinnerSL);
    }
    // creating the listener for the recipe, it is type Listener
    private final Listener listener = new Listener() {
        @Override
        public void didGood(Rootee thing, String msg) {
            dialog.dismiss();
            RecView = findViewById(R.id.menu);
            RecView.setHasFixedSize(true);
            RecView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
            //if aa is not null that means that the view of the app has changed so we add those values to a new arraylist and then call the adapter to pass
            //these values so we can reinitialize the mainpage. if it is null then we initialize the mainpage with random recipes
            if(arrayforreinitialize !=null)
            {
                ArrayList<Recipe> recipes = new ArrayList<Recipe>();
                for(int a = 0; a< arrayforreinitialize.size(); a++)
                {
                    recipes.add(a, arrayforreinitialize.get(a));
                }
                arrayforreinitialize =null;
                rrAdapter = new rrAdapter(MainActivity.this, recipes, rClickListener);
            }
            else {
                rrAdapter = new rrAdapter(MainActivity.this, thing.recipes, rClickListener);
            }
            RecView.setAdapter(rrAdapter);

        }

        @Override
        public void didBad(String msg) {
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    };
    //(!)
    private final AdapterView.OnItemSelectedListener spinnerSL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // here we need to call the getRecipe
            // clear list everytime we select an item its now show on the spinner forever
            tags.clear();
            tags.add(parent.getSelectedItem().toString());
            requests.getRecipe(listener, tags);
            //enabling dialog
            dialog.show();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    // creating the listener here via genarated onrecipeclick
    private final RecipeClickListener rClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClick(String id) {
            //this will pass the id to a new activity (Recipe Detail Act)
            Intent i = new Intent(MainActivity.this,Details.class).putExtra("id",id);
            startActivity(i);
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if( hasFocus){
            decorview.setSystemUiVisibility(
                     View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        // etc.
        savedInstanceState.putParcelableArrayList("aa",rrAdapter.getList());
    }
}


