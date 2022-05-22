import * as React from 'react';
import Box from '@mui/material/Box';
import Rating from '@mui/material/Rating';
import Typography from '@mui/material/Typography';
import { RateReview } from '@mui/icons-material';

export default function BasicRating(to_rate, rating) {
  const [value, setValue] = React.useState(2);
  const RATE ="Rate ";
  to_rate.rating(value);
  return (
    <Box
      sx={{
        '& > legend': { mt: 2 },
      }}
    >
      <Typography component="legend"><div>{RATE+to_rate.to_rate}</div></Typography>
      <Rating
        name="simple-controlled"
        value={value}
        onChange={(event, newValue) => {
          setValue(newValue);
        }}
      />
    </Box>
   
  );
}
