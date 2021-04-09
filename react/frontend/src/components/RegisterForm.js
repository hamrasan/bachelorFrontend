import { Form, Col, Button, Row, Container } from "react-bootstrap";
import { useState } from "react";
import { useHistory } from "react-router-dom";

function RegisterForm() {
  const axios = require("axios");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [passwordAgain, setPasswordAgain] = useState("");
  let history = useHistory();

  const handleSubmit = (e) => {
    e.preventDefault();

    axios({
      method: "post",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      withCredentials: true,
      url: "http://localhost:8080/user",
      data: {
        user: {
          firstName: firstName,
          lastName: lastName,
          password: password,
          email: email,
        },
        password_control: passwordAgain
      },
    })
      .then((res) => {
        console.log("daaata");
        console.log(res);
        if(res.status = 200){
          history.push("/");
        }
      })
      .catch((error) => {
        console.log("after register");
        console.error(error);
      });
  };

  return (
    <div className="registerForm">
      <Container className="pt-5">
        <Form onSubmit={handleSubmit}>
          <Form.Group as={Row} controlId="formHorizontalName">
            <Form.Label column sm={2}>
              Meno
            </Form.Label>
            <Col sm={10}>
              <Form.Control
                type="text"
                placeholder="Meno"
                onChange={(e) => {
                  setFirstName(e.target.value);
                }}
              />
            </Col>
          </Form.Group>

          <Form.Group as={Row} controlId="formHorizontalLastName">
            <Form.Label column sm={2}>
              Priezvisko
            </Form.Label>
            <Col sm={10}>
              <Form.Control
                type="text"
                placeholder="Priezvisko"
                onChange={(e) => {
                  setLastName(e.target.value);
                }}
              />
            </Col>
          </Form.Group>

          <Form.Group as={Row} controlId="formHorizontalEmail">
            <Form.Label column sm={2}>
              Email
            </Form.Label>
            <Col sm={10}>
              <Form.Control
                type="email"
                placeholder="Email"
                onChange={(e) => {
                  setEmail(e.target.value);
                }}
              />
            </Col>
          </Form.Group>

          <Form.Group as={Row} controlId="formHorizontalPassword">
            <Form.Label column sm={2}>
              Heslo
            </Form.Label>
            <Col sm={10}>
              <Form.Control
                type="password"
                placeholder="Heslo"
                onChange={(e) => {
                  setPassword(e.target.value);
                }}
              />
            </Col>
          </Form.Group>

          <Form.Group as={Row} controlId="formHorizontalPassword">
            <Form.Label column sm={2}>
              Heslo znova
            </Form.Label>
            <Col sm={10}>
              <Form.Control
                type="password"
                placeholder="Heslo"
                onChange={(e) => {
                  setPasswordAgain(e.target.value);
                }}
              />
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
