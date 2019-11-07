package com.example.favouritewords;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;

public class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>>allwords;
    private LiveData<List<Word>>starwords;

    public WordRepository(Application application) {
        WordRoomDatabase db=WordRoomDatabase.getDatabase(application);
        wordDao=db.wordDao();
        allwords=wordDao.getAllWords();
        starwords=wordDao.getStarWords();
    }

    LiveData<List<Word>> getwords(){
        return allwords;
    }
    LiveData<List<Word>> getStarWords(){
        return starwords;
    }
    public void insert(Word insert){
        new insertAsyncTask(wordDao).execute(insert);
    }

    public void deleteAll(){
        new deleteAllAsyncTask(wordDao).execute();
    }

    public void deleteWord(Word word){
        new deleteWordAsyncTask(wordDao).execute(word);
    }

    public class insertAsyncTask extends AsyncTask<Word,Void, Void>{
        private WordDao asynctaskDao;

        public insertAsyncTask(WordDao asynctaskDao) {
            this.asynctaskDao = asynctaskDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            asynctaskDao.insert(words[0]);
            return null;
        }
    }


    public class deleteWordAsyncTask extends AsyncTask<Word,Void, Void>{
        private WordDao asynctaskDao;

        public deleteWordAsyncTask(WordDao asynctaskDao) {
            this.asynctaskDao = asynctaskDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            asynctaskDao.deleteWord(words[0]);
            return null;
        }
    }

    public class deleteAllAsyncTask extends AsyncTask<Word,Void, Void>{
        private WordDao asynctaskDao;

        public deleteAllAsyncTask(WordDao asynctaskDao) {
            this.asynctaskDao = asynctaskDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            asynctaskDao.deleteAll();
            return null;
        }
    }

}
