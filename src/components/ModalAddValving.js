import { Modal, Button, Form, Container } from "react-bootstrap";
import TimePicker from "react-time-picker";

function ModalAddValving(props) {

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
          <Form >
            <Form.Group>
              <Form.Check
                inline
                onClick={() => props.handleCheckbox(1)}
                label="Pondelok"
                type="checkbox"
              />
              <Form.Check
                inline
                onClick={() => props.handleCheckbox(2)}
                label="Utorok"
                type="checkbox"
              />
              <Form.Check
                inline
                onClick={() => props.handleCheckbox(3)}
                label="Streda"
                type="checkbox"
              />
              <Form.Check
                inline
                onClick={() => props.handleCheckbox(4)}
                label="Štvrtok"
                type="checkbox"
              />
              <Form.Check
                inline
                onClick={() => props.handleCheckbox(5)}
                label="Piatok"
                type="checkbox"
              />
              <Form.Check
                inline
                onClick={() => props.handleCheckbox(6)}
                label="Sobota"
                type="checkbox"
              />
              <Form.Check
                inline
                onClick={() => props.handleCheckbox(7)}
                label="Nedeľa"
                type="checkbox"
              />
            </Form.Group>
            <Form.Group>
              <Form.Label
                className="my-1 mr-2"
                htmlFor="inlineFormCustomSelectPref"
              >
                Čas
              </Form.Label>
              <TimePicker onChange={props.setTime} value={props.time} />
            </Form.Group>
            <Form.Group>
              <Form.Label>Dĺžka polievania (v min)</Form.Label>
              <Form.Control
                className="mb-2 mr-sm-2"
                id="inlineFormInputName2"
                placeholder="15"
                onChange={(e) => {
                  props.setValvingLength(e.target.value);
                }}
              />
            </Form.Group>
          </Form>
        </Container>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={() => props.onSubmit()}>Potvrdiť</Button>
        <Button onClick={props.onHide}>Zatvoriť</Button>
      </Modal.Footer>
    </Modal>
  );
}

export default ModalAddValving;
