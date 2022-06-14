// import React, { Component } from 'react';
// import TextField from '@mui/material/TextField';
// import HomeIcon from '@mui/icons-material/Home';
// import Box from '@mui/material/Box';
// import Link from '@mui/material/Button';
// import Grid from '@mui/material/Grid';
// import Button from '@mui/material/Button';
// import { UserApi } from '../API/UserApi';
// import FormDialog from './FormDialog';
// import Snackbar from "@mui/material/Snackbar";
// import Alert from "@mui/material/Alert";
// import { escapeLeadingUnderscores } from 'typescript';




// export default function EditProfile() {

//     const [snackbar, setSnackbar] = React.useState(null);
//     const handleCloseSnackbar = () => setSnackbar(null);
    
    
//     constprops.name;
//     lastName: this.props.lastName;
//     security_question: this.props.security_question;
//     improve_security_fields: ["question", "answer","password"];
//     unregister_fields: ["password"];
//     const userApi = new UserApi();
       
//     }


//     const handle_name_edit = async(event)=>  {
//         console.log("in handle name edit\n");
//         const new_name = localStorage.getItem("Edit Name");
//         let response = await userApi.edit_name(new_name);
//         if (!response.was_exception) {
//             setSnackbar({ children: response.message, severity: 'success' });
//             console.log("in edit name - success!\n");
//         }
//         else {
//             setSnackbar({ children: response.message, severity: 'error' });
//         }
//     }


    
//     async handle_last_name_edit(event) {

//         console.log("in handle last name edit\n");
//         let new_name = localStorage.getItem("Edit Last Name");
//         let response =  await this.userApi.edit_last_name(new_name);
        
//         if (!response.was_exception) {
//             this.setState({ snackbar: { children: response.message, severity: "success" } });
//         }
//         else {    
//             this.setState({ snackbar: { children: response.message, severity: "error" } });
//         }
    
//     }

//     const unregister  = async (values)=>{
//         const password = values[0];
//         let response = await this.userApi.unregister(password);
//         if (!response.was_exception) {
//             this.setState({ snackbar: { children: response.message, severity: "success" } });
//             // TODO: set as a guest & go to home page.
//         }
//         else
//         {
//             this.setState({ snackbar: { children: response.message, severity: "error" } });
//         }

//     }
//     const improve_security = async(values) =>{
//         const question = values[0];
//         const answer = values[1];
//         const password = values[2];

//         let response =  await userApi.improve_security(password, question, answer);
//         if (!response.was_exception) {
//             this.setState({ snackbar: { children: response.message, severity: "success" } });
//         }
//         else {    
//             this.setState({ snackbar: { children: response.message, severity: "error" } });
//         }

//     }
    
//     const handle_password_edit = async(event) => {
//         console.log("in handle password edit\n");
//         let new_password = localStorage.getItem("Edit Password");
//         let new_password_check = localStorage.getItem("Re-Enter Password");
//         let old_password = localStorage.getItem("Old Password");
//         if (new_password == new_password_check) {
//             let response =  await this.userApi.edit_password(old_password, new_password);
            
//             if (!response.was_exception) {
//                 this.setState({ snackbar: { children: response.message, severity: "success" } });
    
//             }
//             else {
//                 this.setState({ snackbar: { children: response.message, severity: "error" } });
    
//             }
    
//         }
//         else {
//             this.setState({ snackbar: { children: "Passwords Does Not Match", severity: "error" } });
//             console.log("in edit password - NOT THE SAME!\n");
    
//         }
//     }


//         return (<>
//             <Box sx={{ flexGrow: 1 }}>

//                 <h3 class="Header" align="center">
//                     Edit Profile
//                 </h3>
//                 <Grid container align="left" position="center" spacing={6} paddingRight={50} paddingLeft={50} paddingTop={10}>
//                     <Grid align="center" item xs={13}>
//                         <TextField
//                             autoFocus
//                             id="Edit Name"
//                             defaultValue = {this.props.get_state().name}
//                             label="Edit Name"
//                             variant="outlined"
//                             onChange={handleInputChange}

//                         />
//                         <Button variant="contained" onClick={this.handle_name_edit}>
//                             Save
//                         </Button>
//                     </Grid>

//                     <Grid align="center" item xs={13}>
//                         <TextField
//                             autoFocus
//                             id="Edit Last Name"
//                             defaultValue = {this.props.get_state().lastName}
//                             label="Edit Last Name"
//                             variant="outlined"
//                             onChange={handleInputChange}

//                         />
//                         <Button variant="contained" onClick={this.handle_last_name_edit}>
//                             Save
//                         </Button>

//                     </Grid>
//                     <Grid align="center" item xs={13}>
//                         <TextField
//                             autoFocus
//                             id="Edit Password"
//                             label="Edit Password"
//                             variant="outlined"
//                             onChange={handleInputChange}

//                         />
                      

//                     </Grid>
//                     <Grid align="center" item xs={13}>
//                         <TextField
//                                 autoFocus
//                                 id="Re-Enter Password"
//                                 label="Re-Enter Password"
//                                 variant="outlined"
//                                 onChange={handleInputChange}

//                             />
                       

//                     </Grid>
//                     <Grid align="center" item xs={13}>
//                         <TextField
//                                     autoFocus
//                                     id="Old Password"
//                                     label="Old Password"
//                                     variant="outlined"
//                                     onChange={handleInputChange}

//                                 />
                        

//                     </Grid>
//                     <Grid align="center" item xs={13}>
//                         <Button variant="contained" onClick={this.handle_last_name_edit}>
//                             Save
//                         </Button>

