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
  const productApi = new ProductApi();
  const remove_product_from_cart = (product_id,store_id) =>{
    return productApi.remove_product_from_cart(store_id,product_id);
  }
  
  export default function ShoppingCart(data) {
  const [items, setItems] = React.useState(make_rows(prods));
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
