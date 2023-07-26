package am.example.crudapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

import am.example.crudapplication.R;
import am.example.crudapplication.User;
import am.example.crudapplication.UserDAO;
import am.example.crudapplication.UserDatabase;

public class UpdateUserFragment extends Fragment {

    private EditText nameUpdate;
    private EditText emailUpdate;
    private EditText surnameUpdate;
    private EditText phoneNumberUpdate;
    private UserDAO userDAO;
    private UserDatabase userDatabase;

    Button updateButton;

    public UpdateUserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_update_fragment, container, false);

        nameUpdate = view.findViewById(R.id.updateUserName);
        emailUpdate = view.findViewById(R.id.updateUserEmail);
        surnameUpdate = view.findViewById(R.id.updateUserSurname);
        phoneNumberUpdate = view.findViewById(R.id.updateUserPhoneNumber);

        AtomicInteger userId = new AtomicInteger();
        getParentFragmentManager().setFragmentResultListener("userData", this, (requestKey, result) -> {

            String name = result.getString("userName");
            String email = result.getString("userEmail");
            String surname = result.getString("userSurname");
            String phoneNumber = result.getString("userPhoneNumber");
            userId.set(result.getInt("userId"));
            nameUpdate.setText(name);
            emailUpdate.setText(email);
            surnameUpdate.setText(surname);
            phoneNumberUpdate.setText(phoneNumber);
        });


        userDatabase = UserDatabase.getInstance(getContext());
        userDAO = userDatabase.userDAO();

        updateButton = view.findViewById(R.id.updateUser);
        updateButton.setOnClickListener(view1 -> {
            String userName = nameUpdate.getText().toString();
            String userSurname = surnameUpdate.getText().toString();
            String userEmail = emailUpdate.getText().toString();
            String userPhoneNumber = phoneNumberUpdate.getText().toString();
            if (userEmail.isEmpty()) {
                Toast.makeText(getContext(), "Please input email", Toast.LENGTH_LONG).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                Toast.makeText(getContext(), "Please input validate email", Toast.LENGTH_LONG).show();
            }else if (userPhoneNumber.isEmpty()) {
                Toast.makeText(getContext(), "Please input phone number", Toast.LENGTH_LONG).show();
            } else {
                User userUpdate = new User(userId.get(), userName, userSurname, userEmail, userPhoneNumber);
                userDAO.updateUser(userUpdate);
                Navigation.findNavController(view).navigate(R.id.firstPageFragment2);
                Toast.makeText(getContext(), "User updated", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}