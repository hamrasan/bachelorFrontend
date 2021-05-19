import { Container, CardDeck, Card, Button, Accordion } from "react-bootstrap";
import { useState, useEffect } from "react";
import ModalSetSensorsTime from "../components/ModalSetSensorsTime";
import SensorsGarden from "./SensorsGarden";
import ErrorComponent from "../components/ErrorComponent";
import { useErrorHandler } from "react-error-boundary";


function TheHome() {
  const axios = require("axios");
  const [userTemp, setUserTemp] = useState(15);
  const [minutes, setMinutes] = useState(15);
  const [modalShowTemp, setModalShowTemp] = useState(false);
  const [modalShow, setModalShow] = useState(false);
  const [gardenId, setGardenId] = useState(null);
  const [gardens, setGardens] = useState([]);
  const [error, setError] = useState(false);
  const handleError = useErrorHandler();

   const fetchGardens = () => {
      axios({
        method: "get",
        withCredentials: true,
        url: process.env.REACT_APP_API_URL + "/garden/all",
      })
        .then((res) => {
          if ((res.status == 200)) {
            setGardens(res.data);
            console.log(res);
          }else throw Error(res.status);
        })
        .catch((error) => {
          handleError(error);
          console.error(error);
        });
    };

  const setRefreshData = () => {
    axios.get(process.env.REACT_APP_API_URL + "/sensors/request/" + minutes);
  };

  const handleSubmit = (minutes) => {
    setRefreshData(minutes);
    setModalShow(false);
  };

  useEffect(() => {
    fetchGardens();
  }, [error]);

  return (
    <ErrorComponent onReset={ () => setError(true)}>
    <div>
      <Container fluid className="pt-3 w-75">
        <div className="d-flex justify-content-between">
          <div className="d-flex align-items-baseline">
            <h6>
              Interval merania je {minutes} min
            </h6>
            <Button
              variant="info"
              className="mb-4 ml-2"
              onClick={() => setModalShow(true)}
            >
              Zmeniť
            </Button>
          </div>
          <ModalSetSensorsTime
          show={modalShow}
          onHide={() => setModalShow(false)}
          title={"Zmeň časový interval merania senzorov"}
          bodyTitle={"Vyber časový interval v min:"}
          bodyText={"interval"}
          setMinutes={setMinutes}
          minutes={minutes}
          onSubmit={handleSubmit}
        />
        </div>

        {gardens.map(garden =>(
          <Accordion defaultActiveKey="1">
          <Card>
            <Accordion.Toggle as={Card.Header} eventKey="0">
              {garden.name}
            </Accordion.Toggle>
            <Accordion.Collapse eventKey="0">
              <Card.Body>
              {<SensorsGarden gardenId={garden.id} gardenName={garden.name}></SensorsGarden>}
              </Card.Body>
            </Accordion.Collapse>
          </Card>
        </Accordion>
        ))}
      </Container>
    </div>
    </ErrorComponent>
  );
}

export default TheHome;
