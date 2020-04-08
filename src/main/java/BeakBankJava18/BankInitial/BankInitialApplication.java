package BeakBankJava18.BankInitial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*do we need 2 Spring apps in one project ?
BankInitialApplication and PayrollApplication
 */


/*
* I can't edit README ))
* add readme chapters howto:
* - build
* - service start
*  etc.
* */


/*
* add more packages like:
*
* model
* rest
* service
* repository or dal
* see our project structure as reference
* for instance org.bearbank.model
* org.bearbank.service etc.
* */
@SpringBootApplication
public class BankInitialApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankInitialApplication.class, args);
	}

}
