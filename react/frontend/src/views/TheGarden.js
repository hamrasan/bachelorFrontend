import { useState, useEffect } from "react";
import PlantCard from "../components/PlantCard";
import SearchForm from "../components/SearchForm";
import DropdownFilter from "../components/DropDownFilter";

import { CardDeck, Container } from "react-bootstrap";
import { Link } from "react-router-dom";

function TheGarden() {
  const axios = require("axios");

  const [categories, setCategories] = useState([]);
  const [plants, setPlants] = useState([]);
  const [categoryFilter, setCategoryFilter] = useState([]);

  const getPlants = () => {
    axios.get("http://localhost:8080/plants").then((res) => {
      console.log(res.data);
      setPlants(res.data);
    });
  };

  const handleFilter = (id) => {
    console.log(id);
    let newArray = [];
    if(!categoryFilter.includes(id)){
      newArray= [...categoryFilter, id];
    }
    else{
      newArray = categoryFilter.filter(oldId => id!==oldId);
    }
    setCategoryFilter(newArray);
    console.log(newArray);
  };

  const getCategories = () => {
    axios.get("http://localhost:8080/categories").then((res) => {
      console.log(res.data);
      setCategories(res.data);
    });
  };

  useEffect(() => {
    getCategories();
    getPlants();
  }, []);

  // const mappedPlants = plants.map((plant) => {
  //   return (
  //     <Link to={"/garden/detail/" + plant.id}>
  //       <PlantCard key={plant.id} plant={plant} />
  //     </Link>
  //   );
  // });


  return (
    <div>
      <SearchForm />
      <DropdownFilter categories={categories} categoryFilter={categoryFilter} handleFilter={handleFilter}/>
      <Container>
        <CardDeck>
          {plants.filter(plant => categoryFilter.length > 0 ? categoryFilter.includes(plant.category.id) : plant).map((plant) => (
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
