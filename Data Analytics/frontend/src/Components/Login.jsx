import React, { Component } from 'react'

export default class Login extends Component {
    constructor(){
        super();
        console.log("Log in");
        this.state = {
            username:"",
            password:"",
            isChecked:false,
            http:""
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
    }
    handleFormSubmit(e){
        console.log("Log in");
        sessionStorage.clear();
        localStorage.clear();

        let btn = document.getElementById('btnSubmit');
        btn.innerHTML = "procressing"
        btn.disabled = true;
        const username = this.state.username;
        const password = this.state.password;
        if(username.trim()==''|| password.trim()==''){
            alert("invalid data");
            return
        }
        const user_object = {
            username: username,
            password: password
          };
          console.log(username+" ,"+password);
          fetch("/api/backend/login",{method:"POST",body:JSON.stringify(user_object)})
          .then(
              res=>{
                btn.classList.remove("btn-dark");
                  if(res.ok){
                    res.json().then(

                        res=>{
                            console.log(res);
                              localStorage.clear();
                              sessionStorage.clear();
                              //localStorage.setItem("user",res.data);
                              //localStorage.setItem("token",res.token);
                              //console.log(res.data);
                              //console.log(res.token);
                              localStorage.setItem('role',JSON.parse(res.data).role);
                              console.log(JSON.parse(res.data).role);
                              if(this.state.isChecked){
                                  console.log("saved in local");
                                  localStorage.setItem("user",res.data);
                                  localStorage.setItem("token",res.token+"");
                              }
                              else{
                                  console.log("saved in session");
                                  sessionStorage.setItem("user",res.data);
                                  sessionStorage.setItem("token",res.token+"");
                              }
      
                                  this.props.history.push("/dashboard");
      
                          })
                  }else{
                    btn.classList.add("btn-danger");
                    btn.disabled=false;
                    btn.innerHTML="failed";
                    setTimeout(() => {
                        btn.innerHTML="Login"
                        btn.classList.remove("btn-danger");
                        btn.classList.add("btn-dark"); 
                    }, 1000);
                  }
              });

    }
    render() {
        if(localStorage.getItem('user')!=null && sessionStorage.getItem('user')!=null){
            this.props.history.push("/dashboard");
        }
        else
        return (
            <div>
            <div className="container mt-5">
            <h3>Data Analystic</h3>
            <h3>Log-in</h3>
            <div className="form-group">
                <label>username</label>
                <input type="username" className="form-control" placeholder="Username" value={this.state.username} onChange={this.handleUserNameChanged.bind(this)} />
            </div>
            <br/>
            <div className="form-group">
                <label>password</label>
                <input type="password" className="form-control" placeholder="Password" value={this.state.password} onChange={this.handlePasswordChanged.bind(this)}/>
            </div>
            <br/>
            <div className="form-group">
                <div className="custom-control custom-checkbox">
                    <input type="checkbox" className="custom-control-input" id="customCheck1" value={this.state.isChecked} onChange={this.handleCheckbox.bind(this)}/><span> </span>
                    <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
                </div>
            </div>
            <br/>
            <button type="submit" id='btnSubmit' className="btn btn-dark btn-lg btn-block" onClick={()=>this.handleFormSubmit()}>Log in</button>

            </div>
            </div>
        )
    }
}
 