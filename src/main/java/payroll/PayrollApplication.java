package payroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*do we need 2 Spring apps in one project ?
BankInitialApplication and PayrollApplication
 */
@SpringBootApplication
public class PayrollApplication {
    public static void main(String... args){
        SpringApplication.run(PayrollApplication.class, args);
    }
}
