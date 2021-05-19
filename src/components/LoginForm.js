import { Form, Col, Button, Row, Container } from "react-bootstrap";
import { Link } from "react-router-dom";
import { useState, useEffect, useContext } from "react";
import appContext from "../appContext";
import ErrorComponent from "../components/ErrorComponent";
import { useErrorHandler } from "react-error-boundary";

function LoginForm() {
  const axios = require("axios");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(false);
  let context = useContext(appContext);
  const handleError = useErrorHandler();

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(process.env.REACT_APP_API_URL);

    const requestOptions = {
      method: "POST",
      mode: "cors",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email: email, password: password }),
    };

    fetch(process.env.REACT_APP_API_URL + "/login", requestOptions)
      .then((res) => {
        if (res.status == 200) {
          context.login();
        }
      })
      .catch((error) => {
        if (error.response.status == 401) {
          error.message = " Nesprávne meno alebo heslo. Skontrolujte vaše údaje a skúste to znova.";
          handleError(error);
          context.logout();
        }
        else{
          context.logout();
          handleError(error);
        }
        console.error(error);
      });
  };

  return (
    <ErrorComponent onReset={() => setError(true)}>
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
    </ErrorComponent>
  );
}

export default LoginForm;
