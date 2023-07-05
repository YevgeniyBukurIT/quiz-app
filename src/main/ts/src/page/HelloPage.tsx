import React, {memo, useState} from 'react';
import type { FC } from 'react';

import resets from '../resets.css';
    import classes from './HelloPage.module.css';
import {Button, Image} from "react-bootstrap";
import {CloseIcon} from "../icons/CloseIcon";
import {EyeIcon} from "../icons/EyeIcon";
import {LockIcon} from "../icons/LockIcon";
import {Input} from "reactstrap";
import SignUpPopUp from "./forms/SignUpPopUp";
import SignInForm from "./forms/SignInForm";

interface Props {
    className?: string;
}



export const WelcomePage: FC<Props> = memo(function Welcome(props = {}) {
    const [isRegisterClicked,setRegisterClicked] = useState(false);
    const [isSingInClicked,setSingInClicked] = useState(false);

    const handlesignupClick = () => {
        setRegisterClicked(!isRegisterClicked);
        setSingInClicked(false);
    };
    const handleloginClick = () => {
        setSingInClicked(!isSingInClicked);
        setRegisterClicked(false)
    };


    return (
        <div className="h-50 w-50 d-flex align-self-center justify-content-center ms-auto me-auto flex-column">
            <SignUpPopUp show={!isRegisterClicked} onClose={() => setRegisterClicked(!isRegisterClicked)}></SignUpPopUp>
            <SignInForm show={!isSingInClicked} onClose={() => setSingInClicked(!isSingInClicked)}></SignInForm>

                    <Image className="w-75 ms-auto me-auto" src={require("../icons/prev.jpg")}></Image>
                    <span className={classes.welcome + " text-center text-5xl "}>Ласкаво просимо</span>
                    <span className={classes.welcomeMessage + " mt-3 mb-5 text-xl font-thin"}>Онлайн тестування для ваших потреб</span>
                    <div className={"d-flex w-2/3 justify-content-center flex-col align-items-center ms-auto me-auto "} >
                        <div onClick={handlesignupClick} className={classes.regBg+ " pb-2 pt-2 w-100 text-2xl rounded-5"} >Реєстрація</div>
                        <div onClick={handleloginClick}className={"w-100 "+classes.loginFont + " mt-2 pb-2 pt-2 w-100 text-2xl rounded-5 " + classes.loginButton}>Вхід</div>
                    </div>

        </div>
    );
});