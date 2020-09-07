package com.tiger.quanlylop.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tiger.quanlylop.Activity.LoginActivity;
import com.tiger.quanlylop.DAO.UsersDAO;
import com.tiger.quanlylop.DTO.UsersDTO;
import com.tiger.quanlylop.Library.MD5Hash;
import com.tiger.quanlylop.R;

public class AccountFragment extends Fragment {

    private long ID;
    private String displayName;
    AppCompatEditText editdisplayName;
    AppCompatEditText editUserName;
    AppCompatEditText editPassword;
    AppCompatButton btnUpdateAccount;

    public AccountFragment(long id, String displayName) {
        // Required empty public constructor
        this.ID = id;
        this.displayName = displayName;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);

        AnhXa(view);

        editPassword.setOnTouchListener(editPasswordTouch);
        editdisplayName.setText(displayName);

        btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String displayName = editdisplayName.getText().toString();
                String userName = editUserName.getText().toString();
                String password = editPassword.getText().toString();

                if (displayName.isEmpty() || userName.isEmpty() || password.isEmpty()) return;

                UsersDTO usersDTO = new UsersDTO(ID, displayName, userName, MD5Hash.getMd5(password));
                if (UsersDAO.getInstance().updateUser(getContext(), usersDTO))
                    Toast.makeText(getContext(), "Đã cập nhật thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                getContext().startActivity(intent);
            }
        });

        return view;
    }

    private void AnhXa(View view) {
        editdisplayName = view.findViewById(R.id.editdisplayName);
        editUserName = view.findViewById(R.id.editUserName);
        editPassword = view.findViewById(R.id.editPassword);
        btnUpdateAccount = view.findViewById(R.id.btnUpdateAccount);
    }

    View.OnTouchListener editPasswordTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (editPassword.getRight() - editPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                    // your action here
                    if (editPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                        editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        editPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_key, 0, R.drawable.ic_remove_eye , 0);

                    }else{
                        editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        editPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_key, 0, R.drawable.ic_eye , 0);
                    }
                }
            }
            return false;
        }
    };
}