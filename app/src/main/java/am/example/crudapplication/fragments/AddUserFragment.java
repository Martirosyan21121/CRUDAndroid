package am.example.crudapplication.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

import am.example.crudapplication.MainActivity;
import am.example.crudapplication.R;
import am.example.crudapplication.User;
import am.example.crudapplication.UserDAO;
import am.example.crudapplication.UserDatabase;


public class AddUserFragment extends Fragment {

    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText phoneNumber;


    private ImageView imageView;
    Button selectImage;

    Button saveUser;

    private UserDAO userDAO;
    private UserDatabase userDatabase;

    public AddUserFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_user_fragment, container, false);

        name = view.findViewById(R.id.userName);
        surname = view.findViewById(R.id.userSurname);
        email = view.findViewById(R.id.userEmail);
        phoneNumber = view.findViewById(R.id.userPhoneNumber);
        imageView = view.findViewById(R.id.imageView);
        selectImage = view.findViewById(R.id.btnSelectImage);

        userDatabase = UserDatabase.getInstance(getContext());
        userDAO = userDatabase.userDAO();

        imageView = view.findViewById(R.id.imageView);
        selectImage.setOnClickListener(view1 -> {

        });

        saveUser = view.findViewById(R.id.saveUser);
        saveUser.setOnClickListener(view1 -> {
            String userName = name.getText().toString().replace(" ", "");
            String userSurname = surname.getText().toString();
            String userEmail = email.getText().toString();
            String userPhoneNumber = phoneNumber.getText().toString();
            if (userEmail.isEmpty()) {
                Toast.makeText(getContext(), "Please input email", Toast.LENGTH_LONG).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                Toast.makeText(getContext(), "Please input validate email", Toast.LENGTH_LONG).show();
            } else if (userPhoneNumber.isEmpty()) {
                Toast.makeText(getContext(), "Please input phone number", Toast.LENGTH_LONG).show();
            } else {
                User user = new User(0, userName, userSurname, userEmail, userPhoneNumber);
                userDAO.saveUser(user);
                Toast.makeText(getContext(), "User saved", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_addUserFragment2_to_firstPageFragment2);
            }
        });

        ImageButton backButton = view.findViewById(R.id.backBtn);
        backButton.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_addUserFragment2_to_firstPageFragment2);
        });
        return view;
    }

}