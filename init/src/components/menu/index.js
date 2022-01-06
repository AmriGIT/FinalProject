import React, { Component } from "react";
import { Nav } from "react-bootstrap";
import { Link } from "react-router-dom";

class Menu extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }
  render() {
    const target = this.props.target;
    return (
      <Nav.Link as={Link} to={target} className="menu">
        {this.props.children}
      </Nav.Link>
    );
  }
}

export default Menu;
