package es.unizar.eina.camping.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import es.unizar.eina.camping.R;
import es.unizar.eina.camping.database.Parcela;

public class Camping extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camping);

        ListarParcelas.parcelaViewModel = new ParcelaViewModel(getApplication());
        Button addParcelaButton = findViewById(R.id.button_add_parcela);
        addParcelaButton.setOnClickListener(v -> createParcela());
        Button viewListButton = findViewById(R.id.button_view_list);
        viewListButton.setOnClickListener(v -> {
            Intent intent = new Intent(Camping.this, ListarParcelas.class);
            startActivity(intent);
        });
    }

    private void createParcela() {
        mStartCreateParcela.launch(new Intent(this, ParcelaEdit.class));
    }

    ActivityResultLauncher<Intent> mStartCreateParcela = newActivityResultLauncher(new ExecuteActivityResult() {
        @Override
        public void process(Bundle extras, Parcela parcela) {
            ListarParcelas.parcelaViewModel.insert(parcela);
        }
    });

    ActivityResultLauncher<Intent> newActivityResultLauncher(ExecuteActivityResult executable) {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Parcela parcela = new Parcela(extras.getString(ParcelaEdit.PARCELA_NOMBRE),
                                extras.getString(ParcelaEdit.PARCELA_DESCRIPCION),
                                extras.getInt(ParcelaEdit.PARCELA_MAXOCUPANTES),
                                extras.getDouble(ParcelaEdit.PARCELA_PRECIOPOROCUPANTE));
                        executable.process(extras, parcela);
                    }
                });
    }

    interface ExecuteActivityResult {
        void process(Bundle extras, Parcela parcela);
    }
}
