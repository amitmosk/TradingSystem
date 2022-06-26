import * as React from 'react';
import { DataGrid } from '@mui/x-data-grid';
import Button from '@mui/material/Button';
import { ProductApi } from '../API/ProductApi';
import { Row, Col } from 'react-grid-system';
import Grid from '@mui/material/Grid';
import Snackbar from '@mui/material/Snackbar';
import Alert from '@mui/material/Alert';
import Link from '@mui/material/Button';
import { map } from 'ramda';
import { StoreApi } from '../API/StoreApi';

const columns = [
  { field: 'id', headerName: 'ID', width: 70 },
  { field: 'name', headerName: 'name', width: 130 },
  { field: 'category', headerName: 'category', width: 130 },
  { field: 'price', headerName: 'price', type: 'double', width: 90, },
  { field: 'quantity', headerName: 'quantity', type: 'number', width: 90, editable: true },
  { field: 'store', headerName: 'store', width: 90, hide: true },
  { field: 'key_words', headerName: 'key words', width: 0, hide: true },];

const make_rows = (res) => {
  let ret = []
  res.map(p => { ret.push({ id: p.product_id, name: p.name, category: p.category, price: p.price, quantity: 0, store: 1, key_words: ["hi", "hello"] }) });
  return ret;
}

const productApi = new ProductApi();
const storeApi = new StoreApi();


export default function HomeProducts() {
  let get_all_products = async () => {
    console.log("getting stores : \n\n")
    let stores = await storeApi.get_all_stores();
    console.log(stores)
    return [];
  }

  const prods = get_all_products();

  const [items, setItems] = React.useState(prods);
  const [selected_item, set_selected] = React.useState([]);
  const [selected_row, set_row_selected] = React.useState([]);
  const [edited, set_edited] = React.useState([]);
  const remove_products = async () => {
    set_selected(items.filter(i => selected_row.some(s => s === i.id)))
    if (selected_item.length > 0) {
      let selected = selected_item[0];
      let response = await productApi.remove_product_from_cart(selected.store_id, selected.id);
      if (response.was_exception)
        setSnackbar({ children: response.message, severity: 'error' });
      // alert(response.message)
      else {
        setSnackbar({ children: "product removed succesfully", severity: 'success' });
        // alert("product removed succesfully")
        setItems(items.filter(i => !selected_row.some(s => s === i.id)))
        set_selected([]);
      }
    } else
      setSnackbar({ children: "you should select item to add", severity: 'error' });
    alert("you should select item to add")
  }

  const add_to_cart = async () => {
    set_selected(items.filter(i => selected_row.some(s => s === i.id)))
    if (selected_item.length > 0) {
      let selected = selected_item[0];
      let response = await productApi.add_product_to_cart(selected.store_id, selected.id, selected.quantity);
      if (response.was_exception)
        setSnackbar({ children: response.message, severity: 'error' });
      // alert(response.message)
      else
        setSnackbar({ children: "product added succesfully", severity: 'success' });
      // alert("product added succesfully")
    } else
      setSnackbar({ children: "you should select item to add", severity: 'error' });
    // alert("you should select item to add")
  }

  const [snackbar, setSnackbar] = React.useState(null);

  const handleCloseSnackbar = () => setSnackbar(null);

  const edit_function = async (oldRow, newRow) => {
    if (oldRow.quantity != newRow.quantity)
      return await productApi.edit_product_quantity_in_cart(newRow.store_id, newRow.id, newRow.quantity);
    else if (oldRow.price != newRow.price)
      return await productApi.edit_product_price(newRow.store_id, newRow.id, newRow.price);
    else if (oldRow.name != newRow.name)
      return await productApi.edit_product_name(newRow.store_id, newRow.id, newRow.name);
    else if (oldRow.category != newRow.category)
      return await productApi.edit_product_category(newRow.store_id, newRow.id, newRow.category);
  }

  const processRowUpdate = React.useCallback(
    (newRow, oldRow) => {
      // Make the HTTP request to save in the backend
      let response = edit_function(oldRow, newRow);
      //   setSnackbar({ children: 'User successfully saved', severity: 'success' });
      console.log(newRow)
      return response;
    },
    [productApi.edit_product_quantity_in_cart],
  );

  const handleProcessRowUpdateError = React.useCallback((error) => {
    setSnackbar({ children: error.message, severity: 'error' });
  }, []);

  return (
    <main>
      <div style={{ height: 400, width: '100%' }}>
        <Grid>
          <Row>
            <Col align='left'><Button size="small" width='5' variant="contained" onClick={add_to_cart}>add selected product to cart</Button>
              <Link variant="contained" size="small" href="/" store_id={selected_row}>view store information</Link>
            </Col>
          </Row>
        </Grid>
        <DataGrid rows={items} columns={columns}
          editMode='row'
          onSelectionModelChange={(newSelectionModel) => {
            set_row_selected(newSelectionModel);
          }}
          onEditRowsModelChange={(edit_row) => {
            set_edited(edit_row);
          }}
          processRowUpdate={processRowUpdate}
          onProcessRowUpdateError={handleProcessRowUpdateError}
          experimentalFeatures={{ newEditingApi: true }}

        >
        </DataGrid>
        {!!snackbar && (
          <Snackbar
            open
            anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
            onClose={handleCloseSnackbar}
            autoHideDuration={6000}
          >
            <Alert {...snackbar} onClose={handleCloseSnackbar} />
          </Snackbar>
        )}
      </div>

    </main>
  );
}
