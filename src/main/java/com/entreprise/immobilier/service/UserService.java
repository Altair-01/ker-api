package com.entreprise.immobilier.service;

import com.entreprise.immobilier.dto.UserDTO;
import com.entreprise.immobilier.model.User;
import com.entreprise.immobilier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(UserDTO dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // (tu peux plus tard hasher ici)
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole(dto.getRole());
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserDTO dto) {
        return userRepository.findById(id).map(user -> {
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword());
            user.setPhoneNumber(dto.getPhoneNumber());
            user.setRole(dto.getRole());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec ID : " + id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
