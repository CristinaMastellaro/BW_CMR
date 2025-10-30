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

            // Specification è un modo per costruire query dinamiche usando Criteria
            //Restituisce un oggetto Specification<Cliente e accetta parametro
            // (root, query, builder) -> { ... };
            //root (Root<Cliente>):l'entità su cui stai facendo la query
            //query (CriteriaQuery<?>): la query che stai costruendo
            //builder (CriteriaBuilder): è il costruttore che ti permette di creare condizioni -> like, equal, and, or
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
            //JOIN SERVE A CAMBIARE LA ROOT ES. Cliente.indirizzoLegale -> prendo INDIRIZZO poi Indirizzo.comune  -> prendo COMUNE poi Comune.provincia
            Join<Indirizzo, Comune> comuneJoin = indirizzoJoin.join("comune");
            Join<Comune, Provincia> provinciaJoin = comuneJoin.join("provincia");

            return builder.equal(builder.lower(provinciaJoin.get("provincia")),
                    provincia.toLowerCase());
        };
    }

    public static Specification<Cliente> ordinaDataUltimoContatto() {
        return (root, query, builder) -> {
            query.orderBy(builder.desc(root.get("dataUltimoContatto")));
            return null;
        };
    }

    public static Specification<Cliente> ordinaDataInserimento() {
        return (root, query, builder) -> {
            query.orderBy(builder.desc(root.get("dataInserimento")));
            return null;
        };
    }

    public static Specification<Cliente> ordinaFatturatoAnnuale() {
        return (root, query, builder) -> {
            query.orderBy(builder.desc(root.get("fatturatoAnnuale")));
            return null;
        };
    }

    public static Specification<Cliente> ordinaProvinciaIndirizzoLegale() {
        return (root, query, builder) -> {
            Join<Cliente, Indirizzo> indirizzoJoin = root.join("indirizzoLegale");
            Join<Indirizzo, Comune> comuneJoin = indirizzoJoin.join("comune");
            Join<Comune, Provincia> provinciaJoin = comuneJoin.join("provincia");
            query.orderBy(builder.desc(provinciaJoin.get("provincia")));
            return null;
        };
    }

    public static Specification<Cliente> ordinaNomeContatto() {
        return (root, query, builder) -> {
            query.orderBy(builder.desc(root.get("nomeContatto")));
            return null;
        };
    }
}
