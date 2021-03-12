import { Form, Col, Button, Row , Container} from "react-bootstrap";

function RegisterForm() {
  return (
    <div className="registerForm">
      <Container className="pt-5">
        <Form>
        <Form.Group as={Row} controlId="formHorizontalName">
            <Form.Label column sm={2}>
              Meno
            </Form.Label>
            <Col sm={10}>
              <Form.Control type="email" placeholder="Meno" />
            </Col>
          </Form.Group>

          <Form.Group as={Row} controlId="formHorizontalLastName">
            <Form.Label column sm={2}>
              Priezvisko
            </Form.Label>
            <Col sm={10}>
              <Form.Control type="email" placeholder="Priezvisko" />
            </Col>
          </Form.Group>

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

          <Form.Group as={Row} controlId="formHorizontalPassword">
            <Form.Label column sm={2}>
              Heslo znova
            </Form.Label>
            <Col sm={10}>
              <Form.Control type="password" placeholder="Heslo" />
            </Col>
          </Form.Group>

          <Form.Group as={Row}>
            <Col sm={{ span: 10, offset: 2 }}>
              <Button type="submit">Registrovať</Button>
            </Col>
          </Form.Group>
        </Form>
      </Container>
    </div>
  );
}

export default RegisterForm;