import React, { Component } from "react";
// import Header from './template/Header';
// import Navbar from './template/Navbar';
// import Content from './template/Content';
import { Header, Navbar, Content } from "./template";
// import "./App.css"
import { BrowserRouter as Router } from "react-router-dom";
import { Container, Row, Spinner } from "react-bootstrap";
import { connect } from "react-redux";
import axios from "axios";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      menu: "home",
      isLoading: true,
    };
  }
  getData = async () => {
    const token = localStorage.getItem("token");
    try{
        const res = await axios.get("http://localhost:8080/api/getkeluar", {
          headers: {
            Authorization: "Bearer " + token,
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Headers":
              "Origin, X-Requested-With, Content-Type, Accept",
            "Access-Control-Allow-Credentials": "true",
          },
        });
         console.log("iki", res.data);
         this.props.loginHandler(token);
        this.setState({
          isLoading:false
        })

    }catch(err){
      this.setState({
        isLoading:false
      })
      localStorage.removeItem('token')
      // alert("Login Ulang")
      
    }

  };
  componentDidMount() {
    // setTimeout(this.checkingToken, 3500);
    setTimeout(this.getData,3000)
    // this.getData()
    // console.log(this.props.statusLogin)
    console.log("ikisko app",this.props.suratkeluar)
  }
  render() {
    if (this.state.isLoading) {
      return (
        <Container>
          <Row className="justify-content-md-center">
            <Spinner animation="grow" />
          </Row>
        </Container>
      );
    }
    return (
      <Router>
        <Container>
          <Header />
          <Navbar />
          <Content />
        </Container>
      </Router>
    );
  }
}
const mapStateToProps = (state) => ({
  statusLogin: state.statusLogin,
  token: state.loginRedux.token,
  data: state.dataRedux.data,
  suratkeluar : state.addMasuk.data
});
const mapDispatchToProps = dispatch =>({
  loginHandler: token => dispatch({
    type : "LOGIN_OK",
    payload: token,
  }),
  dataHandler : data => dispatch({
    type : "GETALL",
    data : data
  })
})

export default connect(mapStateToProps, mapDispatchToProps)(App);
