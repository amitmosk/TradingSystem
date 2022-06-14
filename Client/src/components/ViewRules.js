import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import { styled } from '@mui/material/styles';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import { useParams } from 'react-router-dom';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import List from '@mui/material/List';
import BasicTable from './BasicTable';
import CollapsibleTable from './CollapsibleTable';
const Demo = styled('div')(({ theme }) => ({
    backgroundColor: theme.palette.background.paper,
}));


export default function ViewRules({ }) {
    const [snackbar, setSnackbar] = React.useState(null);
    const handleCloseSnackbar = () => setSnackbar(null);
  

  return (
    <div>
           
    <Box position='center' align='center'>
            <Grid position='center' row-spacing={3}>
                <Grid item>
                    <h3 class="Header" align="center">
                        Store Policies Rules
                    </h3>
                </Grid>

                <Grid position='center' align='center'>

                    <Demo>
                        <List>
                            {
                                // <BasicTable></BasicTable>
                                <CollapsibleTable></CollapsibleTable>
                            }
                        </List>
                    </Demo>
                </Grid>
            </Grid>
        </Box>
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
  );
}
// }