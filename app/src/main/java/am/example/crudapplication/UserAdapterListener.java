package am.example.crudapplication;

public interface UserAdapterListener {
    void deleteUser(int id, int position);

    void updateUser(User user);
}
