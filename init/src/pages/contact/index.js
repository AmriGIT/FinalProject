import React, { Component } from 'react';


class Contact extends Component {
    constructor(props) {
        super(props);
        this.state = {
        }
    }
    HapusItem = (e) => {
        const data = this.props.users
        for (let index = 0; index < data.length; index++) {
            console.log(data[e].username)
            // const ItemTobeRemoved = data.find
            return
        }


    }
    renderList = () => {
        return this.props.users.map((user, idx) => {
            return <tr key={idx}>
                <td>{idx + 1}</td>
                <td>{user.username}</td>
                <td>{user.address}</td>
                <td>Edit</td>
                <td> <button onClick={this.HapusItem(idx)}>Hapus</button></td>
            </tr>

        })

    }

    render() {
        return (
            <>
                <table width="100%" border="1px" cellPadding="10px">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Username</th>
                            <th>Address</th>
                            <th colSpan="2">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.renderList()}
                    </tbody>
                </table>

            </>
        );
    }
}

export default Contact;