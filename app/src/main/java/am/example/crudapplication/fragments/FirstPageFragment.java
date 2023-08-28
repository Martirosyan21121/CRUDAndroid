package am.example.crudapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import am.example.crudapplication.R;
import am.example.crudapplication.RestApi;
import am.example.crudapplication.User;
import am.example.crudapplication.UserAdapter;
import am.example.crudapplication.UserAdapterListener;
import am.example.crudapplication.UserDAO;
import am.example.crudapplication.UserDatabase;

public class FirstPageFragment extends Fragment implements UserAdapterListener {

    private List<User> userList;
    private UserAdapter userAdapter;
    private UserDAO userDAO;

    private SearchView searchView;

    public FirstPageFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_page, container, false);

        Button add = view.findViewById(R.id.addButton);
        add.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_firstPageFragment2_to_addUserFragment2));

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        UserDatabase userDatabase = UserDatabase.getInstance(requireContext());
        userDAO = userDatabase.userDAO();
        userList = userDAO.findAll();
        userAdapter = new UserAdapter(userList, this);
        recyclerView.setAdapter(userAdapter);

        searchView = view.findViewById(R.id.search);
        setupSearchView();

        RestApi restApi = new RestApi();
        restApi.loadContacts();
        return view;
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterData(s);
                return true;
            }
        });
    }

    private void filterData(String query) {
        List<User> filteredList = new ArrayList<>();
        for (User u : userList) {
            if (u.getName().toLowerCase().contains(query.toLowerCase())) {
                if (u.getImage() == null) {
                    u.setImage("res/drawable/baseline_account_circle_24.xml".getBytes());
                }
                filteredList.add(u);
            }
        }
        userAdapter.setFilteredList(filteredList);
    }

    @Override
    public void deleteUser(int id, int position) {
        userDAO.deleteUser(id);
        userAdapter.deleteUserPosition(position);
    }

    @Override
    public void updateUser(User user) {
        Optional<User> optionalUser = userDAO.findUserById(user.getId());
        if (optionalUser.isPresent()) {
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

}