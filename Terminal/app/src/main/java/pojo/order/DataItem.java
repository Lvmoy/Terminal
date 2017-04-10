package pojo.order;

/**
 * Created by Lvmoy on 2017/4/5 0005.
 * Mode: - - !
 */

public class DataItem {
    private int id;
    private String name;
    private String type;
    private String value;
    private String iso;
    private String ip;

    public DataItem(){}

    public DataItem(int id, String name, String type, String value, String iso, String ip) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
        this.iso = iso;
        this.ip = ip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return
                "DataItem{" +
                        "id = '" + id + '\'' +
                        ",name = '" + name + '\'' +
                        ",type = '" + type + '\'' +
                        ",value = '" + value + '\'' +
                        ",iso = '" + iso + '\'' +
                        ",ip = '" + ip + '\'' +
                        "}";
    }
}
