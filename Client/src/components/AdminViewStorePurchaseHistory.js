import * as React from 'react';
import Grid from '@mui/material/Grid';
import List from '@mui/material/List';
import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import ListItemIcon from '@mui/material/ListItemIcon';
import Checkbox from '@mui/material/Checkbox';
import Button from '@mui/material/Button';
import Divider from '@mui/material/Divider';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import { StoreApi } from '../API/StoreApi';
import { useParams } from 'react-router-dom';
import { Utils } from '../ServiceObjects/Utils';
import { useEffect } from 'react';
import { AdminApi } from '../API/AdminApi';
import Box from '@mui/material/Box';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import Avatar from '@mui/material/Avatar';
import PersonIcon from '@mui/icons-material/Person';

const Demo = styled('div')(({ theme }) => ({
    backgroundColor: theme.palette.background.paper,
}));

export default function AdminViewStorePurchaseHistory() {

    const [snackbar, setSnackbar] = React.useState(null);
    const handleCloseSnackbar = () => setSnackbar(null);
    const {id} = useParams();
    const [purchaseHistory, setPurchaseHistory] = React.useState([]);
    useEffect(()=>{admin_view_store_purchases_history()}, []);
    const adminApi = new AdminApi();
    const admin_view_store_purchases_history = async () =>{
        // const response = await adminApi.admin_view_store_purchases_history(id);
        // if(!response.was_exception)
        // {
        //     setSnackbar({ children: response.message, severity: 'success' });
        //     console.log(response.value);
        //     setPurchaseHistory(response.value);
        // }
        // else{
        //     setSnackbar({ children: response.message, severity: 'error' });

        // }
    }
    return (
        <>
        <Grid container spacing={3} justifyContent="center" alignItems="center" paddingTop={10}>
            <h3 align="center">Purchase History - Store {id}</h3>      
            </Grid>

            <Box position='center' align='center'>
                    <Grid position='center' row-spacing={3}>
                  

                        <Grid position='center' align='center'>

                            <Demo>
                                <List>
                                    {purchaseHistory.length!==0 ? purchaseHistory.map((staf) => (
                                            <ListItem>
                                                <ListItemAvatar>
                                                    <Avatar>
                                                        <PersonIcon />
                                                    </Avatar>
                                                </ListItemAvatar>
                                                <ListItemText
                                                    primary={"ffff"}
                                                    secondary={"fffffff"}
                                                // primary='fsdf'
                                                />
                                            </ListItem>

                                        )) : <h3 style={{ color: 'red' }}>No Purchase History</h3>




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
        </>
    );
}