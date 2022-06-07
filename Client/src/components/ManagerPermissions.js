import * as React from 'react';
import Grid from '@mui/material/Grid';
import List from '@mui/material/List';
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
function not(a, b) {
    return a.filter((value) => b.indexOf(value) === -1);
}

function intersection(a, b) {
    return a.filter((value) => b.indexOf(value) !== -1);
}

function union(a, b) {
    return [...a, ...not(b, a)];
}

export default function ManagerPermissions() {
    
    const storeApi = new StoreApi();
    const [snackbar, setSnackbar] = React.useState(null);
    const handleCloseSnackbar = () => setSnackbar(null);
    const {id, user_email} = useParams();
    
    const get_permissionss = async () =>{
        const response = await storeApi.get_permissions(user_email, id);
        if (!response.was_exception) {
            setSnackbar({ children: response.message, severity: 'success' });   
        }
        else {
            setSnackbar({ children: response.message, severity: 'error' });   

        }
    }
    useEffect(()=>{get_permissionss()}, []);
    
    const [checked, setChecked] = React.useState([]);
    const [left, setLeft] = React.useState(["Add Item", "Remove Item", "Edit Item Name", "Edit Item Price",
        "Edit Item Category", "Edit Item Keywords", "View Permissions", "View User Questions",
        "Edit Store Policy", "Edit Discount Policy", "Edit Purchase Policy", "View Purchase History",
        "Close Store Temporarily", "Open Closed Store", "Add Manager", "Remove Manager",
        "Add Owner", "Remove Owner", "Edit Permissions", "View Bids Status","Answer Bid Offer",
        "Answer Bid Offer Negotiate",   ]);
    const [right, setRight] = React.useState([]);

    const leftChecked = intersection(checked, left);
    const rightChecked = intersection(checked, right);
    

    const handleToggle = (value) => () => {
        const currentIndex = checked.indexOf(value);
        const newChecked = [...checked];

        if (currentIndex === -1) {
            newChecked.push(value);
        } else {
            newChecked.splice(currentIndex, 1);
        }

        setChecked(newChecked);
    };

    const numberOfChecked = (items) => intersection(checked, items).length;

    const handleToggleAll = (items) => () => {
        if (numberOfChecked(items) === items.length) {
            setChecked(not(checked, items));
        } else {
            setChecked(union(checked, items));
        }
    };

    const handleCheckedRight = () => {
        setRight(right.concat(leftChecked));
        setLeft(not(left, leftChecked));
        setChecked(not(checked, leftChecked));
    };

    const handleCheckedLeft = () => {
        setLeft(left.concat(rightChecked));
        setRight(not(right, rightChecked));
        setChecked(not(checked, rightChecked));
    };
    const permissions_dict = {add_item :0,
        remove_item:1,
        edit_item_name:2,
        edit_item_price:3,
        edit_item_category:4,
        edit_item_keywords:5,
        edit_item_quantity:6,
        view_permissions:7,
        view_users_questions:8,
        edit_store_policy:9,
        edit_discount_policy:10,
        edit_purchase_policy:11,
        view_purchases_history:12,
        close_store_temporarily:13,
        open_close_store:14,
        add_manager:15,
        remove_manager:16,
        add_owner:17,
        remove_owner:18,
        edit_permissions:19,
        view_bids_status:20,
        answer_bid_offer:21,
        answer_bid_offer_negotiate:22,};
    const handleInputChange = event => {
        const name = event.target.name
        const value = event.target.value;
        console.log(value);
        console.log(name);
        localStorage.setItem(name, value);
    
        
        };   
    const hadleSubmit = async () => {
        let permissions = [];
        right.map((per) => {
            if (per === "Add Item") {
                permissions.push("add_item");
            }
            else if (per === "Remove Item") {
                permissions.push("remove_item");
            }
            else if (per === "Edit Item Name") {
                permissions.push("edit_item_name");
            }
            else if (per === "Edit Item Price") {
                permissions.push("edit_item_price");
            }
            else if (per === "Edit Item Category") {
                permissions.push("edit_item_category");
            }
            else if (per === "Edit Item Keywords") {
                permissions.push("edit_item_keywords");
            }
            else if (per === "View Permission") {
                permissions.push("view_permissions");
            }
            else if (per === "View User Questions") {
                permissions.push("view_users_questions");
            }
            else if (per === "Edit Store Policy") {
                permissions.push("edit_store_policy");
            }
            else if (per === "Edit Discount Policy") {
                permissions.push("edit_discount_policy");
            }
            else if (per === "Edit Purchase Policy") {
                permissions.push("edit_purchase_policy");
            }

            else if (per === "View Purchase History") {
                permissions.push("view_purchases_history");
            }

            else if (per === "Close Store Temporarily") {
                permissions.push("close_store_temporarily");
            }
            else if (per === "Open Closed Store") {
                permissions.push("open_close_store");
            }
            else if (per === "Add Manager") {
                permissions.push("add_manager");
            }
            else if (per === "Remove Manager") {
                permissions.push("remove_manager");
            }
            else if (per === "Add Owner") {
                permissions.push("add_owner");
            }
            else if (per === "Remove Owner") {
                permissions.push("remove_owner");
            }
            else if (per === "Edit Permissions") {
                permissions.push("edit_permissions");
            }
            else if (per === "View Bids Status") {
                permissions.push("view_bids_status");
            }
            else if (per === "Answer Bid Offer") {
                permissions.push("answer_bid_offer");
            }
            else if (per === "Answer Bid Offer Negotiate") {
                permissions.push("answer_bid_offer_negotiate");
            }

    


        })
        console.log(permissions);
        let permissions_numbers = [];
        permissions.map((p)=> permissions_numbers.push(permissions_dict[p]));
        console.log(permissions_numbers);
        console.log(id);
        if (permissions_numbers.length == 0 )
        {
            setSnackbar({ children: "Have to choose al least one permission", severity: 'error' });   
            return;
        }
        const user_email=localStorage.getItem("user_email");
        if (Utils.check_email(user_email) == 0)
        {
            setSnackbar({ children: "Illegal email", severity: 'error' });   
            return;
        }
        let permissions_str = "";
        permissions_numbers.map((p)=>{
            permissions_str = permissions_str.concat(p.toString()).concat("/")
        });
        console.log(permissions_str)
        // storeApi.edit_manager_permissions(user_email, store_id, permissions_numbers)
        const response = await storeApi.edit_manager_permissions(user_email, id, permissions_str);
        if (!response.was_exception) {
            setSnackbar({ children: response.message, severity: 'success' });   
            console.log("in edit_manager_permissions - success!\n");
        }
        else {
            setSnackbar({ children: response.message, severity: 'error' });   

        }
    };

    const customList = (title, items) => (
        <Card>
            <CardHeader
                paddingTop={2}
                sx={{ px: 2, py: 1 }}
                avatar={
                    <Checkbox
                        onClick={handleToggleAll(items)}
                        checked={numberOfChecked(items) === items.length && items.length !== 0}
                        indeterminate={
                            numberOfChecked(items) !== items.length && numberOfChecked(items) !== 0
                        }
                        disabled={items.length === 0}
                        inputProps={{
                            'aria-label': 'all items selected',
                        }}
                    />
                }
                title={title}
                subheader={`${numberOfChecked(items)}/${items.length} selected`}
            />
            <Divider />
            <List
                sx={{
                    width: 300,
                    height: 330,
                    bgcolor: 'background.paper',
                    overflow: 'auto',
                    paddingTop: 2,
                }}
                dense
                component="div"
                role="list"
            >
                {items.map((value) => {
                    const labelId = `transfer-list-all-item-${value}-label`;

                    return (
                        <ListItem
                            key={value}
                            role="listitem"
                            button
                            onClick={handleToggle(value)}
                        >
                            <ListItemIcon>
                                <Checkbox
                                    checked={checked.indexOf(value) !== -1}
                                    tabIndex={-1}
                                    disableRipple
                                    inputProps={{
                                        'aria-labelledby': labelId,
                                    }}
                                />
                            </ListItemIcon>
                            <ListItemText id={labelId} primary={`${value}`} />
                        </ListItem>
                    );
                })}
                <ListItem />
            </List>
        </Card>
    );

    return (
        <>

            <Grid container spacing={3} justifyContent="center" alignItems="center">

                <Grid item>{customList('Permission Choices', left)}</Grid>
                <Grid item>
                    <Grid container direction="column" alignItems="center">
                        <Button
                            sx={{ my: 0.5 }}
                            variant="outlined"
                            size="small"
                            onClick={handleCheckedRight}
                            disabled={leftChecked.length === 0}
                            aria-label="move selected right"
                        >
                            &gt;
                        </Button>
                        <Button
                            sx={{ my: 0.5 }}
                            variant="outlined"
                            size="small"
                            onClick={handleCheckedLeft}
                            disabled={rightChecked.length === 0}
                            aria-label="move selected left"
                        >
                            &lt;
                        </Button>
                    </Grid>
                </Grid>
                <Grid item>{customList('Permissions Chosen', right)}</Grid>
                <Grid item>

                </Grid>
            </Grid>
            <Button variant="contained" onClick={hadleSubmit}>Submit</Button>
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
