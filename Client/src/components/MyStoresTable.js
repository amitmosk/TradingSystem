import * as React from "react";
import { DataGrid } from "@mui/x-data-grid";
import { ProductApi } from "../API/ProductApi";
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import { Link } from "react-router-dom";
import { StoreApi } from "../API/StoreApi";
import { Component } from "react";
import IconButton from "@mui/material/IconButton";
import { Navigate } from 'react-router-dom'; 
import Store from "@mui/icons-material/Store";
import { FaceRetouchingOffSharp } from "@mui/icons-material";
import { ConnectApi } from "../API/ConnectApi";
export default class MyStoresTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
      snackbar: null,
      selected_item: [],
      selected_row: [],
      edited: null,
      my_stores:[],
      stores:[],

    };
    this.productApi = new ProductApi();
    this.storeApi = new StoreApi();
    this.connectApi = new ConnectApi();
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
              
             <Link to={{pathname:`StorePage/${id.id}`}}  underline="hover" >{   <IconButton
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
  async componentDidMount()
  {
    const my_stores = [];
    let response = await this.connectApi.get_online_user();
    if (!response.was_exception)
    {
      const user = response.value;
      const store_list_ids = user.storesManaged; 
      // console.log(store_list_ids)
      response = await this.storeApi.get_all_stores();
      if (!response.was_exception)
      {
          this.setState({ snackbar: { children: response.message, severity: "success" } });
          let all_stores = response.value;
          all_stores.map((st)=> 
          {
            if (store_list_ids.includes(st.store_id))
            {
              // console.log(st)
              my_stores.push(st);
            }

          }
          )
          console.log(my_stores);
          this.setState({my_stores : my_stores})
          console.log(this.state.my_stores);
      }
      else{
          this.setState({ snackbar: { children: response.message, severity: "error" } });
      }

    }
    else{
      this.setState({ snackbar: { children: response.message, severity: "error" } });
      return (<Navigate to="/"/>)
    }
    console.log(my_stores); 
    let stores_list=[]
    my_stores.map((st) =>{
            stores_list.push({
                id: st.store_id,
                name: st.name,
                founder: st.founder_email,
                foundation_date:st.foundation_date         
              })    
    });
    console.log(stores_list);
    this.setState({
    stores: stores_list,
    selected: undefined,
    });   
    

  }

  async get_my_stores()
  {
    let response = await this.connectApi.get_online_user();
    if (!response.was_exception)
    {
      const user = response.value
      const store_list_ids = user.storesManaged; 
      // console.log(store_list_ids)
      response = await this.storeApi.get_all_stores();
      if (!response.was_exception)
      {
          this.setState({ snackbar: { children: response.message, severity: "success" } });
          let all_stores = response.value;
          let my_stores = [];
          all_stores.map((st)=> 
          {
            if (store_list_ids.includes(st.store_id))
            {
              // console.log(st)
              my_stores.push(st);
            }

          }
          )
          console.log(my_stores);
          this.setState({my_stores : my_stores})
          console.log(this.state.my_stores);
      }
      else{
          this.setState({ snackbar: { children: response.message, severity: "error" } });
      }

    }
    else{
      this.setState({ snackbar: { children: response.message, severity: "error" } });
      return (<Navigate to="/"/>)
    }
    
  }

  async a() {
    // this.get_my_stores();
    console.log(this.state.my_stores); 
    let stores_list=[]
    this.state.my_stores.map((st) =>{
            stores_list.push({
                id: st.store_id,
                name: st.name,
                founder: st.founder_email,
                foundation_date:st.foundation_date         
              })    
    });
    console.log(stores_list);
    this.setState({
    stores: stores_list,
    selected: undefined,
    });    
  }

  setSnackbar = (val) => {
    this.setState({ snackbar: val });
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

  handleCloseSnackbar = () => this.setSnackbar(null);

  render() {
    return (
      <main>
        <div style={{ height: 400, width: "100%" }}>
          <h1 style={{ color: "white" }}>
            ---------------------------------------------------------
          </h1>{" "}
          <DataGrid
            rows={this.state.stores}
            columns={this.columns}
            editMode="row"
            //   checkboxSelection
            onSelectionModelChange={(newSelectionModel) => {
              this.set_row_selected(newSelectionModel);
            }}
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