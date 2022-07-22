package com.example.projetoveroippa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projetoveroippa.databinding.FragmentSetNewPasswordBinding;
import com.example.projetoveroippa.object.User;


public class SetNewPasswordFragment extends Fragment {
    private FragmentSetNewPasswordBinding binding;


    public SetNewPasswordFragment() {
        // Required empty public constructor
    }

    public static SetNewPasswordFragment newInstance(String param1, String param2) {
        SetNewPasswordFragment fragment = new SetNewPasswordFragment();
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
        binding = FragmentSetNewPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = binding.editTextTextPasswordFP2.getText().toString();
                String newPasswordConfirmed = binding.editTextTextConfirmPasswordFP.getText().toString();
                    if (newPassword.equals(newPasswordConfirmed)) {
                        DataBase.utilizadorAtivo.password = newPassword;
                        DataBase.utilizadorAtivo=null;
                        DataBase.saveData(getContext());
                        Toast.makeText(getContext(), "Your password was reset", Toast.LENGTH_SHORT).show();
                        NavHostFragment.findNavController(SetNewPasswordFragment.this).navigate(R.id.action_setNewPasswordFragment_to_logInFragment22);
                    }
            }
        });
    }
}