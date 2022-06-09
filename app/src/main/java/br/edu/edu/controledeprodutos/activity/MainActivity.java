package br.edu.edu.controledeprodutos.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.edu.edu.controledeprodutos.AdapterProduto;
import br.edu.edu.controledeprodutos.Produto;
import br.edu.edu.controledeprodutos.ProdutoDAO;
import br.edu.edu.controledeprodutos.R;
import br.edu.edu.controledeprodutos.activity.FormProdutoActivity;

public class MainActivity extends AppCompatActivity implements AdapterProduto.Onclick {

    private AdapterProduto adapterProduto;
    private List<Produto> produtoList = new ArrayList<>();
    private SwipeableRecyclerView rvProdutos;
    private ImageButton ibAdd;
    private ImageButton ibVerMais;
    private TextView text_info;

    private ProdutoDAO produtoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        produtoDAO = new ProdutoDAO(this);
        produtoList = produtoDAO.getListProdutos();

        ibAdd = findViewById(R.id.ib_add);
        ibVerMais = findViewById(R.id.ib_ver_mais);

        rvProdutos = findViewById(R.id.rvProdutos);
        text_info = findViewById(R.id.text_info);



        ouvinteCliques();

    }

    @Override
    protected void onStart() {
        super.onStart();

        configRecyclerView();

    }

    private void ouvinteCliques(){
        ibAdd.setOnClickListener(view -> {
            startActivity(new Intent(this, FormProdutoActivity.class));
            Toast.makeText(this, "Cadastrar", Toast.LENGTH_SHORT).show();
        });

        ibVerMais.setOnClickListener(view -> {

            PopupMenu popupMenu = new PopupMenu(this, ibVerMais );
            popupMenu.getMenuInflater().inflate(R.menu.menu_toobar, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if(menuItem.getItemId() == R.id.menu_sobre){
                    Toast.makeText(this, "Sobre", Toast.LENGTH_SHORT).show();
                }
                return true;
            });

            popupMenu.show();

        });
    }


    private void configRecyclerView(){

        produtoList.clear();
        produtoList = produtoDAO.getListProdutos();

        vertificaQtdLista();

        rvProdutos.setLayoutManager(new LinearLayoutManager(this));
        rvProdutos.setHasFixedSize(true);
        adapterProduto = new AdapterProduto(produtoList, this);
        rvProdutos.setAdapter(adapterProduto);

        rvProdutos.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {

                Produto produto = produtoList.get(position);


                    produtoDAO.atualizaProduto(produto);
                    //produtoList.(produto);
                    adapterProduto.notifyItemChanged(position);

                    vertificaQtdLista();


            }

            @Override
            public void onSwipedRight(int position) {

                Produto produto = produtoList.get(position);

                produtoDAO.deletaProduto(produto);
                produtoList.remove(produto);
                adapterProduto.notifyItemRemoved(position);

                vertificaQtdLista();


            }
        });
    }

    private void vertificaQtdLista(){

        if (produtoList.size() == 0 ){
            text_info.setVisibility(View.VISIBLE);
        }else {
            text_info.setVisibility(View.GONE);
        }

    }


    @Override
    public void OnclickLister(Produto produto) {
        Intent intent = new Intent(this, FormProdutoActivity.class);
        intent.putExtra("produto", produto);
         Toast.makeText(this, "Editar produto: " + produto.getNome()  , Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }
}