package com.example.favouritewords;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private WordViewMode mWordViewModel;
    private WordAdapter mAdapter;
    List<Word> mywords=new ArrayList<>();
    private boolean star=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(MainActivity.this, WordEntry.class);
                startActivityForResult(in,1);

            }
        });
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
//        for(int i=0;i<20;i++){
//            Word io=new Word("Hello "+i,false);
//            mywords.add(io);
//
        mWordViewModel = ViewModelProviders.of(this).get(WordViewMode.class);
        mAdapter = new WordAdapter(mywords,mWordViewModel);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setWords(words);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Word myWord = mAdapter.getWordAtPosition(position);
                        Toast.makeText(MainActivity.this, "Deleting " +
                                myWord.getWord(), Toast.LENGTH_LONG).show();

                        // Delete the word
                        mWordViewModel.deleteWord(myWord);
                    }
                });

        helper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                Log.i("Hwllo",data.getStringExtra("word")+"me");
                String word=data.getStringExtra("word");
                mWordViewModel.insert(new Word(word,false));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mWordViewModel.deleteAll();
        }
        else if(id==R.id.star){

            star=!star;

            if(star){
                item.setIcon(R.drawable.ic_solid);
                Toast.makeText(getApplicationContext(),""+star,Toast.LENGTH_LONG).show();
                mWordViewModel.getAllWords().removeObservers(this);
                mWordViewModel.getStarWords().observe(this, new Observer<List<Word>>() {
                    @Override
                    public void onChanged(@Nullable final List<Word> words) {
                        // Update the cached copy of the words in the adapter.
                        mAdapter.setWords(words);
                    }
                });
            }
            else{
                item.setIcon(R.drawable.ic_action_name);
                Toast.makeText(getApplicationContext(),""+star,Toast.LENGTH_LONG).show();
                mWordViewModel.getStarWords().removeObservers(this);
                mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
                    @Override
                    public void onChanged(@Nullable final List<Word> words) {
                        // Update the cached copy of the words in the adapter.
                        mAdapter.setWords(words);
                    }
                });
            }

        }

        return super.onOptionsItemSelected(item);
    }


}
