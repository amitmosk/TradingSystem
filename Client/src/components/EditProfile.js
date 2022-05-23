// import * as React from 'react';
import React, { Component } from 'react';
import TextField from '@mui/material/TextField';
import HomeIcon from '@mui/icons-material/Home';
import Box from '@mui/material/Box';
import Link from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import { UserApi } from '../API/UserApi';
import { Alert } from '@mui/material';
import Typography from '@material-ui/core/Typography';


// EditProfile.defaultProps = {
//     outlinedVar: "outlined",
//     fields: ["Edit Name", "Edit Last Name", "Edit Password", ""],
// };



const handleInputChange = event => {

    const id = event.target.id;
    const value = event.target.value;
    localStorage.setItem(id, value);
};

export default class EditProfile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: this.props.name,
            lastName: this.props.lastName,
            security_question: this.props.security_question,

        }
        console.log("start edit profile");
        this.userApi = new UserApi();
        // this.handleInputChange = this.handleInputChange.bind(this);
        this.handle_name_edit = this.handle_name_edit.bind(this);
        this.handle_last_name_edit = this.handle_last_name_edit.bind(this);
        this.handle_password_edit = this.handle_password_edit.bind(this);
    }


    async handle_name_edit(event)  {
        console.log("in handle name edit\n");
        const new_name = localStorage.getItem("Edit Name");
        let response = await this.userApi.edit_name(new_name);
        alert(response.message);
        if (!response.was_exception) {
            console.log("in edit name - success!\n");
        }
        else {
        }
    }


    async handle_name_edit_premium(event)  {
        console.log("in handle name edit premium\n");
        const answer_of_premium_question = this.get_answer_of_security_question();
        
        const new_name = localStorage.getItem("Edit Name");
        let response = await this.userApi.edit_name_premium(new_name);
        alert(response.message);
        if (!response.was_exception) {
            console.log("in edit name - success!\n");
        }
        else {
        }
    }
    
    async handle_last_name_edit(event) {
        console.log("in handle last name edit\n");
        let new_name = localStorage.getItem("Edit Last Name");
        let response =  await this.userApi.edit_last_name(new_name);
        alert(response.message);
        if (!response.was_exception) {
            //show history
        }
        else {    
        }
    
    }
    
    async handle_password_edit(event) {
        console.log("in handle password edit\n");
        let new_password = localStorage.getItem("Edit Password");
        let new_password_check = localStorage.getItem("Re-Enter Password");
        let old_password = localStorage.getItem("Old Password");
        if (new_password == new_password_check) {
            let response =  await this.userApi.edit_password(old_password, new_password);
            alert(response.message);
            if (!response.was_exception) {
    
            }
            else {
    
            }
    
        }
        else {
            console.log("in edit password - NOT THE SAME!\n");
    
        }
    }
    async componentDidMount() {
        console.log("did mount in edit profile : ");
        console.log(this.props.get_state());
      
    }

    render() {
        return (<>
            <Box sx={{ flexGrow: 1 }}>
                <Link href="/">
                    <HomeIcon></HomeIcon>
                </Link>
                <h3 class="Header" align="center">
                    Edit Profile
                </h3>
                <Grid container align="left" position="center" spacing={6} paddingRight={50} paddingLeft={50} paddingTop={10}>
                    <Grid align="center" item xs={13}>
                        <TextField
                            autoFocus
                            id="Edit Name"
                            defaultValue = {this.props.get_state().name}
                            label="Edit Name"
                            variant="outlined"
                            onChange={handleInputChange}

                        />
                        <Button variant="contained" onClick={this.handle_name_edit}>
                            Save
                        </Button>
                    </Grid>

                    <Grid align="center" item xs={13}>
                        <TextField
                            autoFocus
                            id="Edit Last Name"
                            defaultValue = {this.props.get_state().lastName}
                            label="Edit Last Name"
                            variant="outlined"
                            onChange={handleInputChange}

                        />
                        <Button variant="contained" onClick={this.handle_last_name_edit}>
                            Save
                        </Button>

                    </Grid>
                    <Grid align="center" item xs={13}>
                        <TextField
                            autoFocus
                            id="Edit Password"
                            label="Edit Password"
                            variant="outlined"
                            onChange={handleInputChange}

                        />
                        <Grid align="center" item>
                            <TextField
                                autoFocus
                                id="Re-Enter Password"
                                label="Re-Enter Password"
                                variant="outlined"
                                onChange={handleInputChange}

                            />

                            <Grid item align="center">
                                <TextField
                                    autoFocus
                                    id="Old Password"
                                    label="Old Password"
                                    variant="outlined"
                                    onChange={handleInputChange}

                                />
                                <Button variant="contained" onClick={this.handle_password_edit}>
                                    Save
                                </Button>

                            </Grid>
                            {/* <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" 
                            fields={this.state.improve_security_fields} getValues={this.improve_security.bind(this)}
                             name="Upgrade Security"></FormDialog></Item></Grid> */}
                        </Grid>
                    </Grid>
                </Grid>

            </Box>
        </>)
    }
}