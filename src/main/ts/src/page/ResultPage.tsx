import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {Card, Form, ListGroup} from "react-bootstrap";
import classes from './HelloPage.module.css';
import {Chart} from "react-google-charts";
import axios from "axios";


const testTest = {
    name: "База",
    theme: "yes",
    author: "Zheka Bukur",
    statistic:[{answerStatistic:[{text:"cringe",percent:85,isTrue: true},{text:"base",percent: 15, isTrue: false}]},
        {answerStatistic:[{text:"cringe",percent:15,isTrue: false},
            {text:"base",percent: 85,isTrue: true}]}],
    questions: [
        {
            id: 1, text: "Pes patron?", grade: 2, answerOptions: [{
                text: "cringe", id: 1
            }, {
                text: "base", id: 2
            }]
        }, {
            id: 2, text: "Trivoga?", grade: 2, answerOptions: [{
                text: "cringe", id: 3
            }, {
                text: "base", id: 4
            }]
        }
    ]
}

const ResultPage: React.FC = () => {
    const {testId} = useParams();
    const {userId} = useParams();

    const nav = useNavigate();
    const [test, setTest] = useState<any>({});
    const [answers, setAnswers] = useState(new Map<number, number>());

    useEffect(() => {
        axios.get('/api/test/result/' + testId, {
            headers: {
                Authorization: "Bearer "
                    + JSON.parse(sessionStorage.getItem("user") ?? '{"token":"yes"}').token
            }
        }).then(res => {
            console.log(res.data)
            setTest(res.data)
        }).catch(err => {})
    },[])


    const getData = (questionId: number) =>{
        let data = [["question","percent"]]
        test.statistic[questionId].answerStatistic.map((stat: any) => {
            console.log([stat.text, stat.percent])
            data = data.concat([[stat.text, stat.percent]])
        })
        return data
    }


    return (
                    <div className="w-100 align-self-center d-flex">
                        <ListGroup className="bg-light rounded-4 w-100 navbar-nav-scroll">
                            <div className="d-flex flex-row">
                                <img onClick={() => nav(-1)} className="w-8 m-3" src={require("../icons/left-arrow.png")} alt="1"></img>
                                <p className="font-normal ms-auto me-auto d-flex justify-self-center align-self-center h3">'{test.theme}'</p>
                            </div>
                            <p className="font-light h3 ms-5 justify-self-end align-self-center">Результати учасників:</p>

                            <p className="d-flex ps-5 h5 mb-0 font-bold">Запитань: {test.questions?.length}</p>
                            {test.questions?.map((question: any, index: any) => (

                                <div className="p-5 mt-1 pt-1">
                                    <ListGroup.Item className="d-flex shadow-md hover:shadow-lg flex-column">

                                        <Card className="w-28 border-4 text-center align-self-end">Питання {index + 1}</Card>
                                        <div className="d-flex pt-2 pb-0 justify-content-between w-100 flex-row">
                                            <p className="text-dark-700 m-0 text-xl">{question.text}</p>
                                            {!test.form ?
                                            <Card
                                                className="bg-secondary d-flex bg-opacity-10 w-16 text-center text-xs mt-2 h-75">{question.grade} Бали</Card>
                                                :null
                                            }
                                        </div>
                                        <hr className="bg-gray-700 border-2 mt-0"/>
                                        <Form className="d-flex flex-wrap flex-row justify-content-evenly">
                                            <div className="d-flex w-50 mt-3 flex-column">
                                                {question.answerOptions.map((option: any, optionIndex: any) => (
                                                    <p className={"w-75 m-1 justify-content-baseline font-light text-lg d-flex flex-row " + (test.statistic[index].answerStatistic[optionIndex].true && !test.form ? classes.green : "")}> {optionIndex + 1}. {option.text}
                                                        {(test.statistic[index].answerStatistic[optionIndex].true && !test.form ? " ✓" : "")}</p>
                                                ))}
                                            </div>
                                            <Chart
                                                chartType="PieChart"
                                                data={getData(index)}
                                                options={{title:""}}
                                                width={"40%"}
                                                height={"25%"}

                                                className="w-100 d-flex mt-0 align-self-end"
                                            />
                                        </Form>

                                    </ListGroup.Item>
                                </div>


                            ))}
                            <button className={classes.button + " w-25 mb-5 rounded-4 p-3 h5 font-semibold d-flex align-self-center justify-content-center"} >Меню результатів</button>
                        </ListGroup></div>

    );
};

export default ResultPage;