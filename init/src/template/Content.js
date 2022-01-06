import React, { Component } from "react";
import { Route, Switch } from "react-router-dom";
import { connect } from "react-redux";
import { Disposisi, Home, Login, Logout, SuratKeluar, SuratMasuk } from "../pages";
// import Eparkir2 from '../pages/eparkir2';

class Content extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      selectedUser: -1,
      cekin: "",
      harga: 0,
      currentCount: 0,
      data2: [],
      isLogin: false,
      username: null,
      password: null,
    };
  }
  statusLogin = (status) => {
    this.setState({
      isLogin: status,
    });
  };
  addButton = (newUser) => {
    const newData = this.state.data;
    newData.push(newUser);
    this.setState({
      data: newData,
    });
    // this.props.goToPage("contact");
  };

  editButton = (newUser) => {
    // data unique => findIndex
    const { selectedUser, data: oldData } = this.state;

    oldData.splice(selectedUser, 1, newUser);
    this.setState({
      data: oldData,
      selectedUser: -1,
    });
    this.props.goToPage("contact");
  };

  updateSelectedUser = (idx) => {
    this.setState({
      selectedUser: idx,
    });
    this.props.goToPage("login");
  };
  halamaneparkir = (e) => {
    this.props.goToPage("cls");
    this.setState({
      cekin: e,
    });
  };
  cekout = (newParkir) => {
    console.log(newParkir);
    this.setState(
      {
        harga: newParkir.harga,
        currentCount: newParkir.currentCount,
      },
      this.result
    );
    this.props.goToPage("eparkir");
    return newParkir;
  };
  result = () => {
    const { harga, currentCount, cekin } = this.state;
    const data2 = [harga, currentCount, cekin];
    this.setState({
      data2: data2,
    });
    console.log(data2);
  };
  // componentDidMount =()=>{
  // }

  componentDidMount() {
    this.setState({
      isLogin: this.props.sts2,
    });

    const datauser = [
      {
        username: "Admin",
        password: "1234",
        address: "Jakarta",
      },
      {
        username: "User",
        password: "1234",
        address: "Bogor",
      },
      {
        username: "Operator",
        password: "1234",
        address: "Depok",
      },
    ];
    this.setState({
      data: datauser,
    });
  }
  render() {
    return (
      <Switch>
        <Route path="/" exact component={Home} />
        {!this.props.statusLogin ? (
          <Route
            path="/login-master"
            children={(props) => <Login {...props} />}
          />
        ) : (
          ""
        )}
        {this.props.statusLogin ? (
          <Route
            path="/suratkeluar"
            children={(props) => <SuratKeluar {...props} />}
          />
        ) : (
          ""
        )}
        {this.props.statusLogin ? (
          <Route
            path="/suratmasuk"
            children={(props) => <SuratMasuk {...props} />}
          />
        ) : (
          ""
        )}
        {this.props.statusLogin ? (
          <Route
            path="/disposisi"
            children={(props) => <Disposisi {...props} />}
          />
        ) : (
          ""
        )}
        {this.props.statusLogin ? (
          <Route
            path="/logout"
            children={(props) => <Logout {...props} />}
          />
        ) : (
          ""
        )}

        <Route children={() => <h1> Page Not Found</h1>} />
      </Switch>
    );
  }
}
const mapStateToProps = (state) => ({
  statusLogin: state.loginRedux.statusLogin,
});
export default connect(mapStateToProps)(Content);
