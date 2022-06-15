import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { useParams } from 'react-router-dom';
import { PoliciesApi } from '../API/PoliciesApi';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import Grid from '@mui/material/Grid';

function createData(left, right) {
  return { left, right };
}


export default function BasicTable({rowss}) {
  console.log(rowss);
  rowss.map(a=> console.log(a[0]+" "+a[1]));
const policiesApi = new PoliciesApi();
const {id} = useParams();
//SnackBar
const [snackbar, setSnackbar] = React.useState(null);
const handleCloseSnackbar = () => setSnackbar(null);




const rows = [];
rowss.map(row => 
  {
    rows.push(createData(row[0], row[1]))
});

console.log(rows);
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>

          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow
              key={row.left}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell component="th" scope="row">
                {row.left}
              </TableCell>
              {/* <TableCell align="right">{row.left}</TableCell> */}
              <TableCell align="right">{row.right}</TableCell>
              
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
