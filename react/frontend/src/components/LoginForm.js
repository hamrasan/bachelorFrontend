import { Form, Col, Button, Row , Container} from "react-bootstrap";

function LoginForm() {
  return (
    <div className="loginForm">
      <Container>
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

          <Form.Group as={Row}>
            <Col sm={{ span: 10, offset: 2 }}>
              <Button type="submit">Prihlásiť</Button>
            </Col>
          </Form.Group>
        </Form>
      </Container>
    </div>
  );
}

export default LoginForm;
