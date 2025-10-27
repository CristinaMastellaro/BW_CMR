package team5.BW_CMR.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity

@Data
@NoArgsConstructor
@Table(name = "Indirizzi")
public class Indirizzo {
    @Id
    @GeneratedValue
    private UUID id;
}
