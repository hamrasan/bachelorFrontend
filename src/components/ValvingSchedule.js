import {
  Container,
  Button,
  ListGroup,
  Row,
  Col,
} from "react-bootstrap";
import { useState, useEffect } from "react";
import { useHistory, useParams } from "react-router-dom";
import ModalAddValving from "./ModalAddValving";
import ModalConfirm from "./ModalConfirm";
import ErrorComponent from "../components/ErrorComponent";

function ValvingSchedule() {
  const axios = require("axios");
  const [days, setDays] = useState([]);
  const [time, setTime] = useState("10:00");
  const [valvingLength, setValvingLength] = useState(0);
  const [schedules, setSchedules] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const [modalShowConfirm, setModalShowConfirm] = useState(false);
  const [scheduleIdRemove, setScheduleIdRemove] = useState(null);
  const [error, setError] = useState(false);
  const params = useParams();

  const handleCheckbox = (id) => {
    let newArray = [];
    if (!days.includes(id)) {
      newArray = [...days, id];
    } else {
      newArray = days.filter((oldId) => id !== oldId);
    }
    setDays(newArray);
  };

  const fetchSchedules = () => {
    axios({
      method: "get",
      withCredentials: true,
      url: process.env.REACT_APP_API_URL + "/schedule/all/" + params.id,
    })
      .then((res) => {
        if (res.status === 200) {
          setSchedules(res.data);
          console.log(res);
        } 
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleSubmit = () => {
    setModalShow(false);

    axios({
      method: "post",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      withCredentials: true,
      url: process.env.REACT_APP_API_URL + "/schedule/" + params.id,
      data: {
        days: days,
        time: time,
        valvingLength: valvingLength,
      },
    })
      .then((res) => {
        console.log("daaata");
        console.log(res);
        if (res.status === 200) {
          fetchSchedules();
        } 
      })
      .catch((error) => {
        console.log("after register");
        console.error(error);
      });
  };

  const deleteSchedule = (id) => {
    console.log(id);
    axios({
      method: "delete",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      withCredentials: true,
      data: {},
      url: process.env.REACT_APP_API_URL + "/schedule/" + id,
    })
      .then((res) => {
        console.log(res);
        if (res.status === 200) {
          fetchSchedules();
        } 
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleConfirm = (id) => {
    setModalShowConfirm(false);
    if (id !== null) {
      deleteSchedule(id);
    }
  };

  useEffect(() => {
    fetchSchedules();
  }, [error]);

  return (
    <ErrorComponent onReset={() => setError(true)}>
      <div>
        <Container className="pt-2">
          <Row>
            <Col>
              <h3 className="text-center">Pravidelné polievania</h3>
              <Button
                variant="info"
                className="mt-2 mr-2"
                onClick={() => setModalShow(true)}
              >
                Pridaj polievanie
              </Button>
              <ModalAddValving
                show={modalShow}
                onHide={() => setModalShow(false)}
                title={"Pridaj pravidelné polievanie"}
                onSubmit={handleSubmit}
                setTime={setTime}
                setValvingLength={setValvingLength}
                time={time}
                handleCheckbox={handleCheckbox}
              />
            </Col>
          </Row>

          <Row className="pt-3">
            <Col>
              <ListGroup>
                {schedules.map((schedule) => (
                  <ListGroup.Item className="d-flex">
                    <Col>
                      {schedule.days.map((day) =>
                        day == 1
                          ? "Pondelok, "
                          : day == 2
                          ? "Utorok, "
                          : day == 3
                          ? "Streda, "
                          : day == 4
                          ? "Štvrtok, "
                          : day == 5
                          ? "Piatok, "
                          : day == 6
                          ? "Sobota, "
                          : day == 7
                          ? "Nedeľa, "
                          : null
                      )}
                      {schedule.hour}:
                      {schedule.minutes < 10
                        ? "0" + schedule.minutes
                        : schedule.minutes}
                      , {schedule.length} min
                    </Col>
                    <Col>
                      <button
                        type="button"
                        className="close"
                        onClick={() => {
                          setScheduleIdRemove(schedule.id);
                          setModalShowConfirm(true);
                        }}
                      >
                        <span aria-hidden="true">x</span>
                      </button>
                      <ModalConfirm
                        show={modalShowConfirm}
                        title={"Vymazať rozvrh"}
                        body={"Naozaj chceš vymazať tento rozvrh?"}
                        onSubmit={() => handleConfirm(scheduleIdRemove)}
                        onHide={() => setModalShowConfirm(false)}
                      />
                    </Col>
                  </ListGroup.Item>
                ))}
              </ListGroup>
            </Col>
          </Row>
        </Container>
      </div>
    </ErrorComponent>
  );
}

export default ValvingSchedule;
