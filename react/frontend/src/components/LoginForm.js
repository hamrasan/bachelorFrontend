import { Form, Col, Button, Row, Container } from "react-bootstrap";
import { Link } from "react-router-dom";

function LoginForm() {
  //   const styleReg = {
  //     margin:,
  //     width: "200px",
  // }

  return (
    <div className="loginForm">
      <Container className="pt-5">
        <Form>
          <Form.Group as={Row} controlId="formHorizontalEmail">
            <Form.Label column sm={2}>
              Email
            </Form.Label>
            <Col sm={10}>
              <Form.Control type="email" placeholder="Email" />
            </Col>
          </Form.Group>

          <Form.Group as={Row} controlId="formHorizontalPassword">
            <Form.Label column sm={2}>
              Heslo
            </Form.Label>
            <Col sm={10}>
              <Form.Control type="password" placeholder="Heslo" />
            </Col>
          </Form.Group>

          <Form.Group as={Row} >
          <Col className="d-flex justify-content-center">
              <span className= "mr-1">Ešte nemáš účet?</span>
          
              <Link to="/register" className="ml-1"> Registruj sa tu!</Link>
            </Col>
          </Form.Group>

          <Form.Group as={Row}>
          <Col className="d-flex justify-content-center" >
              <Button type="submit">Prihlásiť</Button>
            </Col>
          </Form.Group>
          
        </Form>
      </Container>
    </div>
  );
}

export default LoginForm;
