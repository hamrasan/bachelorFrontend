import { Modal, Button, Form } from "react-bootstrap";
import { useState } from "react";

function MyVerticallyCenteredModal(props) {
  const [valveGardens, setValveGardens] = useState([]);
  const [idValve, setIdValve] = useState("");
  const [length, setLength] = useState(0);
  const [location, setLocation] = useState("");
  const [name, setName] = useState("");

  const handleCheckbox = (id) => {
    let newArray = [];
    if (!valveGardens.includes(id)) {
      newArray = [...valveGardens, id];
    } else {
      newArray = valveGardens.filter((oldId) => id !== oldId);
    }
    setValveGardens(newArray);
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
        <h4>{props.bodyTitle}</h4>
        <p>
          <Form>
            {props.bodyText === "gardens" ? (
              props.gardens.map((garden) => (
                <Form.Group controlId="formBasicCheckbox">
                  <Form.Check
                    inline
                    onClick={() => handleCheckbox(garden.id)}
                    type="checkbox"
                    defaultChecked={valveGardens.includes(garden.id)} 
                    label={garden.name}
                  />
                </Form.Group>
              ))
            ) : (
              <Form.Group>
                <Form.Row>
                  <Form.Control
                    onChange={(e) => {
                      if (props.bodyText === "addValve")
                        setIdValve(e.target.value);
                      else if (props.bodyText === "immediately")
                        setLength(e.target.value);
                      else if (props.bodyText === "addGarden")
                        setName(e.target.value);
                    }}
                  />
                </Form.Row>
              </Form.Group>
            )}
            {props.bodyText === "addGarden" ? (
              <div>
              <h4>{props.bodySubTitle}</h4>
              <Form.Group>
                <Form.Row>
                  <Form.Control
                    onChange={(e) => {
                        setLocation(e.target.value);
                    }}
                  />
                </Form.Row>
              </Form.Group>          
              </div>
            ) : null}
          </Form>
        </p>
      </Modal.Body>
      <Modal.Footer>
        <Button
          onClick={() =>
            props.onSubmit(
              props.bodyText === "gardens"
                ? valveGardens
                : props.bodyText === "addValve"
                ? idValve
                : props.bodyText === "immediately"
                ? length
                : name,location
            )
          }
        >
          Potvrdiť
        </Button>
        <Button onClick={props.onHide}>Zatvoriť</Button>
      </Modal.Footer>
    </Modal>
  );
}

export default MyVerticallyCenteredModal;
