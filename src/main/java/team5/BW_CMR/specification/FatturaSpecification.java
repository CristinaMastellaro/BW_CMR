
package team5.BW_CMR.specification;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import team5.BW_CMR.entities.Fattura;

import java.time.LocalDate;
import java.util.UUID;

public class FatturaSpecification implements Specification<Fattura> {
    private final String key;
    private final String operation;
    private final Object value;

    public FatturaSpecification(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }


    @Override
    public Predicate toPredicate(Root<Fattura> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (key.equalsIgnoreCase("clienteId")) {
            return cb.equal(root.get("cliente").get("id"), UUID.fromString(value.toString()));
        }

        if (key.equalsIgnoreCase("importo")) {
            double importo = Double.parseDouble(value.toString());
            return switch (operation) {
                case ">" -> cb.greaterThan(root.get("importo"), importo);
                case "<" -> cb.lessThan(root.get("importo"), importo);
                case ":" -> cb.equal(root.get("importo"), importo);
                default -> null;
            };
        }

        if (key.equalsIgnoreCase("data")) {
            LocalDate data = LocalDate.parse(value.toString());
            return switch (operation) {
                case ">" -> cb.greaterThan(root.get("data"), data);
                case "<" -> cb.lessThan(root.get("data"), data);
                case ":" -> cb.equal(root.get("data"), data);
                default -> null;
            };
        }

        if (root.get(key).getJavaType() == String.class) {
            return switch (operation) {
                case ":" -> cb.like(root.get(key), "%" + value + "%");
                default -> null;
            };
        }

        return null;
    }
}