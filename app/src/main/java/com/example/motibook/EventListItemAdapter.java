package com.example.motibook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventListItemAdapter extends RecyclerView.Adapter<EventListItemAdapter.Holder> {
    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener eventItemListener;
    ArrayList<EventListItem> items = new ArrayList<>();
    public EventListItemAdapter(ArrayList<EventListItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        EventListItem item = items.get(position);
        holder.eventName.setText(item.getEventName());

        String strDate = item.getDate().substring(0, 2) + "년 " + item.getDate().substring(2, 4) + "월 " + item.getDate().substring(4, 6) + "일";
        holder.date.setText(strDate);

        String strTime = item.getStartTime().substring(0, 2) + ":" + item.getStartTime().substring(2, 4) + " ~ "
                       + item.getEndTime().substring(0, 2) + ":" + item.getEndTime().substring(2, 4);

        holder.time.setText(strTime);

        holder.locate.setText(item.getStrAddress());
        holder.locateDetail.setText(item.getLocate());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        eventItemListener = listener;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView eventName;
        private TextView date;
        private TextView time;
        private TextView locate;
        private TextView locateDetail;

        public Holder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.eventList_eventName);
            date = itemView.findViewById(R.id.eventList_date);
            time = itemView.findViewById(R.id.eventList_time);
            locate = itemView.findViewById(R.id.eventList_locate);
            locateDetail = itemView.findViewById(R.id.eventList_locateDetail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        eventItemListener.onItemClick(v, pos);
                    }
                }
            });
        }
    }
}






/*







}*/