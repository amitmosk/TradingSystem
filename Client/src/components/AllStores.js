import React, { Component } from "react";
import { Link } from "react-router-dom";
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import HomeIcon from "@mui/icons-material/Home";
import Box from "@mui/material/Box";
import { StoreApi } from "../API/StoreApi";
import List from "@mui/material/List";
import MenuListComposition from "./MenuListComposition";
import { Paper } from "@mui/material";
import { Typography } from "@mui/material";
import BasicRating from "./Rating";
import Grid from "@mui/material/Grid";
import FormDialog from "./FormDialog";
import StoreProductsTable from "./StoreProductsTable";
import AllStoresTable from "./AllStoresTable";
export default class AllStores extends Component {
    static displayName = AllStores.name;
    constructor(props) {
        super(props);
        this.state = {
            snackbar: null,
        }
        this.storeApi = new StoreApi();
       
    }
    async componentDidMount() {

    }
    render() {
        const stores = this.state.stores;
        const { redirectTo } = this.state;
        return (
            <div className="LoginWindow">
              <Grid
                container
                direction="column"
                justifyContent="center"
                alignItems="center"
              >
 
                <h3>Market Stores</h3>
          
              </Grid>
              <AllStoresTable
              ></AllStoresTable>
              
   

              
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
            
        );
      }
}

