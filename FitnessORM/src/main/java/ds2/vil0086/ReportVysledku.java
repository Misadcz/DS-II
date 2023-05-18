package ds2.vil0086;

import lombok.*;
import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportVysledku {

        private int id_reportu;

        private Date datum;

        private BigDecimal vaha;

        private int zakaznik_id_zakaznika;

}
