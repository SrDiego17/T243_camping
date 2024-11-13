package es.unizar.eina.camping.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.unizar.eina.camping.database.Parcela;
import es.unizar.eina.camping.R;

public class ListarParcelas extends AppCompatActivity {

    private FloatingActionButton mFab;
    public static ParcelaViewModel parcelaViewModel;
    private ParcelaListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarparcelas);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        parcelaViewModel = new ViewModelProvider(this).get(ParcelaViewModel.class);

        adapter = new ParcelaListAdapter(new ParcelaListAdapter.ParcelaDiff(),
                this::editParcela, this::deleteParcela);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        parcelaViewModel.getAllParcelas().observe(this, adapter::submitList);
    }

    private void editParcela(int position) {
        Parcela parcela = adapter.getCurrentList().get(position);
        Intent intent = new Intent(this, ParcelaEdit.class);
        intent.putExtra(ParcelaEdit.PARCELA_ID, parcela.getId());
        intent.putExtra(ParcelaEdit.PARCELA_NOMBRE, parcela.getNombre());
        intent.putExtra(ParcelaEdit.PARCELA_DESCRIPCION, parcela.getDescripcion());
        intent.putExtra(ParcelaEdit.PARCELA_MAXOCUPANTES, parcela.getMaxOcupantes());
        intent.putExtra(ParcelaEdit.PARCELA_PRECIOPOROCUPANTE, parcela.getPrecioPorOcupante());
        startActivity(intent);
    }

    private void deleteParcela(int position) {
        Parcela parcela = adapter.getCurrentList().get(position);
        parcelaViewModel.delete(parcela);
        Toast.makeText(this, "Parcela eliminada", Toast.LENGTH_SHORT).show();
    }
}
