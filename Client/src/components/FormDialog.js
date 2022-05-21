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

export default function FormDialog() {
 
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };
  const handleSumbit = () => {
    console.log("in handle submit\n");
    
    //   console.log("in handle submit");
    setOpen(false);
  };

  return (
    <div>
      <Button variant="outlined" onClick={handleClickOpen}>
        Open form dialog
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Subscribe</DialogTitle>
        <DialogContent>
          <DialogContentText>
            To subscribe to this website, please enter your email address here. We
            will send updates occasionally.
          </DialogContentText>
          {/* (store_id, quantity,name, price, category, key_words) */}
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Store ID"
            type="storeid"
            fullWidth
            variant="standard"
          />
                    <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Quantity"
            type="quantity"
            fullWidth
            variant="standard"
          />
                    <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Name"
            type="name"
            fullWidth
            variant="standard"
          />
                    <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Price"
            type="price"
            fullWidth
            variant="standard"
          />
           <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Category"
            type="category"
            fullWidth
            variant="standard"
          />
           <TextField
            autoFocus
            margin="dense"
            id="name"
            label="KeyWords"
            type="keywords"
            fullWidth
            variant="standard"
          />
        </DialogContent>
        
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleSumbit}>Subscribe</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
// }