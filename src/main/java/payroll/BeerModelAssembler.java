package payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class BeerModelAssembler implements RepresentationModelAssembler<Beer, EntityModel<Beer>> {

        @Override
        public EntityModel<Beer> toModel(Beer order) {


                EntityModel<Beer> orderModel = new EntityModel<>(order,
                        linkTo(methodOn(BeerController.class).one(order.getId())).withSelfRel(),
                        linkTo(methodOn(BeerController.class).all()).withRel("orders")
                );


                if (order.getRate() == Rate.NOTRATED) {
                        orderModel.add(
                                linkTo(methodOn(BeerController.class)
                                        .cancel(order.getId())).withRel("cancel"));
                        orderModel.add(
                                linkTo(methodOn(BeerController.class)
                                        .complete(order.getId())).withRel("complete"));
                }

                return orderModel;
        }
}