import { useHistory, useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import PlantForm from "../components/PlantForm";
import ErrorComponent from "../components/ErrorComponent";

function PlantDetail(props) {
  const axios = require("axios");
  const [plant, setPlant] = useState(null);
  const params = useParams();
  const [modalShow, setModalShow] = useState(false);
  const [minTemperature, setMinTemperature] = useState(null);
  const [maxTemperature, setMaxTemperature] = useState(null);
  const [season, setSeason] = useState("");
  const [error, setError] = useState(false);
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
      },
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

  const handleConfirmUpdate= (id) => {
    updatePlant(id);
    setModalShow(false);
  };

  const handleConfirmDelete= (id) => {
    deletePlant(id);
    setModalShow(false);
  };

  const deletePlant = (id) => {
    console.log(id);
    axios({
      method: "delete",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      withCredentials: true,
      data: {},
      url: "http://localhost:8080/plants/" + id,
    })
      .then((res) => {
        console.log(res);
        if (res.status == 200) {
          history.push("/garden");
        } else throw Error(res.status);
      })
      .catch((error) => {
        console.error(error);
      });
  };


  useEffect(() => {
    fetchPlant();
  }, [error]);

  return (
    <ErrorComponent onReset={() => setError(true)}>
      <div>
        <PlantForm
          plant={plant}
          handleConfirmUpdate={handleConfirmUpdate}
          handleConfirmDelete={handleConfirmDelete}
          modalShow={modalShow}
          setModalShow={setModalShow}
          setMinTemperature={setMinTemperature}
          setMaxTemperature={setMaxTemperature}
          setSeason={setSeason}
        />
      </div>
    </ErrorComponent>
  );
}

export default PlantDetail;
