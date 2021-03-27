// import logo from "./logo.svg";
// import "./App.css";
import TheNavigation from './components/TheNavigation'
import RegisterForm from './components/RegisterForm'
import LoginForm from './components/LoginForm'
import TheGarden from './views/TheGarden'
import TheWeather from './views/TheWeather'
import TheHome from './views/TheHome'
import PlantDetail from './views/PlantDetail'
import MyProfile from './views/MyProfile'
import Context from "./appContext";
import AuthRoute from "./AuthRoute";
import { useState, useEffect} from "react";
import Cookies from "js-cookie";
import { Route, Switch } from 'react-router-dom';
import axios from 'axios'

function App() {

  const [isAuth,setIsAuth]= useState(false);
  const [name,setName]= useState('');
  const [oldUrl, setOldUrl] = useState(null);

  const login = () => {
    setIsAuth(true);
  }

  useEffect(() => {
    if (Cookies.get("JSESSIONID") && isAuth == false){
      login();
    }
  }, isAuth);

  const logout = () =>{
    axios({
      method: 'get',
      url: 'http://localhost:8080/logout',
      withCredentials: true,
    })
    .then((res) => {
      console.log(res.data);
      Cookies.remove("JSESSIONID");
      setIsAuth(false);
    });
  }

  const contextValue = {
    isAuth: isAuth,
    login: login,
    logout: logout,
    oldUrl: oldUrl,
    setOldUrl: setOldUrl
  };

  return (
    <Context.Provider value={contextValue}>
    <div className="App">
      
    <header className="App-header">
        <TheNavigation/> 
        
    </header>
    <div>
      
        <Switch>
          <AuthRoute path="/login" guest={true}>
            <LoginForm/>
          </AuthRoute>
          <AuthRoute path="/register" guest={true}>
            <RegisterForm/>
          </AuthRoute>
          <AuthRoute path="/weather">
            <TheWeather />
          </AuthRoute>
          <AuthRoute path="/garden/detail/:id">
            <PlantDetail />
          </AuthRoute>
          <AuthRoute path="/garden" exact>
            <TheGarden />
          </AuthRoute>          
          <AuthRoute path="/profile">
            <MyProfile/>
          </AuthRoute>
          <AuthRoute path="/" exact>
            <TheHome />
          </AuthRoute>
        </Switch>
      
    </div>
    
    </div>
    </Context.Provider>

    // <div className="App">
      
    //     <Form inline>
    //         <FormControl type="text" placeholder="Search" className="mr-sm-2" />
    //         <Button variant="outline-info">Search</Button>
    //     </Form>
      
    // </div>
  );
}

export default App;
