package payroll;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserRepository repo;
    private final UserResourceAssembler assembler;

    UserController(UserResourceAssembler assembler, UserRepository repo){
        this.repo      = repo;
        this.assembler = assembler;
    }
    //Agregating root

    @GetMapping("/users")
    CollectionModel<EntityModel<User>> all(){

        List<EntityModel<User>> users = repo.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser){
        return repo.save(newUser);
    }
    //Single item
    @GetMapping("/user/{id}")
    EntityModel<User> one(@PathVariable Long id){
        User user = repo.findById(id)
                .orElseThrow(() -> new UserNotFoundExption(id));
        return assembler.toModel(user);
    }


    @PutMapping("/user/{id}")
    User userReplace(@RequestBody User newUser, @PathVariable Long id){
        return repo.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setEmail(newUser.getEmail());
                    user.setRole(newUser.getRole());
                    user.setDescription(newUser.getDescription());
                    return repo.save(user);
                })
                .orElseGet(() ->{
                    newUser.setId(id);
                    return repo.save(newUser);

                });
    }
    @DeleteMapping("/user/{id}")
    void deleteUser(@PathVariable Long id){
        repo.deleteById(id);
    }


}
