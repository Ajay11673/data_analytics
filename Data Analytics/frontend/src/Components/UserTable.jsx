import MaterialTable from 'material-table';
import React, { Component} from 'react'

export default class UserTable extends Component {
    constructor({col}){
        super({col});
        this.state={
            columns:col,
            isFirst:true,
            page:(parseInt(sessionStorage.getItem('userPage'))>=0)?parseInt(sessionStorage.getItem('userPage')):0,
        } 
        console.log(this.state);
    }
    componentWillUnmount(){
        sessionStorage.setItem('userPage',this.state.page);
        console.log("unmounting... page="+sessionStorage.getItem('userPage'));
    }
    render() {
        return (
            <div>
                    <MaterialTable
                    options={{
                        search:false,
                        
                    }}
                    
                    title="Users"
                    columns= {this.state.columns}
                    data={query=>new Promise((resolve,reject)=>{

                        if(this.state.isFirst){
                            let pageNo=(parseInt(sessionStorage.getItem('userPage'))>=0)?parseInt(sessionStorage.getItem('userPage')):0
                            console.log(sessionStorage.getItem('page'));
                            console.log("session user-page: "+pageNo);
                            query.page=pageNo;
                            
                            this.state.isFirst=false;
                            //console.log("isFirst ran")
                        }
                        else{
                            this.state.page=query.page;
                            console.log(query.page+", "+this.state.page);
                        }

                        let headerToken = (localStorage.getItem('token')!=null)?localStorage.getItem('token'):sessionStorage.getItem('token');
                        fetch(`/api/backend/getAllUsers?page=${query.page+1}&rowCount=${query.pageSize}`,{method:"GET",headers:{'auth':headerToken,}})
                        .then(res=>res.json().then(
                        res=>{
                            console.log(res);
                            if(res.data=="INVALID"){
                                console.log("yes");
                                if(window.confirm("MESSAGE: your file is not uploaded\nNOTE: token expired re-login")){
                                    sessionStorage.clear();
                                    localStorage.clear();
                                    window.location.href = "/";
                                }
                                return;
                            }
                            let data;
                            console.log(res.data.length+", "+query.pageSize);
                            console.log(res);
                            if(res.data.length !=1){
                                data = res.data.map(e=>{
                                    return JSON.parse(e);
                                });
                            }
                            else{
                                data = [res.data].map(e=>{
                                    console.log('else');
                                    console.log(res);
                                    return JSON.parse(e);
                                });
                            }

                            resolve({
                                data:data,
                                page:query.page, 
                                totalCount:res.size,
                                
                            })
                            
                            
                        }
                        )
                        )
                    }
                    )}
                    
                    />
            </div>
        )
    }
}
