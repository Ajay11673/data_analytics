import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import Table from './Table';

export default class Dashboard extends Component {
    constructor(props){
        super(props);
        if(sessionStorage.getItem("table")!=null){
            console.log("Dashboard constructor");
            let data = JSON.parse(sessionStorage.getItem("table"))
            //console.log(data);
            this.state={
                userData:(localStorage.getItem('user')!=null)?JSON.parse(localStorage.getItem('user'))
                :JSON.parse(sessionStorage.getItem('user')),
                    dataSoure:(localStorage.getItem('user')!=null)?JSON.parse(localStorage.getItem('user')).data_source
                :JSON.parse(sessionStorage.getItem('user')).data_source,
                columns:data.columns,
                recordCount:data.recordCount,
                id: data.id,
                filename: data.filename,
                createdDate:data.createdDate,
                creatorName:data.creatorName,
                allowed_to:data.allowed_to,
                user_id:(localStorage.getItem('user')!=null)?JSON.parse(localStorage.getItem('user')).user_id
                :JSON.parse(sessionStorage.getItem('user')).user_id,
            };
        }else{
            this.state={
                userData:(localStorage.getItem('user')!=null)?JSON.parse(localStorage.getItem('user'))
                        :JSON.parse(sessionStorage.getItem('user')),
                dataSoure:(localStorage.getItem('user')!=null)?JSON.parse(localStorage.getItem('user')).data_source
                        :JSON.parse(sessionStorage.getItem('user')).data_source,
                columns:[],
                recordCount:0,
                id: 0,
                filename: "",
                createdDate:"",
                creatorName:"",
                allowed_to:-1,
                user_id:(localStorage.getItem('user')!=null)?JSON.parse(localStorage.getItem('user')).user_id
                :JSON.parse(sessionStorage.getItem('user')).user_id,
            }
        }
        //console.log(this.state);
        // if(this.state.dataSoure[0]!=null){
        //     this.setState({
        //         recordCount: this.state.dataSoure[0].row_count,
        //         id:this.state.dataSoure[0].id,
        //         filename:this.state.dataSoure[0].file_name,
        //         createdDate:this.state.dataSoure[0].created_date,
        //         creatorName:this.state.dataSoure[0].created_by,
        //     });
        //     this.handleDataSelected(this.state.dataSoure[0]);
        // }
    }
    componentDidUpdate(){
        console.log("dashboard update");
        //console.log(this.state);
    }
    handleDataSelected(data){
        let message = document.getElementById('message');
        if(message){
            message.style.display='block';
        }
        let dataTable = document.getElementById("tableData");
        if(dataTable){
            dataTable.style.display='none';
        }
        let noDataSelected = document.getElementById('noDataSelected');
        if(noDataSelected){
            noDataSelected.style.display='none';
        }
        console.log("data");
        sessionStorage.setItem('page',0);
        //console.log(data);
        let headerToken = (localStorage.getItem('token')!=null)?localStorage.getItem('token'):sessionStorage.getItem('token');
        fetch("/api/backend/getDataSourceHeaders",{method:"POST",headers:{'auth':headerToken,},body:JSON.stringify(data)})
        .then(res=>res.json().then(
            res=>{
                //console.log(res);
                if(res.data=="INVALID"){
                    //console.log("yes");
                    if(window.confirm("MESSAGE: your file is not uploaded\nNOTE: token expired re-login")){
                        sessionStorage.clear();
                        localStorage.clear();
                        window.location.href = "/";
                    }
                    return;
                }
                if(dataTable){
                    dataTable.style.display='block';
                }
                if(message){
                    message.style.display='none';
                }
                if(noDataSelected){
                    noDataSelected.style.display='block';
                }
                //console.log(res);
                this.setState({
                    columns:res,
                    filename:data.file_name,
                    id:data.id,
                    recordCount:data.row_count,
                    createdDate:data.created_date,
                    creatorName:data.created_by,
                    allowed_to:data.allowed_to,
                });
                sessionStorage.setItem('table',JSON.stringify(this.state));
                //sessionStorage.setItem('tableData',JSON.stringify(this.state));
            }
        ))
        //console.log("~~~~~~~~~~~~~~~~~~~~~~~~");
        //this.forceUpdate();
    }

