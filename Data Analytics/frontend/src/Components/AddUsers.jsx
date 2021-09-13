import React, { Component } from 'react'

export default class AddUsers extends Component {
    constructor(){
        super();

        this.state={
            username:"",
            password:"",
            role:"GUEST",
        }

    }
    handleUserNameChanged(e){
        this.setState({username:e.target.value});
    }
    handlePasswordChanged(e){
        this.setState({password:e.target.value});
    }
    handleCheckbox(e){
        this.state.isChecked = e.target.checked
    }0
    handleFormSubmit(){
        const username = this.state.username;
        const password = this.state.password;
        const role = this.state.role;
        if(username.trim()==''||password.trim()==''||role.trim()==''){
            alert('invalid data');
            return;
        }
        let btn = document.getElementById('btnAddUser');
        btn.innerHTML = "procressing"
        console.log("Log in");
        btn.disabled = true;
        
        const user_object = {
            username: username,
            password: password,
            role: role, 
          };

          console.log(username+" ,"+password+" ,"+role);
          let headerToken = (localStorage.getItem('token')!=null)?localStorage.getItem('token'):sessionStorage.getItem('token');
          fetch("/api/backend/signup",{method:"POST",body:JSON.stringify(user_object),headers:{'auth':headerToken,}})
          .then((res)=>{
              if(res.ok){
                btn.disabled=false;
                btn.innerHTML="success"
                btn.classList.remove("btn-primary");
                btn.classList.add("btn-success");
                setTimeout(() => {
                    btn.innerHTML="Add user"
                    btn.classList.remove("btn-success");
                    btn.classList.add("btn-primary");
                  }, 1000);
              }else{
                btn.disabled=false;
                btn.innerHTML="failed"
                btn.classList.remove("btn-primary");
                btn.classList.add("btn-danger");
                setTimeout(() => {
                    btn.innerHTML="Add user"
                    btn.classList.remove("btn-danger");
                    btn.classList.add("btn-primary");
                  }, 1000);
              }

          })

    }
    handleSelectChange(event) { 
        this.setState({
            role:event.target.value
        });
    }
    render() {
        if(localStorage.getItem('user')==null && sessionStorage.getItem('user')==null){
            window.location.href = "/";
        }else
        return (
            <div>
            <div className="container mt-5">
            <h3>Add new user</h3>
            <div className="form-group">
                <label>username</label>
                <input type="username" className="form-control" placeholder="Username" onChange={this.handleUserNameChanged.bind(this)} />
            </div>
            <br/>
            <div className="form-group">
                <label>password</label>
                <input type="password" className="form-control" placeholder="Password" autoComplete="new-password" onChange={this.handlePasswordChanged.bind(this)}/>
            </div>
            <br/>
            <div className="form-group">
            <label>Role</label>
            <select 
                        className="form-control" 
                        name="userList" 
                        id="selectUser" 
                        onChange={this.handleSelectChange.bind(this)}
                        >
                            <option value="GUEST" >GUEST</option>
                            <option value="ADMIN" >ADMIN</option>
            </select>
            </div>
            <br/>
            <button type="submit" id="btnAddUser" className="btn btn-primary" onClick={()=>this.handleFormSubmit()}>Add user</button>

            </div>
            </div>
        )
    }
}
