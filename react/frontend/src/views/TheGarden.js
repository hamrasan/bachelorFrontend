import { useState, useEffect } from "react";
import PlantCard from "../components/PlantCard";
import SearchForm from "../components/SearchForm";
import DropdownFilter from "../components/DropDownFilter";

import { CardDeck, Container, Form } from "react-bootstrap";
import { Link } from "react-router-dom";

function TheGarden() {
  const axios = require("axios");

  const [categories, setCategories] = useState([]);

  const [plants, setPlants] = useState([]);

  const getPlants = () => {
    axios
      .get("http://localhost:8080/plants")
      .then(res => res.json())
      .then(res => {
        console.log(res);
        // const plantsArr = [];
        // console.log(res.json());
        // plantsArr.concat(res.data);
        // res.data.forEach((element) => {
        //   plantsArr.push(element);
        // });
        // setPlants({plants : plantsArr});
        // setPlants(plants.concat(res.data));
        // setPlants((plants) => [...plants, res.data[0]]);
        // console.log(plantsArr);
        // console.log(plants);
      });
  };

  useEffect(() => {
    console.log("som v effect");
    // getCategories();
    getPlants();
  }, []);

  const mappedPlants = plants.map((plant) => {
    return (
      <Link to={"/garden/detail/" + plant.id}>
        <PlantCard key={plant.id} plant={plant} />
      </Link>
    );
  });

  return (
    <div>
      <SearchForm />
      <DropdownFilter categories={categories} />
      <Container>
        <CardDeck>{mappedPlants}</CardDeck>
      </Container>
    </div>
  );
}

export default TheGarden;
