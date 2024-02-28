package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodys.R;

import java.util.List;

import Foods.Step;

public class StepAdapter    extends RecyclerView.Adapter<StepAdapterViewHolder>
{
    public StepAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }

    Context context;
    List<Step> list;
    @NonNull
    @Override
    public StepAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StepAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.instructionrecycler,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull StepAdapterViewHolder holder, int position) {
        holder.Stepsdesc.setText(list.get(position).step);
        holder.Stepsnum.setText("Step "+String.valueOf(list.get(position).number));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class StepAdapterViewHolder extends RecyclerView.ViewHolder
{
    //initializing all our views in our layout
    TextView Stepsnum,Stepsdesc;
    public StepAdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        Stepsdesc=itemView.findViewById(R.id.Stepsdesc);
        Stepsnum=itemView.findViewById(R.id.Stepsnum);
    }
}