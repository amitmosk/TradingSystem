package TradingSystem.server.Service;

import javax.persistence.*;
import java.util.List;

@Entity
public class ManyTrying {

    // id example
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // ----------------------------------------------------------

    // many to many
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Trying> lst;
    // ----------------------------------------------------------


    // -------------------------- contractors -------------------------------
    public ManyTrying() {
    }

    public ManyTrying(List<Trying> lst) {
        this.lst = lst;
    }

    // -------------------------------- getters and setters ----------------------

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Trying> getLst() {
        return lst;
    }

    public void setLst(List<Trying> lst) {
        this.lst = lst;
    }
}
