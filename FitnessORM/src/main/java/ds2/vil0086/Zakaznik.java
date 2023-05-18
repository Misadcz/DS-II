package ds2.vil0086;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;


@Getter
@Setter
@NoArgsConstructor
public class Zakaznik {

    private int id_zakaznika;

    private String jmeno;

    private String prijmeni;

    private String email;

    private Date datumNarozeni;

    private String alergie;

}
