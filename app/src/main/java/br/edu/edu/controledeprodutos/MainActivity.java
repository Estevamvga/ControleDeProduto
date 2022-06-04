package br.edu.edu.controledeprodutos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterProduto.Onclick {

    private AdapterProduto adapterProduto;
    private SwipeableRecyclerView rvProdutos;

    private ImageButton ibAdd;
    private ImageButton ibVerMais;

    private ProdutoDAO produtoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        produtoDAO = new ProdutoDAO(this);

        ibAdd = findViewById(R.id.ib_add);
        ibVerMais = findViewById(R.id.ib_ver_mais);
        rvProdutos = findViewById(R.id.rvProdutos);

        configRecyclerView();

        ouvinteCliques();

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
        rvProdutos.setLayoutManager(new LinearLayoutManager(this));
        rvProdutos.setHasFixedSize(true);
        adapterProduto = new AdapterProduto(produtoDAO.getListProdutos(), this);
        rvProdutos.setAdapter(adapterProduto);

        rvProdutos.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {


            }

            @Override
            public void onSwipedRight(int position) {
                produtoDAO.getListProdutos().remove(position);
                adapterProduto.notifyItemRemoved(position);

            }
        });
    }


    @Override
    public void OnclickLister(Produto produto) {
        Intent intent = new Intent(this, FormProdutoActivity.class);
        intent.putExtra("produto", produto);
        startActivity(intent);
        //Toast.makeText(this, produto.getNome()  , Toast.LENGTH_SHORT).show();
    }
}