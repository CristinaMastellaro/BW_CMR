package team5.BW_CMR.entities;



//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "ruoli")
//public class Ruolo {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Setter(AccessLevel.NONE)
//    private Long id;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private ERuolo nome;
//
//    public Ruolo(ERuolo nome) {
//        this.nome = nome;
//    }

public enum Ruolo {
    ADMIN, USER

}
