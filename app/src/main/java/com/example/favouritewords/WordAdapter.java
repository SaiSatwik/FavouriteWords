package com.example.favouritewords;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    List<Word> data;
    private WordViewMode mWordViewModel;

    public WordAdapter(List<Word> data,WordViewMode mWordViewModel) {
        this.data = data; this.mWordViewModel=mWordViewModel;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word, parent, false);

        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        // TODO bind new data to views in holder. Use position variable for correct position in your data
        if(data!=null){
            Word curr=data.get(position);
            holder.tv.setText(curr.getWord());
            if(curr.isFav()){
                holder.im.setImageResource(R.drawable.ic_solid);
            }
            else{
                holder.im.setImageResource(R.drawable.ic_action_name);

            }
        }
        else{
            holder.tv.setText("No word");
        }
    }

    @Override
    public int getItemCount() {
        // TODO return length of list(deciding how many items will be represented)
        if(data!=null){
            return data.size();
        }
        else{
            return 0;
        }
    }

    void setWords(List<Word> words){
        data = words;
        notifyDataSetChanged();
    }

    public Word getWordAtPosition (int position) {
        return data.get(position);
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView im;
        public WordViewHolder(@NonNull final View itemView) {
            super(itemView);
            // TODO retrieve desired views, save them if you want to have easier access to them in onBindViewHolder
            tv=itemView.findViewById(R.id.textView);
            im=itemView.findViewById(R.id.imageView);
            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Word w=data.get(getLayoutPosition());
                    w.setFav(!w.isFav());

                    mWordViewModel.insert(w);
                    notifyDataSetChanged();
                }
            });

        }
    }
}
