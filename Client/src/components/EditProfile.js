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

const handle_name_edit = event => {
    console.log("in handle name edit\n");
    const new_name = localStorage.getItem("Edit Name");
    // getValues(ans);
    //  const new_name = ans[0];
    const response = this.userApi.edit_name(new_name);
    if (!response.was_execption) {
        console.log("in edit name - success!\n");
        alert("Success, last name saved.");

        //show history
    }
    else {
        alert("Action Failed.");

    }
}

const handle_last_name_edit = event => {
    console.log("in handle last name edit\n");
    //let ans = [];
    //    fields.map((f) => ans.push(localStorage.getItem(f)))
    // getValues(ans);
    let new_name = localStorage.getItem("Edit Last Name");
    // const new_name = ans[0];
    let response = this.userApi.edit_last_name(new_name);
    if (!response.was_execption) {
        console.log("in edit last name - success!\n");
        alert("Success, last name saved.");

        //show history
    }
    else {
        alert("Action Failed.");

    }

}

const handle_password_edit = event => {
    console.log("in handle password edit\n");
    //let ans = [];
    //    fields.map((f) => ans.push(localStorage.getItem(f)))
    // getValues(ans);
    let new_password = localStorage.getItem("Edit Password");
    let new_password_check = localStorage.getItem("Re-Enter Password");
    let old_password = localStorage.getItem("Old Password");

    if (new_password == new_password_check) {
        // const new_name = ans[0];
        const response = this.userApi.edit_password(old_password, new_password);
        if (!response.was_execption) {
            console.log("in edit password - success!\n");
            //show history
            alert("Success, new password saved.");

        }
        else {
            alert("Fail, passwords do not match.");

        }

    }
    else {
        console.log("in edit password - NOT THE SAME!\n");
        alert("Fail, passwords do not match.");

    }
}

const handleInputChange = event => {

    const id = event.target.id;
    const value = event.target.value;
    localStorage.setItem(id, value);
};

export default class EditProfile extends Component {
    constructor(props) {
        super(props);
        this.state = {
        }
        this.userApi = new UserApi();
        //  this.handleInputChange = this.handleInputChange.bind(this);
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
                            label="Edit Name"
                            variant="outlined"
                            onChange={handleInputChange}

                        />
                        <Button variant="contained" onClick={handle_name_edit}>
                            Save
                        </Button>
                    </Grid>

                    <Grid align="center" item xs={13}>
                        <TextField
                            autoFocus
                            id="Edit Last Name"
                            label="Edit Last Name"
                            variant="outlined"
                            onChange={handleInputChange}

                        />
                        <Button variant="contained" onClick={handle_last_name_edit}>
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
                                <Button variant="contained" onClick={handle_password_edit}>
                                    Save
                                </Button>

                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>

            </Box>
        </>)
    }
}