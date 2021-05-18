import { Modal, Button, Container } from "react-bootstrap";

function ModalConfirm(props) {

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
            {props.body}
        </Container>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={() => props.onSubmit()}>Potvrdiť</Button>
        <Button onClick={props.onHide}>Zatvoriť</Button>
      </Modal.Footer>
    </Modal>
  );
}

export default ModalConfirm;
