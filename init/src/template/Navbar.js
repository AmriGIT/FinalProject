import React, { Component } from "react";
import { Row, Navbar as Navbarbro, Container, Nav } from "react-bootstrap";
import { connect } from "react-redux";
import Menu from "../components/menu";

class Navbar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isLogin: false,
    };
  }

  // componentDidMount =()=>{
  //   this.setState({
  //     isLogin : this.props.sts2
  //   })
  // }
  render() {
    return (
      <Row>
        <Navbarbro style={{ backgroundColor: "#439889" }} variant="dark">
          <Container>
            <Nav className="me-auto">
              <Menu target="/">Home</Menu>
              {!this.props.statusLogin ? (
                <Menu target="/login-master">Login Master</Menu>
              ) : (
                ""
              )}
              {this.props.statusLogin ? (
                <Menu target="/suratkeluar">Surat Keluar</Menu>
              ) : (
                ""
              )}
              {this.props.statusLogin ? (
                <Menu target="/suratmasuk">Surat Masuk</Menu>
              ) : (
                ""
              )}
              {this.props.statusLogin ? (
                <Menu target="/disposisi">Disposisi</Menu>
              ) : (
                ""
              )}
              {this.props.statusLogin ? (
                <Menu target="/logout">Logout</Menu>
              ) : (
                ""
              )}
            </Nav>
          </Container>
        </Navbarbro>
      </Row>
    );
  }
}
const mapStateToProps = (state) => ({
  statusLogin: state.loginRedux.statusLogin,
});
export default connect(mapStateToProps)(Navbar);
