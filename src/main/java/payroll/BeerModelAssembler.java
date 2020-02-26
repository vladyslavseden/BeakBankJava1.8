package payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

public class BeerModelAssembler implements RepresentationModelAssembler<Beer, EntityModel<Beer>> {

@Override
public EntityModel<Beer> toModel(Beer beer){
        return new EntityModel<>(beer,
        linkTo(methodOn(UserController.class).one(beer.getId())).withSelfRel(),
        linkTo(methodOn(UserController.class).all()).withRel("beers"));
        }
}
