package payroll;

import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Table(name = "Bear")
class Beer {

    private @Id @GeneratedValue Long id;
    private String  name;
    private String  breqery;
    private String  style;
    private String  description;
    private Rate    rate;

    Beer(){}

    Beer(String  name, String  breqery, String  style, String  description, Rate rate){
        this.name        = name;
        this.breqery     = breqery;
        this.style       = style;
        this.description = description;
        this.rate        = rate;
    }
}
