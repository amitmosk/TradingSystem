


export class Utils  {
    // Boolean -> return 0 for False, 1 for True
    

    // ----------------------------------- Payment -------------------------------------------------------
    
    static check_date(month, year)
    {
        if (year<2022)
        {
            return 0;
        }
        if (year === 2022 && month < 7)
        {
            return 0;
        }
        return 1;
    }
    
    static check_credit_number(credit_number){   
        if (this.check_all_digits(credit_number) == 0)
        {
            return 0;
        }
        if (credit_number.length != 16)
        {
            return 0;
        }
        return 1;
    }
    static check_holder(name){   
        if (/^[a-zA-Z ]+$/.test(name))
        {
            return 1;
        }
        return 0;
        // if(this.check_not_empty(name)==0)
        // {
        //     console.log(name);
        //     return 0;
        // }
        // return 1;
    }
    static check_id(id){   
        if (this.check_all_digits(id) == 0)
        {
            return 0;
        }
        if (id.length != 9)
        {
            return 0;
        }
        return 1;
    }
    static check_ccv(ccv){    
        if (this.check_all_digits(ccv) == 0)
        {
            return 0;
        }
        if (ccv.length != 3)
        {
            return 0;
        }
        return 1;
    }
    static check_month(month){ 
        
        console.log("gggg");
        if(month>12 || month <1)
        {
            return 0;
        }  
        return 1;
    }
    static check_year_later(year){  
        const curr_year = new Date().getFullYear();
        console.log(year)
        if (year <2022)
        {
            return 0;
        } 
        return 1;
    }

    // ----------------------------------- General -------------------------------------------------------
    static check_all_english_letters(str){   
        var letters = /^[A-Za-z]+$/;
        if(str.match(letters))
        {
            return 1;
        }
        return 0;
    }
    
    static check_not_empty(str){   
        if (str.length == 0)
        {
            return 0;
        }
        return 1;
    }
    static check_all_digits(str){   
        if (/^[0-9]+$/.test(str))
        {
            return 1;
        }
        return 0;
    }
    static check_email(email){  
        if(! String(email).toLowerCase().match( /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/))
        {
            return 0;
        }
        return 1;
    }

    // ----------------------------------- Show Bids -------------------------------------------------------


    static check_yes_no(str)
    {
        return 1;
    }


    // ----------------------------------- Discount Policy -------------------------------------------------------
    static check_rule_name(rule_name)
    {
        return this.check_not_empty(rule_name);
    }
    static check_precent()
    {
        return 1;
    }
    // ----------------------------------- Crete Predict -------------------------------------------------------
    static check_range(range)
    {
        if (range>0)
            return 1;
        else
        {
            return 0; 
        }
    }
    
   
    











    // ----------------------------------- Birth Date -------------------------------------------------------
    
    
    static check_yaer_earlier(){   
        return 1;
    }
    
    
    // ----------------------------------- Suuply -------------------------------------------------------
    
    static check_address(address){   
        if (this.check_not_empty(address) == 0)
        {
            return 0;
        }
        return 1;
    }
    static check_city(city){ 
        if (this.check_not_empty(city) == 0)
        {
            return 0;
        }
        return 1;  
    }
    static check_country(country){   
        if (this.check_not_empty(country) == 0)
        {
            return 0;
        }
        return 1;
    }
    static check_zip(zip){   
        if (this.check_all_digits(zip) == 0 )
        {
            return 0;
        }
        return 1;
    }
   
}
