import * as React from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import FolderIcon from '@mui/icons-material/Folder';
import DeleteIcon from '@mui/icons-material/Delete';
import { ContactsOutlined } from '@mui/icons-material';
import { Button } from '@mui/material';



const Demo = styled('div')(({ theme }) => ({
  backgroundColor: theme.palette.background.paper,
}));

export default function InteractiveList({name, list, icon, remove}) {
    console.log(list);
const [toremove, setToremove] = React.useState(null);
  const [dense, setDense] = React.useState(false);
  const [secondary, setSecondary] = React.useState(false);
  const generate = (element)=> {
    return list.map((value) =>
      React.cloneElement(element, {
        key: value,
      }),
    );
  }
  const remove_item = (item) =>{
      remove(item)

  }
  
  return (
    <Box sx={{ flexGrow: 1, maxWidth: 752 }}>
     

       
      <Grid container spacing={2}>
        
        <Grid item xs={12} md={6}>
          <Typography sx={{ mt: 4, mb: 2 }} variant="h6" component="div">
            {name}
          </Typography>
          <Demo>
            <List dense={dense}>
              {list.length !==0 ? list.map((value)=>
              
                <ListItem key = {value}
                secondaryAction={
                  <IconButton edge="end" aria-label="delete">
                    <Button onClick={()=>remove_item(value)}><DeleteIcon /></Button>
                  </IconButton>
                }
              >
                <ListItemAvatar>
                  <Avatar>
                  {icon}
                    
                  </Avatar>
                </ListItemAvatar>
                <ListItemText
                  primary={value}
                  secondary={secondary ? 'Secondary text' : null}
                />
              </ListItem>
              ) : <h8 style={{ color: 'red' }}> No {name} To This Store</h8>
            
              } 
            </List>
          </Demo>
        </Grid>
      </Grid>
    </Box>
  );
}
