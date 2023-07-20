package am.example.crudapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddUserActivity extends AppCompatActivity {
    ImageButton buttonBack;

    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText phoneNumber;

    Button saveUser;
    private UserDAO userDAO;
    private UserDatabase userDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        userDatabase = UserDatabase.getInstance(this);
        userDAO = userDatabase.userDAO();


        name = findViewById(R.id.userName);
        surname = findViewById(R.id.userSurname);
        email = findViewById(R.id.userEmail);
        phoneNumber = findViewById(R.id.userPhoneNumber);
        saveUser = findViewById(R.id.saveUser);

        saveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString();
                String userSurname = surname.getText().toString();
                String userEmail = email.getText().toString();
                String userPhoneNumber = phoneNumber.getText().toString();
                if (phoneNumber.getText().toString().isEmpty()) {
                    Toast.makeText(AddUserActivity.this, "Please input phone number", Toast.LENGTH_LONG).show();
                }
                if (email.getText().toString().isEmpty()) {
                    Toast.makeText(AddUserActivity.this, "Please input email", Toast.LENGTH_LONG).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    Toast.makeText(AddUserActivity.this, "Please input validate email", Toast.LENGTH_LONG).show();
                } else {

                    User user = new User(0, userName, userSurname, userEmail, userPhoneNumber);
                    userDAO.saveUser(user);
                    Toast.makeText(AddUserActivity.this, "User saved", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}