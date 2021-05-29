package update.gautamsolar.creda.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "PIT_Table")
public class Foundation_M_Database extends Model{

    @Column(name = "eng_id")
    public String eng_id;

    @Column(name = "Lat")
    public String Lat;

    @Column(name = "Lon")
    public String Lon;

    @Column(name = "Regn")
    public String Regn;

    @Column(name = "Dati")
    public String Dati;

    @Column(name = "Rode")
    public String Rode;

    @Column(name = "Saria_Jall")
    public String Saria_Jall;

    @Column(name = "Rate_Gitt")
    public String Rate_Gitt;

}
