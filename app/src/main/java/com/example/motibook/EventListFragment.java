package com.example.motibook;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class EventListFragment extends Fragment implements OnBackPressedListener {
    MainActivity mainAct;
    private RecyclerView eventList;
    private ArrayList<EventListItem> eventListItems;

    public EventListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mainAct = (MainActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mainAct = null;
    }

    public static EventListFragment newInstance(String param1, String param2) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_event_list, container, false);

        eventListItems = mainAct.eventListItems;

        eventList = (RecyclerView) rootView.findViewById(R.id.eventSearchListView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        eventList.setLayoutManager(linearLayoutManager);

        EventListItemAdapter eventListItemAdapter = new EventListItemAdapter(eventListItems);
        eventListItemAdapter.setOnItemClickListener(new EventListItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Toast.makeText(getActivity(), "select : " + eventListItems.get(pos).getEventName(), Toast.LENGTH_SHORT).show();
            }
        });

        eventList.setAdapter(eventListItemAdapter);

        return rootView;
    }

    @Override
    public void onBackPressed() {
        MainActivity activity = (MainActivity) getActivity();
        activity.FragmentView(3);
    }
}