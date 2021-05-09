import { Modal, Button, Container, Form } from "react-bootstrap";

function ModalSetSensorsTime(props) {
  const setMinutes = (value) => {
    switch (value) {
      case "1":
        return props.setMinutes(15);
      case "2":
        return props.setMinutes(30);
      case "3":
        return props.setMinutes(60);
      case "4":
        return props.setMinutes(90);
      case "5":
        return props.setMinutes(120);
      case "6":
        return props.setMinutes(180);
      default:
        return props.setMinutes(15);
    }
  };

  return (
    <Modal
      {...props}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          {props.title}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Container>
          <p>
            <Form>
              {props.bodyText === "interval" ? (
                <Form.Group controlId="exampleForm.ControlSelect1">
                  <Form.Label>{props.bodyTitle}</Form.Label>
                  <Form.Control
                    as="select"
                    onChange={(e) => {
                      setMinutes(e.target.value);
                    }}
                  >
                    <option value="1">15</option>
                    <option value="2">30</option>
                    <option value="3">60</option>
                    <option value="4">90</option>
                    <option value="5">120</option>
                    <option value="6">180</option>
                  </Form.Control>
                </Form.Group>
              ) : null}
            </Form>
          </p>
        </Container>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={() => props.onSubmit(props.minutes)}>
          Potvrdiť
        </Button>
        <Button onClick={props.onHide}>Zatvoriť</Button>
      </Modal.Footer>
    </Modal>
  );
}

export default ModalSetSensorsTime;
