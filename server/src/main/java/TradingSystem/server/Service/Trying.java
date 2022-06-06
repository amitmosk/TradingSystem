package TradingSystem.server.Service;
import javax.persistence.*;

@Entity
@Table(name = "trying_table")
public class Trying {
    public Trying() {
    }

    public Trying(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Column(name="class_name")
    private String name;

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
