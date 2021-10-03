import React, { Component } from 'react';
import { Home, Contact, Login } from "../pages"


class Content extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: [{
                username: "Admin",
                password: "1234",
                address: "Jakarta"
            }, {
                username: "User",
                password: "1234",
                address: "Bogor"
            }, {
                username: "Operator",
                password: "1234",
                address: "Depok"
            }]
        }
    }

    addButton = newUser => {
        const newData = this.state.data
        newData.push(newUser)
        this.setState({
            data: newData
        })
        this.props.goToPage("contact")
    }
    cekLogin = (contact) =>{
        this.props.goToPage(contact)
    }

    render() {
        if (this.props.menu === "login")
            return <Login addData={this.addButton} allUser={this.state.data} cekLogin={this.cekLogin} />

        if (this.props.menu === "contact")
            return <Contact users={this.state.data} />

        return <Home />
    }
}

export default Content;