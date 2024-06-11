package com.Application.GestionDesTransferts.Repositories;
import com.Application.GestionDesTransferts.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail (String email);
}
