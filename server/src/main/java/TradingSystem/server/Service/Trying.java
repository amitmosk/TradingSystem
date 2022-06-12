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


    // map of two complex object
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "table_name",
            joinColumns = {@JoinColumn(name = "holder_class", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "key_val", referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "val_val")
    private Map<ManyTrying,ManyTrying> complex_table;
    // ----------------------------------------------------------


    @ElementCollection
    @CollectionTable(name = "item_prices")
    @MapKeyColumn(name="product_id")
    @Column(name="quantity")
    private Map<String, Double> itemPriceMap;
    // ----------------------------------------------------------


    // Many to one map example
    @OneToMany
    @JoinTable(name = "manyTryingMap",
            joinColumns = {@JoinColumn(name = "many_trying", referencedColumnName = "id")})
    @MapKeyColumn(name = "trying_id") // the key column
    private Map<Integer,ManyTrying> manyTryingMap;


    // ----------------------------------------------------------


    //map of entity to non complex

    @ElementCollection
    @MapKeyColumn(name = "many_id") // the key column
    @Column(name = "num")
    private Map<ManyTrying,Integer> complex_to_normal_map;
    // ----------------------------------------------------------


    // many to many example
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ManyTrying> manyTryingList;
    // ----------------------------------------------------------

    // normal column example
    private String name;
    // ----------------------------------------------------------

    // one to one example
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "trying_num", referencedColumnName = "id")
    private ManyTrying one_to_one;
    // ----------------------------------------------------------


    public Trying(String name) {
        this.name = name;
        this.itemPriceMap = new HashMap<>();
        this.itemPriceMap.put("hi",3.1);
        this.complex_table = new HashMap<>();
        this.complex_to_normal_map = new HashMap<>();
    }

    public Trying() { }

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

    public Map<Integer, ManyTrying> getManyTryingMap() {
        return manyTryingMap;
    }

    public void setManyTryingMap(Map<Integer, ManyTrying> manyTryingMap) {
        this.manyTryingMap = manyTryingMap;
    }

    public Map<ManyTrying, ManyTrying> getComplex_table() {
        return complex_table;
    }

    public void setComplex_table(Map<ManyTrying, ManyTrying> complex_table) {
        this.complex_table = complex_table;
    }

    public void add_many_trying(ManyTrying many1, ManyTrying many2){
        this.complex_table.put(many1,many2);
    }

    public void add_to_map_complex_non(ManyTrying manyTrying3, int i) {
        this.complex_to_normal_map.put(manyTrying3,i);
    }
}
