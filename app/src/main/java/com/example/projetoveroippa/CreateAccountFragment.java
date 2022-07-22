package com.example.projetoveroippa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projetoveroippa.databinding.FragmentCreateAccountBinding;
import com.example.projetoveroippa.databinding.FragmentLogInBinding;
import com.example.projetoveroippa.object.User;

import java.util.ArrayList;


public class CreateAccountFragment extends Fragment {
    private FragmentCreateAccountBinding binding;


    public CreateAccountFragment() {
        // Required empty public constructor
    }


    public static CreateAccountFragment newInstance(String param1, String param2) {
        CreateAccountFragment fragment = new CreateAccountFragment();
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
        binding = FragmentCreateAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] spinner = new String[]{"What was the name of your first pet?", "What is your favourite Videogame of all time?", "What is your favourite restaurant?"};
        Spinner spinnerSecurityQuestion = binding.SpinnerSecurityQuestion;
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinner);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSecurityQuestion.setAdapter(adapter3);


        binding.buttonCreateAccountNoCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userCreated = binding.editTextTextPersonName2.getText().toString();
                String emailCreated = binding.editTextTextEmailAddress.getText().toString();
                String passwordCreated = binding.editTextTextPassword2.getText().toString();
                String passwordCreatedConfirmed = binding.editTextTextPassword3.getText().toString();
                String securityQuestion = binding.SpinnerSecurityQuestion.getSelectedItem().toString();
                String securityQuestionAnswer = binding.AnswerCA.getText().toString();
                String e = "";
                boolean validator = true;


                if (userCreated.equals(e)) {
                    Toast.makeText(getContext(), "Your Name is missing", Toast.LENGTH_SHORT).show();
                    validator = false;
                }
                if (emailCreated.equals(e)) {
                    Toast.makeText(getContext(), "Your Email is missing", Toast.LENGTH_SHORT).show();
                    validator = false;
                }
                if (passwordCreated.equals(e)) {
                    Toast.makeText(getContext(), "Your password is missing", Toast.LENGTH_SHORT).show();
                    validator = false;
                } else if (passwordCreatedConfirmed.equals(e)) {
                    Toast.makeText(getContext(), "Don't forget to confirm your Password ", Toast.LENGTH_SHORT).show();
                    validator = false;
                } else if (!passwordCreated.equals(passwordCreatedConfirmed)) {
                    Toast.makeText(getContext(), "The Passwords don't match", Toast.LENGTH_SHORT).show();
                    validator = false;
                }

                boolean verifyUser = existingUser(DataBase.getUsers(getContext()), userCreated);
                if (verifyUser) {
                    Toast.makeText(getContext(), "This user already exists", Toast.LENGTH_SHORT).show();
                    validator = false;
                }
                if (validator) {
                    User user = new User();
                    user.userName = userCreated;
                    user.email = emailCreated;
                    user.password = passwordCreated;
                    user.question = securityQuestion;
                    user.answer = securityQuestionAnswer;
                    DataBase.addAndSaveUser(getContext(), user);
                    Toast.makeText(getContext(), "Your account was successfully created. Now log in to make calls into Praesensa", Toast.LENGTH_LONG).show();
                    NavHostFragment.findNavController(CreateAccountFragment.this).navigate(R.id.action_createAccountFragment_to_logInFragment2);
                }


            }
        });
    }

    public boolean existingUser(ArrayList<User> u, String username) {
        for (int i = 0; i < u.size(); i++) {
            if (username.equals(u.get(i).userName)) {
                return true;
            }
        }
        return false;
    }
}