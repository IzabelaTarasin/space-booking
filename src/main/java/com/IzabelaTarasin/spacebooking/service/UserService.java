package com.IzabelaTarasin.spacebooking.service;

import com.IzabelaTarasin.spacebooking.error.ConflictException;
import com.IzabelaTarasin.spacebooking.error.FieldError;
import com.IzabelaTarasin.spacebooking.error.NotFoundException;
import com.IzabelaTarasin.spacebooking.model.User;
import com.IzabelaTarasin.spacebooking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUserById(UUID id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND",
                        "Nie znaleziono użytkownika o id:" + id));
    }

    public User createUser(User user){
        List<FieldError> fieldErrors = new ArrayList<>();

        if (userRepository.existsByEmail(user.getEmail())) {
            fieldErrors.add(new FieldError("email", "Ten adres e-mail jest już zajęty"));
        }
        if (userRepository.existsByTelephoneNumber(user.getTelephoneNumber())) {
            fieldErrors.add(new FieldError("telephoneNumber", "Ten numer telefonu jest już zajęty"));
        }
        if (!fieldErrors.isEmpty()) {
            throw new ConflictException(
                    "USER_CONTACT_TAKEN",
                    "Podane dane kontaktowe są już użyte",
                    fieldErrors
            );
        }
        user.setId(null); //jeśli ktoś wyśle id w JSON-ie, i tak zawsze robił się insert i wygenerowało się nowe UUID
        //bez tego gdy wysle w post users id to mam bla 500 error
        return userRepository.save(user);
    }

    public User update(UUID id, User user){
        User existingUser = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("USER_NOT_FOUND", "Nie znaleziono użytkownika o id: " + id);
        });
        existingUser.setFirstName(user.getFirstName());  //alternatywnie @DynamicUpdate
        existingUser.setLastName(user.getLastName());
        existingUser.setTelephoneNumber(user.getTelephoneNumber());

        return userRepository.save(existingUser);
    }

    public void delete (UUID id){
        if(!userRepository.existsById(id)){
            throw new NotFoundException("USER_NOT_FOUND", "Nie można usunąć użytkownika");
        }
        userRepository.deleteById(id);
    }
}
