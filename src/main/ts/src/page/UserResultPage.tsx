import React, {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import {Button, Card, Form, ListGroup} from "react-bootstrap";
import classes from './HelloPage.module.css';


const testTest = {
    name: "",
    theme: "",
    grade: 0,
    author: "",
    answers: [{isRight: false, isUserSelected: true}, {isRight: true, isUserSelected: false}],
    questions: [
        {
            id: 1, text: "", grade: 2, answerOptions: [{
                text: "", id: 1, isTrue: true, isUserSelected: false
            }, {
                text: "", id: 2, isTrue: false, isUserSelected: true
            }]
        }]
}

const UserResult: React.FC = () => {
    const nav = useNavigate();
    const {testId} = useParams();
    const {userId} = useParams();
    const [test, setTest] = useState<any>(testTest);

    const getAnswer = (question: any) => {
        return question.answerOptions.filter((ao: any) => ao.selected)[0]
    }

    useEffect(() => {
        axios.get('/api/test/result/' + testId+"/" +userId, {
            headers: {
                Authorization: "Bearer "
                    + JSON.parse(sessionStorage.getItem("user") ?? '{"token":"yes"}').token
            }
        }).then(res => {
            console.log(res.data)
            setTest(res.data)
        }).catch(err => {
        })
    }, [])

    const isUserSelected = (q: number, o: number) => {
        console.log(q + " " + o)
        console.log(test.questions.length)
        return test.answers[(test.questions.slice(0, q).reduce((red: number, q: any) =>
            red + q.answerOptions.length, 0)) + o].userSelected
    }

    const isTrue = (q: number, o: number) => {
        return test.answers[(test.questions.slice(0, q).reduce((red: number, q: any) =>
            red + q.answerOptions.length, 0)) + o].right
    }
    // const isEnabled = (qId: number, oId: number): boolean => {
    //     return answers.get(qId) !== 0 || answers.get(qId) === oId
    // }

    return (
        <div className="w-100 align-self-center d-flex">
            <ListGroup className="bg-light rounded-4 w-100 navbar-nav-scroll">
                <div className="d-flex flex-row">
                    <img onClick={() => nav(-1)} className="w-8 m-3" src={require("../icons/left-arrow.png")}
                         alt="1"></img>
                    <p className="font-normal ms-auto me-auto d-flex justify-self-end align-self-center h3">'{test.theme}'</p>
                </div>
                <p className="font-light justify-self-end align-self-center">Творець:{test.author}</p>

                <p className="d-flex ps-4 h5 mb-0 font-bold">Запитань: {test.questions.length}</p>
                {!test.form ?
                <p className="d-flex ps-4 h5 mb-0 font-bold">Ваша оцінка: {test.grade}</p> :null}

                {test.questions.map((question: any, index: any) => (

                    <div className="p-4 pt-1">
                        <ListGroup.Item className="d-flex shadow-md hover:shadow-lg flex-column">

                            <Card className="w-28 border-4 align-self-end">Питання {index + 1}</Card>
                            <div className="d-flex pt-2 pb-0 justify-content-between w-100 flex-row">
                                <p className="text-dark-700 m-0 text-xl">{question.text}</p>
                                {!test.form ?
                                    <Card
                                        className="g-secondary bg-opacity-10 w-16 text-xs mb-0 mt-2 h-75">{question.grade} Бали</Card> : null
                                }
                            </div>
                            <hr className="bg-gray-700 border-2 mt-0"/>
                            <Form className="d-flex justify-content-evenly flex-column">
                                <div className="d-flex w-50 mt-3 flex-column">
                                    {question.answerOptions.map((option: any, optionIndex: any) => (
                                        <p className={"w-75 m-1 justify-content-baseline font-light text-lg d-flex flex-row " +
                                            (isTrue(index, optionIndex) && isUserSelected(index, optionIndex) ? classes.green : !isTrue(index, optionIndex) && isUserSelected(index, optionIndex) && !test.form ? "text-red-500 font-semibold" : "")}> {optionIndex + 1}. {option.text}
                                            {((isTrue(index, optionIndex) && isUserSelected(index, optionIndex)) || test.form && isUserSelected(index, optionIndex) ? " ✓" : !isTrue(index, optionIndex) && isUserSelected(index, optionIndex) ? " ×" : "")}</p>
                                    ))}
                                </div>
                            </Form>
                        </ListGroup.Item>
                    </div>


                ))}
            </ListGroup>
        </div>
    );
}

export default UserResult;