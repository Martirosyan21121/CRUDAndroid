package am.example.crudapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Optional;

import am.example.crudapplication.R;
import am.example.crudapplication.User;
import am.example.crudapplication.UserAdapter;
import am.example.crudapplication.UserAdapterListener;
import am.example.crudapplication.UserDAO;
import am.example.crudapplication.UserDatabase;

public class FirstPageFragment extends Fragment implements UserAdapterListener {

    private UserDatabase userDatabase;

    private List<User> userList;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    Button searchButton;
    private UserDAO userDAO;

    public FirstPageFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_page, container, false);

        Button add = view.findViewById(R.id.addButton);
        add.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_firstPageFragment2_to_addUserFragment2));

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userDatabase = UserDatabase.getInstance(requireContext());
        userDAO = userDatabase.userDAO();
        userList = userDAO.findAll();

        userAdapter = new UserAdapter(userList, this);
        recyclerView.setAdapter(userAdapter);


        searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(view12 -> {
            EditText search = view.findViewById(R.id.search);
            String searchName = search.getText().toString();
            if (searchName.isEmpty()) {
                Toast.makeText(getContext(), "Please input name", Toast.LENGTH_LONG).show();
            } else if (userDAO.findAllByName(searchName).isEmpty()) {
                Toast.makeText(getContext(), "Name dose not exist", Toast.LENGTH_LONG).show();
            } else {
                userList = userDAO.findAllByName(searchName);
                userAdapter = new UserAdapter(userList, this);
                recyclerView.setAdapter(userAdapter);
                Toast.makeText(getContext(), "Search successfully done", Toast.LENGTH_LONG).show();
            }
        });

        ImageButton homeButton = view.findViewById(R.id.homeButton);
        homeButton.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.firstPageFragment2);
        });
        return view;
    }

    @Override
    public void deleteUser(int id, int position) {
        userDAO.deleteUser(id);
        userAdapter.deleteUserPosition(position);
    }

    @Override
    public void updateUser(User user) {
        Optional<User> optionalUser = userDAO.findUserById(user.getId());
        User user1 = optionalUser.get();
        Bundle bundle = new Bundle();
        bundle.putInt("userId", user1.getId());
        bundle.putString("userName", user1.getName());
        bundle.putString("userEmail", user1.getEmail());
        bundle.putString("userSurname", user1.getSurname());
        bundle.putString("userPhoneNumber", user1.getPhoneNumber());
        getParentFragmentManager().setFragmentResult("userData", bundle);
    }
}