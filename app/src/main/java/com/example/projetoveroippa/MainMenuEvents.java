package com.example.projetoveroippa;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projetoveroippa.databinding.FragmentMainMenuEventsBinding;
import com.example.projetoveroippa.databinding.FragmentSetNewPasswordBinding;
import com.example.projetoveroippa.object.Event;
import com.example.projetoveroippa.object.Praesensa;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collection;
import java.util.Collections;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class MainMenuEvents extends Fragment {
    private FragmentMainMenuEventsBinding binding;
    Event deletedEvent = null;
    public MainMenuEvents() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MainMenuEvents newInstance(String param1, String param2) {
        MainMenuEvents fragment = new MainMenuEvents();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainMenuEventsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Collections.sort(DataBase.utilizadorAtivo.events, new Event.myEventComparator());
        EventAdapter Adapter = new EventAdapter(getContext(), DataBase.utilizadorAtivo.events, getActivity());
        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deletedEvent = DataBase.utilizadorAtivo.events.get(viewHolder.getAdapterPosition());
                int position = viewHolder.getAdapterPosition();
                DataBase.utilizadorAtivo.events.remove(viewHolder.getAdapterPosition());
                Adapter.notifyDataSetChanged();
                DataBase.saveData(getContext());
                Snackbar.make(view, deletedEvent.name,Snackbar.LENGTH_LONG).setAction(getString(R.string.undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      DataBase.utilizadorAtivo.events.add(position,deletedEvent);
                      Adapter.notifyDataSetChanged();
                    }
                }).show();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(getContext(),c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(Color.RED)
                        .addBackgroundColor(Color.RED)
                        .addActionIcon(R.drawable.ic_baseline_delete_24)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        binding.EventsRecyclerView.setAdapter(Adapter);
        binding.EventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(binding.EventsRecyclerView);
        binding.logOut2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase.utilizadorAtivo = null;
                DataBase.saveData(getContext());
                NavHostFragment.findNavController(MainMenuEvents.this).navigate(R.id.action_mainMenuEvents_to_mainMenu);
            }
        });
        binding.GoToCallCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainMenuEvents.this).navigate(R.id.action_mainMenuEvents_to_callCenter);
            }
        });

        binding.floatingActionButtonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainMenuEvents.this).navigate(R.id.action_mainMenuEvents_to_calendarFragment);
            }
        });
    }

}
