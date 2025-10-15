package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.UserDTO;
import com.entreprise.immobilier.mapper.UserMapper;
import com.entreprise.immobilier.model.User;
import com.entreprise.immobilier.repository.UserRepository;
import com.entreprise.immobilier.service.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO);
    }

    @Override
    public User createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé."));

        existing.setFirstName(userDTO.getFirstName());
        existing.setLastName(userDTO.getLastName());
        existing.setEmail(userDTO.getEmail());
        existing.setPhoneNumber(userDTO.getPhoneNumber());
        existing.setRole(userDTO.getRole());
        existing.setEnabled(userDTO.isEnabled());

        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Utilisateur non trouvé.");
        }
        userRepository.deleteById(id);
    }
}
