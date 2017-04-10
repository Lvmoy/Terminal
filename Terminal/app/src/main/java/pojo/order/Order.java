package pojo.order;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Lvmoy on 2017/4/1 0001.
 * Mode: - - !
 */
@JsonObject
public class Order {
    //Order id  primary key
    @JsonField(name ="id")
    private int id;

    @JsonField(name ="machineType")
    private int machineType;

    @JsonField(name ="orderType")
    private int orderType;

    @JsonField(name ="orderName")
    private String orderName;

    @JsonField(name ="ip")
    private String ip;

    @JsonField(name ="machine_port")
    private String machine_port;

    @JsonField(name ="type")
    private String type;

    @JsonField(name ="value")
    private String value;
    public Order()
    {

    }

    public Order(int id, int machineType, int orderType, String orderName, String ip, String machine_port, String type, String value) {
        this.id = id;
        this.machineType = machineType;
        this.orderType = orderType;
        this.orderName = orderName;
        this.ip = ip;
        this.machine_port = machine_port;
        this.type = type;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMachineType() {
        return machineType;
    }

    public void setMachineType(int machineType) {
        this.machineType = machineType;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMachine_port() {
        return machine_port;
    }

    public void setMachine_port(String machine_port) {
        this.machine_port = machine_port;
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

    @Override
    public String toString() {
        return
                "Order{" +
                        "id = '" + id + '\'' +
                        ",machineType = '" + machineType + '\'' +
                        ",orderType = '" + orderType + '\'' +
                        ",orderName = '" + orderName + '\'' +
                        ",ip = '" + ip + '\'' +
                        ",machine_port = '" + machine_port + '\'' +
                        ",type = '" + type + '\'' +
                        ",value = '" + value + '\'' +

                        "}";
    }

}
