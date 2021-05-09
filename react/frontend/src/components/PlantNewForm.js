import { useState, useEffect } from "react";
import { Form, Row, Col, Container, Button } from "react-bootstrap";
import { useHistory, useParams } from "react-router-dom";
import DatePicker from "react-datepicker";
import TheSpinner from "../components/TheSpinner";
import SelectPlants from "../components/SelectPlants";
import "react-datepicker/dist/react-datepicker.css";
import ErrorComponent from "../components/ErrorComponent";
import { useErrorHandler } from "react-error-boundary";


function PlantNewForm(props) {
  const axios = require("axios");
  const [categoryName, setCategoryName] = useState("");
  const [plant, setPlant] = useState(null);
  const [plantName, setPlantName] = useState("");
  const [categories, setCategories] = useState([]);
  const [subcategoryName, setSubcategoryName] = useState("");
  const [dateOfPlant, setDateOfPlant] = useState(new Date());
  const [minTemperature, setMinTemperature] = useState(null);
  const [maxTemperature, setMaxTemperature] = useState(null);
  const [season, setSeason] = useState("");
  const [error, setError] = useState(false);
  let history = useHistory();
  const handleError = useErrorHandler();
  const params = useParams();

  useEffect(() => {
    getCategories();
  }, [error]);

  const getCategories = () => {
    axios({
      method: "get",
      url: "http://localhost:8080/categories",
      withCredentials: true,
    })
      .then((res) => {
        if (res.status == 200) {
          setCategories(res.data);
        } else throw Error(res.status);
      })
      .catch((error) => {
        handleError(error);
        console.error(error);
      });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("dateOfPlant " + dateOfPlant);
    console.log(plant);

    axios({
      method: "post",
      url: "http://localhost:8080/plants",
      withCredentials: true,
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      data: {
        date: dateOfPlant,
        plant: plant.id,
        minTemperature: minTemperature,
        maxTemperature: maxTemperature,
        season: season,
        garden: params.name,
      },
    })
      .then((res) => {
        if (res.status == 200) {
          console.log("plant was send");
          history.push("/garden");
        } else throw Error(res.status);
      })
      .catch((error) => {
        handleError(error);
        console.error(error);
      });
  };

  return (
    <ErrorComponent onReset={() => setError(true)}>
      <div>
        <Container>
          <Row>
            <Col>
              <h2 className="text-center">
                Pridanie novej pestovanej rastliny
              </h2>
            </Col>
          </Row>

          {/* <Row className="mb-3">
          <Col className="d-flex flex-row-reverse">
            <Button variant="outline-success">Upraviť</Button>{" "}
          </Col>
        </Row> */}

          <Row>
            <Col>
              <Form onSubmit={handleSubmit}>
                <Form.Group controlId="exampleForm.ControlSelect1">
                  <Form.Label>Kategória</Form.Label>
                  <Form.Control
                    as="select"
                    onChange={(e) => {
                      setCategoryName(e.target.value);
                      setSubcategoryName("");
                    }}
                  >
                    <option value="default" selected="selected">
                      Vyber z možností
                    </option>
                    {categories.map((category) => (
                      <option value={category.name}>{category.name}</option>
                    ))}
                  </Form.Control>
                </Form.Group>

                {categoryName !== "" ? (
                  <>
                    <Form.Group controlId="exampleForm.ControlSelect1">
                      <Form.Label>Podkategória</Form.Label>
                      <Form.Control
                        as="select"
                        onChange={(e) => {
                          setSubcategoryName(e.target.value);
                        }}
                      >
                        <option value="default" selected="selected">
                          Vyber z možností
                        </option>
                        {categories
                          .filter((category) => category.name == categoryName)
                          .map((category) =>
                            category.subcategoryNames.map((subcategory) =>
                              subcategory === "None" ? (
                                <option value={subcategory}>
                                  Bez podkategórie
                                </option>
                              ) : (
                                <option value={subcategory}>
                                  {subcategory}
                                </option>
                              )
                            )
                          )}
                      </Form.Control>
                    </Form.Group>
                    <SelectPlants
                      subcategory={subcategoryName}
                      category={categoryName}
                      setPlantName={setPlantName}
                      setPlant={setPlant}
                      setMinTemperature={setMinTemperature}
                      setMaxTemperature={setMaxTemperature}
                      setSeason={setSeason}
                    ></SelectPlants>

                    <Form.Group>
                      <Form.Label> Minimálna možná teplota rastliny</Form.Label>
                      <Form.Control
                        type="text"
                        defaultValue={minTemperature}
                        onChange={(e) => {
                          setMinTemperature(e.target.value);
                        }}
                      />
                    </Form.Group>

                    <Form.Group>
                      <Form.Label> Maximálna možná teplota rastliny</Form.Label>
                      <Form.Control
                        type="text"
                        defaultValue={maxTemperature}
                        onChange={(e) => {
                          setMaxTemperature(e.target.value);
                        }}
                      />
                    </Form.Group>

                    <Form.Group>
                      <Form.Label> Sezóna rastliny</Form.Label>
                      <Form.Control
                        type="text"
                        defaultValue={season}
                        onChange={(e) => {
                          setSeason(e.target.value);
                        }}
                      />
                    </Form.Group>

                    <Form.Group controlId="exampleForm.ControlSelect1">
                      <Form.Label>Dátum zasadenia </Form.Label>
                      <div>
                        <DatePicker
                          selected={dateOfPlant}
                          className="form-control"
                          onChange={(date) => setDateOfPlant(date)}
                          dateFormat="y-MM-dd"
                        />
                      </div>
                    </Form.Group>
                  </>
                ) : null}

                <Form.Group as={Row}>
                  <Col className="d-flex justify-content-right">
                    <Button type="submit">Pridať</Button>
                  </Col>
                </Form.Group>
              </Form>
            </Col>
          </Row>
        </Container>
      </div>
    </ErrorComponent>
  );
}

export default PlantNewForm;
