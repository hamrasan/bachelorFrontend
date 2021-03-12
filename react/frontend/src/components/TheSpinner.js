import { Container, Spinner, Row,Col } from "react-bootstrap";

function TheSpinner() {

    return (
      <Container>
        <Row className="pt-5">
          <Col className="d-flex justify-content-center">
            <Spinner className="text-center" animation="border" />
          </Col>
        </Row>
      </Container>
    );
}

export default TheSpinner;
