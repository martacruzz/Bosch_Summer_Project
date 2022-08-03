package com.example.projetoveroippa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projetoveroippa.databinding.FragmentLogInBinding;
import com.example.projetoveroippa.databinding.FragmentMainMenuBinding;


public class MainMenu extends Fragment {
private FragmentMainMenuBinding binding;

    public MainMenu() {
        // Required empty public constructor
    }


    public static MainMenu newInstance(String param1, String param2) {
        MainMenu fragment = new MainMenu();
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
          if (DataBase.utilizadorAtivo!=null){
            NavHostFragment.findNavController(MainMenu.this).navigate(R.id.action_mainMenu_to_mainMenuEvents);
              Toast.makeText(getContext(), getString(R.string.welcome_back) + " " + DataBase.utilizadorAtivo.userName, Toast.LENGTH_SHORT).show();
        }
        binding= FragmentMainMenuBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainMenu.this).navigate(R.id.action_mainMenu_to_logInFragment2);
            }
        });
        binding.buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainMenu.this).navigate(R.id.action_mainMenu_to_createAccountFragment);
            }
        });
        binding.textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainMenu.this).navigate(R.id.action_mainMenu_to_fotgotPasswordFr);
            }
        });
    }
}