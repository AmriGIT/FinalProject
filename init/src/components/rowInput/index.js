import React, { Component } from 'react';



class RowInput extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    render() {
        const { label, type, name, change, valueState } = this.props
        return (
            <div className="row-input">
                <span>{label}</span>
                <input
                    type={type}
                    name={name}
                    onChange={change}
                    value={valueState}
                />
            </div>
        );
    }
}

export default RowInput;