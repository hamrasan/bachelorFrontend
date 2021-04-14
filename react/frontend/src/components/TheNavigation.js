import { Navbar, Nav } from "react-bootstrap";

import { Link } from "react-router-dom";
import { useContext } from "react";
import contextValue from "../appContext";

function TheNavigation(props) {
  const context = useContext(contextValue);

  return (
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
      <Navbar.Brand as={Link} to="/" exact="true">
        Inteligentná záhrada
      </Navbar.Brand>
      <Navbar.Toggle aria-controls="responsive-navbar-nav" />
      <Navbar.Collapse id="responsive-navbar-nav">
        <Nav className="mr-auto">
          <Nav.Link as={Link} to="/" exact="true">
            Domov
          </Nav.Link>
          <Nav.Link as={Link} to="/garden">
            Moja záhrada
          </Nav.Link>
          <Nav.Link as={Link} to="/valving">
            Zalievanie
          </Nav.Link>
          <Nav.Link as={Link} to="/weather">
            Počasie
          </Nav.Link>
        </Nav>
        {context.isAuth ? (
          <Nav>
            <Nav.Link as={Link} to="/logout">
              {" "}
              Odhlásiť
            </Nav.Link>
            <Nav.Link as={Link} to="/profile">
              {" "}
              {props.user.firstName}
            </Nav.Link>
          </Nav>
        ) : (
          <Nav>
            <Nav.Link as={Link} to="/login">
              {" "}
              Prihlásiť sa
            </Nav.Link>
          </Nav>
        )}
      </Navbar.Collapse>
    </Navbar>
  );
}

export default TheNavigation;
