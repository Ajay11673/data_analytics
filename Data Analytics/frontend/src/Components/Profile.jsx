import React, { Component } from 'react'

export default class Profile extends Component {
    constructor(){
        super();
        this.state={
            userData:(localStorage.getItem('user')!=null)?JSON.parse(localStorage.getItem('user'))
                    :JSON.parse(sessionStorage.getItem('user')),
        }
    }
    handleLogout(){
        localStorage.clear();
        sessionStorage.clear();
        window.location.href = "/";
    }
    render() { 
        if(localStorage.getItem('user')==null && sessionStorage.getItem('user')==null){
            window.location.href = "/";
        }
        else
        return (
            <div>
                <div style={{background:'#000',padding:'10px'}}>
                    <div> 
                    <span style={{color:'#fff'}}>Profile</span>
                    </div>
                    <button onClick={()=>this.handleLogout()} className="btn btn-primary">Logout</button>
                    <br/>

                </div>
                <div style={{display:'flex',justifyContent:'center',alignItems:'center',height:'70vh'}}>
                    <table >
                        <tbody>
                            <tr>
                                <th>user id</th>
                                <td>&nbsp;{this.state.userData.user_id}</td>
                            </tr>
                            <tr>
                                <th>User name</th>
                                <td>&nbsp;{this.state.userData.user_name}</td>
                            </tr>
                            <tr>
                                <th>role</th>
                                <td>&nbsp;{this.state.userData.role}</td>
                            </tr>
                            <tr>
                                <th>added date</th>
                                <td>&nbsp;{this.state.userData.added_date}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}
