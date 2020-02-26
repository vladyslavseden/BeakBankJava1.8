package payroll;

import jdk.net.SocketFlow;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class BeerController {

    private final BeerRepository brepo;
    private final BeerModelAssembler assembler;

    BeerController(BeerRepository brepo,
                   BeerModelAssembler assembler) {

        this.brepo     = brepo;
        this.assembler = assembler;
    }

    @GetMapping("/beers")
    CollectionModel<EntityModel<Beer>> all() {

        List<EntityModel<Beer>> beers = brepo.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(beers,
                linkTo(methodOn(BeerController.class).all()).withSelfRel());
    }

    @GetMapping("/beers/{id}")
    EntityModel<Beer> one(@PathVariable Long id) {
        Beer beer = brepo.findById(id)
                .orElseThrow(() -> new BeerNotFoundException(id));

        return assembler.toModel(beer);
    }

    @PostMapping("/beers")
    ResponseEntity<EntityModel<Beer>> newBeer(@RequestBody Beer beer) {

        beer.setRate(SocketFlow.Status.NULL);
        Beer newBeer = brepo.save(beer);

        return ResponseEntity
                .created(linkTo(methodOn(BeerController.class).one(newBeer.getId())).toUri())
                .body(assembler.toModel(newBeer));
    }
}
