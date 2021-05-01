import { useState, useEffect } from "react";
import PlantCard from "../components/PlantCard";
import SearchForm from "../components/SearchForm";
import DropdownFilter from "../components/DropDownFilter";

import { CardDeck, Container, Button } from "react-bootstrap";
import { Link } from "react-router-dom";

function TheGarden() {
  const axios = require("axios");

  const [categories, setCategories] = useState([]);
  const [plants, setPlants] = useState([]);
  const [categoryFilter, setCategoryFilter] = useState([]);
  const [subcategoryFilter, setSubcategoryFilter] = useState([]);
  const [searchString, setSearchString] = useState("");
  const [modalShow, setModalShow] = useState(false);


  const fetchPlants = () => {
    axios({
      method: "get",
      url: "http://localhost:8080/plants",
      withCredentials: true,
    })
      .then((res) => {
        if (res.status == 200) {
          setPlants(res.data);
        } else throw Error(res.status);
      })
      .catch((error) => {
        console.error(error);
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

  const fetchCategories = () => {
    axios.get("http://localhost:8080/categories").then((res) => {
      console.log(res.data);
      setCategories(res.data);
    });
  };

  useEffect(() => {
    fetchCategories();
    fetchPlants();
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
      <Button
        variant="info"
        className="mb-4 ml-2"
        onClick={() => setModalShow(true)}
      >
        Pridať záhradu
      </Button>
      {/* <ModalSetSensorsTime
          show={modalShow}
          onHide={() => setModalShow(false)}
          title={"Zmeň časový interval merania senzorov"}
          bodyTitle={"Vyber časový interval v min:"}
          bodyText={"interval"}
          setMinutes={setMinutes}
          minutes={minutes}
          onSubmit={handleSubmit}
        /> */}

      <SearchForm onChange={setSearchString} value={searchString} />
      <Container>
        <Link to={"/garden/new"}>
          <Button variant="info"> Pridaj novú rastlinu </Button>{" "}
        </Link>
      </Container>
      <DropdownFilter
        categories={categories}
        categoryFilter={categoryFilter}
        handleFilter={handleFilter}
      />
      <Container>
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
  );
}

export default TheGarden;
