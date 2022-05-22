import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

FormDialogPurchase.defaultProps = {
  outlinedVar: "outlined",
}

export default function FormDialogPurchase({ fields, getValues, name, outlinedVar }) {


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
    console.log("aaaaaaaaaaaaaa\n");
    getValues(ans);
    console.log("bbbbbbbbbbbbbbbb\n");
    setOpen(false);

  };
  const handleInputChange = event => {
    // console.log('Click');

    const name = event.target.name
    const value = event.target.value;
    localStorage.setItem(name, value);
  };

  return (
    <div>
      <Button variant={outlinedVar} onClick={handleClickOpen}>
        {name}
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Submit</DialogTitle>
        <DialogContent>

           <select name="option" value={this.state.option} onChange={this.handleInputChange} required>
                            <option value="name">name</option>
                            <option value="category">category</option>
                            <option value="keywords">keywords</option>
                        </select>





        </DialogContent>

        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleSumbit}>Submit</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
// }