package team5.BW_CMR.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import team5.BW_CMR.entities.FatturaStato;
import team5.BW_CMR.entities.StatoFattura;

import java.time.LocalDate;
import java.util.UUID;

public class FatturaStatoSpecification implements Specification<FatturaStato> {
private final String key;
private final String operation;
private Object value;

public FatturaStatoSpecification (String key, String operation, Object value){
    this.key=key;
    this.operation=operation;
    this.value=value;
}

@Override
    public Predicate toPredicate(Root<FatturaStato> root, CriteriaQuery<?> query, CriteriaBuilder cb){
    if(key.equalsIgnoreCase("fatturaId")){
        return cb.equal(root.get("fattura").get("id"), UUID.fromString(value.toString()));
    }
    if(key.equalsIgnoreCase("statoFattura")){
        return cb.equal(root.get("statoFattura"), StatoFattura.valueOf(value.toString()));
    }

    if(key.equalsIgnoreCase("dataStato")){
        LocalDate data= LocalDate.parse(value.toString());
        return switch (operation){
            case">"-> cb.greaterThan(root.get("dataStato"),data);
            case"<"-> cb.lessThan(root.get("dataStato"),data);
            case":","="-> cb.equal(root.get("dataStato"),data);
            default->null;
        };

    }
return null;

}
}
