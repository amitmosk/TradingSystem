import * as React from "react";
import { DataGrid } from "@mui/x-data-grid";
import Button from "@mui/material/Button";
import { ProductApi } from "../API/ProductApi";
import { Row, Col } from "react-grid-system";
import Grid from "@mui/material/Grid";
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
// import Link from "@mui/material/Button";
import { Link } from "react-router-dom";
import { map } from "ramda";
import { StoreApi } from "../API/StoreApi";
import { Component } from "react";
import IconButton from "@mui/material/IconButton";
import AddShoppingCartOutlined from "@mui/icons-material/AddShoppingCartOutlined";

export default class StoreProductsTable extends Component {
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
        headerName: "Action",
        width: 250,
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
      // {
      //   field: "Go To Product",
      //   headerName: "Go To Product",
      //   width: 250,
      //   // Important: passing id from customers state so I can delete or edit each user
      //   renderCell: (id) => (
      //     <>
      //       {/* <IconButton
      //         color="primary"
      //         aria-label="go to product page"
      //         onClick={() => this.go_to_product(id)}
      //       >
      //         <AddShoppingCartOutlined />
      //       </IconButton> */}
      //       <Link to={{pathname:`ProductPage/${1}`, state:{product_id:1, store_id:1 } }}   underline="hover" >{   <IconButton
      //         color="primary"
      //         aria-label="store"
      //       >
      //         <AddShoppingCartOutlined />
      //       </IconButton>}</Link>
      //     </>
      //   ),
      // },
      
    ];
  }
  async componentDidMount() {
    let products = await this.storeApi.get_products_by_store_id(
      this.state.store_id
    );
    let products_list = [];
    products.value.map((p) =>
      products_list.push({
        id: p.product_id,
        name: p.name,
        category: p.category,
        price: p.price,
        quantity: 0,
        store: p.store_id,
        key_words: p.key_words,
      })
    );
    this.setState({
      items: products_list,
      products: products_list,
      selected: undefined,
    });
  }

  setSnackbar = (val) => {this.setState({ snackbar: val });};
  setItems = (val) => {this.setState({ items: val });};
  set_selected = (val) => {this.setState({ selected_item: val });};
  set_row_selected = (val) => {this.setState({ selected_row: val });};
  set_edited = (val) => {this.setState({ edited: val });};

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
      this.setSnackbar({children: "item was added to cart",severity: "success",});
  };
  go_to_product = async (id) => {
    let selected = this.state.items.find((i) => id.id === i.id);
    let response = await this.productApi.add_product_to_cart(
      selected.store,//store
      selected.id,//product
      selected.quantity
    );
    if (response.was_exception)
      this.setSnackbar({ children: response.message, severity: "error" });
    else
      this.setSnackbar({children: "item was added to cart",severity: "success",});
  };
  handleCloseSnackbar = () => this.setSnackbar(null);

  processRowUpdate = (newRow, oldRow) => {
    if (newRow.quantity < 1) {
      this.setSnackbar({children: "item quantity must be above 0", severity: "error",});
      return oldRow;
    }
    let new_list = this.state.items.filter((p) => p.id !== newRow.id);
    new_list.push(newRow);
    new_list.sort((a,b)=>(a.id-b.id));
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
        </div>
      </main>
    );
  }
}
