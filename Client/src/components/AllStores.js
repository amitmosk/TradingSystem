import React, { Component } from "react";
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import { StoreApi } from "../API/StoreApi";
import Grid from "@mui/material/Grid";
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

