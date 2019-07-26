package sn.common.bean;

public class HelloBean {
    private Integer id;
    private String name;

    public HelloBean() {}

    public HelloBean(int i, String name) {
        this.id = i;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
