package am.example.crudapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Optional;

@Dao
public interface UserDAO {

    @Insert
    void saveUser(User user);

    @Update
    void updateUser(User user);

    @Query(value = "DELETE from USER WHERE id=:id")
    void deleteUser(int id);

    @Query(value = "SELECT * FROM user")
    List<User> findAll();

    @Query(value = "SELECT * FROM user WHERE id IN (:userId)")
    Optional<User> findUserById(long userId);
    @Query(value = "SELECT * FROM user WHERE name=:name")
    List<User> findAllByName(String name);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllContacts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

}
