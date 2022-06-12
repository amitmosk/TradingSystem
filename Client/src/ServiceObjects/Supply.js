

export class Supply  {
    // public class SupplyInfo {

    //     private String name;
    //     private String address;
    //     private String city;
    //     private String country;
    //     private String zip;
        
    constructor(data) {
        this.name = data.name;
        this.address = data.address;
        this.city = data.city;
        this.country = data.country;
        this.zip = data.zip;
    }
    
    static create(name, address, city, country, zip) {
        return new Supply({
            name:name,
            address:address,
            city:city,
            country:country,
            zip:zip,
        })

    }
}