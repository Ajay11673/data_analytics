import React from 'react';
import styled from 'styled-components';
import { BrowserRouter as Router, Route, Link, withRouter, NavLink } from "react-router-dom";

const StyledSideNav = styled.div`   
    position: fixed;     /* Fixed Sidebar (stay in place on scroll and position relative to viewport) */
    height: 100%;
    width: 100px;     /* Set the width of the sidebar */
    background-color: #000; /* Black */
    overflow-x: hidden;     /* Disable horizontal scroll */
    padding-top: 10px;
    color:#fff;
`;
export const Nav = styled.nav`
  background: #000;
  height: 65px;
  display: flex;
  justify-content: flex-start;
  padding: 0.5rem;
  z-index: 12;
  /* Third Nav */
  /* justify-content: flex-start; */
`;
 
class SideNav extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            activePath: props.location.pathname,
            data: props.data,
        }
    }

    onItemClick = (path) => {
        this.setState({ activePath: path });
    }

    render() {
        const { items, activePath , data} = this.state;
        return(
            <StyledSideNav>
                {
                    data.map((data) => {
                        return (
                            <Nav>
                              <NavLink to={{pathname:`/show-data/${data.id}`, load:{data}}} style={{color:'white', textDecoration:'none'}}>{data.file_name}</NavLink>
                              
                            </Nav>
                        );
                    })
                }
            </StyledSideNav>
        );
    }
}

const RouterSideNav = withRouter(SideNav);

const StyledNavItem = styled.div`
    height: 70px;
    width: 75px; /* width must be same size as NavBar to center */
    text-align: center; /* Aligns <a> inside of NavIcon div */
    margin-bottom: 0;   /* Puts space between NavItems */
    a {
        font-size: 2.7em;
        color: ${(props) => props.active ? "white" : "#9FFFCB"};
        :hover {
            opacity: 0.7;
            text-decoration: none; /* Gets rid of underlining of icons */
        }  
    }
`;

class NavItem extends React.Component {
    handleClick = () => {
        const { path, onItemClick } = this.props;
        onItemClick(path);
    }

    render() {
        const { active } = this.props;
        return(
            <StyledNavItem active={active}>
                <Link to={this.props.path} className={this.props.css} onClick={this.handleClick}>
                    <NavIcon></NavIcon>
                </Link>
            </StyledNavItem>
        );
    }
}

const NavIcon = styled.div`
`;

export default class Sidebar extends React.Component {
    render() {
      let user = JSON.parse(localStorage.getItem('user'))
        return (
            <RouterSideNav data={user.data_source}></RouterSideNav>
        );
    }
}
