package com.example.favouritewords;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class WordViewMode extends AndroidViewModel {
    private WordRepository repository;
    private LiveData<List<Word>>mywords;
    private LiveData<List<Word>>starwords;

    public WordViewMode(@NonNull Application application) {
        super(application);
        repository=new WordRepository(application);
        mywords=repository.getwords();
        starwords=repository.getStarWords();
    }

    LiveData<List<Word>> getAllWords(){return mywords;}
    LiveData<List<Word>> getStarWords(){return starwords;}
    public void insert(Word word){repository.insert(word);}
    public void deleteAll(){repository.deleteAll();}
    public void deleteWord(Word word){repository.deleteWord(word);}


}
