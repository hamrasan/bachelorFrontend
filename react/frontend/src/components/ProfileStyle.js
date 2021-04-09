import { Container, Col, Row, Image } from "react-bootstrap";
import context from "react-bootstrap/esm/AccordionContext";

function ProfileStyle(props) {
  const pic = props.picture;
  const username = props.user.firstName + " " + props.user.lastName;

  return (
    <Container className="mt-3">
      <Row className="d-flex justify-content-center">
        <Col className="text-center" xs={6} md={4}>
          <Image
            src={pic}
            style={{ height: "auto", width: "70%" }}
            roundedCircle
          />
        </Col>
      </Row>
      <Row className="d-flex justify-content-center mt-3">
        <Col className="text-center" xs={6} md={4}>
          <h1>{username}</h1>
        </Col>
      </Row>
      <Row>
        <Col className="text-center">
          <h6>{props.user.email}</h6>
        </Col>
      </Row>
    </Container>
  );
}

export default ProfileStyle;
