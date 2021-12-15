package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.oscarapp.R;

import java.util.List;

import Model.FilmeItem;

public class AdapterFilmes extends RecyclerView.Adapter<AdapterFilmes.MyViewHolder> {
    private Context context;
    private List<FilmeItem> listFilmes;

    public AdapterFilmes(Context context, List<FilmeItem> list) {
        this.context = context;
        this.listFilmes = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nome, genero;
        ImageView img;

        public MyViewHolder(View view) {
            super(view);
            nome = view.findViewById(R.id.textViewNomeFilme);
            genero = view.findViewById(R.id.textViewGeneroFilme);
            img = view.findViewById(R.id.imageViewFilme);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list_filmes, parent, false);

        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FilmeItem obj = listFilmes.get(position);
        holder.nome.setText(obj.getNome());
        holder.genero.setText(obj.getGenero());
        Glide.with(context)
                .load(obj.getImg())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return listFilmes.size();
    }



}
