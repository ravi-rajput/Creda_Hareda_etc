package update.gautamsolar.creda.Database;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
@Table(name = "RMU_Table")
public  class RMU_Table extends Model {

    @Column(name = "fotoRMu")
    public String fotoRMu;

    @Column(name = "eng_id")
    public String eng_id;

    @Column(name = "NewRMuNO")
    public String NewRMuNO;

    @Column(name = "latrmu")
    public String latrmu;

    @Column(name = "lonrmu")
    public String lonrmu;


    @Column(name = "Eng_Contact_rmu")
    public String Eng_Contact_rmu;


    @Column(name = "regn_no_rmu")
    public String regn_no_rmu;

    @Column(name = "datetime")
    public String datetime;

}

