import { useState, useEffect } from "react";
import { Form, Card, CardGroup, Row, Col } from "react-bootstrap";
import ErrorComponent from "../components/ErrorComponent";

function SelectPlants(props) {
  const axios = require("axios");
  const [plants, setPlants] = useState([]);
  const [error, setError] = useState(false);

  useEffect(() => {
    getSubcategoryPlants();
  }, [props]);

  const getSubcategoryPlants = () => {
    if (
      props.category !== "" &&
      props.subcategory !== "" &&
      props.subcategory !== "default" &&
      props.category !== "default"
    ) {
      axios({
        method: "get",
        url:
          "http://localhost:8080/plants/" +
          props.category +
          "/" +
          props.subcategory,
        withCredentials: true,
      })
        .then((res) => {
          if (res.status == 200) {
            console.log(res);
            setPlants(res.data);
          } else throw Error(res.status);
        })
        .catch((error) => {
          console.log("ERRRRROOOOOOOOOR");
          console.error(error);
        });
    }
  };

  return (
    <ErrorComponent onReset={() => setError(true)}>
      <Col style={{ overflow: "scroll", minWidth: "50%", height: "500px" }}>
        Rastlina
        {props.subcategory !== "" && props.subcategory !== "default"
          ? plants.map((plant) => (
              <CardGroup
                onClick={() => {
                  props.setActualPlantInfo(plant);
                }}
              >
                <Card className="mt-2 mr-0">
                  <div className="d-flex justify-content-center bg-light">
                    <Card.Img
                      variant="top"
                      src={"http://localhost:8080/gallery/" + plant.picture}
                      style={{ maxWidth: "200px", maxHeight: "200px" }}
                    />
                  </div>
                </Card>
                <Card className="mt-2 mr-0">
                  <Card.Body>
                    <Card.Title style={{ color: "green" }}>
                      {plant.name}
                    </Card.Title>
                    <Card.Title>Min: {plant.minTemperature}</Card.Title>
                    <Card.Title>Max: {plant.maxTemperature}</Card.Title>
                    <Card.Title>Sezóna: {plant.season}</Card.Title>
                  </Card.Body>
                </Card>
              </CardGroup>
            ))
          : null}
      </Col>
    </ErrorComponent>
  );
}

export default SelectPlants;
