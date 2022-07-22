package com.example.projetoveroippa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetoveroippa.databinding.FragmentMainMenuEventsBinding;
import com.example.projetoveroippa.databinding.FragmentSetNewPasswordBinding;


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
