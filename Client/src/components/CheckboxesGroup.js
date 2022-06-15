//DELETE this page
import * as React from 'react';
import Box from '@mui/material/Box';
import FormLabel from '@mui/material/FormLabel';
import FormControl from '@mui/material/FormControl';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormHelperText from '@mui/material/FormHelperText';
import Checkbox from '@mui/material/Checkbox';

export default function CheckboxesGroup({list, l}) {
  // console.log(list);
  // console.log(l);
  const [state, setState] = React.useState({ list
    // {list.map((n)=> n.toString() : false)}
    
    // gilad: true,
    // jason: false,
    // antoine: false,
  });
//   list.map((n)=> {const [state, setState] = React.useState({n:false})})
const check_state = ()=>
{
  let count=0;
  for (const [key, value] of Object.entries(state.list)) {
    // console.log(key);
    // console.log(value);
    if(value)
    {
      count=9;
    }
  }
  return count;
}  
const handleChange = (event) => {
    // console.log(event.target.name);
    // console.log(event.target.checked);
    let updatedState={};
    for (const [key, value] of Object.entries(state.list)) {
      console.log(key);
    console.log(value);
      updatedState.list[key] = value;
    }
    
    updatedState.list[event.target.name] = event.target.checked
    console.log(updatedState)
    setState(updatedState);
    // setState({
    //   ...state,
    //   [event.target.name]: event.target.checked,
    // });
    // const c = check_state();
    // console.log(c);
  };
//   list.map((n)=> {const {n.toString()} = state})
//   const error = list.filter((v) => v).length !== 2;

  // const {} = state;
  // const { gilad, jason, antoine } = state;
  const error = l.filter((v) => v).length !== 2;

  return (
    <Box sx={{ display: 'flex' }}>
      <FormControl sx={{ m: 3 }} component="fieldset" variant="standard">
        <FormLabel component="legend">Assign responsibility</FormLabel>
        <FormGroup>
          {l.map(a=>
            <FormControlLabel
            control={
              <Checkbox checked={state.list[a]} onChange={handleChange} name={a} />
            }
            label={a}
          />
            )}

          {/* <FormControlLabel
            control={
              <Checkbox checked={gilad} onChange={handleChange} name="gilad" />
            }
            label="Gilad Gray"
          />
          <FormControlLabel
            control={
              <Checkbox checked={jason} onChange={handleChange} name="jason" />
            }
            label="Jason Killian"
          />
          <FormControlLabel
            control={
              <Checkbox checked={antoine} onChange={handleChange} name="antoine" />
            }
            label="Antoine Llorca"
          /> */}
        </FormGroup>
      </FormControl>
      
    </Box>
  );
}
