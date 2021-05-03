import {useHistory, useParams} from "react-router-dom";
import { useState, useEffect } from "react";
import PlantForm from '../components/PlantForm';

function PlantDetail(props){
  const axios = require("axios");
  const [plant, setPlant] = useState(null);
  const params = useParams();
  const [modalShow, setModalShow] = useState(false);
  const [minTemperature, setMinTemperature] = useState(null);
  const [maxTemperature, setMaxTemperature] = useState(null);
  const [season, setSeason] = useState("");
  let history = useHistory();


  const fetchPlant = () => {
    axios({
      method: "get",
      url: "http://localhost:8080/plants/" + params.id,
      withCredentials: true,
    })
      .then((res) => {
        if (res.status == 200) {
          console.log(res.data);
          setPlant(res.data);
          setMaxTemperature(res.data.maxTemperature);
          setMinTemperature(res.data.minTemperature);
          setSeason(res.data.season);
        } else throw Error(res.status);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const updatePlant = (id) => {
    axios({
      method: "post",
      url: "http://localhost:8080/plants/update",
      withCredentials: true,
      data: {
        id: id,
        minTemperature: minTemperature,
        maxTemperature: maxTemperature,
        season: season,
      }
    })
      .then((res) => {
        if (res.status == 200) {
          console.log(res);
          fetchPlant();
          history.push("/garden");
        } else throw Error(res.status);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleConfirm = (id) => {
    updatePlant(id);
    setModalShow(false);
  }

  useEffect(() => {
    fetchPlant();
  }, []);

    return (
        <div>
          <PlantForm plant={plant} handleConfirm={handleConfirm} modalShow={modalShow} setModalShow={setModalShow}
           setMinTemperature={setMinTemperature} setMaxTemperature={setMaxTemperature} setSeason={setSeason}/>
        </div>
      );
}

export default PlantDetail;