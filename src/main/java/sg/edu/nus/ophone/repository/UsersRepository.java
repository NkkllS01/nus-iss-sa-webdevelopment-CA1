package sg.edu.nus.ophone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.ophone.model.Users;

import java.util.List;

//code by Team3.Cynthia Peh
public interface UsersRepository extends JpaRepository<Users, Integer> {

  @Query("SELECT u FROM Users u WHERE u.id = :id")
  List<Users> findByUserId(@Param("id") String id);

  @Query("SELECT u FROM Users u WHERE u.email = :email")
  List<Users> findByUserEmail(@Param("email") String email);

  @Query("SELECT u FROM Users u WHERE u.userType =:userType")
  List<Users> findByUserType(String userType);
}
