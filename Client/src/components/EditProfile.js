import React, { useState } from 'react';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import Link from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import { UserApi } from '../API/UserApi';
import FormDialog from './FormDialog';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import { escapeLeadingUnderscores } from 'typescript';
import { User } from '../ServiceObjects/User';
import { Utils } from '../ServiceObjects/Utils';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import { Row } from "react-grid-system";
import { experimentalStyled as styled } from '@mui/material/styles';
import Paper from '@mui/material/Paper';
import { useEffect } from 'react';
import { ConnectApi } from '../API/ConnectApi';




export default function EditProfile(props) {
    const Item = styled(Paper)(({ theme }) => ({
        backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
        ...theme.typography.body2,
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.secondary,
    }));
    console.log("props");
    console.log(props);
    const [snackbar, setSnackbar] = React.useState(null);
    const handleCloseSnackbar = () => setSnackbar(null);
    const connectApi = new ConnectApi();
    const [user, setUser] = useState(props.user);
    useEffect(() => { get_online_user() }, []);
    const get_online_user = async () => {
        let response = await connectApi.get_online_user();
        console.log(response.value);
        if (!response.was_exception) {
            setUser(response.value);
            if (response.message == "The system is not available right now, come back later")
                setSnackbar({ children: response.message, severity: 'success' });

        }
        else {
            setSnackbar({ children: response.message, severity: 'error' });

        }

    }
    const name = user.name;
    const lastName = user.lastName;
    const security_question = user.security_question;
    const improve_security_fields = ["question", "answer", "password"];
    const unregister_fields = ["password"];
    const change_password_fields = ["Old Password", "New Password", "Re-Enter Password"];
    const userApi = new UserApi();

    //Handle Input Change
    const handleInputChange_name = event => {
        const value = event.target.value;
        console.log(value);
        setNewname(value);
    };
    const handleInputChange_last_name = event => {
        const value = event.target.value;
        console.log(value);
        setNewlastname(value);
    };
    const handleInputChange_old_password = event => {
        const value = event.target.value;
        console.log(value);
        setOldpassword(value);
    };
    const handleInputChange_new_password = event => {
        const value = event.target.value;
        console.log(value);
        setNewpassword(value);
    };
    const handleInputChange_reEntered_password = event => {
        const value = event.target.value;
        console.log(value);
        setReEnterpassword(value);
    };
    //Fields
    const [newname, setNewname] = useState(name);
    const [newlastname, setNewlastname] = useState(lastName);
    const [oldpassword, setOldpassword] = useState(null);
    const [newpassword, setNewpassword] = useState(null);
    const [reEnterpassword, setReEnterpassword] = useState(null);

    //Functions To Server
    const handle_name_edit = async () => {
        console.log(newname);
        let response = await userApi.edit_name(newname);
        if (!response.was_exception) {
            setSnackbar({ children: response.message, severity: 'success' });
            console.log("in edit name - success!\n");
        }
        else {
            setSnackbar({ children: response.message, severity: 'error' });
        }
    }
    const handle_last_name_edit = async () => {

        let response = await userApi.edit_last_name(newlastname);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });
        }

    }
    const unregister = async (values) => {
        const password = values[0];
        let response = await userApi.unregister(password);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            props.updateUserState(User.guest());
            window.location.href = `/`
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });
        }

    }
    const improve_security = async (values) => {
        const question = values[0];
        const answer = values[1];
        const password = values[2];
        if (Utils.check_not_empty(question) == 0) {
            this.setState({ snackbar: { children: "Question can't be ampty", severity: "error" } });
            return;
        }
        if (Utils.check_not_empty(answer) == 0) {
            this.setState({ snackbar: { children: "Answer can't be ampty", severity: "error" } });
            return;
        }

        let response = await userApi.improve_security(password, question, answer);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });
        }

    }
    const handle_password_edit = async () => {
        console.log("in handle password edit\n");

        if (newpassword == reEnterpassword) {
            let response = await this.userApi.edit_password(oldpassword, newpassword);
            if (!response.was_exception) {
                this.setState({ snackbar: { children: response.message, severity: "success" } });

            }
            else {
                this.setState({ snackbar: { children: response.message, severity: "error" } });

            }

        }
        else {
            this.setState({ snackbar: { children: "Passwords Does Not Match", severity: "error" } });
            console.log("in edit password - NOT THE SAME!\n");

        }
    }


    return (<>
        <Box sx={{ flexGrow: 1 }}>

            <h2 class="Header" align="center">
                Edit Profile
            </h2>
            <Grid container align="left" position="center" spacing={6} paddingRight={50} paddingLeft={50} paddingTop={10}>
                <Grid align="center" item xs={13}>
                    <TextField
                        autoFocus
                        id="Edit Name"
                        defaultValue={name}
                        label="Edit Name"
                        variant="outlined"
                        onChange={handleInputChange_name}

                    />
                    <Button variant="contained" onClick={handle_name_edit}>
                        Save
                    </Button>
                </Grid>

                <Grid align="center" item xs={13}>
                    <TextField
                        autoFocus
                        id="Edit Last Name"
                        defaultValue={lastName}
                        label="Edit Last Name"
                        variant="outlined"
                        onChange={handleInputChange_last_name}

                    />
                    <Button variant="contained" onClick={handle_last_name_edit}>
                        Save
                    </Button>

                </Grid>


                <Grid container position="center" spacing={6} paddingRight={50} paddingLeft={30} paddingTop={2} >
                    <Grid item xs={80}>  <Item variant="outlined"> <FormDialog outlinedVar="text"
                        fields={change_password_fields} getValues={handle_password_edit}
                        name="Change Password"></FormDialog></Item ></Grid>

                    <Grid item xs={80}>  <Item variant="outlined"> <FormDialog outlinedVar="text"
                        fields={improve_security_fields} getValues={improve_security}
                        name="Upgrade Security"></FormDialog></Item ></Grid>

                    <Grid item xs={80}>  <Item variant="outlined"> <FormDialog outlinedVar="text"
                        fields={unregister_fields} getValues={unregister}
                        name="Unregister"></FormDialog></Item ></Grid>

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
    </>)

}
