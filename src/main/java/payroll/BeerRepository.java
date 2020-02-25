package payroll;

import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;

public interface BeerRepository extends JpaAttributeConverter<Beer, Long> {
}
