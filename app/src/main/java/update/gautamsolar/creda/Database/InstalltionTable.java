package update.gautamsolar.creda.Database;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
@Table(name = "creda_installation_table")
public  class InstalltionTable extends Model {

    @Column(name = "foto1")
    public String foto1;

    @Column(name = "eng_id")
    public String eng_id;

    @Column(name = "foto2")
    public String foto2;

    @Column(name = "foto3")
    public String foto3;

    @Column(name = "foto4")
    public String foto4;

    @Column(name = "foto5")
    public String foto5;

    @Column(name = "foto6")
    public String foto6;

    @Column(name = "foto7")
    public String foto7;

    @Column(name = "foto8")
    public String foto8;

    @Column(name = "foto9")
    public String foto9;

    @Column(name = "foto10")
    public String foto10;

    @Column(name = "Lat")
    public String Lat;

    @Column(name = "Lon")
    public String Lon;

    @Column(name = "Eng_contact")
    public String Eng_contact;

    @Column(name = "Regn")
    public String Regn;

    @Column(name = "Dati")
    public String Dati;
}

