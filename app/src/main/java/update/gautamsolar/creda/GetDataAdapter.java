package update.gautamsolar.creda;

public class GetDataAdapter {


    int Id;
    String name;
    String phone_number;
    String dispatch_status;
    String pump_type;
    String structure;


    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getId() {

        return Id;
    }

    public void setId(int Id1) {

        this.Id = Id1;
    }


    public String getdispatch_status() {

        return dispatch_status;
    }

    public void setdispatch_status(String dispatch_status1) {

        this.dispatch_status = dispatch_status1;
    }


    public String getPhone_number() {

        return phone_number;
    }

    public void setPhone_number(String phone_number1) {

        this.phone_number = phone_number1;
    }


    public String getPump_type() {

        return pump_type;
    }

    public void setPump_type(String pump_type1) {

        this.pump_type = pump_type1;
    }

    public String getStructure() {

        return structure;
    }

    public void setStructure(String structure1) {

        this.structure = structure1;
    }

}
