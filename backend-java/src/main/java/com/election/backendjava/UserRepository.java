package com.election.backendjava;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // methoden toevoegen zoals findByUsername, findByEmail, etc.
    User findByUsername(String username);
    User findByEmail(String email);
}
