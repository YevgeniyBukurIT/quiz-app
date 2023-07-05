import React from "react";
import {Button} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import classes from './MainPage.module.css';


export const RoleChooser: React.FC = () => {

    const nav = useNavigate();

    return (
        <div className={classes.bg + " text-dark d-flex rounded-5 shadow-xl  " +
        "h-1/3 w-50 ms-auto me-auto align-self-center justify-content-center align-items-center flex-column"}>
        <div className="rounded-3 text-white border-2 align-self-end border-black mb-5  w-20 bg-transparent">
            <Button className="d-flex p-0 text-sm ms-auto me-auto" variant="" onClick={() => nav("/")}>Вийти</Button>
        </div>
        <span className=" font-semibold p-4 uppercase">Опитування - це важлива частина будь-якого процесу прийняття рішень.</span>
        <span className="text-red-500 p-4 font-semibold pt-3 text-sm">Оберіть роль</span>
        <div className="d-flex flex-row justify-content-center w-100">
            <button className="w-44 p-1 border-4 border-black rounded-4 justify-content-centers align-text-center h-100 hover:text-yellow-200 rouded-4 hover:bg-slate-900 font-semibold ms-auto me-auto " onClick={() => nav("/creator/list")}>Творець</button>
            <button className="rounded-4 hover:bg-slate-800 hover:text-black  border-2 border-slate-900 w-44 bg-slate-900 font-semibold text-white ms-auto me-auto" onClick={() => nav("/main")}>Користувач</button>
        </div>
    </div>)
}