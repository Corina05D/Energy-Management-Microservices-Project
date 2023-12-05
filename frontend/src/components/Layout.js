import React, { Component } from 'react';
import  NavMenu  from './NavMenu';

const Layout =({children})=> {
    return (
        <div>
            <NavMenu />
            <div className="app-container">{children}</div>
        </div>
    );
}

export default Layout;