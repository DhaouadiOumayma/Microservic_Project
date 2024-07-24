package com.example.user.services;

import com.example.user.dtaos.UserDto;
import com.example.user.models.User;
import com.example.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

/*    @KafkaListener(topics = "haha", groupId = "2")
    public void consume(String message) {
        System.out.println("Received message from Kafka: " + message);
        // Process the message
    }*/

    public UserDto convertToDTO(User user) {
        UserDto userDTO = new UserDto();
        userDTO.setId(user.getId());
        userDTO.setNom(user.getNom());
        userDTO.setPrenom(user.getPrenom());
        user.setStatus(userDTO.getStatus());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return userDTO;
    }

    public User convertToEntity(UserDto userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setNom(userDTO.getNom());
        user.setPrenom(userDTO.getPrenom());
        user.setStatus(userDTO.getStatus());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    public UserDto createUser(UserDto userDTO) {
        User user = convertToEntity(userDTO);
        user = userRepository.save(user);
        return convertToDTO(user);
    }
}
