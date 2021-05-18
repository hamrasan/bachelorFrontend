import {
  Container,
  Card,
  Button,
  Accordion,
  CardGroup,
  Form,
  Row,
  Col,
  ListGroup,
} from "react-bootstrap";
import { useState, useEffect } from "react";
import ModalConfirm from "../components/ModalConfirm";
import ErrorComponent from "../components/ErrorComponent";
import { useErrorHandler } from "react-error-boundary";


function TheNotification() {
  const axios = require("axios");
  const [notifications, setNotifications] = useState([]);
  const [modalShow, setModalShow] = useState(null);
  const [lowTemp, setLowTemp] = useState(0);
  const [highTemp, setHighTemp] = useState(15);
  const [error, setError] = useState(false);
  const handleError = useErrorHandler();

  const fetchNotifications = () => {
    axios({
      method: "get",
      withCredentials: true,
      url: "http://localhost:8080/notifications/all",
    })
      .then((res) => {
        if (res.status == 200) {
          setNotifications(res.data);
        } else throw Error(res.status);
      })
      .catch((error) => {
        handleError(error);
        console.error(error);
      });
  };

  const fetchLowTemp = () => {
    axios({
      method: "get",
      withCredentials: true,
      url: "http://localhost:8080/user/lowTemp",
    })
      .then((res) => {
        if (res.status == 200) {
          console.log(res.data);
          setLowTemp(res.data);
        } else throw Error(res.status);
      })
      .catch((error) => {
        console.error(error);
        handleError(error);
      });
  };

  const fetchHighTemp = () => {
    axios({
      method: "get",
      withCredentials: true,
      url: "http://localhost:8080/user/highTemp",
    })
      .then((res) => {
        if (res.status == 200) {
          console.log(res.data);
          setHighTemp(res.data);
        } else throw Error(res.status);
      })
      .catch((error) => {
        console.error(error);
        handleError(error);
      });
  };

  const setupNotifications = () => {
    axios({
      method: "post",
      withCredentials: true,
      url: "http://localhost:8080/user/notifications/",
      data: {
        lowTemp: lowTemp,
        highTemp: highTemp,
      },
    })
      .then((res) => {
        if (res.status == 200) {
        } else throw Error(res.status);
      })
      .catch((error) => {
        console.error(error);
        handleError(error);
      });
  };

  const addNotificationSeen = (id) => {
    axios({
      method: "post",
      withCredentials: true,
      url: "http://localhost:8080/notifications/seen/" + id,
      data: {},
    })
      .then((res) => {
        if (res.status == 200) {
          fetchNotifications();
        } else throw Error(res.status);
      })
      .catch((error) => {
        console.error(error);
        handleError(error);
      });
  };

  const handleConfirm = () => {
    setupNotifications();
    setModalShow(false);
  };

  useEffect(() => {
    fetchNotifications();
    fetchHighTemp();
    fetchLowTemp();
  }, [error]);

  return (
    <ErrorComponent onReset={() => setError(true)}>
      <div>
        <Container fluid className="pt-3 w-75">
          <Accordion defaultActiveKey="0">
            <Card>
              <Accordion.Toggle
                as={Card.Header}
                eventKey="0"
                className="bg-warning d-flex justify-content-center"
              >
                Notifikácie
              </Accordion.Toggle>
              <Accordion.Collapse eventKey="0">
                <Card.Body>
                  <Row>
                    <Col>
                      <ListGroup>
                        {notifications.map((notification) => (
                          <ListGroup.Item>
                            <span>{notification.message}</span>
                            <button
                              type="button"
                              className="close"
                              onClick={() => {
                                addNotificationSeen(notification.id);
                              }}
                            >
                              <span aria-hidden="true">✓</span>
                            </button>
                          </ListGroup.Item>
                        ))}
                      </ListGroup>
                    </Col>
                  </Row>
                </Card.Body>
              </Accordion.Collapse>
            </Card>
          </Accordion>

          <CardGroup className="pt-5">
            <Card>
              <Card.Header>Požadovaná teplota</Card.Header>
              <Card.Body>
                <Card.Title>Ak teplota stúpne nad:</Card.Title>
                <Form.Control
                  className="mb-2 mr-sm-2"
                  id="inlineFormInputName2"
                  value={highTemp}
                  onChange={(e) => {
                    setHighTemp(e.target.value);
                  }}
                />
              </Card.Body>
            </Card>
            <Card>
              <Card.Header>Požadovaná teplota</Card.Header>
              <Card.Body>
                <Card.Title>Ak teplota klesne pod:</Card.Title>
                <Form.Control
                  className="mb-2 mr-sm-2"
                  id="inlineFormInputName2"
                  value={lowTemp}
                  onChange={(e) => {
                    setLowTemp(e.target.value);
                  }}
                />
              </Card.Body>
            </Card>
          </CardGroup>

          <Row className="mt-3">
            <Col className="d-flex justify-content-center">
              <Button
                variant="outline-success"
                onClick={() => setModalShow(true)}
              >
                Upraviť
              </Button>
              <ModalConfirm
                show={modalShow}
                title={"Upraviť notifikácie"}
                body={"Naozaj chceš upraviť notifikácie ?"}
                onSubmit={() => handleConfirm()}
                onHide={() => setModalShow(false)}
              />
            </Col>
          </Row>
        </Container>
      </div>
    </ErrorComponent>
  );
}

export default TheNotification;
