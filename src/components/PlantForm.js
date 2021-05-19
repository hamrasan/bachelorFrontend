import { Form, Row, Col, Container, Button } from "react-bootstrap";
import { useState, useEffect } from "react";
import TheSpinner from "../components/TheSpinner";
import ModalConfirm from "../components/ModalConfirm";

function PlantForm(props) {
  let plant = props.plant;
  console.log(plant);
  const [modal, setModal] = useState(false);

  if (plant == null) {
    return <TheSpinner></TheSpinner>;
  }

  return (
    <div>
      <Container>
        <Row>
          <Col>
            <h1 className="text-center">{plant.name}</h1>
          </Col>
        </Row>

        <Row>
          <Col className="d-flex flex-row-reverse mb-3">
            <img
              src={process.env.REACT_APP_API_URL + "/gallery/" + plant.picture}
              class="img-fluid rounded mx-auto"
              alt={plant.name}
              style={{ maxWidth: "400px", maxHeight: "400px" }}
            />
          </Col>
        </Row>

        <Row>
          <Col>
            <Form>
              <Form.Group as={Row} controlId="formPlaintextPassword">
                <Form.Label column sm="2">
                  Najnižšia možná teplota:
                </Form.Label>
                <Col sm="10">
                  <Form.Control defaultValue={plant.minTemperature} onChange={(e) => {
                  props.setMinTemperature(e.target.value);
                }}/>
                </Col>
              </Form.Group>

              <Form.Group as={Row} controlId="formPlaintextPassword">
                <Form.Label column sm="2">
                  Najvyššia možná teplota:
                </Form.Label>
                <Col sm="10">
                  <Form.Control defaultValue={plant.maxTemperature} onChange={(e) => {
                  props.setMaxTemperature(e.target.value);
                }}/>
                </Col>
              </Form.Group>

              <Form.Group as={Row} controlId="formPlaintextPassword">
                <Form.Label column sm="2">
                  Dátum výsadby:
                </Form.Label>
                <Col sm="10">
                  <Form.Control defaultValue={plant.dateOfPlant} />
                </Col>
              </Form.Group>

              <Form.Group as={Row} controlId="formPlaintextPassword">
                <Form.Label column sm="2">
                  Obdobie dozrievania:
                </Form.Label>
                <Col sm="10">
                  <Form.Control defaultValue={plant.season} onChange={(e) => {
                  props.setSeason(e.target.value);
                }}/>
                </Col>
              </Form.Group>
            </Form>
          </Col>
        </Row>

        <Row className="mb-3">
          <Col className="d-flex justify-content-center">
            <Button
              variant="outline-success"
              onClick={() => props.setModalShow(true)}
            >
              Upraviť
            </Button>
            <ModalConfirm
              show={props.modalShow}
              title={"Upraviť dáta rastliny"}
              body={"Naozaj chceš upraviť dáta rastliny " + plant.name + "?"}
              onSubmit={() => props.handleConfirmUpdate(plant.id)}
              onHide={() => props.setModalShow(false)}
            />
          </Col>
          <Col className="d-flex justify-content-center">
            <Button
              variant="outline-danger"
              onClick={() => props.setModalShow(true)}
            >
              Vymazať rastlinu
            </Button>
            <ModalConfirm
              show={props.modalShow}
              title={"Vymazať vybranú rastlinu"}
              body={"Naozaj chceš vymazať rastlinu " + plant.name + "?"}
              onSubmit={() => props.handleConfirmDelete(plant.id)}
              onHide={() => props.setModalShow(false)}
            />
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default PlantForm;
