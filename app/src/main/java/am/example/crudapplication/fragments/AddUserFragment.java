package am.example.crudapplication.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import am.example.crudapplication.R;
import am.example.crudapplication.User;
import am.example.crudapplication.UserDAO;
import am.example.crudapplication.UserDatabase;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class AddUserFragment extends Fragment {

    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText phoneNumber;

    private static final int SELECT_PICTURE = 1;
    private ImageView imageView;
    private byte[] image;
    ImageButton selectImage;
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
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, SELECT_PICTURE);
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
            } else if (userPhoneNumber.length() < 6) {
                Toast.makeText(getContext(), "Phone number need to have minimum 6 characters", Toast.LENGTH_SHORT).show();
            } else if (image == null) {
                User user = new User(0, userName, userSurname, userEmail, userPhoneNumber, null);
                userDAO.saveUser(user);
                Toast.makeText(getContext(), "User saved", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_addUserFragment2_to_firstPageFragment2);
            } else {
                User user = new User(0, userName, userSurname, userEmail, userPhoneNumber, image);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);
            try {
                Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), selectedImageUri);
                Bitmap copyBitmap = originalBitmap.copy(originalBitmap.getConfig(), true);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                copyBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                image = stream.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            image = null;
        }
    }



}


