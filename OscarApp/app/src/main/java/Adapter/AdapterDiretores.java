package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.oscarapp.R;

import java.util.ArrayList;
import java.util.List;

import Model.DiretorItem;
import Model.FilmeItem;

public class AdapterDiretores extends RecyclerView.Adapter<AdapterDiretores.MyViewHolder> {
    private Context context;
    private List<DiretorItem> listDiretores;
    private List<RadioButton> radioButtonList = new ArrayList<>();

    public List<RadioButton> getRadioButtons() {
        return radioButtonList;
    }

    public AdapterDiretores(Context context, List<DiretorItem> list) {
        this.context = context;
        this.listDiretores = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RadioButton radioDiretor;

        public MyViewHolder(View view) {
            super(view);
            radioDiretor = view.findViewById(R.id.diretorRadio);
        }
    }

    @NonNull
    @Override
    public AdapterDiretores.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list_diretores, parent, false);

        return new AdapterDiretores.MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDiretores.MyViewHolder holder, int position) {
        DiretorItem obj = listDiretores.get(position);
        holder.radioDiretor.setText(obj.getNome());
        radioButtonList.add(holder.radioDiretor);
    }

    @Override
    public int getItemCount() {
        return listDiretores.size();
    }

}
