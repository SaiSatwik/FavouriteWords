package com.example.favouritewords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class WordEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_entry);
    }

    public void saveWord(View view) {
        Intent in=new Intent(this, MainActivity.class);
        EditText ed=findViewById(R.id.editText);
        in.putExtra("word",ed.getText().toString());
        Log.i("testing",ed.getText().toString());
        setResult(RESULT_OK,in);
        finish();
    }
}
