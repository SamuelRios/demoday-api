package com.demodayapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.demodayapi.models.CommitteeUser;
import com.demodayapi.models.User;
import jakarta.transaction.Transactional;

@Repository
public interface CommitteeUserRepository extends CrudRepository<CommitteeUser, Integer> {
  List<CommitteeUser> findAll();

  

  @Modifying
  @Transactional

  @Query("SELECT xu FROM CommitteeUser xu WHERE xu.committee.id = :demoday_id")
  List<CommitteeUser> listUsersActiveCommittee(@Param("demoday_id") int demoday_id);

  @Modifying
  @Transactional
  @Query("DELETE FROM CommitteeUser u WHERE u.committee.id = :demoday_id")
  void deleteAllCommitteeUsers(@Param("demoday_id") int demoday_id);

  @Modifying
  @Transactional
  @Query("DELETE FROM CommitteeUser u WHERE u.user.id = :id")
  void deleteByIdUser(@Param("id") String id);


}
