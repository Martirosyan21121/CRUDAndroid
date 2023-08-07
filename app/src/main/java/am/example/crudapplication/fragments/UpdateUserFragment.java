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
    private static final int SELECT_PICTURE = 1;
    private ImageView imageView;

    private byte[] image;
    Button selectImage;

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
        imageView = view.findViewById(R.id.imageViewUpdate);
        selectImage = view.findViewById(R.id.btnSelectImageUpdate);

        AtomicInteger userId = new AtomicInteger();
        getParentFragmentManager().setFragmentResultListener("userData", this, (requestKey, result) -> {

            String name = result.getString("userName");
            String email = result.getString("userEmail");
            String surname = result.getString("userSurname");
            String phoneNumber = result.getString("userPhoneNumber");
//            byte[] image = result.getByteArray("userImage");
            userId.set(result.getInt("userId"));
            nameUpdate.setText(name);
            emailUpdate.setText(email);
            surnameUpdate.setText(surname);
            phoneNumberUpdate.setText(phoneNumber);
//            imageView.setImageBitmap(image);
        });


        userDatabase = UserDatabase.getInstance(getContext());
        userDAO = userDatabase.userDAO();
        imageView = view.findViewById(R.id.imageViewUpdate);
        selectImage.setOnClickListener(view1 -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, SELECT_PICTURE);
        });

        updateButton = view.findViewById(R.id.updateUser);
        updateButton.setOnClickListener(view1 -> {
            String userName = nameUpdate.getText().toString().replace(" ", "");
            String userSurname = surnameUpdate.getText().toString();
            String userEmail = emailUpdate.getText().toString();
            String userPhoneNumber = phoneNumberUpdate.getText().toString();
            if (userEmail.isEmpty()) {
                Toast.makeText(getContext(), "Please input email", Toast.LENGTH_LONG).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                Toast.makeText(getContext(), "Please input validate email", Toast.LENGTH_LONG).show();
            } else if (userPhoneNumber.isEmpty()) {
                Toast.makeText(getContext(), "Please input phone number", Toast.LENGTH_LONG).show();
            } else if (image == null) {
                User user = new User(userId.get(), userName, userSurname, userEmail, userPhoneNumber, null);
                userDAO.updateUser(user);
                Toast.makeText(getContext(), "User updated", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.firstPageFragment2);
            } else {
                User userUpdate = new User(userId.get(), userName, userSurname, userEmail, userPhoneNumber, image);
                userDAO.updateUser(userUpdate);
                Navigation.findNavController(view).navigate(R.id.firstPageFragment2);
                Toast.makeText(getContext(), "User updated", Toast.LENGTH_LONG).show();
            }
        });

        ImageButton backToAddPage = view.findViewById(R.id.updateBtn);
        backToAddPage.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_updateUserFragment2_to_firstPageFragment2);
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
                copyBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                image = stream.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            image = null;
        }
    }
}