import { Card, Container, CardDeck, Spinner, Row, Col } from "react-bootstrap";
import { useState, useEffect } from "react";
import TheSpinner from "../components/TheSpinner";

function TheHome() {
  const axios = require("axios");
  const [temperature, setTemperature] = useState(null);

  const getTemperature = () => {
    axios.get("http://localhost:8080/sensors/temperature").then((res) => {
      console.log(res.data);
      setTemperature(res.data);
    });
  };

  const makeFormattedDate = (oldDate) => {
    let date = new Date(oldDate);
    return date.toLocaleDateString() + " " + date.toLocaleTimeString();
  };

  useEffect(() => {
    getTemperature();
  }, []);

  if (temperature == null) {
    return <TheSpinner></TheSpinner>;
  }

  return (
    <div>
      <Container>
        <CardDeck>
          <Card>
            <Card.Img
              variant="top"
              src="assets/jahoda.jpg"
              style={{ height: "200px", width: "200px" }}
            />
            <Card.Body>
              <Card.Title>Teplota</Card.Title>
              <Card.Text>{temperature.value} °C</Card.Text>
            </Card.Body>
            <Card.Footer>
              <small className="text-muted">Dátum merania: {makeFormattedDate(temperature.date)}</small>
            </Card.Footer>
          </Card>
        </CardDeck>
      </Container>
    </div>
  );
}

export default TheHome;
