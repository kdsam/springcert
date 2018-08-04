package com.ps.repos;

import com.ps.ents.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by iuliana.cosmina on 2/23/16.
 */
//44. Complete the definition of this interface to make the tests in TestUserRepo.java pass.
@Transactional(readOnly = true)
public interface UserRepo extends JpaRepository<User, Long> {


    @Query("select u from User u where u.username like %?1%")
    List<User> findAllByUsername(String username);

    User findOneByUsername(@Param("un") String username);

    String findUsernameById(Long id);

    @Query("select count(u)from User u")
    long countUsers();

}