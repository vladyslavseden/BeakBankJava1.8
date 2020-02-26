package payroll;

public class BeerNotFoundException extends RuntimeException {
    BeerNotFoundException(Long id){
        super("There is no beer with id: " + id);
    }
}
