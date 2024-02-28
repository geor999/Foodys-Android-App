package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodys.R;

import java.util.ArrayList;
import java.util.List;

import Foods.ExtendedIngredient;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {
    public IngredientAdapter(Context context, ArrayList<ExtendedIngredient> list) {
        this.context = context;
        this.list=list;
    }

    Context context;
    List<ExtendedIngredient> list;

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.mealingredients,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        //holder.name.setText(list.get(position).name);
        holder.quantity.setText(list.get(position).original);
        //Picasso.get().load("https://spoonacular.com/cdn/ingredients100x100/"+list.get(position).image).into(holder.photo);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}

class IngredientsViewHolder extends RecyclerView.ViewHolder {
    //initializing all our views in our layout
    TextView quantity,name;
    ImageView photo;
    public IngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        quantity=itemView.findViewById(R.id.IngredientsNumber);
    }
}