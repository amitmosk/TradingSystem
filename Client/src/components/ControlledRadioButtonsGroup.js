import * as React from 'react';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import { useEffect } from 'react';

export default function ControlledRadioButtonsGroup({list, name, save}) {
    // console.log(list);
  const [value, setValue] = React.useState('female');
  const handleChange = (event) => {
    console.log(event.target.value);
    setValue(event.target.value);
    save(event.target.value);
  };

  return (
    <FormControl>
      <FormLabel id="demo-controlled-radio-buttons-group">{name}</FormLabel>
      <RadioGroup
        aria-labelledby="demo-controlled-radio-buttons-group"
        name="controlled-radio-buttons-group"
        value={value}
        onChange={handleChange}
      >
          {list.map(a=> <FormControlLabel value={a} control={<Radio />} label={a}/>)}
          {/* {list.map(a => <FormControlLabel value={"a"+a.toString()} control={<Radio />} label={"{a}"+a.toString()}/>)} */}
          {/* {list.map((a)=>{<FormControlLabel value={"a"+a.toString()} control={<Radio />} label={"{a}"+a.toString()}/>})} */}
         {/* <FormControlLabel value="female" control={<Radio />} label="Female" />
        <FormControlLabel value="male" control={<Radio />} label="Male" /> */}
      </RadioGroup>
    </FormControl>
  );
}
