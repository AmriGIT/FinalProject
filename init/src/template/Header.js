import React, { Component } from 'react';


class Header extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    render() {
        return (
            <div style={{
                height: 120,
                backgroundColor: 'lightgray'
            }} />
        );
    }
}

export default Header;