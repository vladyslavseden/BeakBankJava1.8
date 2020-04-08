package payroll;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
/*
* here is following hierarchy:
*
*  BeerControler
*   --BeerController
*
* remove extra class
*
* */
@RestController
class BeerController {

    //    can we do just @Autowired here and remove constructor below?
    // rename brepo
    private final BeerRepository brepo;
    private final BeerModelAssembler assembler;

    BeerController(BeerRepository brepo,
                   BeerModelAssembler assembler) {

        this.brepo     = brepo;
        this.assembler = assembler;
    }

    // method rename to more readable
    @GetMapping("/beers")
    CollectionModel<EntityModel<Beer>> all() {

        List<EntityModel<Beer>> beers = brepo.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(beers,
                linkTo(methodOn(BeerController.class).all()).withSelfRel());
    }

    // method rename to more readable
    @GetMapping("/beers/{id}")
    EntityModel<Beer> one(@PathVariable Long id) {
        Beer beer = brepo.findById(id)
                .orElseThrow(() -> new BeerNotFoundException(id));

        return assembler.toModel(beer);
    }

    // method rename to more readable
    @PostMapping("/beers")
    ResponseEntity<EntityModel<Beer>> newBeer(@RequestBody Beer beer) {

        beer.setRate(Rate.NOTRATED);
        Beer newBeer = brepo.save(beer);

        return ResponseEntity
                .created(linkTo(methodOn(BeerController.class).one(newBeer.getId())).toUri())
                .body(assembler.toModel(newBeer));
    }

    /* see REST best practices, e.g. use nouns etc.
    specific points: https://medium.com/@mwaysolutions/10-best-practices-for-better-restful-api-cbe81b06f291

    global vision: https://martinfowler.com/articles/richardsonMaturityModel.html
    */

    @DeleteMapping("/beer/{id}/cancel")
    ResponseEntity<RepresentationModel> cancel(@PathVariable Long id) {

        Beer beer = brepo.findById(id).orElseThrow(() -> new BeerNotFoundException(id));

        if (beer.getRate() == Rate.ADDED) {
            beer.setRate(Rate.NOTRATED);
            return ResponseEntity.ok(assembler.toModel(brepo.save(beer)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't cancel an beer order that is in the " + beer.getRate() + " status"));
    }

    /*the same as above*/
    @PutMapping("/beer/{id}/complete")
    ResponseEntity<RepresentationModel> complete(@PathVariable Long id) {

        Beer beer = brepo.findById(id).orElseThrow(() -> new BeerNotFoundException(id));

        if (beer.getRate() == Rate.ADDED) {
            beer.setRate(Rate.RATED);
            return ResponseEntity.ok(assembler.toModel(brepo.save(beer)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't complete an order that is in the " + beer.getRate() + " status"));
    }
}
