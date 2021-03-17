import { Card, Container, CardDeck, Spinner, Row, Col } from "react-bootstrap";
import { useState, useEffect } from "react";
import TheSpinner from "../components/TheSpinner";

function TheHome() {
  const axios = require("axios");
  const [temperature, setTemperature] = useState(null);
  const [humidity, setHumidity] = useState(null);
  const [pressure, setPressure] = useState(null);
  const [rain, setRain] = useState(null);

  const fetchTemperature = () => {
    axios.get("http://localhost:8080/sensors/temperature").then((res) => {
      console.log(res.data);
      setTemperature(res.data);
    });
  };

  const fetchPressure = () => {
    axios.get("http://localhost:8080/sensors/pressure").then((res) => {
      console.log(res.data);
      setPressure(res.data);
    });
  };

  const fetchHumidity = () => {
    axios.get("http://localhost:8080/sensors/humidity").then((res) => {
      console.log(res.data);
      setHumidity(res.data);
    });
  };

  const refreshData = () => {
    axios.get("http://localhost:8080/sensors/request").then((res) => {
      fetchHumidity();
      fetchPressure();
      fetchTemperature();
    });
  };

  const fetchRain = () => {
    axios.get("http://localhost:8080/sensors/rain").then((res) => {
      console.log(res.data);
      setRain(res.data);
    });
  };

  const makeFormattedDate = (oldDate) => {
    let date = new Date(oldDate);
    return date.toLocaleDateString() + " " + date.toLocaleTimeString();
  };

  useEffect(() => {
    fetchTemperature();
    fetchHumidity();
    fetchPressure();
    fetchRain();
  }, []);

  if (temperature == null || humidity == null || pressure == null) {
    return <TheSpinner></TheSpinner>;
  }

  return (
    <div>
      <Container className="pt-5">
        <CardDeck>
          <Card>
            <Card.Img
              variant="top"
              src="assets/sensors/teplomer.png"
              style={{ height: "200px", width: "200px" }}
            />
            <Card.Body>
              <Card.Title>Teplota</Card.Title>
              <Card.Text>{temperature.value} °C</Card.Text>
            </Card.Body>
            <Card.Footer>
              <small className="text-muted">
                Dátum merania: {makeFormattedDate(temperature.date)}{" "}
              </small>
            </Card.Footer>
          </Card>

          <Card>
            <Card.Img
              variant="top"
              src="assets/sensors/pressure.png"
              style={{ height: "200px", width: "200px" }}
            />
            <Card.Body>
              <Card.Title>Tlak vzduchu</Card.Title>
              <Card.Text>{pressure.value} hPa </Card.Text>
            </Card.Body>
            <Card.Footer>
              <small className="text-muted">
                Dátum merania: {makeFormattedDate(pressure.date)}{" "}
              </small>
            </Card.Footer>
          </Card>

          <Card>
            <Card.Img
              variant="top"
              src="assets/sensors/humiditySmaller.png"
              style={{ height: "200px", width: "200px" }}
            />
            <Card.Body>
              <Card.Title>Vlhkosť vzduchu</Card.Title>
              <Card.Text>{humidity.value} %</Card.Text>
            </Card.Body>
            <Card.Footer>
              <small className="text-muted">
                Dátum merania: {makeFormattedDate(humidity.date)}{" "}
              </small>
            </Card.Footer>
          </Card>
        </CardDeck>
        <br />
        <button
          type="button"
          class="btn btn-warning"
          onClick={() => refreshData()}
        >
          Aktualizuj
        </button>
      </Container>
      <br />

      {rain != null ? (
        <Container>
          <CardDeck>
            <Card>
              <Card.Img
                variant="top"
                src={
                  rain.raining
                    ? "assets/sensors/rain.jpg"
                    : "assets/sensors/sun.jpg"
                }
                style={{ height: "200px", width: "200px" }}
              />
              <Card.Body>
                <Card.Title>Zrážky</Card.Title>
                <Card.Text>
                  {rain.raining ? "Prší" : "V tomto okamžiku neprší"}
                </Card.Text>
              </Card.Body>
              <Card.Footer>
                <small className="text-muted">
                  Dátum merania: {makeFormattedDate(rain.date)}{" "}
                </small>
              </Card.Footer>
            </Card>
          </CardDeck>
        </Container>
      ) : (
       null
      )}
    </div>
  );
}

export default TheHome;
