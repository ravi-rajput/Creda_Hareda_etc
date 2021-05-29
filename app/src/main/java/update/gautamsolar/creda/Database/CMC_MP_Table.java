package update.gautamsolar.creda.Database;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

//This is our table name
@Table(name = "MP_CMC_Table")
public  class CMC_MP_Table extends Model {

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

    @Column(name = "button_id")
    public String button_id;


    @Column(name = "Lat")
    public String Lat;

    @Column(name = "Lon")
    public String Lon;



    @Column(name = "Regn")
    public String Regn;

    @Column(name = "Dati")
    public String Dati;

}