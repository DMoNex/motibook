package com.example.motibook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteListItemAdapter extends RecyclerView.Adapter<NoteListItemAdapter.Holder> {
    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View v, int pos);
    }

    private OnItemClickListener noteItemListener;
    private OnItemLongClickListener noteLongItemListener;
    ArrayList<NoteListItem> items = new ArrayList<>();

    public NoteListItemAdapter(ArrayList<NoteListItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        NoteListItem item = items.get(position);
        holder.bookName.setText(item.getBookName());
        holder.ISBN.setText(item.getISBN());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        noteItemListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        noteLongItemListener = listener;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView bookName;
        private TextView ISBN;

        public Holder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.bookList_bookName);
            ISBN = itemView.findViewById(R.id.bookList_author);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        noteItemListener.onItemClick(v, pos);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        noteLongItemListener.onItemLongClick(v, pos);
                    }

                    return false;
                }
            });
        }
    }
}
