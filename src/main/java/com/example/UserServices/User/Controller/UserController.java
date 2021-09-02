package com.example.UserServices.User.Controller;

import com.example.UserServices.User.Exception.*;
import com.example.UserServices.User.Model.User;
import com.example.UserServices.User.Model.UserModelAssembler;
import com.example.UserServices.User.Model.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api", produces = { MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE })
public class UserController  {

    private final UserRepository userRepository;
    private final UserModelAssembler userModelAssembler;

    public UserController(UserRepository userRepository, UserModelAssembler userModelAssembler) {
        this.userRepository = userRepository;
        this.userModelAssembler = userModelAssembler;
    }


    @RequestMapping(value="/users", method= RequestMethod.GET)
    public CollectionModel<EntityModel<User>> getUsers() {
        List<EntityModel<User>> users = userRepository.findAll().stream()
                .map(userModelAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).getUsers()).withSelfRel());
    }

//    @RequestMapping(value="/user/count", method= RequestMethod.GET)
//    public Long getUser() {
//        return userRepository.count();
//    }

    @RequestMapping(value="/user", method= RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody User userRecord)
    {
        if (!userRepository.findByEmail(userRecord.getEmail()).isPresent()){
            EntityModel<User> entityModel = userModelAssembler.toModel(userRepository.save(userRecord));

            return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
        }
        else {
            throw new ResourceAlreadyExistException("This user already exist");
        }

    }

    @RequestMapping(value="/user/{email}", method= RequestMethod.PUT)
    public ResponseEntity<?>  updateUser(@RequestBody User userRecord, @PathVariable String email ) {
        User user = userRepository.findByEmail(userRecord.getEmail()).orElseThrow(()->
                new ResourceNotFoundException("User not found with email: " + userRecord.getEmail()));
        user.setEmail(email);
        userRepository.save(user);

        return new ResponseEntity("The user was updated successfully.", HttpStatus.OK);
    }


    @GetMapping("/user/{email}" )
    public EntityModel<User> getUser(@PathVariable String email) {

        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found with email: " + email));

        return userModelAssembler.toModel(user);
        
    }

    @RequestMapping(value="/user/{email}", method= RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable String email) {

        List<User> list  = userRepository.findAll();

        list.stream().filter(user -> user.getEmail().equals(email)).findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Delete: User not found with email: " + email));


        list.stream().filter(user -> user.getEmail().equals(email)).forEachOrdered(userRepository::delete);

        return new ResponseEntity("The user was deleted successfully.", HttpStatus.ACCEPTED);
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
