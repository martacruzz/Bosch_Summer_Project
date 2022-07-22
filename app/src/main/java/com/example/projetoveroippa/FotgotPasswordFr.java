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

import com.example.projetoveroippa.databinding.FragmentCallCenterBinding;
import com.example.projetoveroippa.databinding.FragmentFotgotPasswordBinding;
import com.example.projetoveroippa.object.User;

import java.util.ArrayList;


public class FotgotPasswordFr extends Fragment {
    private FragmentFotgotPasswordBinding binding;


    public FotgotPasswordFr() {
        // Required empty public constructor
    }

    public static FotgotPasswordFr newInstance(String param1, String param2) {
        FotgotPasswordFr fragment = new FotgotPasswordFr();
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
        binding = FragmentFotgotPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String editUserName = binding.EditUsername.getText().toString();
        binding.buttonForgotInForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editUserName = binding.EditUsername.getText().toString();
                ArrayList<User> users = DataBase.getUsers(getContext());
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).userName.equals(editUserName)) {
                        binding.textViewSecurityQuestionFP.setVisibility(View.VISIBLE);
                        binding.textViewSecurityQuestionFP.setText(DataBase.getUsers(getContext()).get(i).question);
                        binding.EditSecurityQuestion.setVisibility(View.VISIBLE);
                        binding.editAnswer.setVisibility(View.VISIBLE);
                        binding.editSecurityQuestionAnswer.setVisibility(View.VISIBLE);
                        /*if (binding.editAnswer.getText().toString().equals(DataBase.getUsers(getContext()).get(i).answer)){
                            binding.editTextTextPasswordFP.setVisibility(View.VISIBLE);
                            binding.newPasswordFP.setVisibility(View.VISIBLE);
                        }*/
                        binding.buttonDoneFP.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        binding.buttonDoneFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validator=false;
                for (int i = 0; i < DataBase.getUsers(getContext()).size(); i++) {
                    if (binding.editAnswer.getText().toString().equals(DataBase.getUsers(getContext()).get(i).answer)) {
                        DataBase.utilizadorAtivo=DataBase.getUsers(getContext()).get(i);
                        NavHostFragment.findNavController(FotgotPasswordFr.this).navigate(R.id.action_fotgotPasswordFr_to_setNewPasswordFragment);
                        validator=true;
                        break;
                    }
                }
                if (!validator){
                    Toast.makeText(getContext(), "Wrong answer", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
