package com.example.projetoveroippa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetoveroippa.databinding.FragmentMainMenuEventsBinding;
import com.example.projetoveroippa.databinding.FragmentSetNewPasswordBinding;
import com.example.projetoveroippa.object.Event;


public class MainMenuEvents extends Fragment {
    private FragmentMainMenuEventsBinding binding;

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
        Event a = new Event();
        a.name = "Evacu";
        a.date = "12/12/2022";
        a.hour = "00.00h";
        a.description = "Make Evacu Call";
        Event b = new Event();
        b.name = "Airport";
        b.date = "12/03/2022";
        b.hour = "15.45h";
        b.description = "Make Airport Call";

        DataBase.events.add(a);
        DataBase.events.add(b);

        EventAdapter Adapter = new EventAdapter(getContext(), DataBase.events);

        binding.EventsRecyclerView.setAdapter(Adapter);
        binding.EventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
    }
}
