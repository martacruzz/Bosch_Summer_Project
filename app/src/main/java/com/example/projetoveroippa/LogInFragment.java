package com.example.projetoveroippa;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projetoveroippa.databinding.FragmentLogInBinding;
import com.example.projetoveroippa.databinding.FragmentMainMenuBinding;
import com.example.projetoveroippa.object.User;

import java.util.ArrayList;


public class LogInFragment extends Fragment {
    private FragmentLogInBinding binding;


    public LogInFragment() {
        // Required empty public constructor
    }

    public static LogInFragment newInstance(String param1, String param2) {
        LogInFragment fragment = new LogInFragment();
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

        binding = FragmentLogInBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLogInNoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameInput = binding.editTextTextPersonName.getText().toString();
                String password = binding.editTextTextPassword.getText().toString();
                String s = "";
                if (usernameInput.equals(s) || password.equals(s)) {
                    Toast.makeText(getContext(), getString(R.string.smth_missing), Toast.LENGTH_SHORT).show();
                } else {

                    boolean validator = false;
                    for (int i = 0; i < DataBase.getUsers(getContext()).size(); i++) {
                        if (DataBase.getUsers(getContext()).get(i).userName.equals(usernameInput) && DataBase.getUsers(getContext()).get(i).password.equals(password)) {
                            DataBase.utilizadorAtivo = DataBase.getUsers(getContext()).get(i);
                            DataBase.saveData(getContext());
                            validator = true;
                        }
                    }
                    if (validator) {
                        Toast.makeText(getContext(), getString(R.string.logged_in), Toast.LENGTH_SHORT).show();
                        NavHostFragment.findNavController(LogInFragment.this).navigate(R.id.action_logInFragment2_to_mainMenuEvents);
                    } else {
                        Toast.makeText(getContext(), getString(R.string.userpasswordincorrect), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

}