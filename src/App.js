// import "./App.css";
import TheNavigation from "./components/TheNavigation";
import TheNotification from "./views/TheNotification";
import RegisterForm from "./components/RegisterForm";
import LoginForm from "./components/LoginForm";
import TheGarden from "./views/TheGarden";
import MyValving from "./views/MyValving";
import PlantNewForm from "./components/PlantNewForm";
import ValvingSchedule from "./components/ValvingSchedule";
import TheHome from "./views/TheHome";
import MyHistory from "./views/MyHistory";
import PlantDetail from "./views/PlantDetail";
import MyProfile from "./views/MyProfile";
import Context from "./appContext";
import AuthRoute from "./AuthRoute";
import { useState, useLayoutEffect } from "react";
import Cookies from "js-cookie";
import { Redirect, Switch } from "react-router-dom";
import axios from "axios";
import ErrorComponent from "./components/ErrorComponent";


function App() {
  const [isAuth, setIsAuth] = useState(false);
  const [oldUrl, setOldUrl] = useState(null);
  const [user, setUser] = useState(null);
  const [error, setError] = useState(false);

  const login = () => {
    console.log("login name");
    console.log(user);
    axios({
      method: "get",
      url: "http://localhost:8080/user",
      withCredentials: true,
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
      data: {},
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        console.log(res);
        logoutFrontEnd();
      })
      .catch((error) => {
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
    user: user,
  };


  return (
    <Context.Provider value={contextValue}>
      <div className="App">
        <header className="App-header">
          <TheNavigation user={user} />
        </header>

        <ErrorComponent onReset={ () => setError(true)}>
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
                <MyValving />
              </AuthRoute>
              <AuthRoute path="/valving/schedule/:id">
                <ValvingSchedule />
              </AuthRoute>
              <AuthRoute path="/history/:sensor/:name">
                <MyHistory />
              </AuthRoute>
              <AuthRoute path="/garden/detail/:id">
                <PlantDetail />
              </AuthRoute>
              <AuthRoute path="/garden" exact>
                <TheGarden />
              </AuthRoute>
              <AuthRoute path="/garden/new/:name">
                <PlantNewForm user={user} />
              </AuthRoute>
              <AuthRoute path="/profile">
                <MyProfile />
              </AuthRoute>
              <AuthRoute path="/notifications" exact>
                <TheNotification />
              </AuthRoute>
              <AuthRoute path="/" exact>
                <TheHome />
              </AuthRoute>
            </Switch>
          </div>
        </ErrorComponent>
      </div>
    </Context.Provider>
  );
}

export default App;
