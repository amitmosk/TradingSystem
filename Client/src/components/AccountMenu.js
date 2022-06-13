import * as React from "react";
import Box from "@mui/material/Box";
import Avatar from "@mui/material/Avatar";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import ListItemIcon from "@mui/material/ListItemIcon";
import Divider from "@mui/material/Divider";
import IconButton from "@mui/material/IconButton";
import Tooltip from "@mui/material/Tooltip";
import Logout from "@mui/icons-material/Logout";
import { Button } from "@material-ui/core";
import { Link } from "react-router-dom";
import SimpleBadge from "./SimpleBadge";
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';

export default function AccountMenu({ log, state, user }) {
  console.log(user+"\n\n\n\n\n");
  console.log(user.storesManaged+"\n\n\n\n\n");
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  const logout = () =>{
    handleClose();
    log();
  }

  return (
    <React.Fragment>
      <Box sx={{ display: "flex", alignItems: "center", textAlign: "center" }}>
        <Tooltip title="Account settings">
          <IconButton
            onClick={handleClick}
            size="small"
            sx={{ ml: 2 }}
            aria-controls={open ? "account-menu" : undefined}
            aria-haspopup="true"
            aria-expanded={open ? "true" : undefined}
          >
            <Avatar sx={{ width: 32, height: 32 }}>M</Avatar>
          </IconButton>
        </Tooltip>
      </Box>
      <Menu
        anchorEl={anchorEl}
        id="account-menu"
        open={open}
        onClose={handleClose}
        // onClick={handleClose}
        PaperProps={{
          elevation: 0,
          sx: {
            overflow: "visible",
            filter: "drop-shadow(0px 2px 8px rgba(0,0,0,0.32))",
            mt: 1.5,
            "& .MuiAvatar-root": {
              width: 32,
              height: 32,
              ml: -0.5,
              mr: 1,
            },
            "&:before": {
              content: '""',
              display: "block",
              position: "absolute",
              top: 0,
              right: 14,
              width: 10,
              height: 10,
              bgcolor: "background.paper",
              transform: "translateY(-50%) rotate(45deg)",
              zIndex: 0,
            },
          },
        }}
        transformOrigin={{ horizontal: "right", vertical: "top" }}
        anchorOrigin={{ horizontal: "right", vertical: "bottom" }}
      >
        <MenuItem>
          <Avatar />{" "}
          
          <Link to={{pathname:`/ShoppingCart`}} onClick={handleClose} underline="hover">{"My Cart"} </Link>
          {/* <Link href="/ShoppingCart" underline="hover"> */}
            
        </MenuItem>
        {/* <MenuItem>
          <Avatar />{" "}
          
          <Link to={{pathname:`/ShoppingCart`}} onClick={handleClose} underline="hover">{<ShoppingCartIcon></ShoppingCartIcon>} </Link>
            
        </MenuItem> */}
        {state !== 0 ? (
          <>
            <MenuItem>
              <Avatar />
              <Link to={{pathname:`/EditProfile`}} onClick={handleClose} underline="hover">{"Edit Profile"} </Link>
              {/* <Link href="/EditProfile" underline="hover"> */}
                
            </MenuItem>
            <MenuItem>
              <Avatar />{" "}
              <Link to={{pathname:`/ViewUserPurchaseHistory`}} onClick={handleClose} underline="hover">{"My Purchases History"} </Link>
              {/* <Link href="/ViewUserPurchaseHistory" underline="hover"> */}
                
            </MenuItem>
            <MenuItem>
              <Avatar />{" "}
              <Link to={{pathname:`/UserViewQuestions`}} onClick={handleClose} underline="hover">{"My Questions"}  </Link>
              {/* <Link href="/UserViewQuestions" underline="hover"> */}
                
            </MenuItem>
            {/* <MenuItem>
              <Avatar />{" "}
              <Link to={{pathname:`/Notifications`}} onClick={handleClose} underline="hover"> {"Notifications"}</Link>
            </MenuItem> */}
            
            <MenuItem>
              <Avatar />{" "}
              <Link to={{pathname:`/MyStores`}} onClick={handleClose} underline="hover" >{'My Stores'}</Link>
            </MenuItem>
            <Divider />
            <MenuItem>
              <ListItemIcon>
                <Logout fontSize="small" />
              </ListItemIcon>
              <Button onClick={logout}>Logout</Button>
            </MenuItem>
          </>
        ) : null}
      </Menu>
    </React.Fragment>
  );
}
