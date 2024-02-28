package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodys.R;
import java.util.List;
import Foods.AnalyzedInstruction;
import Foods.SimilarRoot;
import Listeners.RecipeClickListener;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsViewHolder>{
    public InstructionsAdapter(Context context, List<AnalyzedInstruction> list) {
        this.context = context;
        this.list = list;
    }
    Context context;
    List<AnalyzedInstruction> list;
    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionsViewHolder(LayoutInflater.from(context).inflate(R.layout.steps,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder holder, int position) {
        holder.steps.setHasFixedSize(true);
        holder.steps.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        StepAdapter stepAdapter = new StepAdapter(context,list.get(position).steps);
        holder.steps.setAdapter(stepAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class InstructionsViewHolder extends RecyclerView.ViewHolder{

    /* Αφού λόγω api αναγκαζόμαστε να χρησιμοποιήσουμε λίστα για τα βήματα θα πρέπει να χρησιμοποιήσουμε recycler view. Επειδή θα χρησιμοποιήσουμε recycler view θα πρέπει να δημιουργήσουμε ένα άλλο xml αρχείο το οποίο θα γίνει
    bind στο recyclerview αυτό από ένα άλλο adapter για να μπορέσουμε να χρησιμοποιήσουμε τα στοιχεία της κλάσης AnalyzedInstruction δηλαδή τον αριθμό του βήματος και τις οδηγίες του. Έαν οι πληροφορίες δινόταν διαφορετικά από
    το api δεν θα χρειαζόταν όλο αυτό
     */
    RecyclerView steps;
    public InstructionsViewHolder(@NonNull View itemView) {
        super(itemView);
        steps=itemView.findViewById(R.id.Steps);
    }
}
