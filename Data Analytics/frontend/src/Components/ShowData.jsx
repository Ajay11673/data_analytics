import React, { Component } from 'react'
import MaterialTable from 'material-table';

export default class ShowData extends Component {
    constructor(props){
        super(props);
        this.state={
            columns:[],
            recordCount:0,
            id: props.location.load.data.id,
            filename: props.location.load.data.file_name,
        }
        console.log("props");
        console.log(props);
        
    }
    componentDidUpdate(prevProps){
        console.log("update:");
        console.log("prev: "+prevProps.match.params.id);
        console.log("current: "+this.props.match.params.id);
        if(prevProps.match.params.id!=this.props.match.params.id){
            this.setState({
                id: this.props.location.load.data.id,
            filename: this.props.location.load.data.file_name,
            });
            this.componentDidMount();
            this.render();
        }

    }
    async componentDidMount(){
        await fetch("http://localhost:8080/demo/getDataSourceHeaders",{method:"POST",body:JSON.stringify(this.props.location.load.data)})
        .then(res=>res.json().then(
            res=>{
                console.log(res);
                this.setState({
                    columns:res,
                    recordCount:1000,
                });
                console.log(this.state.id);
            }
        ))
    }
    
    render() {
        return (
            <div className="container" style={{marginTop:'10px'}}>
                <MaterialTable 
                options={{
                    search:false,
                }}
                title={this.state.filename}
                columns= {this.state.columns}
                data={query=>new Promise((resolve,reject)=>{
                    let id = this.state.id;
                    console.log(id);
                    fetch(`http://localhost:8080/demo/getPagniatedDataSource?dataSourceId=${this.state.id}&page=${query.page+1}&recordCount=${query.pageSize}`,{method:"GET"})
                    .then(res=>res.json().then(
                    res=>{
                        console.log(res);
                        resolve({
                            data:res,
                            page:query.page,
                            totalCount:this.state.recordCount,
                        })
                    }
                    ))
                })}
                />
            </div>
        )
    }
}


