package team5.BW_CMR.specifications;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import team5.BW_CMR.entities.Cliente;
import team5.BW_CMR.entities.Comune;
import team5.BW_CMR.entities.Indirizzo;
import team5.BW_CMR.entities.Provincia;

import java.time.LocalDate;

public class ClienteSpecifications {

    public static Specification<Cliente> nomeContattoContiene(String parteNome) {
        return (root, query, builder) -> {
            if (parteNome == null || parteNome.isEmpty()) {
                return builder.conjunction();
            }
            return builder.like(builder.lower(root.get("nomeContatto")),
                    "%" + parteNome.toLowerCase() + "%");
        };
    }

    public static Specification<Cliente> fatturatoUgualeA(Double fatturato) {
        return (root, query, builder) -> {
            if (fatturato == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("fatturatoAnnuale"), fatturato);
        };
    }

    public static Specification<Cliente> dataInserimentoUgualeA(LocalDate data) {
        return (root, query, builder) -> {
            if (data == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("dataInserimento"), data);
        };
    }

    public static Specification<Cliente> dataUltimoContattoUgualeA(LocalDate data) {
        return (root, query, builder) -> {
            if (data == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("dataUltimoContatto"), data);
        };
    }

    public static Specification<Cliente> provinciaUgualeA(String provincia) {
        return (root, query, builder) -> {
            if (provincia == null || provincia.isEmpty()) {
                return builder.conjunction();
            }
            Join<Cliente, Indirizzo> indirizzoJoin = root.join("indirizzoLegale");
            Join<Indirizzo, Comune> comuneJoin = indirizzoJoin.join("comune");
            Join<Comune, Provincia> provinciaJoin = comuneJoin.join("provincia");

            return builder.equal(builder.lower(provinciaJoin.get("provincia")),
                    provincia.toLowerCase());
        };
    }

}
