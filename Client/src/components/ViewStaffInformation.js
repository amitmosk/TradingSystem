import * as React from 'react';
import { Component } from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';

import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import FolderIcon from '@mui/icons-material/Folder';
import DeleteIcon from '@mui/icons-material/Delete';
import HomeIcon from '@mui/icons-material/Home';
import Link from '@mui/material/Button';
import PersonIcon from '@mui/icons-material/Person';
import EditIcon from '@mui/icons-material/Edit';
import { Store } from '../ServiceObjects/User';
import { StoreApi } from '../API/StoreApi';

// const staffy = [{ emaill1: "eylon@walla.com", emaill2: "eylon@eylon.eylon", type1: "typetype" },
// { emaill1: "eylon@walla.com", emaill2: "eylon@eylon.eylon", type1: "typetype" }];


const Demo = styled('div')(({ theme }) => ({
    backgroundColor: theme.palette.background.paper,
}));



export default class ViewStaffInformation extends Component {

    constructor(props) {
        super(props);
        this.state = {
            store_id:this.props.store_id,
            staff: [],
        };
        this.storeApi = new StoreApi();
        console.log("in view stuff information, id = "+this.props.store_id);



    }

    async get_staff_info() {
        console.log("get store staff info\n");

        let response = await this.storeApi.view_store_management_information(this.state.store_id);
        if (!response.was_exception) {
            console.log("in get store staff info - success!\n");
            // return response.message;
            console.log(response.value.appointmentInformationList);
            // return response;
            this.setState({ staff: response.value.appointmentInformationList });
        }
        else {
            alert(response.message);


        }
    }

    async componentDidMount() {
        this.get_staff_info();
    }

    render() {
        // const staff = (this.get_staff_info()).then((value) => value);
        // console.log("this is 1 : " + staff + "\n");
        // staff.then((value) => console.log(value.value.appointmentInformationList));

        // return ((this.get_staff_info()).then((value) =>
        return (
            // console.log("value = " + value + "\n"),
            // console.log(value.value.appointmentInformationList),
            <>
                <Link to="/">
                    <HomeIcon></HomeIcon>
                </Link>
                <Box position='center' align='center'>
                    <Grid position='center' row-spacing={3}>
                        <Grid item>
                            <h3 class="Header" align="center">
                                Staff Information
                            </h3>
                        </Grid>

                        <Grid position='center' align='center'>

                            <Demo>
                                <List>
                                    {

                                        this.state.staff.map((staf) => (
                                            <ListItem>
                                                <ListItemAvatar>
                                                    <Avatar>
                                                        <PersonIcon />
                                                    </Avatar>
                                                </ListItemAvatar>
                                                <ListItemText
                                                    primary={staf.type + ': ' + staf.member_email}
                                                    secondary={'Appointed by: ' + staf.appointer_email}
                                                // primary='fsdf'
                                                />
                                            </ListItem>

                                        ))




                                    }
                                </List>
                            </Demo>
                        </Grid>
                    </Grid>
                </Box>
            </>
        );
        // )
        // );

    }
}