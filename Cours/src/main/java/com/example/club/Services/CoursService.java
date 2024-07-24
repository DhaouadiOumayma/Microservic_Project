package com.example.club.Services;

import com.example.club.FeignClient.UserClient;
import com.example.club.Models.Cours;
import com.example.club.Repositories.CoursRepository;
import com.example.club.dtos.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CoursService {

    @Autowired
    private CoursRepository clubRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

  /*  public void sendMessage(String message) {
        kafkaTemplate.send("haha", message);
    }*/


    private static final Logger logger = LoggerFactory.getLogger(CoursService.class);

    // Utilisation de RestTemplate pour récupérer une ressource
    public String getResourceFromOtherService() {
        String url = "http://localhost:9090/api/users";
        return restTemplate.getForObject(url, String.class);
    }

    // Utilisation de RestTemplate pour créer un utilisateur

    public UserDto createUser(UserDto userDto) {
        String url = "http://localhost:9090/api/users/add";
        ResponseEntity<UserDto> response = restTemplate.postForEntity(url, userDto, UserDto.class);
        return response.getBody();
    }



    // Utilisation de FeignClient pour récupérer un utilisateur par ID

    //@CircuitBreaker(name = "stockService", fallbackMethod = "handleGetStockFailure")
    public UserDto getUserById(String userId) {
        return userClient.getUserById(userId);
    }

    @Recover
    public UserDto handleGetStockFailure(String userId, Exception e) {
        logger.error("Failed to fetch user with userId: {}", userId, e);
        return new UserDto(); // Retourne une valeur par défaut en cas d'erreur
    }

    public List<Cours> getAllClubs() {
        return clubRepository.findAll();
    }

    public Cours getClubById(Long id) {
        return clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club not found"));
    }

    public Cours createClub(Cours club) {
        return clubRepository.save(club);
    }

    public Cours updateClub(Long id, Cours updatedClub) {
        Cours existingClub = getClubById(id);
        // Update fields as necessary
        return clubRepository.save(existingClub);
    }

    public void deleteClub(Long id) {
        clubRepository.deleteById(id);
    }
}
