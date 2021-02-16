import { useState } from "react";
import PlantCard from "../components/PlantCard";

import { CardDeck } from "react-bootstrap";
import { Link } from "react-router-dom";

function TheGarden() {
  const [plants, setPlants] = useState([
    {
      id: 0,
      name: "Rajčiak jedlý",
      picture: "../assets/paradajka-lycopersicum-rajciak-semena.jpg",
      minTemperature: "12",
      maxTemperature: "35",
      dateOfPlant: "1.5.2021",
      season: "leto",
      category: "ovocie",
    },
    {
      id: 1,
      name: "Paprika červená",
      picture: "../assets/paradajka-lycopersicum-rajciak-semena.jpg",
      minTemperature: "17",
      maxTemperature: "35",
      dateOfPlant: "1.5.2021",
      season: "leto",
      category: "zelenina",
    },
    {
      id: 2,
      name: "Uhorka hadovka",
      picture: "../assets/paradajka-lycopersicum-rajciak-semena.jpg",
      minTemperature: "12",
      maxTemperature: "30",
      dateOfPlant: "1.5.2021",
      season: "leto",
      category: "zelenina",
    },
  ]);

  const mappedPlants = plants.map((plant) => {
    return (
      <Link to={"/garden/detail/" + plant.id}>
        <PlantCard key={plant.id} plant={plant} />
      </Link>
    );
  });

  return (
    <div>
      <CardDeck>{mappedPlants}</CardDeck>
    </div>
  );
}

export default TheGarden;
