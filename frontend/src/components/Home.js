import React, { Component } from 'react';

export class Home extends Component {
    static displayName = Home.name;

    render() {
        return (
            <div className="main-dashboard">
                <h1>Energy Utility Platform</h1>
                <p>Enabling real time monitoring for smart devices.</p>
                <p>Requirements:</p>
                <p>Develop an Energy Management System that consists of a frontend and two microservices
                    designed to manage users and their associated smart energy metering devices. The system can be
                    accessed by two types of users after a login process: administrator (manager), and clients. The
                    administrator can perform CRUD (Create-Read-Update-Delete) operations on user accounts
                    (defined by ID, name, role: admin/client), smart energy metering devices (defined by ID,
                    description, address, maximum hourly energy consumption), and on the mapping of users to
                    devices (each user can own one or more smart devices in different locations). </p>
                <p>You can access our page for more information: dsrl.eu/courses/sd</p>
            </div>
        );
    }
}