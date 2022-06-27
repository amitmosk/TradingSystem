import * as React from 'react';
import Box from '@mui/material/Box';
import Rating from '@mui/material/Rating';
import Typography from '@mui/material/Typography';
import { RateReview } from '@mui/icons-material';

export default function BasicRating({to_rate, rating, params, color}) {
  console.log(params);
  const [value, setValue] = React.useState(0);
  const RATE ="Rate ";
  
  return (
    <Box
      sx={{
        '& > legend': { mt: 2 },
      }}
    >
      <Typography component="legend"><h5 style={{ color: color }}>{RATE+to_rate}</h5></Typography>
      <Rating
        name="simple-controlled"
        value={value}
        onChange={(event, newValue) => {
          setValue(newValue);
          rating(newValue, (params[0]),(params[1]) );
        }}
      />
    </Box>
   
  );
}
