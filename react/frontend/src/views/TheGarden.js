import { useState, useEffect } from "react";
import PlantCard from "../components/PlantCard";
import SearchForm from "../components/SearchForm";
import DropdownFilter from "../components/DropDownFilter";

import { CardDeck, Container, Button, Nav, Row, Col } from "react-bootstrap";
import { Link } from "react-router-dom";
import MyVerticallyCenteredModal from "../components/MyVerticallyCenteredModal";
import ErrorComponent from "../components/ErrorComponent";
import { useErrorHandler } from "react-error-boundary";

function TheGarden() {
  const axios = require("axios");

  const [categories, setCategories] = useState([]);
  const [plants, setPlants] = useState([]);
  const [categoryFilter, setCategoryFilter] = useState([]);
  const [subcategoryFilter, setSubcategoryFilter] = useState([]);
  const [searchString, setSearchString] = useState("");
  const [modalShow, setModalShow] = useState(false);
  const [gardens, setGardens] = useState([]);
  const [actualGardenName, setActualGardenName] = useState("");
  const [error, setError] = useState(false);
  const handleError = useErrorHandler();


  const fetchPlants = (id, name) => {
    setActualGardenName(name);
    axios({
      method: "get",
      url: "http://localhost:8080/plants/garden/" +id,
      withCredentials: true,
    })
      .then((res) => {
        if (res.status == 200) {
          setPlants(res.data);
        } else throw Error(res.status);
      })
      .catch((error) => {
        handleError(error);
        console.error(error);
      });
  };

  const fetchGardens = () => {
    axios({
      method: "get",
      withCredentials: true,
      url: "http://localhost:8080/garden/all",
    })
      .then((res) => {
        if ((res.status == 200)) {
          setGardens(res.data);
          console.log(res.data);
          if(res.data.length>0){
            setActualGardenName(res.data[0].name);
            fetchPlants(res.data[0].id, res.data[0].name);
          }
        }else throw Error(res.status);
      })
      .catch((error) => {
        console.error(error);
        handleError(error);
      });
  };

  const createGarden = (name, location) => {
    axios({
      method: "post",
      url: "http://localhost:8080/garden/",
      withCredentials: true,
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      data: {
        name: name,
        location: location,
      },
    })
      .then((res) => {
        if (res.status == 200) {
          fetchGardens();
        }else throw Error(res.status);
      })
      .catch((error) => {
        console.error(error);
        handleError(error);
      });
  };

  const handleFilter = (id) => {
    console.log(id);
    let newArray = [];
    if (!categoryFilter.includes(id)) {
      newArray = [...categoryFilter, id];
    } else {
      newArray = categoryFilter.filter((oldId) => id !== oldId);
    }
    setCategoryFilter(newArray);
    console.log(newArray);
  };

  const handleSubmit = (name, location) => {
    createGarden(name, location);
    setModalShow(false);
  };

  const fetchCategories = () => {
    axios.get("http://localhost:8080/categories").then((res) => {
      console.log(res.data);
      setCategories(res.data);
    });
  };

  useEffect(() => {
    fetchCategories();
    fetchGardens();
  }, [error]);

  // const mappedPlants = plants.map((plant) => {
  //   return (
  //     <Link to={"/garden/detail/" + plant.id}>
  //       <PlantCard key={plant.id} plant={plant} />
  //     </Link>
  //   );
  // });

  return (
    <ErrorComponent onReset={() => setError(true)}>
    <div>
      <Container className="pt-3">
        <Button
          variant="info"
          className="mb-3"
          onClick={() => setModalShow(true)}
        >
          Pridať záhradu
        </Button>
        <MyVerticallyCenteredModal
          show={modalShow}
          onHide={() => setModalShow(false)}
          title={"Pridať záhradu"}
          bodyTitle={"Zadaj názov"}
          bodyText={"addGarden"}
          bodySubTitle={"Zadaj lokalitu"}
          onSubmit={handleSubmit}
        />
      </Container>

      <Container>
        {gardens.length > 1 ? (
          <Nav variant="tabs" defaultActiveKey={gardens[0].id} className="mb-3">
            {gardens.map((garden) => (
              <Nav.Item onClick={() => fetchPlants(garden.id)}>
                <Nav.Link eventKey={garden.id}>{garden.name}</Nav.Link>
              </Nav.Item>
            ))}
          </Nav>
        ) : null}

        {gardens.length>0 ? 
             (<Row>
             <Col className="d-flex flex-column justify-content-center">
               <SearchForm onChange={setSearchString} value={searchString} />
             </Col>
             <Col>
               <DropdownFilter
                 categories={categories}
                 categoryFilter={categoryFilter}
                 handleFilter={handleFilter}
               />
             </Col>
             <Col className="d-flex flex-row-reverse">
               <Link to={"/garden/new/" + actualGardenName}>
                 <Button variant="info"> Pridaj novú rastlinu </Button>{" "}
               </Link>
             </Col>
           </Row>)
        :null}

        <CardDeck>
          {plants
            .filter((plant) =>
              categoryFilter.length > 0
                ? categoryFilter.includes(plant.subcategoryDto.category.id)
                : plant
            )
            .filter((plant) =>
              searchString.length >= 3
                ? plant.name.toLowerCase().includes(searchString.toLowerCase())
                : plant
            )
            .map((plant) => (
              <Link key={plant.id} to={"/garden/detail/" + plant.id}>
                <PlantCard key={plant.id} plant={plant} />
              </Link>
            ))}
        </CardDeck>
      </Container>
    </div>
    </ErrorComponent>
  );
}

export default TheGarden;
