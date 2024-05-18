package com.demodayapi.repositories;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.demodayapi.models.User;
import com.demodayapi.enums.UserStatusEnum;


@Repository
public interface UserRepository extends CrudRepository<User, String>{

    List<User> findAll();
    User findByCpf(String cpf);
    User findByEmail(String email);
    List<User> findByStatus(UserStatusEnum status);
}
