package Adapters;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodys.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Foods.Recipe;
import Listeners.RecipeClickListener;


public class rrAdapter extends  RecyclerView.Adapter<rrRecipeViewHolder> {
    //we create some obj in our adapter
    Context context;
    ArrayList<Recipe> list;
    //create a separate interface listener for the recipe click action thing and pass it to the below const
    RecipeClickListener rcListener;


    public rrAdapter(Context context, ArrayList<Recipe> list,RecipeClickListener rcListener) {
        // generate a constructor
        this.context = context;
        this.list = list;
        this.rcListener = rcListener;
    }
    //extends Parcelable for us to be able to pass it in onSaveInstanceState
    public ArrayList<? extends Parcelable> getList() {
        return list;
    }
    public void setList(ArrayList<Recipe> list){
        this.list=list;
    }

    @NonNull
    @Override
    public rrRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new rrRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.foodlist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull rrRecipeViewHolder holder, int position) {
        holder.FoodTitle.setText(list.get(position).title);
        holder.FoodTitle.setSelected(true);
        holder.text_likes.setText(list.get(position).aggregateLikes + " Likes");
        holder.text_merides.setText(list.get(position).servings + " Portions");
        //we call picasso to apply the image
        Picasso.get().load(list.get(position).image).into(holder.FoodImage);
        //we call cardview
        holder.ListContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcListener.onRecipeClick(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class rrRecipeViewHolder extends RecyclerView.ViewHolder {
    //ViewHolder class to initialize our view .
    CardView ListContainer;
    TextView FoodTitle,text_merides,text_likes;
    ImageView FoodImage;

    public rrRecipeViewHolder(@NonNull View itemView) {
        //initializing all our views in our layout
        super(itemView);
        ListContainer = itemView.findViewById(R.id.ListContainer);
        FoodTitle = itemView.findViewById(R.id.FoodTitle);
        text_merides = itemView.findViewById(R.id.text_merides);
        text_likes = itemView.findViewById(R.id.text_likes);
        FoodImage = itemView.findViewById(R.id.FoodImage);
    }
}
