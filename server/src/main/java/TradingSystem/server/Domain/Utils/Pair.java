package TradingSystem.server.Domain.Utils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Pair class
@Entity
public class Pair implements Serializable
{
    @Id
    @GeneratedValue
    private Long id;
    public String first;       // the first field of a pair
    public Integer second;      // the second field of a pair

    // Constructs a new pair with specified values
    public Pair(String first, Integer second)
    {
        this.first = first;
        this.second = second;
    }

    public Pair() { }

    // Checks specified object is "equal to" the current object or not
    public boolean equals(Pair o)
    {
        if (this == o) {
            return true;
        }
        // call `equals()` method of the underlying objects
        if (!first.equals(o.first)) {
            return false;
        }
        return second.equals(o.second);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }
}