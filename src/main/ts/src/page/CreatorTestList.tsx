import {Button, ButtonGroup, Card, Dropdown, DropdownButton, FormText, Image} from "react-bootstrap";

import ListGroup from "react-bootstrap/ListGroup";

import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios/index";


const user = JSON.parse(sessionStorage.getItem("user") ?? "{}");
export const CreatorTestList: React.FC<{}> = (props: {}) => {
    const navigate = useNavigate();
    const [showState, setShowState] = useState();
    const [loading, setLoading] = useState(false);

    const [tests, setTests] = useState<any[]>([]);

    useEffect(() => {
        axios.get('/api/test/' + user.id, {
            headers: {
                Authorization: "Bearer "
                    + JSON.parse(sessionStorage.getItem("user") ?? '{"token":"yes"}').token
            }
        }).then(res => {
            setTests(res.data)
        }).catch(err => console.error(err))
    }, [])

    const onTestClick = (testId: number) => {

    }


    return (
        <><h1 className="d-flex align-self-center m-3"></h1>
        <div className="column h-100 min-h-0.5 w-75 justify-content-center align-self-center p-3 rounded-3">
            <ListGroup>
                {tests.map(t =>
                    <ListGroup.Item className="mt-2 rounded-3 hover:bg-slate-100 shadow-md ease-in duration-300 mb-2">
                        <div className="d-flex mt-2 ms-3 w-100 justify-content-between"></div>
                        <div
                            className="d-flex align-items-start flex-row flex-nowrap justify-content-start">
                            <div
                                className="d-flex mt-3 flex-column justify-content-start align-items-start">
                                <span>Назва: {t.name ?? 0}</span>
                                <span>Тема: {t.theme} </span>
                                <span>L:@#@:|</span>
                            </div>
                        </div>
                        <div className="d-flex">
                            <div className="rounded-4 ms-auto bg-red-400">
                                <Button onClick={() => navigate("/test/results/" + t.id)} variant=""
                                        className="text-light font-white">Переглянути</Button>
                            </div>
                        </div>
                    </ListGroup.Item>
                )
                }
        </ListGroup>
        </div>
</>)
    ;
};