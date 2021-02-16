// import logo from "./logo.svg";
// import "./App.css";
import TheNavigation from './components/TheNavigation'
import RegisterForm from './components/RegisterForm'
import LoginForm from './components/LoginForm'
import TheGarden from './views/TheGarden'
import TheWeather from './views/TheWeather'
import TheHome from './views/TheHome'
import PlantDetail from './views/PlantDetail'

import { Route, Switch } from 'react-router-dom';

function App() {
  return (
    <div className="App">
    <header className="App-header">
        <TheNavigation/> 
    </header>

    <div>
        <Switch>
          <Route path="/login">
            <LoginForm/>
          </Route>
          <Route path="/register">
            <RegisterForm/>
          </Route>
          <Route path="/weather">
            <TheWeather />
          </Route>
          <Route path="/garden/detail/:id">
            <PlantDetail />
          </Route>
          <Route path="/garden" exact>
            <TheGarden />
          </Route>
          <Route path="/" exact>
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
