package com.example.UserServices.User;

import com.example.UserServices.User.Exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

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
    public void addUser(@RequestBody User userRecord)
    {
         userRepository.save(userRecord);
    }

    @RequestMapping(value="/user/{email}", method= RequestMethod.PUT)
    public String updateUser(@RequestBody User userRecord, @PathVariable String email ) {
        Integer id = userRecord.getId();
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with email: " + email));

        user.setEmail(userRecord.getEmail());
        return "The user was updated successfully." + HttpStatus.OK;

    }

    @GetMapping("/user/{email}")
    public User getUsers(@PathVariable String email) {
        List<User> list  = userRepository.findAll();

        return list.stream().filter(user -> user.getEmail().equals(email)).findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("User not found with email: " + email));

    }

    @RequestMapping(value="/user/{email}", method= RequestMethod.DELETE)
    public void deleteUser(@PathVariable String email) {
        List<User> list  = userRepository.findAll();

         list.stream().filter(user -> user.getEmail().equals(email)).findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("User not found with email: " + email));


        list.stream().filter(user -> user.getEmail().equals(email)).forEachOrdered(userRepository::delete);

    }


}
