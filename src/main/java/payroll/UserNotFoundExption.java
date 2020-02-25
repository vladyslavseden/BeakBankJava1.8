package payroll;

public class UserNotFoundExption extends RuntimeException {
    UserNotFoundExption(Long id){
        super("There is no user with id: " + id);
    }
}
