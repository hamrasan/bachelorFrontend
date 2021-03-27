import {useParams} from "react-router-dom";
import { useState, useEffect } from "react";
import PlantForm from '../components/PlantForm';

function PlantDetail(props){
  const axios = require("axios");
  const [plant, setPlant] = useState(null);
  const params = useParams();

  const getPlant = () => {
    axios.get("http://localhost:8080/plants/all/" + params.id)
    .then((res) => {
      setPlant(res.data);
      console.log(res.data);
    });
  };

  useEffect(() => {
    getPlant();
  }, []);

    return (
        <div>
          <PlantForm plant={plant}/>
        </div>
      );
}

export default PlantDetail;