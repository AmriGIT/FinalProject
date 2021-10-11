import React, { Component } from 'react'
// import Header from './template/Header';
// import Navbar from './template/Navbar';
// import Content from './template/Content';
import { Header, Navbar, Content } from "./template"
// import "./App.css"
import { BrowserRouter as Router} from 'react-router-dom';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      menu: "home",
      setStatus : ""
    }
  }

  changePage = page => {
    this.setState({
      menu: page
    })
    // console.log(page);
  }
  changeStatus = status =>{
    this.setState({
      setStatus : status
    })
  }

  render() {
    console.log("AppJS",this.state.setStatus)
    return (
      <Router>
        <Header />
        <Navbar setsts={this.state.setStatus} />
        <Content
          menu = {this.state.menu}
          sts={this.changeStatus}
        />
      </Router>
    );
  }
}

export default App