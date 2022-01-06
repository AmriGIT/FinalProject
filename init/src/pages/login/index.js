// import Button from "@restart/ui/esm/Button";
import axios from "axios";
import React, { Component } from "react";
import { Form, Button, Container, Row, Card, Col } from "react-bootstrap";
import { connect } from "react-redux";
const BaseUrl = "http://localhost:8080/api/authenticate";
class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      isLogin: false,
      show: "",
      setShow: false,
      isloading : false,
      error :[],
      isError : false
    };
  }
  setValue = (e) => {
    this.setState({
      [e.target.name]: e.target.value,
    });
  };

  userLogin = async () => {
    const username = this.state.username;
    const password = this.state.password;
    this.setState({
        isloading : true
    })
    const user = { username, password };
    try {
      const rest = await axios.post(`${BaseUrl}`, user);
      this.setState({
        isloading : false
    })
      localStorage.setItem("token", rest.data.token)
      this.props.loginHandler(rest.data.token);
    } catch (err) {
       this.setState({
           error : err.response.data.errors,
           isloading: false,
           isError: true
       })

    }
    if(!this.props.statusLogin){
      alert("User dan Password Salah")
    }else{
      this.props.history.push("/suratkeluar")

    }
    console.log("status_Login di Login ",this.props.statusLogin);
  };
  render() {
      const text = this.state.isloading? "Loading....":"Login"
    return (
      <Container style={{ paddingTop: "15px" }}>
        <Row className="justify-content-md-center">
          <Col xs={6}>
            <Card>
              <Card.Header>Login Master</Card.Header>
              <Card.Body>
                <Card.Title>Login Master</Card.Title>
                <Form>

                  <Form.Group className="mb-3" controlId="formBasicEmail">
                    <Form.Label>Username</Form.Label>
                    <Form.Control
                      type="input"
                      name="username"
                      onChange={this.setValue}
                      placeholder="Enter email"
                    />
                  </Form.Group>

                  <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                      type="password"
                      name="password"
                      onChange={this.setValue}
                      placeholder="Password"
                    />
                  </Form.Group>
                  <Form.Group className="mb-3" controlId="formBasicCheckbox">
                    <Form.Check type="checkbox" label="Check me out" />
                  </Form.Group>
                  <Button
                    onClick={this.userLogin}
                    // onClick={this.props.loginHandler}
                    variant="primary"
                    type="button"
            
                  >
                    {text}
                  </Button>
                </Form>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    );
  }
}

const mapStateToProps = (state) => ({
  statusLogin: state.loginRedux.statusLogin,
  token: state.loginRedux.token,
});

const mapDispatchToProps = (dispatch) => ({
  loginHandler: (token) =>
    dispatch({
      type: "LOGIN_OK",
      payload: token,
    }),
});
export default connect(mapStateToProps, mapDispatchToProps)(Login);
