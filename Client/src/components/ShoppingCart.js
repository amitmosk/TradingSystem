// import React, { Component } from 'react';
// import "./Login.css";
// import Link from '@mui/material/Button';
// import HomeIcon from '@mui/icons-material/Home';
// import { ConnectApi } from '../API/ConnectApi';
// import { CartApi } from '../API/CartApi';
// import Register from "./Register.js";
// import List from '@mui/material/List';
// import ListItem from '@mui/material/ListItem';
// import ListItemText from '@mui/material/ListItemText';
// import ListSubheader from '@mui/material/ListSubheader';
// import { DataGrid, GridColDef, GridValueGetterParams } from '@mui/x-data-grid';
// import { StoreApi } from '../API/StoreApi';
// import {Store} from '../ServiceObjects/Store'


// const axios = require('axios');
// const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";

// export default class ShoppingCart{
//     static displayName = ShoppingCart.name;

//     constructor(props) {
//         super(props);
//         this.state = { 
//             columns : [
//                 { field: 'id', headerName: 'ID', width: 70 },
//                 { field: 'name', headerName: 'name', width: 130 },
//                 { field: 'category', headerName: 'category', width: 130 },
//                 { field: 'price', headerName: 'price', type: 'number', width: 90,},
//                 { field: 'quantity', headerName: 'quantity', type: 'number', width: 90,},
//                 { field: 'store', headerName: 'store', type: 'Store', width: 90,},
//             ],
//               rows : [
//                 { id: 1, name: 'Snow', category: "a", price: 1, quantity:10, store:"bb"},
//                 { id: 2, name: 'Snow', category: "a", price: 1, quantity:10, store:"bb"},
//                 { id: 3, name: 'Snow', category: "a", price: 1, quantity:10, store:"bb"},
//                 { id: 4, name: 'Snow', category: "a", price: 1, quantity:10, store:"bb"},
//                 { id: 5, name: 'Snow', category: "a", price: 1, quantity:10, store:"bb"},
//                 { id: 6, name: 'Snow', category: "a", price: 1, quantity:10, store:"bb"},
//                 { id: 7, name: 'Snow', category: "a", price: 1, quantity:10, store:"bb"},
//                 { id: 8, name: 'Snow', category: "a", price: 1, quantity:10, store:"bb"},
//               ],
//             payment:undefined,
//             supply:undefined,
//             selection:[]
//         };
        
//     }
    
//     async componentDidMount() {
//     }


//     async buy_cart(){
//         let payment = this.state.payment;
//         let supply = this.state.supply;
//         console.log("payment is "+payment+" , supply is "+supply+"\n");
//         let response = await CartApi.buy_cart(payment, supply);
//         alert(response.message);
//     }

//     async view_cart(){
//         let response = await CartApi.view_cart();
//         this.products = response.value;
//     }

//     remove_product(select){
//         console.log("hi bye removed");
//     }

//     render() {
//         const [select, setSelection] = React.useState([]);
//             return (
//                 <main class="CartMain">
//                     <div class="ProductsWindow" style={{ height: 400, width: '100%' }}>
//                     <DataGrid rows={this.state.rows} columns={this.state.columns} checkboxSelection
//                      onSelectionChange={(newSelection) => {
//                         setSelection(newSelection.rows);
//                       }}/>
//                     <Button onClick={this.remove_products(select)}>remove selected products</Button>
//                     </div>
//                 </main>
//             );
//     }
// }
import * as React from 'react';
import { DataGrid } from '@mui/x-data-grid';
import Button from '@mui/material/Button';
import {Product} from '../ServiceObjects/Product';
import { CartApi } from '../API/CartApi';
import { ProductApi } from '../API/ProductApi';

const prods = [Product.create(10,'snow',"a",1,10,"bb"),Product.create(21,'snow',"a",1,10,"bb")];
const columns = [
  { field: 'id', headerName: 'ID', width: 70 },
  { field: 'name', headerName: 'name', width: 130 },
  { field: 'category', headerName: 'category', width: 130 },
  { field: 'price', headerName: 'price', type: 'number', width: 90,},
  { field: 'quantity', headerName: 'quantity', type: 'number', width: 90,},
  { field: 'store', headerName: 'store', width: 90,},];
  const make_rows = (res) => {
    let ret = []
    let i = 0;
    res.map(p=>{i++;ret.push({idx:i,id:p.product_id,name:p.name,category:p.category,price:p.price,quantity:1000,store:1})});
    return ret;
  }

  const remove_product_from_cart = (product_id,store_id) =>{
    return ProductApi.remove_product_from_cart(store_id,product_id);
  }
  
  export default function ShoppingCart(data) {
  const [items, setItems] = React.useState(make_rows(data.prods));
  const [selectionModel, setSelectionModel] = React.useState([]);
  const remove_products = () => {
    setItems(items.filter(i=> !selectionModel.some(s=> s === i.id)))
    setSelectionModel([]);
  }
  return (
      <main>
    <div style={{ height: 400, width: '100%' }}>
                          <DataGrid rows={items} columns={columns}         
                          checkboxSelection
                            onSelectionModelChange={(newSelectionModel) => {
                            setSelectionModel(newSelectionModel);}}
        selectionModel={selectionModel}/>
    </div>
    <div><Button variant="contained" onClick={remove_products}>hello</Button></div>
    </main>
  );
                            }
