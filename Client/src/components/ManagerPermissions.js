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
    const [snackbar, setSnackbar] = React.useState(null);
    const {store_id, user_email} = useParams();
    const [checked, setChecked] = React.useState([]);
    const [left, setLeft] = React.useState(["Add Item", "Remove Item", "Edit Item Name", "Edit Item Price",
        "Edit Item Category", "Edit Item Keywords", "View Permissions", "View User Questions",
        "Edit Store Policy", "Edit Discount Policy", "Edit Purchase Policy", "View Purchase History",
        "Close Store Temporarily", "Open Closed Store", "Add Manager", "Remove Manager",
        "Add Owner", "Remove Owner", "Edit Permissions"]);
    const [right, setRight] = React.useState([]);

    const leftChecked = intersection(checked, left);
    const rightChecked = intersection(checked, right);
    const storeApi = new StoreApi();

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

    const hadleSubmit = () => {
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


        })

        storeApi.edit_manager_permissions(user_email, store_id, permissions)
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
        </>
    );
}