//                     </Grid>
//                     {/* <Grid align="center" item xs={13}>
//                         <TextField
//                             autoFocus
//                             id="Edit Password"
//                             label="Edit Password"
//                             variant="outlined"
//                             onChange={handleInputChange}

//                         />
//                         </Grid>
//                         <Grid align="center" item xs={13}>
//                             <TextField
//                                 autoFocus
//                                 id="Re-Enter Password"
//                                 label="Re-Enter Password"
//                                 variant="outlined"
//                                 onChange={handleInputChange}

//                             />
//                             </Grid>

//                             <Grid item align="center">
//                                 <TextField
//                                     autoFocus
//                                     id="Old Password"
//                                     label="Old Password"
//                                     variant="outlined"
//                                     onChange={handleInputChange}

//                                 />
//                                 <Button variant="contained" onClick={this.handle_password_edit}>
//                                     Save
//                                 </Button>

//                             </Grid> */}
//                             <Grid><FormDialog outlinedVar="text" 
//                             fields={improve_security_fields} getValues={improve_security}
//                              name="Upgrade Security"></FormDialog></Grid> 
//                         <Grid><FormDialog outlinedVar="text" 
//                             fields={unregister_fields} getValues={unregister}
//                              name="unregister"></FormDialog></Grid> 
//                         </Grid>
                    
//             </Box>
            
//             {!!snackbar && (
//             <Snackbar
//             open
//             anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
//             onClose={handleCloseSnackbar}
//             autoHideDuration={6000}
//             >
//             <Alert {...snackbar} onClose={handleCloseSnackbar} />
//             </Snackbar>
//         )}
//         </>)
    
// }




import React, { Component } from 'react';
import TextField from '@mui/material/TextField';
import HomeIcon from '@mui/icons-material/Home';
import Box from '@mui/material/Box';
import Link from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import { UserApi } from '../API/UserApi';
import FormDialog from './FormDialog';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import { escapeLeadingUnderscores } from 'typescript';



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
            improve_security_fields: ["question", "answer","password"],
            unregister_fields: ["password"],
            snackbar: null,
        }
        console.log("start edit profile");
        this.userApi = new UserApi();
        this.handle_name_edit = this.handle_name_edit.bind(this);
        this.handle_last_name_edit = this.handle_last_name_edit.bind(this);
        this.handle_password_edit = this.handle_password_edit.bind(this);
    }


    async handle_name_edit(event)  {
        console.log("in handle name edit\n");
        const new_name = localStorage.getItem("Edit Name");
        let response = await this.userApi.edit_name(new_name);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in edit name - success!\n");
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });
        }
    }


    
    async handle_last_name_edit(event) {

        console.log("in handle last name edit\n");
        let new_name = localStorage.getItem("Edit Last Name");
        let response =  await this.userApi.edit_last_name(new_name);
        
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
        }
        else {    
            this.setState({ snackbar: { children: response.message, severity: "error" } });
        }
    
    }

    async unregister(values){
        const password = values[0];
        let response = await this.userApi.unregister(password);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            // TODO: set as a guest & go to home page.
        }
        else
        {
            this.setState({ snackbar: { children: response.message, severity: "error" } });
        }

    }
    async improve_security(values){
        const question = values[0];
        const answer = values[1];
        const password = values[2];

        let response =  await this.userApi.improve_security(password, question, answer);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
        }
        else {    
            this.setState({ snackbar: { children: response.message, severity: "error" } });
        }

    }
    
    async handle_password_edit(event) {
        console.log("in handle password edit\n");
        let new_password = localStorage.getItem("Edit Password");
        let new_password_check = localStorage.getItem("Re-Enter Password");
        let old_password = localStorage.getItem("Old Password");
        if (new_password == new_password_check) {
            let response =  await this.userApi.edit_password(old_password, new_password);
            
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
    async componentDidMount() {
        console.log("did mount in edit profile : ");
        console.log(this.props.get_state());
      
    }

    render() {
        return (<>
            <Box sx={{ flexGrow: 1 }}>

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
                      

                    </Grid>
                    <Grid align="center" item xs={13}>
                        <TextField
                                autoFocus
                                id="Re-Enter Password"
                                label="Re-Enter Password"
                                variant="outlined"
                                onChange={handleInputChange}

                            />
                       

                    </Grid>
                    <Grid align="center" item xs={13}>
                        <TextField
                                    autoFocus
                                    id="Old Password"
                                    label="Old Password"
                                    variant="outlined"
                                    onChange={handleInputChange}

                                />
                        

                    </Grid>
                    <Grid align="center" item xs={13}>
                        <Button variant="contained" onClick={this.handle_last_name_edit}>
                            Save
                        </Button>

                    </Grid>
                    {/* <Grid align="center" item xs={13}>
                        <TextField
                            autoFocus
                            id="Edit Password"
                            label="Edit Password"
                            variant="outlined"
                            onChange={handleInputChange}

                        />
                        </Grid>
                        <Grid align="center" item xs={13}>
                            <TextField
                                autoFocus
                                id="Re-Enter Password"
                                label="Re-Enter Password"
                                variant="outlined"
                                onChange={handleInputChange}

                            />
                            </Grid>

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

                            </Grid> */}
                            <Grid><FormDialog outlinedVar="text" 
                            fields={this.state.improve_security_fields} getValues={this.improve_security.bind(this)}
                             name="Upgrade Security"></FormDialog></Grid> 
                        <Grid><FormDialog outlinedVar="text" 
                            fields={this.state.unregister_fields} getValues={this.unregister.bind(this)}
                             name="unregister"></FormDialog></Grid> 
                        </Grid>
                    
            </Box>
            
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
        </>)
    }
}