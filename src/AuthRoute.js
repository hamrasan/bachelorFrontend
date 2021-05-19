import React from "react";
import { Redirect, Route } from "react-router";
import { useContext } from "react";
import contextValue from "./appContext";

function AuthRoute(props) {
  const context = useContext(contextValue);

  if (context.isAuth && props.guest) {
    return  <Redirect to="/" />;
    // <Redirect to={context.oldUrl}/>;   
  }
  else if (context.isAuth) {
    return <Route {...props}>{props.children}</Route>;
  } else if (!context.isAuth && props.guest) {
    return <Route {...props}>{props.children}</Route>;
  } else {
    context.setOldUrl(props.path);
    return <Redirect to="/login" />;
  }
}

export default AuthRoute;
