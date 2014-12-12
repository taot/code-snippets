package test.perf;

import java.io.Serializable;
import java.util.List;

public class Account implements Serializable {

    private Long id;

    private String name;

    private String desc;

    private List<Account> children;

    public Account(Long id, String name, String desc, List<Account> children) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public List<Account> getChildren() {
        return children;
    }

}
