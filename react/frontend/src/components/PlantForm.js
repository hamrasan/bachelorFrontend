import { Form, Row, Col, Container, Button, Spinner } from "react-bootstrap";
import TheSpinner from "../components/TheSpinner";

function PlantForm(props) {
  let plant = props.plant;

  if(plant==null) {
    return(
      <TheSpinner></TheSpinner>
    )
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
          <Col className="d-flex flex-row-reverse">
            <img
              src={"../" + plant.picture}
              class="img-fluid rounded mx-auto"
              alt={plant.name}
            />
          </Col>
        </Row>

        <Row className="mb-3">
          <Col className="d-flex flex-row-reverse">
            <Button variant="outline-success">Upraviť</Button>{" "}
          </Col>
        </Row>

        <Row>
          <Col>
            <Form>

              <Form.Group as={Row} controlId="formPlaintextPassword">
                <Form.Label column sm="2">
                  Najnižsia možná teplota:
                </Form.Label>
                <Col sm="10">
                  <Form.Control defaultValue={plant.minTemperature} />
                </Col>
              </Form.Group>

              <Form.Group as={Row} controlId="formPlaintextPassword">
                <Form.Label column sm="2">
                  Najvyššia možná teplota:
                </Form.Label>
                <Col sm="10">
                  <Form.Control defaultValue={plant.maxTemperature} />
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
                  <Form.Control defaultValue={plant.season} />
                </Col>
              </Form.Group>
            </Form>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default PlantForm;
