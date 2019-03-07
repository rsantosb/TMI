package tmi.busho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btn_reconocedor = findViewById(R.id.btn_reconocedor);
        btn_reconocedor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentReconocedor= new Intent (MainActivity.this, ReconocedorCuadros.class);
                startActivity(intentReconocedor);
            }
        });

        final Button btn_filtros = findViewById(R.id.btn_jugar);
        btn_filtros.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentFiltros= new Intent (MainActivity.this, FiltrosCuadros.class);
                startActivity(intentFiltros);
            }
        });


    }
}
