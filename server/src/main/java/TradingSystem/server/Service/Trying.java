package TradingSystem.server.Service;
import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Trying {
    // id example
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // ----------------------------------------------------------

    // map example
    @ElementCollection
    //name = the name of the table
    //joincolumn - name - the name of the foreinkey of the map
    // referenced column name = the father PK
    @CollectionTable(name = "order_item_mapping",
            joinColumns = {@JoinColumn(name = "trying_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "item_name") // the key column
    @Column(name = "price") // the value column
    private Map<String, Double> itemPriceMap;
    // ----------------------------------------------------------



    // many to many example
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ManyTrying> manyTryingList;
    // ----------------------------------------------------------

    // normal column example
    @Column(name="class_name")
    private String name;
    // ----------------------------------------------------------

    // one to one example
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trying_num", referencedColumnName = "id")
    private ManyTrying one_to_one;
    // ----------------------------------------------------------




    public Trying(String name) {
        this.name = name;
        this.itemPriceMap = new HashMap<>();
        this.itemPriceMap.put("hi",3.1);
    }
    public Trying() {
    }

    // ------------------------ getters and setters ----------------------------------


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<String, Double> getItemPriceMap() {
        return itemPriceMap;
    }

    public void setItemPriceMap(Map<String, Double> itemPriceMap) {
        this.itemPriceMap = itemPriceMap;
    }

    public List<ManyTrying> getManyTryingList() {
        return manyTryingList;
    }

    public void setManyTryingList(List<ManyTrying> manyTryingList) {
        this.manyTryingList = manyTryingList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ManyTrying getOne_to_one() {
        return one_to_one;
    }

    public void setOne_to_one(ManyTrying one_to_one) {
        this.one_to_one = one_to_one;
    }
}
