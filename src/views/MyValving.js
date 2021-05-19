import { useState, useEffect } from "react";
import { Container, Row, CardDeck, Button } from "react-bootstrap";
import ValveCard from "../components/ValveCard";
import MyVerticallyCenteredModal from "../components/MyVerticallyCenteredModal";
import ErrorComponent from "../components/ErrorComponent";
import { useErrorHandler } from "react-error-boundary";

function MyValving() {
  const [valves, setValves] = useState([]);
  const [gardens, setGardens] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const [error, setError] = useState(false);
  const axios = require("axios");
  const handleError = useErrorHandler();
  let i = 1;

  const updateGardens = (id, gardens) => {
    valves.filter((valve) =>
      valve.id === id ? postAddGardens(id, gardens) : null
    );
  };

  const valvingImmediately = (valveName, length) => {
    axios({
      method: "post",
      url: process.env.REACT_APP_API_URL + "/valve/immediately/" + valveName,
      withCredentials: true,
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      data: {
        length: length,
      },
    })
      .then((res) => {
        if (res.status == 200) {
          console.log(res);
        } else throw Error(res.status);
      })
      .catch((error) => {
        console.error(error);
        handleError(error);
      });
  };

  const postAddGardens = (id, valveGardens) => {
    axios({
      method: "post",
      url: process.env.REACT_APP_API_URL + "/valve/add_gardens/" + id,
      withCredentials: true,
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      data: {
        gardens: valveGardens,
      },
    })
      .then((res) => {
        fetchValves();
      })
      .catch((error) => {
        console.error(error);
        handleError(error);
      });
  };

  const fetchValves = () => {
    axios({
      method: "get",
      url: process.env.REACT_APP_API_URL + "/valve/all",
      withCredentials: true,
    })
      .then((res) => {
        if (res.status == 200) {
          console.log(res);
          setValves(res.data);
        } else throw Error(res.status);
      })
      .catch((error) => {
        console.error(error);
        handleError(error);
      });
  };

  const fetchGardens = () => {
    axios({
      method: "get",
      url: process.env.REACT_APP_API_URL + "/garden/all",
      withCredentials: true,
    })
      .then((res) => {
        if (res.status == 200) {
          console.log(res);
          setGardens(res.data);
        } else throw Error(res.status);
      })
      .catch((error) => {
        console.error(error);
        handleError(error);
      });
  };
  // 636422248cce4eeea646
  const createValve = (id) => {
    axios({
      method: "post",
      url: process.env.REACT_APP_API_URL + "/valve/create/" + id,
      withCredentials: true,
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      data: {},
    })
      .then((res) => {
        if (res.status == 200) {
          fetchValves();
        } 
        else throw Error(res.status);
      })
      .catch((error) => {
        if (error.response.status == 403) {
          error.message= "Pridanie zariadenia s týmto identifikačným číslom nie je možné. Skontrolujte identifikačné číslo a skúste to znova.";
          handleError(error);
        }
        else{
          handleError(error);
        }
      });
  };

  const handleSubmit = (id) => {
    console.log(id);
    createValve(id);
    setModalShow(false);
  };

  useEffect(() => {
    fetchGardens();
    fetchValves();
  }, [modalShow, error]);

  return (
    <ErrorComponent onReset={() => setError(true)}>
      <div>
        <Container className="pt-2">
          <Button
            variant="info"
            className="mt-2 mr-2"
            onClick={() => setModalShow(true)}
          >
            Pridaj polievač
          </Button>
          <MyVerticallyCenteredModal
            show={modalShow}
            onHide={() => setModalShow(false)}
            title={"Pridať polievač"}
            bodyTitle={"Zadaj identifikačné číslo polievača:"}
            bodyText={"addValve"}
            onSubmit={handleSubmit}
          />
        </Container>
        <Container className="pt-3">
          <Row>
            <CardDeck>
              {valves.map((valve) => (
                <ValveCard
                  key={valve.id}
                  valve={valve}
                  number={i++}
                  gardens={gardens}
                  updateGardens={updateGardens}
                  valvingImmediately={valvingImmediately}
                />
              ))}
            </CardDeck>
          </Row>
        </Container>
      </div>
    </ErrorComponent>
  );
}

export default MyValving;
