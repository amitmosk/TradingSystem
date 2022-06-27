import React, { Component } from "react";
import Button from "@mui/material/Button";
import Link from "@mui/material/Button";
import HomeIcon from "@mui/icons-material/Home";
import { ConnectApi } from "../API/ConnectApi";
import Register from "./Register.js";
import Box from "@mui/material/Box";
import ImageListItem from "@mui/material/Box";
import ImageList from "@mui/material/Box";
import { StoreApi } from "../API/StoreApi";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import ListSubheader from "@mui/material/ListSubheader";
import { CartApi } from "../API/CartApi";
import MenuListComposition from "./MenuListComposition";
import { Container, Row, Col } from "react-grid-system";
import { Paper } from "@mui/material";
import { Typography } from "@mui/material";
import Rating from "@mui/material/Rating";
import BasicRating from "./Rating";
import ShoppingCart from "./ShoppingCart";
import { ThirtyFpsRounded } from "@mui/icons-material";
import Grid from "@mui/material/Grid";
import FormDialog from "./FormDialog";
import Card from "@mui/material/Card";
import { experimentalStyled as styled } from "@mui/material/styles";
import { AdminApi } from "../API/AdminApi";
import { Navigate } from "react-router-dom";
import { User } from "../ServiceObjects/User";
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === "dark" ? "#1A2027" : "#fff",
  ...theme.typography.body2,
  padding: theme.spacing(2),
  textAlign: "center",
  color: theme.palette.text.secondary,
}));

const GUEST = 0;
const ASSIGN_USER = 1;
const ADMIN = 2;

export default class ViewStat extends Component {
  static displayName = ViewStat.name;
  constructor(props) {
    super(props);
    this.state = {
      stat: undefined,
      init_system_time: undefined,
      login_per_minutes: undefined,
      logout_per_minutes: undefined,
      connect_per_minutes: undefined,
      register_per_minutes: undefined,
      buy_cart_per_minutes: undefined,
      num_of_users: undefined,
      num_of_onlines: undefined,
      num_of_guests: undefined,
      num_of_non_managers_and_owners: undefined,
      managers_but_not_owners_or_founders: undefined,
      owners_or_founders: undefined,
      got_input: true,
      snackbar: null,
    };
    this.adminApi = new AdminApi();
  }

  async componentDidMount() {
    console.log("in get market stat\n");
    let response = await this.adminApi.get_market_stats();
    console.log("response = " + response);
    const stats = response.value;
    // alert(response.message);
    if (!response.was_excecption) {
      console.log("in get market stats - success!\n");
      console.log(response);
      if (response.message == "The system is not available right now, come back later")
        this.setState({ snackbar: { children: response.message, severity: "success" } });
      this.setState({
        //snackbar: { children: response.message, severity: "success" },
        init_system_time: stats.init_system_time,
        login_per_minutes: stats.login_per_minutes,
        logout_per_minutes: stats.logout_per_minutes,
        connect_per_minutes: stats.connect_per_minutes,
        register_per_minutes: stats.register_per_minutes,
        buy_cart_per_minutes: stats.buy_cart_per_minutes,
        num_of_onlines: stats.num_of_onlines,
        num_of_users: stats.num_of_users,
        num_of_guests: stats.num_of_guests,
        num_of_non_managers_and_owners: stats.num_of_non_managers_and_owners,
        managers_but_not_owners_or_founders: stats.managers_but_not_owners_or_founders,
        owners_or_founders: stats.owners_or_founders,
        got_input: true,
      });
    } else {
      console.log("Error View Stat");
      this.setState({ got_input: false });
      return (
        <>
          <Navigate to="/" />
        </>
      );
    }
  }

  render() {
    const { redirectTo } = this.state;
    return (
      <main class="LoginMain">
        <div class="LoginWindow">
          <row>
            <h3>Market Statistics</h3>
          </row>
          {/* {[0,1,2,3,4,5,6,7].map((item) => (
                            <Card >
                            {item}
                            </Card>
                        ))} */}
          <Card>
            <div> init system time: {this.state.init_system_time}</div>
            <div> login per minutes: {this.state.login_per_minutes}</div>
            <div> logout per minutes: {this.state.logout_per_minutes}</div>
            <div> connect per minutes: {this.state.connect_per_minutes}</div>
            <div> register per minutes: {this.state.register_per_minutes}</div>
            <div> buy cart per minutes: {this.state.buy_cart_per_minutes}</div>
            <div> number of online users: {this.state.num_of_onlines}</div>
            <div> number of users: {this.state.num_of_users}</div>
            <div> number of guests: {this.state.num_of_guests}</div>
            <div>
              {" "}
              number of users that are not owners or managers:{" "}
              {this.state.num_of_non_managers_and_owners}
            </div>
            <div>
              {" "}
              number of users that are managers but now owners:{" "}
              {this.state.managers_but_not_owners_or_founders}
            </div>
            <div>
              {" "}
              number of users that are owners or founders:{" "}
              {this.state.owners_or_founders}
            </div>
          </Card>
          {/* <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.answer_user_questions_fields} getValues={this.manager_answer_question.bind(this)} name="Answer Users Questions"></FormDialog></Item ></Grid> */}
        </div>
        {!!this.state.snackbar && (
          <Snackbar
            open
            anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
            onClose={this.handleCloseSnackbar}
            autoHideDuration={6000}
          >
            <Alert
              {...this.state.snackbar}
              onClose={this.handleCloseSnackbar}
            />
          </Snackbar>
        )}
      </main>
    );
  }
}
