import MaterialTable from 'material-table';
import React, { Component} from 'react'

export default class Table extends Component {
        tableRef = React.createRef();
    constructor({col,recCount,id,fileName,page}){
        super({col,recCount,id,fileName,page});
        //sessionStorage.removeItem('page');
        this.state={
            columns:col,
            recordCount:recCount,
            id: id,
            filename: fileName,
            isFirst:true,
            page:(parseInt(sessionStorage.getItem('page'))!=null)?parseInt(sessionStorage.getItem('page')):0,
        }
        // if(sessionStorage.getItem('page')==null){
        //     sessionStorage.setItem('page',0);
        // }
        
        console.log("From table constructor");
        console.log(this.state);
    }
    
    componentDidUpdate(prevProps){
        if(this.props.id!=prevProps.id){
            console.log("table update");
        //  console.log("child componentDidUpdate");
            console.log("props");
            console.log(this.props);
            console.log("prevProps");
            console.log(prevProps);
            // console.log(prevProps);
            // console.log("state");
            // console.log(this.state);
            this.setState({
                columns:this.props.col,
                recordCount:this.props.recCount,
                id: this.props.id,
                filename: this.props.fileName,
                isFirst:true,
            })
            this.tableRef.current.onQueryChange();
            console.log(this.state);
        }

        //console.log("~~~~~~~~~~~~~~~~~~~~~~~~");

    }
    componentWillUnmount(){
        sessionStorage.setItem('page',this.state.page);
        console.log("unmounting... page="+sessionStorage.getItem('page'));
    }
    render() {
        //console.log("child render");
        return (
            <div> 
                    <MaterialTable
                    tableRef={this.tableRef}
                    id="materialtable"
                    options={{
                        search:false,
                    }}
                    
                    title={this.state.filename}
                    columns= {this.state.columns}
                    data={query=>new Promise((resolve,reject)=>{
                        if(this.state.isFirst){
                            let pageNo=(parseInt(sessionStorage.getItem('page'))!=null)?parseInt(sessionStorage.getItem('page')):0;
   
                            console.log("session page: "+pageNo);
                            if(pageNo==0)
                            query.page=0;
                            else
                            query.page=pageNo;

                            this.state.isFirst=false;
                            console.log("isFirst ran")
                        }
                        else{
                            this.state.page=query.page;
                            console.log(query.page+", "+this.state.page);
                        }
 
                        console.log(query);
                        //(this.state.isFirst)?this.state.isFirst=false:sessionStorage.setItem('page',query.page+1);
                        let headerToken = (localStorage.getItem('token')!=null)?localStorage.getItem('token'):sessionStorage.getItem('token');
                        
                        fetch(`/api/backend/getPagniatedDataSource?dataSourceId=${this.state.id}&page=${query.page+1}&recordCount=${query.pageSize}`,{method:"GET",headers:{'auth':headerToken,}})
                        .then(res=>res.json().then(
                        res=>{
                            console.log(query.page);
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
                            resolve({
                                data:res,
                                page:query.page,
                                totalCount:this.state.recordCount,
                            })
                            
                        }
                        ))
                    }
                    )}
                    
                    />
            </div>
        )
    }
}
