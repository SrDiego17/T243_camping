package es.unizar.eina.camping.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.unizar.eina.camping.database.Parcela;
import es.unizar.eina.camping.database.ParcelaRepository;

public class ParcelaViewModel extends AndroidViewModel {

    private ParcelaRepository mRepository;

    private final LiveData<List<Parcela>> mAllParcelas;

    public ParcelaViewModel(Application application) {
        super(application);
        mRepository = new ParcelaRepository(application);
        mAllParcelas = mRepository.getAllParcelas();
    }

    LiveData<List<Parcela>> getAllParcelas() { return mAllParcelas; }

    public void insert(Parcela parcela) { mRepository.insert(parcela); }

    public void update(Parcela parcela) { mRepository.update(parcela); }
    public void delete(Parcela parcela) { mRepository.delete(parcela); }
}
