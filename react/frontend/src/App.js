// import "./App.css";
import TheNavigation from "./components/TheNavigation";
import RegisterForm from "./components/RegisterForm";
import LoginForm from "./components/LoginForm";
import TheGarden from "./views/TheGarden";
import MyValving from "./views/MyValving";
import PlantNewForm from "./components/PlantNewForm";
import ValvingSchedule from "./components/ValvingSchedule";
import TheHome from "./views/TheHome";
import PlantDetail from "./views/PlantDetail";
import MyProfile from "./views/MyProfile";
import Context from "./appContext";
import AuthRoute from "./AuthRoute";
import { useState, useLayoutEffect } from "react";
import Cookies from "js-cookie";
import { Redirect, Switch } from "react-router-dom";
import axios from "axios";

function App() {
  const [isAuth, setIsAuth] = useState(false);
  const [oldUrl, setOldUrl] = useState(null);
  const [user, setUser] = useState(null);


  const login = () => {
    console.log("login name");
    console.log(user);
    axios({
      method: "get",
      url: "http://localhost:8080/user",
      withCredentials: true
    })
    .then((res) => {
      console.log("res.data");
      console.log(res);
      setUser(res.data);
      setIsAuth(true);
    })
    .catch((error) => {
      logoutFrontEnd();
      console.log("after logout");
      console.error(error);
    });
    // setName(user.firstName);
  };

  useLayoutEffect(() => {
    if (Cookies.get("JSESSIONID") && isAuth == false) {
        login();
    }
  }, isAuth);

  const logout = () => {
    axios({
      method: "post",
      withCredentials: true,
      url: "http://localhost:8080/logout",
      data: { },
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
      }
    }).then((res) => {
      console.log(res);
      logoutFrontEnd();
    }).catch((error) => {
      console.log("after logout");
      console.error(error);
    });

  };

  const logoutFrontEnd = () => {
    Cookies.remove("JSESSIONID");
    setIsAuth(false);
    setUser(null);
  };

  const contextValue = {
    isAuth: isAuth,
    login: login,
    logout: logout,
    oldUrl: oldUrl,
    setOldUrl: setOldUrl,
    user: user
  };

  return (
    <Context.Provider value={contextValue}>
      <div className="App">
        <header className="App-header">
          <TheNavigation user={user}/>
        </header>
        <div>
          <Switch>
            <AuthRoute path="/login" guest={true}>
              <LoginForm />
            </AuthRoute>
            <AuthRoute path="/register" guest={true}>
              <RegisterForm />
            </AuthRoute>
            <AuthRoute path="/logout">
              {() => {
                logout();
                return <Redirect to="/login" />;
              }}
            </AuthRoute>
            <AuthRoute path="/valving" exact>
              <MyValving/>
            </AuthRoute>
            <AuthRoute path="/valving/schedule/:id">
              <ValvingSchedule/>
            </AuthRoute>
            <AuthRoute path="/garden/detail/:id">
              <PlantDetail />
            </AuthRoute>
            <AuthRoute path="/garden" exact>
              <TheGarden />
            </AuthRoute>
            <AuthRoute path="/garden/new">
              <PlantNewForm user={user}/>
            </AuthRoute>
            <AuthRoute path="/profile">
              <MyProfile />
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
