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
    axios.get("http://localhost:8080/plants").then((res) => {
      console.log(res.data);
      setPlants(res.data);
    });
  };

  useEffect(() => {
    console.log("som v effect");
    // getCategories();
    getPlants();
  }, []);

  // const mappedPlants = plants.map((plant) => {
  //   return (
  //     <Link to={"/garden/detail/" + plant.id}>
  //       <PlantCard key={plant.id} plant={plant} />
  //     </Link>
  //   );
  // });

  //   return (
  //     <div>
  //       <SearchForm />
  //       <DropdownFilter categories={categories} />
  //       <Container>
  //         <CardDeck>{mappedPlants}</CardDeck>
  //       </Container>
  //     </div>
  //   );
  // }

  return (
    <div>
      <SearchForm />
      <DropdownFilter categories={categories} />
      <Container>
        <CardDeck>
          {plants.map((plant) => (
            <Link key={plant.id} to={"/garden/detail/" + plant.id}>
              <PlantCard key={plant.id} plant={plant} />
            </Link>
          ))}
        </CardDeck>
      </Container>
    </div>
  );
}

export default TheGarden;
