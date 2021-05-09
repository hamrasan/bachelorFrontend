import { useState, useEffect } from "react";
import { Form } from "react-bootstrap";
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
      <div>
        <Form.Group controlId="exampleForm.ControlSelect1">
          <Form.Label>Rastlina</Form.Label>
          <Form.Control
            as="select"
            onChange={(e) => {
              props.setPlantName(e.target.value);
              props.setPlant(
                plants.find((plant) => plant.name == e.target.value)
              );
              props.setSeason(
                plants.find((plant) => plant.name == e.target.value).season
              );
              props.setMinTemperature(
                plants.find((plant) => plant.name == e.target.value)
                  .minTemperature
              );
              props.setMaxTemperature(
                plants.find((plant) => plant.name == e.target.value)
                  .maxTemperature
              );
              console.log(plants.find((plant) => plant.name == e.target.value));
            }}
          >
            <option value="default" selected="selected">
              Vyber z možností
            </option>
            {props.subcategory !== "" && props.subcategory !== "default"
              ? plants.map((plant) => (
                  <option value={plant.name}>{plant.name}</option>
                ))
              : null}
          </Form.Control>
        </Form.Group>
      </div>
    </ErrorComponent>
  );
}

export default SelectPlants;
