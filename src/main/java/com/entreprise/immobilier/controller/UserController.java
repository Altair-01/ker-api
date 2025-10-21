package com.entreprise.immobilier.controller;

import com.entreprise.immobilier.dto.UserDTO;
import com.entreprise.immobilier.model.User;
import com.entreprise.immobilier.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** 🔓 Accessible uniquement aux administrateurs */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<UserDTO> searchByEmailOrPhone(
            @RequestParam(required = false) String email,
            @RequestParam(required = false, name = "phone") String phoneNumber) {

        return userService.findByEmailOrPhone(email, phoneNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Recherche stricte par email (admin ou l'utilisateur lui-même) */
    @PreAuthorize("hasRole('ADMIN') or #email == authentication.name")
    @GetMapping("/by-email/{email}")
    public ResponseEntity<UserDTO> getByEmail(@PathVariable String email) {
        return userService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Recherche stricte par téléphone (réservé admin par défaut) */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-phone/{phone}")
    public ResponseEntity<UserDTO> getByPhone(@PathVariable String phone) {
        return userService.findByPhone(phone)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
