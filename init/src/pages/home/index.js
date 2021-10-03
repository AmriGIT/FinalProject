import React, { Component } from 'react';



class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    render() {
        return (
            <div style={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                flexDirection: "column"
            }}>
                <div>
                    <div>{this.state.username1}</div>
                    <div>{this.state.password}</div>
                    <button onClick={this.changeButton}>5</button>
                    <span>{this.state.counter}</span>
                    <button onClick={this.plusOneButton}>+1</button>
                </div>
                <div>
                    <div>
                        <span>Username</span>
                        <input type="text" name="username1" onChange={this.updateUsername} />
                    </div>
                    <div>
                        <span>Password</span>
                        <input type="password" name="password" onChange={this.updateUsername} />
                    </div>
                </div>
            </div >
        );
    }
}

export default Home;