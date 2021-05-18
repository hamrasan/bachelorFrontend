import { Form, Col, Button, Row, Container } from "react-bootstrap";
import { Link } from "react-router-dom";
import { useState, useEffect, useContext } from "react";
import appContext from "../appContext";
import { useErrorHandler } from "react-error-boundary";


function LoginForm() {
  const axios = require("axios");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  let context = useContext(appContext);
  const handleError = useErrorHandler();

  const handleSubmit = (e) => {
    e.preventDefault();

    axios({
      method: 'post',
      url: 'http://localhost:8080/login',
      withCredentials: true,
      headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
      },
      data: {
        email: email,
        password: password,
      }
    }).then(res => {
      if(res.status == 200){
        context.login();
      } else throw Error(res.status);
    })
    .catch((error) => {
      handleError(error);
      context.logout();
      console.log("after logout");
      console.error(error);
    });
  
  };

  return (
    <div className="loginForm">
      <Container className="pt-5">
        <Form onSubmit={handleSubmit}>
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

          <Form.Group as={Row}>
            <Col className="d-flex justify-content-center">
              <span className="mr-1">Ešte nemáš účet?</span>

              <Link to="/register" className="ml-1">
                {" "}
                Registruj sa tu!
              </Link>
            </Col>
          </Form.Group>

          <Form.Group as={Row}>
            <Col className="d-flex justify-content-center">
              <Button type="submit">Prihlásiť</Button>
            </Col>
          </Form.Group>
        </Form>
      </Container>
    </div>
  );
}

export default LoginForm;
