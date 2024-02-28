package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodys.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Foods.ExtendedIngredient;
import Foods.Recipe;
import Foods.SimilarRoot;
import Listeners.RecipeClickListener;
import Listeners.SimilarRecipesListener;
import Listeners.SimilarRecipesOnClickListener;


public class SimilarRecipesAdapter extends RecyclerView.Adapter<SimilarRecipesViewHolder> {

    public SimilarRecipesAdapter(Context context, List<SimilarRoot> list, RecipeClickListener srListener) {
        this.context = context;
        this.list = list;
        this.srListener = srListener;
    }

    Context context;
    List<SimilarRoot> list;
    RecipeClickListener srListener;

    @NonNull
    @Override
    public SimilarRecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarRecipesViewHolder(LayoutInflater.from(context).inflate(R.layout.foodlist2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarRecipesViewHolder holder, int position) {
        holder.FoodTitle.setText(list.get(position).title);
        holder.FoodTitle.setSelected(true);
        //we call picasso to apply the images
        Picasso.get().load("https://spoonacular.com/recipeImages/"+list.get(position).id+"-556x370."+list.get(position).imageType).into(holder.FoodImage);

        //we call cardview
        holder.SimilarList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srListener.onRecipeClick(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });
    }
    @Override
    public int getItemCount() {
            return list.size();
        }
}


class SimilarRecipesViewHolder extends RecyclerView.ViewHolder {
    //initializing all our views in our layout
    CardView SimilarList;
    TextView FoodTitle,Merides;
    ImageView FoodImage;
    public SimilarRecipesViewHolder(@NonNull View itemView) {
        super(itemView);
        SimilarList= itemView.findViewById(R.id.FoodlistContainer);
        FoodTitle = itemView.findViewById(R.id.textfoodlist2);
        FoodImage = itemView.findViewById(R.id.eikonafoodlist2);
    }
}