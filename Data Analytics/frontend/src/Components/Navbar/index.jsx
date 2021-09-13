import React from 'react';
import {
  Nav,
  NavLink,
  Bars,
  NavMenu,
  NavBtn,
  NavBtnLink,
} from './NavbarElements';

const Navbar = () => {
    return (
      <> 
        <Nav>
        <NavLink to='#'>
              Data_Analytics
            </NavLink>
          <NavMenu>
            <NavLink to='/dashboard' activeStyle>
              Dashboard
            </NavLink>
            {localStorage.getItem('role')=='ADMIN'?<NavLink to='/settings' activeStyle>
              Settings
            </NavLink>:<div/>}
            
            {/* Second Nav */}
            {/* <NavBtnLink to='/sign-in'>Sign In</NavBtnLink> */}
          </NavMenu>
          <NavBtn>
            <NavBtnLink  to="/profile">Profile</NavBtnLink>
          </NavBtn>
        </Nav>
      </>
    );
  };
    
  export default Navbar;