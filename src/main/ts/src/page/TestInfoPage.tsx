import "bootstrap/dist/css/bootstrap.min.css";
import {Card, Button} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import axios from 'axios';
import Test from "../models/Test";
import classes from './MainPage.module.css';
import {Input} from "reactstrap";
import {useNavigate, useParams} from "react-router-dom";

const testTest = {
    id: 1,
    name: "База",
    theme: "yes",
    author: "Zheka Bukur",
    authCode: "sddsdsadadasdsadsdasdas",
    registered: 1,
    completed: 1
}
const TestInfoPage: React.FC = () => {
    const {testId} = useParams()
    const [test,setTest] = useState<any>(testTest)
    const nav = useNavigate();
    useEffect( () => {
        // Fetch lots from API
        axios.get('/api/test/overview/' + testId, {
                headers: {
                    Authorization: "Bearer "
                        + JSON.parse(sessionStorage.getItem("user") ?? "").token
                }
            }
        )
            .then(response => {
                setTest(response.data)
            })
            .catch(error => console.log(error));
    },[])


    return (
        <div className={classes.bg + " p-4 ms-auto me-auto text-black align-self-center mb-40 w-75 justify-content-center"}>
            <div className="d-flex text-white p-0 pt-0 bg-transparent justify-content-between">
                <img onClick={() => nav(-1)} className="bg-transparent p-0 m-0 w-8" src={require("../icons/arrow.png")} alt="1"></img>
                <div className={" w-30 text-black " + classes.name}>{JSON.parse(sessionStorage.getItem("user") ?? "{}").user.name} </div>
            </div>

            <div className={'row p-5 pb-4 text-white pt-0'}>

                <div className={classes.enterText + " text-black  font-semibold  text-lg "}>Тема: {test.theme}</div>
                <div className={classes.enterText + " text-black  font-semibold  text-lg "}>Зареєстровано: {test.registered}</div>
                <div className={classes.enterText + " text-black  font-semibold  text-lg "}>Виконало: {test.completed}</div>

                <div className="text-white me-auto m-3">
                    <text
                           className={'bg-transparent text-red-500 me-auto ms-auto text-xl p-3 border-black rounded-3 w-100 ps-2'}>
                        Код аутентифікації: {test.authCode}</text>
                </div>
                <div className="d-flex flex-row mt-3 justify-content-around w-100">
                    <div onClick={() => nav("/test/creator/result/" + testId)} className="rounded-5 hover:shadow-xl hover:bg-slate-800 hover:text-white  border-slate-900 w-25 me-3">
                        {/*<Button className="d-flex ms-auto me-auto" variant="" >Кругові діаграми</Button>*/}
                        <img className="card-img" src={require("../icons/piechart.png")} alt={""}/>
                    
                    </div>
                    <div className="rounded-4  hover:shadow-xl  hover:bg-slate-800 hover:text-white  border-slate-900 w-25 me-3">
                        {/*<Button  className="d-flex text-amber-200 ms-auto me-auto" variant="">Таблиця</Button>*/}
                        <img className="card-img w-100"  onClick={() => nav("/test/creator/result/"+ testId +"/list")} src={require("../icons/table.png")} alt={""}/>
                    </div>

                </div>
            </div>
        </div>
    );
}

export default TestInfoPage;