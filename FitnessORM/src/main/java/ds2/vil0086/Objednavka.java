package ds2.vil0086;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Objednavka {

    private int id_objednavky;

    private String typ_jidelnicku;

    private String typ_treninkoveho_planu;

    private int zakaznik_id_zakaznika;

    private int tp_id_treninkoveho_planu;


}
