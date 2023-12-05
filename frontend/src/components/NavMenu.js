import React, { useContext } from 'react';
import { Collapse, Navbar, NavbarBrand, NavbarToggler, NavItem, NavLink } from 'reactstrap';
import { Link } from 'react-router-dom';
import './NavMenu.css';
import { AppContext } from '../App';

const NavMenu = () => {
    const context = useContext(AppContext);
    console.log('Context in NavMenu:', context);
    const { isLoggedIn, setIsLoggedIn, setUserId, setUsers, setDevices, userRole } = context;

    const clickLogout = () => {
        setIsLoggedIn(false);
        setUserId(0);
        setUsers(null);
        setDevices(null);
    }
    return (
        <header>
            <Navbar className="navbar-expand-sm navbar-toggleable-sm background border-bottom box-shadow mb-3" container light>
                <NavbarBrand tag={Link} to="/">Energy Platform</NavbarBrand>
                <NavbarToggler className="mr-2" />
                <Collapse className="d-sm-inline-flex flex-sm-row-reverse" >
                    <ul className="navbar-nav flex-grow">
                        <NavItem>
                            <NavLink tag={Link} className="text-white" to="/home">Home</NavLink>
                        </NavItem>
                        {
                            isLoggedIn && userRole === 'administrator'  &&
                            <NavItem>
                                <NavLink tag={Link} className="text-white" to="/admin-page-devices">Personal Devices</NavLink>
                            </NavItem>
                        }
                        {
                            isLoggedIn && userRole === 'administrator' &&
                            <NavItem>
                                <NavLink tag={Link} className="text-white" to="/admin-page">Users and Devices</NavLink>
                            </NavItem>
                        }
                        {
                            isLoggedIn && userRole === 'user' &&
                            <NavItem>
                                <NavLink tag={Link} className="text-white" to="/user-dashboard">Devices</NavLink>
                            </NavItem>
                        }
                        {
                            isLoggedIn ? (
                                <NavItem>
                                    <NavLink tag={Link} className="text-white" to="/home" onClick={clickLogout}>Logout</NavLink>
                                </NavItem>
                            ) : (
                                <NavItem>
                                    <NavLink tag={Link} className="text-white" to="/login">Login</NavLink>
                                </NavItem>
                            )
                        }
                    </ul>
                </Collapse>
            </Navbar>
        </header>
    );
}

export default NavMenu;