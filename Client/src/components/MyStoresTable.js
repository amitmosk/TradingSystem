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
export default class MyStoresTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
      stores: [],
      snackbar: null,
      items: [],
      selected_item: [],
      selected_row: [],
      edited: null,
      snackbar: null,

    };
    this.productApi = new ProductApi();
    this.storeApi = new StoreApi();
    this.columns = [
      { field: "id", headerName: "ID", width: 70 },
      { field: "name", headerName: "name", width: 130 },
      { field: "founder", headerName: "Founder", width: 150 },
      { field: "foundation_date", headerName: "Foundation Date", width: 150 },
      {
        field: "storeInformation",
        headerName: "Store Information",
        width: 150,
        // Important: passing id from customers state so I can delete or edit each user
        renderCell: (id) => (
          <>
            {/* <IconButton
              color="primary"
              aria-label="store"
              // onClick={() => this.go_to_store_page(id)}
              onClick={() => this.go_to_store_page(id)}
            >
              <Store />
            </IconButton> */}
              
             <Link to={{pathname:`StorePage/${this.state.items.find((i) => id.id === i.id).id}`}}  underline="hover" >{   <IconButton
              color="primary"
              aria-label="store"
            >
              <Store />
            </IconButton>}</Link> 
          </>
        ),
      },
     
     
     
    ];
  }

  async componentDidMount() {
    const response = await this.storeApi.get_all_stores();
    if (!response.was_exception)
    {
        this.setState({ snackbar: { children: response.message, severity: "success" } });
        let stores = response.value;
        console.log("stores = "+stores);
        let stores_list=[]
        stores.map((st) =>{
            if (st.store_id == this.props.store_id)
            {
                stores_list.push({
                    id: st.store_id,
                    name: st.name,
                    founder: st.founder_email,
                    foundation_date:st.foundation_date
                  
                  
                  })

            }
        }
          
           
        
            );
        this.setState({
        items: stores_list,
        stores: stores_list,
        selected: undefined,
        });

    }
    else{
        this.setState({ snackbar: { children: response.message, severity: "error" } });

    }
    
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

        </div>
      </main>
    );
  }
}