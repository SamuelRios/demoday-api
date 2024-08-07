package com.demodayapi.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.demodayapi.models.User;
import com.demodayapi.enums.UserStatusEnum;
import com.demodayapi.enums.UserTypeEnum;


@Repository
public interface UserRepository extends CrudRepository<User, String>{

    List<User> findAll();
    User findByCpf(String cpf);
    User findByEmail(String email);
    List<User> findByStatus(UserStatusEnum status);
    List<User> findByType(UserTypeEnum type);

    @Query("SELECT xu FROM User xu WHERE xu.type = :type ORDER BY xu.name ASC ")
    List<User> listOfTypeUser(@Param("type") UserTypeEnum type);

}
