package com.example.projetoveroippa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.ContactsContract;
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
    private User forgotPassUser;

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
                boolean verifier = false;
                String editUserName = binding.EditUsername.getText().toString();
                ArrayList<User> users = DataBase.getUsers(getContext());
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).userName.equals(editUserName)) {
                        forgotPassUser= users.get(i);
                        binding.textViewSecurityQuestionFP.setVisibility(View.VISIBLE);
                        binding.textViewSecurityQuestionFP.setText(DataBase.getUsers(getContext()).get(i).question);
                        binding.EditSecurityQuestion.setVisibility(View.VISIBLE);
                        binding.editAnswer.setVisibility(View.VISIBLE);
                        binding.editSecurityQuestionAnswer.setVisibility(View.VISIBLE);
                        binding.buttonDoneFP.setVisibility(View.VISIBLE);
                        verifier = true;
                    }
                }
                    if (!verifier){
                        Toast.makeText(getContext(), getString(R.string.user_not_found), Toast.LENGTH_SHORT).show();
                    }
            }
        });
        binding.buttonDoneFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e = "";
                boolean validator = false;

                    if (binding.editAnswer.getText().toString().equals(forgotPassUser.answer)) {
                        DataBase.utilizadorAtivo = forgotPassUser;
                        //NavHostFragment.findNavController(FotgotPasswordFr.this).navigate(R.id.action_fotgotPasswordFr_to_setNewPasswordFragment);
                        validator = true;
                    }

                if (validator) {
                    NavHostFragment.findNavController(FotgotPasswordFr.this).navigate(R.id.action_fotgotPasswordFr_to_setNewPasswordFragment);

                }
                if (!validator) {
                    Toast.makeText(getContext(), getString(R.string.wrong_answer), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
