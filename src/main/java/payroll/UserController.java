package payroll;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<?> newUser(@RequestBody User newUser) throws URISyntaxException {
        EntityModel<User> entityModel = assembler.toModel(repo.save(newUser));
        return ResponseEntity.
                created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
    //Single item
    @GetMapping("/user/{id}")
    EntityModel<User> one(@PathVariable Long id){
        User user = repo.findById(id)
                .orElseThrow(() -> new UserNotFoundExption(id));
        return assembler.toModel(user);
    }


    @PutMapping("/user/{id}")
    ResponseEntity<?> userReplace(@RequestBody User newUser, @PathVariable Long id) throws URISyntaxException{
        User updatedUser = repo.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setRole(newUser.getRole());
                    return repo.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repo.save(newUser);
                });
        EntityModel<User> entityModel = assembler.toModel(updatedUser);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
    @DeleteMapping("/user/{id}")
   ResponseEntity<?> deletUser(@PathVariable Long id){
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
