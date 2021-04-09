import {useParams} from "react-router-dom";
import { useState, useEffect } from "react";
import PlantForm from '../components/PlantForm';

function PlantDetail(props){
  const axios = require("axios");
  const [plant, setPlant] = useState(null);
  const params = useParams();

  const getPlant = () => {
    axios({
      method: "get",
      url: "http://localhost:8080/plants/" + params.id,
      withCredentials: true,
    })
      .then((res) => {
        if (res.status == 200) {
          console.log(res.data);
          setPlant(res.data);
        } else throw Error(res.status);
      })
      .catch((error) => {
        console.error(error);
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