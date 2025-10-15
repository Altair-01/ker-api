package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.UserDTO;
import com.entreprise.immobilier.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserById(Long id);
    User createUser(UserDTO userDTO);
    User updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
}
