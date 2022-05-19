import React, { Component } from "react";
import "./HomePageSearch.css"
import Button from '@mui/material/Button';
import { Input } from "@mui/material";
import { ProductApi } from "../API/ProductApi";
import { ConnectApi } from "../API/ConnectApi";
import train from "../API/train.ts";

const axios = require('axios');
const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/api/amit";
const ariaLabel = { 'aria-label': 'description' };


export default class HomePageSearch extends Component  {

    constructor(props) {
        super(props);
        this.state = { 
            productname:"",
            rt: undefined,

        };
        this.connectAPI = new ConnectApi();
        this.productApi = new ProductApi();
    }
    
    


    
    async find_product_by_name(event){
        console.log("yyyyyyyyyyyyyyyyyyyyyyyyyyy\n\n\n\n\n");
        let name = this.state["productname"];
        console.log(name+"\n\n\n")
        // let response = await ProductApi.find_product_by_name(name);
        let response = this.productApi.testtttt(" ya gever");

        console.log("\n\n"+response.value+"\n\n");
        console.log("ttttttttttttttttttttttttttttt\n\n\n\n\n");

       
    }
    // async componentDidMount() {
       
    // }
    render () {
        const {redirectTo} = this.state
        return (
            <main class="HomePageSearchMain" >
                    <div class="HomePageSearchWindow " >
                        <h3>Welcome To the Green Trading System</h3>
                        <form class="HomePageSearchForm" >
                        <Input placeholder="Search product by name"  inputProps={ariaLabel} onChange={(event) => {this.setState({ "productname": event.target.value});}}  />
                            {/* <input type="text" name="productname" value={this.state.productname}
                                    placeholder="Search product by name" required/> */}
                                    <Button onClick={() => this.find_product_by_name()} variant="contained">Search </Button>
                            {/* <button type="submit" value="Search"  /> */}
                           
                            {/* onClick={this.search_product_by_name.bind(this)} */}
                            
                        </form>
                    </div>
                </main>
    
        );
      }
  

};

