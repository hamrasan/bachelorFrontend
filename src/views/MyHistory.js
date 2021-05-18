import ProfileStyle from "../components/ProfileStyle";
import contextValue from "../appContext";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import ErrorComponent from "../components/ErrorComponent";
import { useErrorHandler } from "react-error-boundary";

import {
  Container,
  Row,
  Form,
  Col,
  Button,
  ListGroup,
  ListGroupItem,
} from "react-bootstrap";

function MyHistory() {
  const axios = require("axios");
  const params = useParams();
  const [history, setHistory] = useState([]);
  const [error, setError] = useState(false);
  const handleError = useErrorHandler();

  const makeFormattedDate = (oldDate) => {
    let date = new Date(oldDate);
    return date.toLocaleDateString() + " " + date.toLocaleTimeString();
  };

  const fetchHistory = () => {
    axios({
      method: "get",
      withCredentials: true,
      url:
        "http://localhost:8080/sensors/history_all/" +
        params.sensor +
        "/" +
        params.name,
    })
      .then((res) => {
        if ((res.status == 200)) {
          setHistory(res.data);
          console.log(res);
        } else throw Error(res.status);
      })
      .catch((error) => {
        handleError(error);
        console.error(error);
      });
  };

  useEffect(() => {
    fetchHistory();
  }, [error]);

  return (
    <ErrorComponent onReset={ () => setError(true)}>
    <div>
      <Container>
        <ListGroup className="list-group-flush">
          {history.map((item) => (
            <ListGroupItem className="d-flex flex-row justify-content-around">
              {" "}
              <span>{makeFormattedDate(item.date)}</span>{" "}
              <span>
                {params.sensor == "rain"
                  ? item.value == false
                    ? "Neprší"
                    : "Prší"
                  : params.sensor == "temperature"
                  ? item.value + " °C"
                  : params.sensor == "pressure"
                  ? item.value + " hPa"
                  : item.value + " %"}
              </span>
            </ListGroupItem>
          ))}
        </ListGroup>
      </Container>
    </div>
    </ErrorComponent>
  );
}

export default MyHistory;