    handleRemove(id){
        let message = document.getElementById('message');
        if(message){
            message.style.display='block';
        }
        let dataTable = document.getElementById("tableData");
        if(dataTable){
            dataTable.style.display='none';
        }
        let noDataSelected = document.getElementById('noDataSelected');
        if(noDataSelected){
            noDataSelected.style.display='none';
        }
        let headerToken = (localStorage.getItem('token')!=null)?localStorage.getItem('token'):sessionStorage.getItem('token');
        fetch(`/api/backend/deleteDataSource?id=${id}&user_id=${this.state.user_id}`,{method:"GET",headers:{'auth':headerToken,}}).then(res=>res.json()
        .then(res=>{

            if(res.data=="INVALID"){
                console.log("yes");
                if(window.confirm("MESSAGE: your file is not uploaded\nNOTE: token expired re-login")){
                    sessionStorage.clear();
                    localStorage.clear();
                    window.location.href = "/";
                }
                return;
            }
            //console.log(res);
            if(localStorage.getItem('user')!=null)
                localStorage.setItem('user',JSON.stringify(res));
            else
                localStorage.setItem('user',JSON.stringify(res));

                this.setState({
                    userData:res,
                    dataSoure:res.data_source,
                    recordCount:0,
                    id: 0,
                    filename: "",
                    createdDate:"",
                    creatorName:"",
                    allowed_to:-1,
                });
                sessionStorage.removeItem("table");
                console.log("changing it to block "+noDataSelected+", "+message);
                if(noDataSelected){
                    console.log("changing it to block");
                    noDataSelected.style.display='block';
                }
                if(message){
                    message.style.display='none';
                }
            }
        )) 
    }
    handleShow(id,value){ 
        let visibility = document.getElementById('visibility');
        if(visibility){
            visibility.style.cursor = 'progress';
        }
        let headerToken = (localStorage.getItem('token')!=null)?localStorage.getItem('token'):sessionStorage.getItem('token');
        fetch(`/api/backend/updateDataSourceVisibility?id=${id}&value=${value}`,{method:"GET",headers:{'auth':headerToken,}}).then(res=>res.json().then(
            res=>{
                console.log(res);
                if(res.data=="INVALID"){
                    if(window.confirm("MESSAGE: your file is not uploaded\nNOTE: token expired re-login")){
                        sessionStorage.clear();
                        localStorage.clear();
                        window.location.href = "/";
                    }
                    return;
                }
                if(visibility){
                    visibility.style.cursor = 'pointer';
                }
                if(res.data){
                    this.setState({allowed_to:value});
                }
            }
        ))
    }
    render() {
        //console.log("render");
        if(localStorage.getItem('user')==null && sessionStorage.getItem('user')==null){
            window.location.href = "/";
        }
        else
        return(
            <div>

                <div style={{background:'#000'}}>
                    <div>
                    <span style={{color:'#fff'}}>Data Sources</span>
                   {this.state.dataSoure[0]===undefined?<div></div>:<Link className="btn btn-primary" style={{float:'right'}} to="/add-data">Add new dataSource</Link>} 
                    </div>
                    
                    <br/>

                    {this.state.dataSoure.map(data=>(
                        <button onClick={()=>this.handleDataSelected(data)} style={{background:'none', border:'none',color:'#fff', cursor:'pointer'}}>{data.file_name}</button>
                    ))}
                </div>
                <div id="message" style={{position:'absolute',width:'100%',height:'78vh',display:'none',background:'rgb(0,0,0,0.3)'}}>
                <div style={{display:'flex',justifyContent:'center',alignItems:'center',height:'70vh'}}>
                <center>
                        <span>Loading</span><br/>
                    </center>
                </div>

                </div>

                {this.state.dataSoure[0]===undefined?
                <div  style={{display:'flex',justifyContent:'center',alignItems:'center',height:'70vh'}}>
                    <center>
                        <span>No data Available</span><br/>
                        <Link className="btnAsText" style={{color:'black'}} to="/add-data">Add Data</Link>
                    </center>
                    
                </div>
                :
                this.state.columns[0]==undefined?
                <div id='noDataSelected' style={{display:'flex',justifyContent:'center',alignItems:'center',height:'70vh'}}>
                <center>
                    <span>No data Selected</span><br/>
                </center>
                </div>
                :
                <div>
                <div id='noDataSelected' style={{position:'absolute',width:'100%',height:'78vh',display:'block'}} > 
                    <div style={{display:'flex',justifyContent:'center',alignItems:'center',height:'70vh'}}>
                    <center>
                        <span>No data Selected</span><br/>
                    </center>
                    
                    </div>
                </div> 
                <div id="tableData">
                <Table
                col={this.state.columns}
                recCount={this.state.recordCount}
                id ={this.state.id}
                fileName= {this.state.filename}
                />
                
                <div style={{padding:'30px'}} >
                    <table>
                        <tbody>
                            <tr>
                                <th>Data Added By:</th>
                                <tr> &nbsp;{this.state.creatorName}</tr>
                            </tr>
                            <tr>
                                <th>Data Added date:</th>
                                <tr> &nbsp;{this.state.createdDate}</tr>
                            </tr>
                        </tbody>
                    </table>
                    {this.state.userData.role==='ADMIN'?
                    <div>
                        <h6>Actions</h6>
                        <ol>
                            <li><button className="btnAsText" onClick={()=>this.handleRemove(this.state.id)}>Remove</button> the dataSource</li><br/>
                            {this.state.allowed_to==0?<li >
                                <button id="visibility" className="btnAsText" 
                                onClick={()=>this.handleShow(this.state.id,1)}>hide</button> the data to guests</li>
                                :
                                <li >
                                    <button id="visibility" className="btnAsText" 
                                    onClick={()=>this.handleShow(this.state.id,0)}>Show</button> the data to guests</li>}
                        </ol>
                    </div>
                    :
                    <div>
                         <h6>Actions</h6>
                        <ol>
                            <li><button className="btnAsText" onClick={()=>this.handleRemove(this.state.id)}>Remove</button> the dataSource</li><br/>
                        </ol>
                    </div>
                    }
                    
                </div>
                </div>

                </div>
                }
                                
            </div>

        );
       
    }
}
