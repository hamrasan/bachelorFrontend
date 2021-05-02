import { Container, CardDeck, Card, Button, Accordion } from "react-bootstrap";
import { useState, useEffect } from "react";
import TheSpinner from "../components/TheSpinner";
import SensorCard from "../components/SensorCard";
import ModalSetSensorsTime from "../components/ModalSetSensorsTime";
import SensorsGarden from "./SensorsGarden";

function TheHome() {
  const axios = require("axios");
  const [userTemp, setUserTemp] = useState(15);
  const [minutes, setMinutes] = useState(15);
  const [modalShow, setModalShow] = useState(false);
  const [gardenId, setGardenId] = useState(null);
  const [gardens, setGardens] = useState([]);

   const fetchGardens = () => {
      axios({
        method: "get",
        withCredentials: true,
        url: "http://localhost:8080/garden/all",
      })
        .then((res) => {
          if ((res.status = 200)) {
            setGardens(res.data);
            console.log(res);
          }
        })
        .catch((error) => {
          console.error(error);
        });
    };

  const setRefreshData = () => {
    axios.get("http://localhost:8080/sensors/request/" + minutes);
  };

  const handleSubmit = (minutes) => {
    setRefreshData(minutes);
    setModalShow(false);
  };

  useEffect(() => {
    fetchGardens();
  }, []);

  return (
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
          {/* <div className="d-flex align-items-baseline">
            <Button
              variant="info"
              className="mb-4 ml-2"
              onClick={() => setModalShow(true)}
            >
              Nastaviť požadovanú teplotu
            </Button>
          </div> */}
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

        {gardens.map(garden =>(
          <Accordion defaultActiveKey="1">
          <Card>
            <Accordion.Toggle as={Card.Header} eventKey="0">
              {garden.name}
            </Accordion.Toggle>
            <Accordion.Collapse eventKey="0">
              <Card.Body>
              {<SensorsGarden gardenId={garden.id}></SensorsGarden>}
              </Card.Body>
            </Accordion.Collapse>
          </Card>
        </Accordion>
        ))}
      </Container>
    </div>
  );
}

export default TheHome;
