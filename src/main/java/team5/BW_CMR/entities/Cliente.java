package team5.BW_CMR.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Cliente {
    @Id
    @GeneratedValue
    private UUID id;
}
