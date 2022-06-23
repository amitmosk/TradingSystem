import * as React from "react";
import { DataGrid } from "@mui/x-data-grid";
import { ProductApi } from "../API/ProductApi";
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import { Link } from "react-router-dom";
import { StoreApi } from "../API/StoreApi";
import { Component } from "react";
import IconButton from "@mui/material/IconButton";
import AddShoppingCartOutlined from "@mui/icons-material/AddShoppingCartOutlined";
import Store from "@mui/icons-material/Store";
import MonetizationOn from "@mui/icons-material/MonetizationOn";
import FormDialog from './FormDialog';
import { Utils } from '../ServiceObjects/Utils';



export default class HomeProductsTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
      store_id: this.props.store_id,
      products: [],
      snackbar: null,
      items: [],
      selected_item: [],
      selected_row: [],
      edited: null,
      add_bid_fields:["Quantity", "Price"],
    };
    this.productApi = new ProductApi();
    this.storeApi = new StoreApi();
    this.columns = [
      { field: "id", headerName: "ID", width: 70 },
      { field: "name", headerName: "name", width: 130 },
      { field: "category", headerName: "category", width: 130 },
      { field: "price", headerName: "price", type: "double", width: 90 },
      {
        field: "quantity",
        headerName: "quantity",
        type: "number",
        width: 90,
        editable: true,
      },
      { field: "store", headerName: "store", width: 90, hide: true },
      { field: "key_words", headerName: "key words", width: 0, hide: true },
      {
        field: "action",
        headerName: "ADD TO CART",
        width: 75,
        // Important: passing id from customers state so I can delete or edit each user
        renderCell: (id) => (
          <>
            <IconButton
              color="primary"
              aria-label="add to shopping cart"
              onClick={() => this.add_to_cart(id)}
            >
              <AddShoppingCartOutlined />
            </IconButton>
          </>
        ),
      },
      {
        field: "action_store",
        headerName: "VIEW STORE",
        width: 150,
        // Important: passing id from customers state so I can delete or edit each user
        renderCell: (id) => (
          <>
              
             <Link to={{pathname:`StorePage/${this.state.items.find((i) => id.id === i.id).store}`}}   underline="hover" >{   <IconButton
              color="primary"
              aria-label="store"
            >
              <Store />
            </IconButton>}</Link> 
          </>
        ),
      },
      
      {
        
        field: "add_bid",
        headerName: "ADD BID",
        width: 150,
        // Important: passing id from customers state so I can delete or edit each user
        renderCell: (id) => (
          <>
            {!this.props.stores_managed.includes(this.state.items.find((i) => id.id === i.id).store) ?
            <FormDialog fields={this.state.add_bid_fields} getValues={this.add_bid.bind(this)} params={[this.state.items.find((i) => id.id === i.id).store,id.id]} name={   <IconButton
              color="primary"
              aria-label="store"
            >
              <MonetizationOn />
            </IconButton>}></FormDialog>: null}
          </> 
        ),
      } 
    ];
  }
  async add_bid(values)
  {
    const storeApi = new StoreApi();
    const quantity = values[0];
    const price = values[1];
    const store_id = values[2];
    const product_id = values[3];
    if (quantity == undefined || Utils.check_not_empty(quantity)==0 || Utils.check_all_digits(quantity)==0)
    {
      this.setState({ snackbar: { children: "Illegal quantity", severity: "error" } });
      return;
    }
    if (price == undefined || Utils.check_not_empty(price)==0 || Utils.check_all_digits(price)==0)
    {
      this.setState({ snackbar: { children: "Illegal price", severity: "error" } });
      return;
    }
    console.log(values);
    console.log(quantity);
    console.log(price);
    const response = await storeApi.add_bid(store_id, product_id, quantity, price);
    if (!response.was_exception) {
      this.setState({ snackbar: { children: response.message, severity: "success" } });
    }
    else {
      this.setState({ snackbar: { children: response.message, severity: "error" } });

    }

  }

  async componentDidMount() {
    let stores = await this.storeApi.get_all_stores();
    console.log("stores = " + stores.value);
    let products_list = [];
    stores.value.map((st) =>
      st.inventory.map((p) =>
        products_list.push({
          id: p.product_id,
          name: p.name,
          category: p.category,
          price: p.price,
          quantity: 0,
          store: p.store_id,
          key_words: p.key_words,
        })
      )
    );
    console.log(this.props.stores_managed);
    console.log("products = "+products_list);
    this.setState({
      items: products_list,
      products: products_list,
      selected: undefined,
    });
  }

  setSnackbar = (val) => {
    this.setState({ snackbar: val });
  };
  setItems = (val) => {
    this.setState({ items: val });
  };
  set_selected = (val) => {
    this.setState({ selected_item: val });
  };
  set_row_selected = (val) => {
    this.setState({ selected_row: val });
  };
  set_edited = (val) => {
    this.setState({ edited: val });
  };

  get_store_id = async (id) => {
    console.log("in go to store id")
    let selected = this.state.items.find((i) => id.id === i.id); // represents selected row
    let selected_store_id = selected.store; // required store_id
    return selected_store_id; 
  };

  add_to_cart = async (id) => {
    let selected = this.state.items.find((i) => id.id === i.id);
    let response = await this.productApi.add_product_to_cart(
      selected.store,
      selected.id,
      selected.quantity
    );
    if (response.was_exception)
      this.setSnackbar({ children: response.message, severity: "error" });
    else
      this.setSnackbar({
        children: "item was added to cart",
        severity: "success",
      });
  };

  handleCloseSnackbar = () => this.setSnackbar(null);

  processRowUpdate = (newRow, oldRow) => {
    if (newRow.quantity < 1) {
      this.setSnackbar({
        children: "item quantity must be above 0",
        severity: "error",
      });
      return oldRow;
    }
    let new_list = this.state.items.filter((p) => p.id !== newRow.id);
    new_list.push(newRow);
    new_list.sort((a, b) => a.id - b.id);
    this.setState({ items: new_list });
    return newRow;
  };

  handleProcessRowUpdateError = (error) => {
    this.setState({ snackbar: { children: error.message, severity: "error" } });
  };

  render() {
    return (
      <main>
        <div style={{ height: 400, width: "100%" }}>
          <h1 style={{ color: "white" }}>
            ---------------------------------------------------------
          </h1>{" "}
          <DataGrid
            rows={this.state.items}
            columns={this.columns}
            editMode="row"
            //   checkboxSelection
            onSelectionModelChange={(newSelectionModel) => {
              this.set_row_selected(newSelectionModel);
            }}
            processRowUpdate={this.processRowUpdate}
            onProcessRowUpdateError={this.handleProcessRowUpdateError}
            experimentalFeatures={{ newEditingApi: true }}
          ></DataGrid>
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
}