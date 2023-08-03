package am.example.crudapplication;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;

    private UserAdapterListener adapterListener;


    public void deleteUserPosition(int position) {
        userList.remove(position);
    }

    public UserAdapter(List<User> userList, UserAdapterListener userAdapterListener) {
        this.adapterListener = userAdapterListener;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.textViewName.setText(user.getName());
        holder.textViewEmail.setText(user.getEmail());
        holder.textViewSurname.setText(user.getSurname());
        holder.textViewPhoneNumber.setText(user.getPhoneNumber());
        holder.imageView.setImageURI(Uri.parse(user.getImage()));
        holder.delete.setOnClickListener(view -> {
            adapterListener.deleteUser(user.getId(), position);
            Toast.makeText(view.getContext(), "User deleted", Toast.LENGTH_LONG).show();
            Navigation.findNavController(view).navigate(R.id.firstPageFragment2);
        });

        holder.update.setOnClickListener(view -> {
            holder.textViewName.setText(user.getName());
            holder.textViewEmail.setText(user.getEmail());
            holder.textViewSurname.setText(user.getSurname());
            holder.textViewPhoneNumber.setText(user.getPhoneNumber());
            holder.imageView.setImageURI(Uri.parse(user.getImage()));
            adapterListener.updateUser(user);
            Navigation.findNavController(view).navigate(R.id.updateUserFragment2);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewEmail;
        TextView textViewSurname;
        TextView textViewPhoneNumber;

        ImageView imageView;

        ImageView delete;
        ImageView update;

        UserViewHolder(View view) {
            super(view);
            textViewName = view.findViewById(R.id.textUserName);
            textViewEmail = view.findViewById(R.id.textUserEmail);
            textViewSurname = view.findViewById(R.id.textUserSurname);
            textViewPhoneNumber = view.findViewById(R.id.textUserPhoneNumber);
            imageView = view.findViewById(R.id.imageViewToFirstPage);
            delete = view.findViewById(R.id.delete);
            update = view.findViewById(R.id.update);
        }
    }
}
