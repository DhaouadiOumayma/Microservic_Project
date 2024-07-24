package com.example.club.Controllers;

import com.example.club.Models.Cours;
import com.example.club.dtos.CoursDto;
import com.example.club.Services.CoursService;
import com.example.club.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cours")
public class ClubController {

    @Autowired
    private CoursService clubService;



    @GetMapping
    public List<CoursDto> getAllClubs() {
        return clubService.getAllClubs().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }



    @GetMapping("/{id}")
    public CoursDto getClubById(@PathVariable Long id) {
        Cours club = clubService.getClubById(id);
        return convertToDto(club);
    }

    @PostMapping("/add")
    public CoursDto createClub(@RequestBody CoursDto clubDto) {
        Cours club = convertToEntity(clubDto);
        Cours savedClub = clubService.createClub(club);
        return convertToDto(savedClub);
    }

    @PutMapping("/{id}")
    public CoursDto updateClub(@PathVariable Long id, @RequestBody CoursDto clubDto) {
        Cours club = convertToEntity(clubDto);
        Cours updatedClub = clubService.updateClub(id, club);
        return convertToDto(updatedClub);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        clubService.deleteClub(id);
        return ResponseEntity.noContent().build();
    }

    // Méthode pour convertir Club en ClubDto
    private CoursDto convertToDto(Cours club) {
        CoursDto clubDto = new CoursDto();
        clubDto.setId(club.getId());
        clubDto.setNom(club.getNom());
        clubDto.setTypeClub(club.getTypeClub());
        clubDto.setDescription(club.getDescription());
        // Ajoutez les autres propriétés nécessaires
        return clubDto;
    }

    // Méthode pour convertir ClubDto en Club
    private Cours convertToEntity(CoursDto clubDto) {
        Cours club = new Cours();
        club.setId(clubDto.getId());
        club.setNom(clubDto.getNom());
        club.setTypeClub(clubDto.getTypeClub());
        club.setDescription(clubDto.getDescription());
        // Ajoutez les autres propriétés nécessaires
        return club;
    }

    // Avec RestTemplate
    @PostMapping("/users/add")
    public UserDto createUser(@RequestBody UserDto userDto) {
        return clubService.createUser(userDto);
    }

    //Avec OpenFeign
    @GetMapping("/user/{userId}")
    public UserDto getUser(@PathVariable String userId) {
        return clubService.getUserById(userId);
    }

   /* @PostMapping("/sendmessage")
    public ResponseEntity<String> sendMessageToKafka(@RequestBody String message) {
        clubService.sendMessage(message);
        return ResponseEntity.ok("Message sent to Kafka: " + message);
    }*/
}
