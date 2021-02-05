// import logo from "./logo.svg";
// import "./App.css";
import TheNavigation from './components/TheNavigation'
import LoginForm from './components/LoginForm'
import TheGarden from './views/TheGarden'
import TheWeather from './views/TheWeather'
import TheHome from './views/TheHome'

import { Route, Switch } from 'react-router-dom';

function App() {
  return (
    <div className="App">
    <header className="App-header">
        <TheNavigation/> 
    </header>

    <div>
        <Switch>
          <Route path="/garden">
            <TheGarden />
          </Route>
          <Route path="/login">
            <LoginForm/>
          </Route>
          <Route path="/weather">
            <TheWeather />
          </Route>
          <Route path="/">
            <TheHome />
          </Route>
        </Switch>
    </div>

    </div>
    
    // <div className="App">
      
    //     <Form inline>
    //         <FormControl type="text" placeholder="Search" className="mr-sm-2" />
    //         <Button variant="outline-info">Search</Button>
    //     </Form>
      
    // </div>
  );
}

export default App;
