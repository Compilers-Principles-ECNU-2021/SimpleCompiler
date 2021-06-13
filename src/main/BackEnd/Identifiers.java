public class Identifiers {

    String name; // name
    String type;  // int  / real
    String value; // 具体的数值
    String tacName;  //tac中的名字

    public Identifiers(String name, String type, String value, String tacName) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.tacName = tacName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTacName() {
        return tacName;
    }

    public void setTacName(String tacName) {
        this.tacName = tacName;
    }

    @Override
    public String toString() {
        return "Identifiers{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", tacName='" + tacName + '\'' +
                '}';
    }
}
