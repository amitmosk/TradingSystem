import * as React from 'react';
import { DataGrid, GridActionsCellItem } from '@mui/x-data-grid';
import Button from '@mui/material/Button';
import { ProductApi } from '../API/ProductApi';
import { Row, Col } from 'react-grid-system';
import Grid from '@mui/material/Grid';
import Snackbar from '@mui/material/Snackbar';
import Alert from '@mui/material/Alert';
import Link from '@mui/material/Button';
import { map } from 'ramda';
import { StoreApi } from '../API/StoreApi';
import { Component } from 'react';
import { UserApi } from '../API/UserApi';
import {CartApi} from '../API/CartApi';
import IconButton from '@mui/material/IconButton'
import DeleteIcon from '@mui/icons-material/Delete';

  
export default class ShoppingCart extends Component{
    constructor(props) {
        super(props);
        this.state = {
            price:0,
            products: [],
            snackbar:null,
            items:[],
            selected_item:[],
            selected_row:[],
            edited:null
        };
        this.productApi = new ProductApi();
        this.storeApi = new StoreApi();
        this.userApi = new UserApi();
        this.cartApi = new CartApi();
        this.columns = [
            { field: 'id', headerName: 'ID', width: 70 },
            { field: 'name', headerName: 'name', width: 130 },
            { field: 'category', headerName: 'category', width: 130 },
            { field: 'price', headerName: 'price', type: 'double', width: 90,},
            { field: 'quantity', headerName: 'quantity', type: 'number', width: 90, editable:true},
            { field: 'store', headerName: 'store', width: 90, hide: true},
            { field: 'key_words', headerName:'key words',  width: 0, hide:true},
            {
              field: "action",
              headerName: "ADD TO CART",
              width: 250,
              // Important: passing id from customers state so I can delete or edit each user
              renderCell: (id) => (
                <>                
                <IconButton aria-label="delete" onClick={() => this.remove_products(id)}>
                  <DeleteIcon />
                </IconButton>
                </>)
            }]
        }   
    async componentDidMount() {
        let cart = await this.cartApi.view_user_cart(); 
        console.log(cart);
        let products_list = []
        this.setState({price:cart.value.price});
        cart.value.products.map(p=>products_list.push(
          {id:p.product_id,name:p.name,category:p.category,price:p.price,quantity:p.quantity,store:p.store_id,key_words:p.key_words}
          ))
          this.setState({items:products_list,products: products_list, selected:undefined});}
                                    
  setSnackbar = (val) => {this.setState({snackbar:val})}
  setItems = (val) => {this.setState({items:val})}
  set_selected = (val) => {this.setState({selected_item:val})}
  set_row_selected = (val) => {this.setState({selected_row:val})}
  set_edited = (val) => {this.setState({edited:val})}

//   remove_products = async () => {
//     this.set_selected(this.state.items.filter(i=> this.state.selected_row.some(s=> s === i.id)))
//     if(this.state.selected_item.length > 0){
//       let selected = this.state.selected_item[0];
//       let response = await this.productApi.remove_product_from_cart(selected.store_id,selected.id);
//       if(response.was_exception)
//         alert(response.message)
//       else{
//         alert("product removed succesfully")
//         this.setItems(this.state.items.filter(i=> !this.state.selected_row.some(s=> s === i.id)))
//         this.set_selected([]);
//       }
//     }else
//       alert("you should select item to add")
//   }

  buy_cart = async () =>{
      let response = await this.cartApi.buy_cart("","");
      if(response.was_exception)
        this.setSnackbar({ children: response.message, severity: 'error' });
      else
        this.setSnackbar({ children: 'buy cart worked succesfully', severity: 'success' });
  }
  

  handleCloseSnackbar = () => this.setSnackbar(null);



  edit_function = async (oldRow, newRow) =>{
    if(oldRow.quantity !== newRow.quantity)
      return await this.productApi.edit_product_quantity_in_cart(newRow.store_id,newRow.id,newRow.quantity);
    else if(oldRow.price !== newRow.price)
      return await this.productApi.edit_product_price(newRow.store_id,newRow.id,newRow.price);
    else if(oldRow.name !== newRow.name)
      return await this.productApi.edit_product_name(newRow.store_id,newRow.id,newRow.name);
    else if(oldRow.category !== newRow.category)
      return await this.productApi.edit_product_category(newRow.store_id,newRow.id,newRow.category);
  }

  remove_products = async (id) => {
        let selected = this.state.items.find(i=> id.id===i.id)
        let response = await this.productApi.remove_product_from_cart(selected.store,selected.id);
        if(response.was_exception)
            this.setSnackbar({ children: response.message, severity: 'error' });
        else{
            this.setSnackbar({ children: 'product removed successfully', severity: 'success' });
            let new_items = this.state.items.filter(i=> selected.id!==i.id)
            this.setState({items:new_items,selected_item:[]})
      }
  }

  processRowUpdate = 
    (newRow,oldRow) => {
        if(newRow.quantity < 1){
            this.setSnackbar({ children: 'item quantity must be above 0', severity: 'error' });
            return oldRow;
        }
      let new_list = this.state.items.filter(p=>p.id !== newRow.id)
      new_list.push(newRow) 
      this.setState({items:new_list})
      return newRow;
    }

  handleProcessRowUpdateError = (error) => {
    this.setState({snackbar:{ children: error.message, severity: 'error' }});
  }

  render(){
  return (
      <main>
    <div style={{horizontal:'center', height: 400, width: '100%' }}>
          <Grid horizontal='center'>
            <Row><h1 horizontal="center">Shopping cart items</h1></Row>
            <Row><h6>you can edit your products quantity by double click on quantity.</h6></Row>
          </Grid>
          <DataGrid align='center' horizontal='center' rows={this.state.items} columns={this.columns}         
                            editMode='row'
                                            //   checkboxSelection
                            onSelectionModelChange={(newSelectionModel) => {
                            this.set_row_selected(newSelectionModel);}}
                            // onEditRowsModelChange={(edit_row)=>{
                            // this.set_edited(edit_row);}}
                            processRowUpdate={this.processRowUpdate}
                            onProcessRowUpdateError={this.handleProcessRowUpdateError}
                            experimentalFeatures={{ newEditingApi: true }}
                            // selectionModel={selected_row}
                            // editRowsModel = {edited}
                            // onEditRowsModelChange={(edit_row,details)=>edit_quantity(edit_row)}
        >
          </DataGrid>
          <Grid>
            <Row><h1 style={{color: "white"}}>-------------------------</h1></Row>
            <Row>
          <Col>cart total price : {this.state.price}</Col><Col><Button width='5' variant="contained" onClick={this.buy_cart}>buy cart</Button></Col>
            </Row>
            <Row><h1 style={{color: "white"}}>-------------------------</h1></Row>
          </Grid>
          {!!this.state.snackbar && (
        <Snackbar
          open
          anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
          onClose={this.handleCloseSnackbar}
          autoHideDuration={6000}
        >
          <Alert {...this.state.snackbar} onClose={this.handleCloseSnackbar} />
        </Snackbar>
      )}
    </div>
    
    </main>
  );}
          }
