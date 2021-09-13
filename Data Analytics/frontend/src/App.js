import { BrowserRouter, Route} from 'react-router-dom';
import './App.css';
import AddData from './Components/AddData';
import AddUsers from './Components/AddUsers';
import Dashboard from './Components/Dashboard';
import Login from './Components/Login';
import Navbar from './Components/Navbar';
import Profile from './Components/Profile';

import Settings from './Components/Settings';
import ShowData from './Components/ShowData';


function App() {
  const LoginContainer =()=>(
    <div>
        <Route path="/" component={Login}/>
    </div>
  )
  const DefaultContainer =()=>(
    <div>
          <Navbar/>
        <Route path="/dashboard" component={Dashboard}/>
        <Route path="/settings" component={Settings}/>
        <Route exact path="/add-users" component={AddUsers}/>
        <Route exact path="/add-data" component={AddData}/>
        <Route exact path="/show-data/:id" component={ShowData}/>
        <Route exact path="/profile" component={Profile}/>
    </div>
  )
  return (
    <div>
      <BrowserRouter>
        <Route exact path="/" component={LoginContainer}/>
        <Route exact path="/dashboard" component={DefaultContainer}/>
        <Route exact path="/settings" component={DefaultContainer}/>
        <Route exact path="/add-users" component={DefaultContainer}/>
        <Route exact path="/add-data" component={DefaultContainer}/>
        <Route exact path="/show-data/:id" component={DefaultContainer}/>
        <Route exact path="/profile" component={DefaultContainer}/>

      </BrowserRouter>
    </div>

    
  );
}

export default App;
