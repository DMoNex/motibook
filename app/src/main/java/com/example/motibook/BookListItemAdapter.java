package com.example.motibook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookListItemAdapter extends RecyclerView.Adapter<BookListItemAdapter.Holder> {
    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener bookItemListener;
    ArrayList<BookListItem> items = new ArrayList<>();

    public BookListItemAdapter(ArrayList<BookListItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        BookListItem item = items.get(position);
        holder.bookName.setText(item.getBookName());
        holder.authorName.setText(item.getAuthor());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        bookItemListener = listener;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView bookName;
        private TextView authorName;

        public Holder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.bookList_bookName);
            authorName = itemView.findViewById(R.id.bookList_author);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        bookItemListener.onItemClick(v, pos);
                    }
                }
            });
        }
    }
}