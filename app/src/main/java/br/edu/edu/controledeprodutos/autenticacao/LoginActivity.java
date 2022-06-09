package br.edu.edu.controledeprodutos.autenticacao;

 import androidx.appcompat.app.AppCompatActivity;

 import android.content.Intent;
 import android.os.Bundle;
 import android.widget.TextView;

 import br.edu.edu.controledeprodutos.R;

public class LoginActivity extends AppCompatActivity {

    private TextView text_cria_conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciaComponentes();

        configCliques();
    }

    private void configCliques(){

        text_cria_conta.setOnClickListener(view -> startActivity(new Intent(this, CriarContaActivity.class)));
    }

    private void iniciaComponentes(){

        text_cria_conta = findViewById(R.id.text_criar_conta);
    }
}