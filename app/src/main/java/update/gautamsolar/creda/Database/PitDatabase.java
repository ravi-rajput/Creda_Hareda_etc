package update.gautamsolar.creda.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "PIT_Table")
public class PitDatabase extends Model{

    @Column(name = "eng_id")
    public String eng_id;

    @Column(name = "foto1")
    public String foto1;

    @Column(name = "Lat")
    public String Lat;

    @Column(name = "Lon")
    public String Lon;

    @Column(name = "Regn")
    public String Regn;

    @Column(name = "Dati")
    public String Dati;

}
