package team5.BW_CMR.payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtenteDTO {
    private String username;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    //  private String avatarUrl;
    //private boolean admin;
}
