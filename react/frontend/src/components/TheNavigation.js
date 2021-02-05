import {
    Navbar,
    Nav
  } from "react-bootstrap";

  import {
    Link
  } from "react-router-dom";

function TheNavigation() {
    return(
        <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
          <Navbar.Brand as={Link} to="/" exact="true">Inteligentná záhrada</Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="mr-auto">
            <Nav.Link as={Link} to="/" exact="true">Domov</Nav.Link>
            <Nav.Link as={Link} to="/garden">Moja záhrada</Nav.Link>
            <Nav.Link as={Link} to="/weather" >Počasie</Nav.Link>
          </Nav>
          <Nav>
            <Nav.Link as={Link} to="/login"> Prihlásiť sa</Nav.Link>
          </Nav>
          </Navbar.Collapse>
        </Navbar>
    );
}

export default TheNavigation;