import React, {useEffect, useState} from "react";
import axios from "axios";
import test from "node:test";
import {List} from "reactstrap";
import {Button, ListGroup, ListGroupItem} from "react-bootstrap";
import moment from "moment";
import {useNavigate} from "react-router-dom";


export const TestList : React.FC<{isCreator:boolean}> = (props : {isCreator:boolean}) => {

    const [tests,setTest] = useState<any>([]);
    const nav = useNavigate();

    useEffect(() => {
        axios.get("/api/test/user/" + (props.isCreator ? "created/" : "taken/"),{headers: {
            Authorization: "Bearer "
            + JSON.parse(sessionStorage.getItem("user") ?? "").token
        }}
            )
            .then(res => {
                let tests = res.data;

                tests.forEach((t: any) => t.end = new Date(Number(t.end)))

                setTest(res.data);

            })
            .catch(err => console.error(err))
    },[])
    return (<div className="w-100 justify-content-center d-flex">
        <ListGroup className="w-75 p-3 bg-white rounded-4 navbar-nav-scroll">
            <div className="inline-flex">
            <div className="w-10 me-auto">
                <img onClick={() => nav(-1)} src={require("../icons/left-arrow.png")} className="card-img w-10"/>
            </div>
                <span className="inline-flex font-semibold text-xl mb-5 my-auto me-auto">{!props.isCreator ? "Пройдені тести" : "Створені тести"}</span>
            </div>
            {tests.length !== 0 ?
                (tests.map((test :any) =>
                <div className="d-flex px-4 mx-auto bg-slate-50 w-100 border-2 p-3 mt-2 border-slate-950 inline-flex flex-row rounded-5">
                    <span className="font-semibold my-auto text-lg">Тема:{test.theme}</span>
                    {props.isCreator ?
                    <div className="ms-auto">
                        <button onClick={() => nav("/test/creator/overview/" + test.id)} className="bg-yellow-200 px-4 font-bold rounded-4">Переглянути</button>
                    </div> :
                        <div className="ms-auto">
                            <div>{moment(test.end).format("hh:mm / DD-MM")}</div>
                            <button onClick={() => nav("/test/result/" + test.id + "/" + JSON.parse(sessionStorage.getItem("user") ?? "").user.id)}className="bg-yellow-200 px-4 font-bold rounded-4">Результати</button>
                        </div>
                    }
                </div>)) : null
            }
            {props.isCreator ? <button onClick={() => nav("/test/create")} className="d-flex text-yellow-200 py-2 font-semibold justify-content-center w-25 m-5 bg-slate-800 p-3 rounded-5 align-self-center">
                Створити
            </button> : null}

        </ListGroup>
    </div>)
}
