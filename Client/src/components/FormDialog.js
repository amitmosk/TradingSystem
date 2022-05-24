import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';


// export default class FormDialog extends React.Component{
//     constructor(props) {
//         super(props);
//         this.state = {
//             fields:props.fields,

//         };

//     }
FormDialog.defaultProps = {
  title: "Submit",
  outlinedVar: "outlined",
  submit_button: "Submit",
}

export default function FormDialog({ fields, getValues, name, outlinedVar, title, submit_button }) {


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
        <DialogTitle>{title}</DialogTitle>
        <DialogContent>

          {/* (store_id, quantity,name, price, category, key_words) */}
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