package com.example.UserServices.User;

import com.example.UserServices.User.Exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@RestController
public class UserController  {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @RequestMapping(value="/user/all", method= RequestMethod.GET)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(value="/user/count", method= RequestMethod.GET)
    public Long getUser() {
        return userRepository.count();
    }

    @RequestMapping(value="/user", method= RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody User userRecord)
    {

        if (!userRepository.findByEmail(userRecord.getEmail()).isPresent()){
            userRepository.save(userRecord);
            return new ResponseEntity<>("The user was created successfully.", HttpStatus.CREATED);
        }
        else {
            throw new ResourceAlreadyExistException("This user already exist");
        }

    }

    @RequestMapping(value="/user/{email}", method= RequestMethod.PUT)
    public ResponseEntity<?>  updateUser(@RequestBody User userRecord, @PathVariable String email ) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found with email: " + email));
        user.setEmail(userRecord.getEmail());
        return new ResponseEntity<>("The user was updated successfully.", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{email}")
    public User getUsers(@PathVariable String email) {

        return userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found with email: " + email));

    }

    @RequestMapping(value="/user/{email}", method= RequestMethod.DELETE)
    public void deleteUser(@PathVariable String email) {

        List<User> list  = userRepository.findAll();

        list.stream().filter(user -> user.getEmail().equals(email)).findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("User not found with email: " + email));


        list.stream().filter(user -> user.getEmail().equals(email)).forEachOrdered(userRepository::delete);

    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String testExceptionHandling(@RequestParam int code) {
        switch (code) {
            case 401:
                throw new UnauthorizedException("You are not authorized");
            case 404:
                throw new ResourceNotFoundException("Requested resource is not found.");
            case 400:
                throw new BadRequestException("Please provide resource id.");
            case 409:
                throw new ResourceAlreadyExistException("Resource already exists in DB.");
            default:
                return "No Exception.";

        }
    }


}
