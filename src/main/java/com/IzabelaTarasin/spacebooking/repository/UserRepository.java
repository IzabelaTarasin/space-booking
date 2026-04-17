package com.IzabelaTarasin.spacebooking.repository;

import com.IzabelaTarasin.spacebooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    //Do zarządzania danymi pasażerów, ich profilami i logowaniem.

    Optional<User> findByEmail(String email);  //nie lista bo email ma byc unique
    List<User> findByLastName(String lastName);
    boolean existsByTelephoneNumber(String telephoneNumber);
    boolean existsByEmail(String email);

}
