import "bootstrap/dist/css/bootstrap.min.css";
import {Card, Button} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import axios from 'axios';
import Test from "../models/Test";
import classes from './MainPage.module.css';
import {Input} from "reactstrap";
import {useNavigate} from "react-router-dom";


const HomePage: React.FC = () => {
    const [tests, setTests] = useState<Test[]>([]);
    const [key, setKey] = useState("");
    const navigate = useNavigate();

    const onEnterClick = () => {
        // Fetch lots from API
        axios.post('/api/test/start/', {authenticator: key}, {
                headers: {
                    Authorization: "Bearer "
                        + JSON.parse(sessionStorage.getItem("user") ?? "").token
                }
            }
        )
            .then(response => {
                localStorage.setItem("test", JSON.stringify(response.data));
                navigate("/test/session/")

            })
            .catch(error => console.log(error));
    }
    const handleKeyChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setKey(event.target.value);
    };


    return (
        <div className="ms-auto me-auto align-self-center mb-40 w-50 justify-content-center">
            <div className={"p-5 " + classes.name}>{JSON.parse(sessionStorage.getItem("user") ?? "{}").user.name} </div>

            <div className={'row pb-4 pt-0   ' + classes.bg}>
                <div className="d-flex flex-row p-2 pt-0">
                    <img onClick={() => navigate("/choose")} className="w-8 m-3" src={require("../icons/left-arrow.png")} alt="1"></img>

                </div>
                <div className={classes.enterText + " font-bold text-lg mb-2 ms-2"}>Вхід у сесію</div>

                <div className="text-black">
                    <input onChange={handleKeyChange} placeholder="Код-ідентифікатор"
                           className={'bg-transparent border-black p-1 border-2 rounded-3 text-black w-100 ms-2 ps-2' + classes.placeholder}></input>
                </div>
                <div className="d-flex flex-column">
                <Button onClick={onEnterClick} className={'mt-5 mb-0 ms-auto me-auto ' + classes.button}>Вхід</Button>

                <Button onClick={() => navigate("/user/list")} className={'mt-2 mb-0 ms-auto me-auto ' + classes.button}>Пройдені тести</Button>
                </div>
            </div>
        </div>
    );
}

export default HomePage;