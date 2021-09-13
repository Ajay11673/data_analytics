import React, { Component } from 'react'

export default class AddData extends Component {
    constructor(){
        super();
        this.state = {
            userData:(localStorage.getItem('user')!=null)?JSON.parse(localStorage.getItem('user'))
                    :JSON.parse(sessionStorage.getItem('user')),
            fileName:"",
            file:"",
        }
        console.log(this.state.userData.data_source[0]===undefined);
    }
    handleFileNameChanged(e){
        this.setState({fileName:e.target.value}); 
    }
    handleFileChanged(e){
        this.setState({file : e.target.files[0]});
        let fileNameInput = document.getElementById('fileName');
        if(fileNameInput){
            fileNameInput.value= e.target.files[0].name.split(".")[0];
            this.setState({
                fileName:e.target.files[0].name.split(".")[0],
            })
        }
        
        console.log(e.target.files[0].name);
    }

    handleFormSubmit(){
        
        if(this.state.fileName.trim()==''|| this.state.file==undefined || this.state.file.length===0){
            alert('Invalid Data');
            return;
        }
        if(this.state.fileName.trim().length>50){
            alert('filename is too big ');
            return;
        }
        let btn = document.getElementById('btnSubmit');
        btn.innerHTML = "procressing"
        btn.disabled = true;

        const userData = {id:this.state.userData.user_id}
        const fileName = this.state.fileName;
        const file = this.state.file;
        console.log(file);
        let fd = new FormData();
        fd.append('file',file);
        fd.append('fileName',fileName);
        fd.append('userData',JSON.stringify(userData));
        const user_object = {
            file: file,
            fileName: fileName,
            userData: userData,
          };
          let headerToken = (localStorage.getItem('token')!=null)?localStorage.getItem('token'):sessionStorage.getItem('token');
          console.log(headerToken);
          console.log(userData);
          fetch("/api/backend/addDataSource",{method:"POST",headers:{'auth':headerToken},body:fd,}).then(
              res=>{
                //console.log(res);
                btn.classList.remove("btn-primary");
                  if(res.ok){
                    res.json().then(res=>{
                        if(res.data!="INVALID"){
                            console.log(res);
                            btn.disabled=false;
                            btn.innerHTML="success"
                            btn.classList.add("btn-success");
                            if(localStorage.getItem('user')!=null)
                                localStorage.setItem('user',JSON.stringify(res));
                            else
                                sessionStorage.setItem('user',JSON.stringify(res));

                                setTimeout(() => {
                                    btn.innerHTML="save"
                                    btn.classList.remove("btn-success");
                                    btn.classList.add("btn-primary");
                                }, 1000);
                        }else{
                            console.log(res);
                            btn.innerHTML="Token expired file not saved";
                            btn.classList.add("btn-warning");

                            if(window.confirm("MESSAGE: your file is not uploaded\nNOTE: token expired re-login")){
                                sessionStorage.clear();
                                localStorage.clear();
                                window.location.href = "/";
                            }
                        }

                    })
                  }
                  else{
                    btn.classList.add("btn-danger"); 
                    btn.disabled=false;
                    btn.innerHTML="failed";
                    setTimeout(() => {
                        btn.innerHTML="save"
                        btn.classList.remove("btn-danger");
                        btn.classList.add("btn-primary");
                    }, 1000);
                  }
              }
          )
          ;


    }
    render() {
        if(localStorage.getItem('user')==null && sessionStorage.getItem('user')==null){
            window.location.href = "/";
        }else
        return (
            <div>
            <div className="container mt-5">
            <h3>Add new Data source</h3>
            <div className="form-group">
                <label>Data Source name</label>
                <input name="filename" id="fileName" className="form-control" placeholder="Data Source name" onChange={this.handleFileNameChanged.bind(this)} />
            </div>
            <br/>

            <div className="form-group">
                <label>Datasource</label>
                <input name="file" type="file" className="form-control" onChange={this.handleFileChanged.bind(this)}/>
            </div>
            <br/>
            <br/>
            <button type="submit" id="btnSubmit" className="btn btn-primary" onClick={()=>this.handleFormSubmit()}>save</button>

            </div>
            </div>
        )
    }
}
