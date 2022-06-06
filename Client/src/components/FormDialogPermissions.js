import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';


FormDialogPermissions.defaultProps = {
  title: "Submit",
  outlinedVar: "outlined",
  submit_button: "Submit",
}

export default function FormDialogPermissions({ fields, getValues, name, outlinedVar, title, submit_button }) {

    // const add_item =0 ;
    // const remove_item=1
    // const edit_item_name=2;
    // const edit_item_price=3;
    // const edit_item_category=4;
    // const edit_item_keywords=5;
    // const edit_item_quantity=6;
    // const view_permissions=7;
    // const view_users_questions=8;
    // const edit_store_policy=9;
    // const edit_discount_policy=10;
    // const edit_purchase_policy=11;
    // const view_purchases_history=12;
    // const close_store_temporarily=13;
    // const open_close_store=14;
    // const add_manager=15
    // const remove_manager=16;
    // const add_owner=17;
    // const remove_owner=18;
    // const edit_permissions=19;
    const permissions = {add_item :0,
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
        edit_permissions:19};
    const keys = [];
    for (var key in permissions) {
      if (permissions.hasOwnProperty(key)) {
        keys.push(key);
      }
    }
localStorage.setItem("permission_selected", "add_item");

  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };



  const handleClose = () => {
    setOpen(false);
  };
  const handleSumbit = event => {
    console.log("in handle submit\n");
    let ans = [];
    fields.map((f) => ans.push(localStorage.getItem(f)))
    ans.push(localStorage.getItem("permission_selected"));
    getValues(ans);
    setOpen(false);

  };
  const handleInputChange = event => {
    const name = event.target.name
    const value = event.target.value;
    console.log(value);
    console.log(name);
    localStorage.setItem(name, value);

    
  };

  return (
    <div>
      <Button variant={outlinedVar} onClick={handleClickOpen}>
        {name}
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>{title}</DialogTitle>
        <DialogContent>

          {fields.map((field) => (
            <TextField
              autoFocus
              margin="dense"
              id="name"
              name={field}
              label={field}
              type="storeid"
              fullWidth
              variant="standard"
              onChange={handleInputChange}
            />
          ))}
               
                    <select name="permission_selected"  onChange={handleInputChange}  required>
                    {keys.map((p) => (<option value={p}>{p}</option>))}
                </select>
                  

       





        </DialogContent>

        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleSumbit}>{submit_button}</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
// }