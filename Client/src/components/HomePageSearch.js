import React, { Component } from "react";
import Button from '@mui/material/Button';
import { Input } from "@mui/material";
import { ProductApi } from "../API/ProductApi";
import { ConnectApi } from "../API/ConnectApi";
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";


export default class HomePageSearch extends Component {

    constructor(props) {
        super(props);
        this.state = {
            productname: undefined,
            products: undefined,
            option: "name",
            val:undefined,

        };
        this.connectAPI = new ConnectApi();
        this.productApi = new ProductApi();
        this.handleInputChange = this.handleInputChange.bind(this);
    }
    handleInputChange(event) {
        const target = event.target;
        console.log(target.name);
        console.log(target.value);
        this.setState({
            [target.name]: target.value
        });
    }
    async componentDidMount() {

    }




    async find_product_by(option, val) {
        console.log("in find product by");
        console.log(option);
        switch (option) {
            case "name":
                this.find_product_by_name(val)
                break;
            case "category":
                this.find_product_by_category(val)
                break;
            case "keywords":
                this.find_product_by_keywords(val)
                break;
            default:
                console.log("option is empty");
        }

    }
    async find_product_by_name(val) {
        //this.props.show_products("products");
        console.log(val);
        console.log("in find product by name");
        let response = await this.productApi.find_products_by_name(val);
        if (!response.was_exception) {

              const products = response.value;

            //show products
            this.props.show_products(products);
        }
        else {
            console.log("in find product by name - fail");
        }

    }

    async find_product_by_category(val) {
        console.log("in find product by category");
        let response = await this.productApi.find_products_by_category(val);
        if (!response.was_exception) {
            this.setState({
                products: response.value
            });
            //show products
        }
        else {
            console.log("in find product by category - fail");
        }

    }
    async find_product_by_keywords(val) {
        console.log("in find product by keywords");
        let response = await this.productApi.find_products_by_keywords(val);
        if (!response.was_exception) {
            this.setState({
                products: response.value
            });
            //show products
        }
        else {
            console.log("in find product by keywords - fail");
        }

    }

    render() {
        const searchLabel = "Search product by "
        const { redirectTo } = this.state
        return (
            <main class="HomePageSearchMain" >
                <div class="HomePageSearchWindow " >
                    {/* <h3>Welcome To the Green Trading System</h3> */}
                    <form class="HomePageSearchForm" >
                        <Input name="val" value={this.state.val} placeholder={searchLabel + this.state.option} onChange={this.handleInputChange} required/>
                        <Button onClick={() => this.find_product_by(this.state.option, this.state.val)} variant="contained">Search </Button>
                        
                        
                        <select name="option" value={this.state.option} onChange={this.handleInputChange} required>
                            <option value="name">name</option>
                            <option value="category">category</option>
                            <option value="keywords">keywords</option>
                        </select>


                    </form>
                    {!!this.state.snackbar && (
                    <Snackbar
                    open
                    anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
                    onClose={this.handleCloseSnackbar}
                    autoHideDuration={6000}
                    >
                    <Alert
                        {...this.state.snackbar}
                        onClose={this.handleCloseSnackbar}
                    />
                    </Snackbar>
  )}
                </div>
            </main>

        );
    }


};