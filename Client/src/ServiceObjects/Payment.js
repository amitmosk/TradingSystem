

export class Payment  {
    // public class PaymentInfo {
    //     private String card_number;
    //     private String month;
    //     private String year;
    //     private String holder;
    //     private String ccv;
    //     private String id;
        
    constructor(data) {
        this.card_number = data.card_number;
        this.month = data.month;
        this.year = data.year;  
        this.holder = data.holder;
        this.ccv = data.ccv;
        this.id = data.id;
    }
    
    static create(card_number, month, year, holder, ccv, id) {
        return new Payment({
            card_number:card_number,
            month:month,
            year:year,
            holder:holder,
            ccv:ccv,
            id:id,
        })

    }
}