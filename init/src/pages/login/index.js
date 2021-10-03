import React, { Component } from 'react';
import { RowInput } from "../../components"
import "./login.css"



class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            address: "",
            isLogin: false
        }
    }

    setValue = e => {
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    resetForm = () => {
        this.setState({
            username: "",
            password: ""
        })
    }

    loginButton = () => {
        const { username, password } = this.state
        const result = this.props.allUser.find(obj => obj.username === username)
        if (result != null) {
            if (username === result.username && password === result.password) { // Bagaimana caranya me-reset value input ketika login success
                this.setState({ isLogin: true })
                this.resetForm()
                this.props.cekLogin("contact")
            } else alert("Invalid username or password!!")
        }else{
            alert ("Username Invalid")
        }

    }
    cekdata = () => {
        const result = this.props.allUser.find(obj => obj.username === this.state.username)
        console.log(result.username)
    }

    registerButton = () => {
        const { username, password, address } = this.state
        const newUser = {
            username, password, address
        }
        this.props.addData(newUser)
    }

    render() {
        return (
            <div className="login-containter">
                <fieldset>
                    <legend>Value</legend>
                    <h1>Username: {this.state.username}</h1>
                    <h1>Password: {this.state.password}</h1>
                    <h1>Status Login: {this.state.isLogin.toString()}</h1>
                </fieldset>
                <div className="login-containter">
                    <RowInput
                        label="Username"
                        type="text"
                        name="username"
                        change={this.setValue}
                        valueState={this.state.username}
                    />
                    <RowInput
                        label="Password"
                        type="password"
                        name="password"
                        change={this.setValue}
                        valueState={this.state.password}
                    />
                    <RowInput
                        label="Address"
                        type="text"
                        name="address"
                        change={this.setValue}
                        valueState={this.state.address}
                    />
                    <div className="row-button">
                        <button onClick={this.loginButton}>Log In</button>
                        <button onClick={this.registerButton}>Register</button>
                        <button onClick={this.cekdata}>cek</button>
                    </div>
                </div>
            </div>
        );
    }
}

export default Login;