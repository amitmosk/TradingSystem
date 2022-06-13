import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { useParams } from 'react-router-dom';
import { useEffect } from 'react';
import { PoliciesApi } from '../API/PoliciesApi';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import Grid from '@mui/material/Grid';

function createData(name, calories, fat, carbs, protein) {
  return { name, calories, fat, carbs, protein };
}


export default function BasicTable() {
const policiesApi = new PoliciesApi();
const {id} = useParams();
//SnackBar
const [snackbar, setSnackbar] = React.useState(null);
const handleCloseSnackbar = () => setSnackbar(null);



//Getters
const [purchases,setPurchases ] =React.useState(["purchase1", "purchase2", "purchase3"]);
const [predicts,setPredicts ] =React.useState(["predict1", "predict2", "predict3"]);
const [discounts,setDiscounts ] =React.useState(["discount1", "discount2", "discount3"]);

const get_purchase_policy = async () => {
    const response = policiesApi.get_purchase_policy(id);
    if(!response.was_exception)
    {
        setSnackbar({ children: response.message, severity: 'success' });     }
    else{
        setSnackbar({ children: response.message, severity: 'error' }); 
    }
}
const send_predicts = async () => {
    const response = policiesApi.send_predicts(id);
    if(!response.was_exception)
    {
        setSnackbar({ children: response.message, severity: 'success' });     }
    else{
        setSnackbar({ children: response.message, severity: 'error' }); 
    }
}
const get_discount_policy = async () => {
    const response = policiesApi.get_discount_policy(id);
    if(!response.was_exception)
    {
        setSnackbar({ children: response.message, severity: 'success' });     }
    else{
        setSnackbar({ children: response.message, severity: 'error' }); 
    }
}
useEffect(()=>{get_purchase_policy()}, []);
useEffect(()=>{send_predicts()}, []);
useEffect(()=>{get_discount_policy()}, []);



const rows = [
  createData('Frozen yoghurt', 159, 6.0, 24, 4.0),
  createData('Ice cream sandwich', 237, 9.0, 37, 4.3),
  createData('Eclair', 262, 16.0, 24, 6.0),
  createData('Cupcake', 305, 3.7, 67, 4.3),
  createData('Gingerbread', 356, 16.0, 49, 3.9),
];
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Dessert (100g serving)</TableCell>
            <TableCell align="right">Calories</TableCell>
            <TableCell align="right">Fat&nbsp;(g)</TableCell>
            <TableCell align="right">Carbs&nbsp;(g)</TableCell>
            <TableCell align="right">Protein&nbsp;(g)</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow
              key={row.name}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell component="th" scope="row">
                {row.name}
              </TableCell>
              <TableCell align="right">{row.calories}</TableCell>
              <TableCell align="right">{row.fat}</TableCell>
              <TableCell align="right">{row.carbs}</TableCell>
              <TableCell align="right">{row.protein}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
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
    </TableContainer>
        
  );
}
