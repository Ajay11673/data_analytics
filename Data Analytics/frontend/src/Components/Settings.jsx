import React, { Component,useMemo } from 'react'
import { Link } from 'react-router-dom';
import UserTable from './UserTable';
export default class Settings extends Component {
    constructor(){
        super();
        this.state = {
            usersData:{},
            columns:[
                { title: 'user id', field: 'userId' },
                { title: 'username', field: 'userName' },
                { title: 'role', field: 'role'},
                { title: 'added date', field: 'addedDate'},
              ],
        } 
    }

  
    render() {
        if(localStorage.getItem('user')==null && sessionStorage.getItem('user')==null){
            window.location.href = "/";
        }else{
            let userData = (localStorage.getItem('user')!=null)?JSON.parse(localStorage.getItem('user'))
            :JSON.parse(sessionStorage.getItem('user'));
            if(userData.role != "ADMIN")
                window.location.href = "/dashboard";
            else
            return (
                <div>   
                    <div style={{background:'#000'}}>
                        <div>
                        <Link className="btn btn-primary" to="/add-users" style={{float:'right'}} >Create new user</Link><br/>
                        <span style={{color:'#fff'}}>Users</span>
                        </div>
                        <UserTable 
                            col={this.state.columns}
                        />
                    </div>
                </div>
            )
        }

    }
}
