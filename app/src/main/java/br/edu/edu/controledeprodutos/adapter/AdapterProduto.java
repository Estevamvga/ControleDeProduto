package br.edu.edu.controledeprodutos.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.edu.controledeprodutos.R;
import br.edu.edu.controledeprodutos.model.Produto;

public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.MyViewHolder> {

    private List<Produto> produtosList;
    private Onclick onclick;

    public AdapterProduto(List<Produto> produtosList, Onclick onclick) {

        this.produtosList=produtosList;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    //Configurar as informaçoes da linha EX:.
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    //Pegar todas as informações do produto e exibir no aplicativo
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Produto produto=produtosList.get(position);

        holder.textProduto.setText(produto.getNome());
        holder.textEstoque.setText("Estoque: " + produto.getEstoque());
        holder.textValor.setText("R$ " + produto.getValor());

        holder.itemView.setOnClickListener(view -> onclick.OnclickLister(produto));

    }

    @Override
    //Precisa retornar a quantidade da Listagem
    public int getItemCount() {

        return produtosList.size();
    }

    public interface Onclick{
        void OnclickLister(Produto produto);
    }

    //Declarar a linha de Layout
    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textProduto, textEstoque, textValor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textProduto = itemView.findViewById(R.id.text_produto);
            textEstoque = itemView.findViewById(R.id.text_estoque);
            textValor = itemView.findViewById(R.id.text_valor);
        }
    }

}
