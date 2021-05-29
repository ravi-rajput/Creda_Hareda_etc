package update.gautamsolar.creda.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
@Table(name = "survey_table_mp")
public  class Site_Survey_Table_MP extends Model {

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


    @Column(name = "boredepth")
    public String boredepth;

    @Column(name = "boresize")
    public String boresize;

    @Column(name = "waterlevel")
    public String waterlevel;

    @Column(name = "borestatus")
    public String borestatus;

    @Column(name = "exstmotorstring")
    public String exstmotorstring;



    @Column(name = "adharnumber")
    public String adharnumber;

    @Column(name = "choosepumphead")
    public String choosepumphead;


    @Column(name = "bank_name")
    public String bank_name;


    @Column(name = "account_number")
    public String account_number;


    @Column(name = "ifsc_code")
    public String ifsc_code;













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

