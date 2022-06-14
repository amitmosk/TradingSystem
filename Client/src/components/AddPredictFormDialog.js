import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';




export default function AddPredictFormDialog({time,  fields, getValues, head,option1, option2, option3, name, outlinedVar, title, submit_button , params}) {
    AddPredictFormDialog.defaultProps = {
        title: head,
        outlinedVar: "outlined",
        submit_button: "title2",
        }
        console.log(time);
  // fields.map(f=> localStorage.setItem(f, undefined));
  const[rulename, setRulename] = React.useState(null);
//   setRulename(null);
  const[value, setValue] = React.useState(null);
//   setValue(null);
  const [open, setOpen] = React.useState(false);
  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };
  const handleOption1 = event => {
    console.log("in handle submit\n");
    let ans = [];
    fields.map((f) => ans.push(localStorage.getItem(f)))
    if(time !== undefined)
    {
        ans.push(localStorage.getItem("date"));
    }
    ans.push(1)
    getValues(ans);
    setOpen(false);

  };
  const handleOption2 = event => {
    console.log("in handle submit\n");
    let ans = [];
    fields.map((f) => ans.push(localStorage.getItem(f)))
    if(time !== undefined)
    {
        ans.push(localStorage.getItem("date"));
    }
    ans.push(0)
    getValues(ans);
    setOpen(false);

  };
  const handleOption3 = event => {
    console.log("in handle submit\n");
    let ans = [];
    fields.map((f) => ans.push(localStorage.getItem(f)))
    ans.push(2)
    getValues(ans);
    setOpen(false);

  };
  const handleInputChange = event => {
    const name = event.target.name
    const value = event.target.value;
    console.log(value);
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
          {time!==undefined ? <input type="date" name="date"  onChange={handleInputChange}
                                    placeholder="mm/yyyy" required/> :null}
        </DialogContent>

        <DialogActions>
          <Button onClick={handleOption1}>{option1}</Button>
          <Button onClick={handleOption2}>{option2}</Button>
          <Button onClick={handleOption3}>{option3}</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
// }