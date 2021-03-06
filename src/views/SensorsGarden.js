import { Container, CardDeck, Button } from "react-bootstrap";
import { useState, useEffect } from "react";
import SensorCard from "../components/SensorCard";

function SensorsGarden(props) {
  const axios = require("axios");
  const [temperature, setTemperature] = useState(null);
  const [temperatureHistory, setTemperatureHistory] = useState([]);
  const [humidity, setHumidity] = useState(null);
  const [humidityHistory, setHumidityHistory] = useState([]);
  const [pressure, setPressure] = useState(null);
  const [pressureHistory, setPressureHistory] = useState([]);
  const [soil, setSoil] = useState(null);
  const [soilHistory, setSoilHistory] = useState([]);
  const [rain, setRain] = useState(null);
  const [rainHistory, setRainHistory] = useState([]);

  const fetchTemperature = () => {
    axios({
      method: "get",
      withCredentials: true,
      url: process.env.REACT_APP_API_URL + "/sensors/temperature/" + props.gardenId,
    })
      .then((res) => {
        if ((res.status == 200) && (res.data != "")) {
          setTemperature(res.data);
          console.log(res);
        } 
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const fetchHistoryTemperature = () => {
    axios({
      method: "get",
      withCredentials: true,
      url: process.env.REACT_APP_API_URL + "/sensors/history/temperature/" + props.gardenId,
    })
      .then((res) => {
        if ((res.status == 200)) {
          setTemperatureHistory(res.data);
          console.log(res);
        } 
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const fetchSoil = () => {
    axios({
      method: "get",
      withCredentials: true,
      url: process.env.REACT_APP_API_URL + "/sensors/soil/" + props.gardenId,
    })
      .then((res) => {
        if ((res.status == 200) && (res.data != "")) {
          setSoil(res.data);
          console.log(res);
        } 
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const fetchHistorySoil = () => {
    axios({
      method: "get",
      withCredentials: true,
      url: process.env.REACT_APP_API_URL + "/sensors/history/soil/" + props.gardenId,
    })
      .then((res) => {
        if ((res.status == 200)) {
          setSoilHistory(res.data);
          console.log(res);
        } 
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const fetchPressure = () => {
    axios({
      method: "get",
      withCredentials: true,
      url: process.env.REACT_APP_API_URL + "/sensors/pressure/" + props.gardenId,
    })
      .then((res) => {
        if ((res.status == 200) && (res.data != "")) {
          setPressure(res.data);
          console.log(res);
        } 
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const fetchHistoryPressure = () => {
    axios({
      method: "get",
      withCredentials: true,
      url: process.env.REACT_APP_API_URL + "/sensors/history/pressure/" + props.gardenId,
    })
      .then((res) => {
        if ((res.status == 200)) {
          setPressureHistory(res.data);
          console.log(res);
        }
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const fetchHumidity = () => {
    axios({
      method: "get",
      withCredentials: true,
      url: process.env.REACT_APP_API_URL + "/sensors/humidity/" + props.gardenId,
    })
      .then((res) => {
        if ((res.status == 200) && (res.data != "")) {
          setHumidity(res.data);
          console.log(res);
          console.log("humidity");
        } 
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const fetchHistoryHumidity = () => {
    axios({
      method: "get",
      withCredentials: true,
      url: process.env.REACT_APP_API_URL + "/sensors/history/humidity/" + props.gardenId,
    })
      .then((res) => {
        if ((res.status == 200)) {
          setHumidityHistory(res.data);
          console.log(res);
        } 
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const fetchRain = () => {
    axios({
      method: "get",
      withCredentials: true,
      url: process.env.REACT_APP_API_URL + "/sensors/rain/" + props.gardenId,
    })
      .then((res) => {
        if ((res.status == 200) && (res.data != "")) {
          setRain(res.data);
          console.log(res);
        } 
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const fetchHistoryRain = () => {
    axios({
      method: "get",
      withCredentials: true,
      url: process.env.REACT_APP_API_URL + "/sensors/history/rain/" + props.gardenId,
    })
      .then((res) => {
        if ((res.status == 200)) {
          setRainHistory(res.data);
          console.log(res);
        } 
      })
      .catch((error) => {
        console.error(error);
      });
  };

  useEffect(() => {
    fetchTemperature();
    fetchHumidity();
    fetchPressure();
    fetchRain();
    fetchSoil();
    fetchHistoryRain();
    fetchHistorySoil();
    fetchHistoryHumidity();
    fetchHistoryTemperature();
    fetchHistoryPressure();
  }, []);

  return (
    <div>
          <CardDeck className="d-xl-flex justify-content-lg-center">
            <SensorCard
              key="temperature"
              pathName="temperature"
              sensor={temperature}
              name="Teplota"
              text={temperature !== null ? temperature.value + " ??C" : "Senzor nie je dostupn??"}
              gardenName={props.gardenName}
              history={temperatureHistory.map((temperature) => {
                return { date: temperature.date, value: temperature.value + " ??C" };
              })}
              picture="assets/sensors/teplomer.png"
            ></SensorCard>
            <SensorCard
              key="pressure"
              pathName="pressure"
              sensor={pressure}
              name="Tlak vzduchu"
              text={pressure !== null ? pressure.value + " hPa" : "Senzor nie je dostupn??"}
              gardenName={props.gardenName}
              history={pressureHistory.map((pressure) => {
                return { date: pressure.date, value: pressure.value + " hPa" };
              })}
              picture="assets/sensors/pressure.png"
            ></SensorCard>
            <SensorCard
              key="humidity"
              pathName="humidity"
              sensor={humidity}
              name="Vlhkos?? vzduchu"
              text={humidity !== null ? humidity.value + " %" : "Senzor nie je dostupn??"}
              gardenName={props.gardenName}
              history={humidityHistory.map((humidity) => {
                return { date: humidity.date, value: humidity.value + " %" };
              })}
              picture="assets/sensors/humidity.png"
            ></SensorCard>
            <SensorCard
              key="soil"
              pathName="soil"
              sensor={soil}
              name="Vlhkos?? p??dy"
              text={soil !== null ? soil.value + " %" : "Senzor nie je dostupn??"}
              gardenName={props.gardenName}
              history={soilHistory.map((soil) => {
                return { date: soil.date, value: soil.value + " %" };
              })}
              picture="assets/sensors/soil.png"
            ></SensorCard>
            {rain != null ? (
              <SensorCard
                key="rain"
                pathName="rain"
                sensor={rain}
                name="Zr????ky"
                gardenName={props.gardenName}
                history={rainHistory.map((rain) => {
                  return {
                    date: rain.date,
                    value: rain.raining ? "Pr????" : "Nepr????",
                  };
                })}
                text={rain.raining ? "Pr????" : "V tomto okam??iku nepr????"}
                picture={
                  rain.raining
                    ? "assets/sensors/rain.jpg"
                    : "assets/sensors/sun.jpg"
                }
              ></SensorCard>
            ) : null}
          </CardDeck>
    </div>
  );
}

export default SensorsGarden;
